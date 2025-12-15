package com.marotech.vending.ws;

import com.marotech.vending.api.*;
import com.marotech.vending.model.*;
import com.marotech.vending.service.RepositoryService;
import com.marotech.vending.service.VoucherProcessingService;
import com.marotech.vending.util.Constants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/redeem-points")
public class RedeemPointsController extends BaseController {


    @Autowired
    private RepositoryService repositoryService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Redeems reward points in system",
            notes = "Redeems reward points in system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token is valid. Reward points are redeemed"),
            @ApiResponse(code = 400, message = "Bad request. Adjust values before retrying again", response =
                    ServiceResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceResponse.class)
    })
    public ResponseEntity<ServiceResponse>
    redeemPoints(@RequestBody RedeemPointsRequest request) {
        ServiceResponse response = new ServiceResponse();
        response.setResponseType(ResponseType.LOYALTY_POINTS);
        try {
            if (request == null) {
                response.setCode(HttpCode.BAD_REQUEST);
                response.setMessage(NULL_REQUEST_FOUND);
                LOG.error("Error: {} ", NULL_REQUEST_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            AppSession appSession = repositoryService.
                    fetchAppSessionByToken(request.getToken());
            if (appSession == null) {
                response.setCode(HttpCode.BAD_REQUEST);
                LOG.error("Error: {} {}", TOKEN_NOT_FOUND, request);
                response.setMessage(TOKEN_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            LocalDateTime updatedTime = appSession.getDateLastUpdated();
            LocalDateTime expiryTime = updatedTime.plusSeconds(config.getIntegerProperty(APP_SESSION_TTL));
            if (LocalDateTime.now().isAfter(expiryTime)) {
                response.setCode(HttpCode.UN_AUTHORISED);
                response.setMessage("Specified token has expired. Please log in again");
                LOG.error("Error: Specified token has expired. Please log in again: {}", request);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            response.setToken(appSession.getToken());
            AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(request.getMobileNumber());

            if (authUser == null) {
                response.setCode(HttpCode.UN_AUTHORISED);
                response.setMessage("Specified user was not found in the system");
                LOG.error("Error: Specified user was not found in the system: {}", request);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            User user = authUser.getUser();

            RewardPoints rewardPoints = repositoryService.fetchRewardPointsByUser(user);
             if(!rewardPoints.getCanRedeem()){
                response.setCode(HttpCode.BAD_REQUEST);
                response.setMessage("Points cannot be redeemed at this time. Please log in again");
                LOG.error("Error: User tried to redeem un-redeemable points: {}", request);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            redeem(user, rewardPoints);
            PointsDTO dto = new PointsDTO();
            dto.setCurrency(user.getAccount().
                    getAvailableBalance().getCurrency());
            RedeemPointsDTO redeemPointsDTO = rewardPoints.calculateRedeemableValues();
            dto.setRedeemableAmount(redeemPointsDTO.getRedeemableAmount());
            dto.setPoints(rewardPoints.getPoints());
            dto.setCanRedeem(false);
            response.getAdditionalInfo().put(LOYALTY_POINTS, GSON.toJson(dto));
            appSession.setDateLastUpdated(LocalDateTime.now());
            response.getAdditionalInfo().put(TTL, config.getProperty(APP_SESSION_TTL));
            repositoryService.save(appSession);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            response.setMessage("An error occurred in the system. Please contact our customer service team");
            LOG.error("Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void redeem(User user, RewardPoints rewardPoints) {

        RedeemPointsDTO dto = rewardPoints.calculateRedeemableValues();
        BigDecimal redemptionAmount = dto.getRedeemableAmount();
        Account userAccount = user.getAccount();
        Account systemAccount = repositoryService.fetchSystemAccount();
        AvailableBalance systemBalance = systemAccount.getAvailableBalance();
        AvailableBalance userBalance = userAccount.getAvailableBalance();
        BigDecimal systemAmount = systemBalance.getAmount().
                subtract(redemptionAmount).setScale(2, RoundingMode.HALF_EVEN);
        systemBalance.setAmount(systemAmount);
        BigDecimal userAmount = systemBalance.getAmount().
                add(redemptionAmount).setScale(2, RoundingMode.HALF_EVEN);
        userBalance.setAmount(userAmount);
        repositoryService.save(userBalance);
        repositoryService.save(systemBalance);
        rewardPoints.setDateLastRedeemed(LocalDateTime.now());
        rewardPoints.deductPoints(dto.getRedeemablePoints());
        repositoryService.save(rewardPoints);

        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setActivityType(ActivityType.REDEEMED_LOYALTY_POINTS);
            activity.setActor(user);
            activity.setUser(user);
            activity.setTitle(user.getFullName() + " redeemed "
                    + redemptionAmount + " " + getPurchaseCurrency() + " on " + LocalDate.now());
            repositoryService.save(activity);
        }
    }
    private static final Logger LOG = LoggerFactory.getLogger(RedeemPointsController.class);
}
