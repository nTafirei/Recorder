package com.marotech.vending.ws;

import com.marotech.vending.api.*;
import com.marotech.vending.model.*;
import com.marotech.vending.service.RepositoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController extends BaseController {


    @Autowired
    private RepositoryService repositoryService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches account balance from system",
            notes = "Fetches account balance from system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token is valid. Balance is included"),
            @ApiResponse(code = 400, message = "Bad request. Adjust values before retrying again", response =
                    ServiceResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceResponse.class)
    })
    public ResponseEntity<ServiceResponse>
    getBalance(@RequestBody BalanceRequest request) {
        ServiceResponse response = new ServiceResponse();
        response.setResponseType(ResponseType.BALANCE);
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
                LOG.error("Error: Specified token has expired. Please log in again: " + request);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            response.setToken(appSession.getToken());
            AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(appSession.getMobileNumber());
            User user = authUser.getUser();
            chargerForService(user, response, request);

            if (response.getCode() != HttpCode.OK) {
                return ResponseEntity.status(response.getCode()).body(response);
            }

            BalanceDTO dto = new BalanceDTO();
            dto.setCurrency(user.getAccount().
                    getAvailableBalance().getCurrency());
            dto.setAmount(user.getAccount().getAvailableBalance().getAmount());
            response.getAdditionalInfo().put(BALANCE, GSON.toJson(dto));
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

    private void chargerForService(User user, ServiceResponse response, BalanceRequest request) {
        List<ChargeRate> chargeRates = repositoryService.
                fetchChargeRatesForDateAndType(LocalDate.now(),
                        TransactionType.BALANCE_INQUIRY);
        if (chargeRates.isEmpty()) {
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            LOG.error("Could not find charge rates for BALANCE_INQUIRY in order to service " +
                    request.getToken());
            response.setMessage("An error occurred in our system. Please contact customer service team");
            return;
        }
        //There is only a single rate for balance inquiry
        ChargeRate chargeRate = chargeRates.get(0);
        if (chargeRate.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        AvailableBalance balance = user.getAccount().getAvailableBalance();

        if (balance.getAmount()
                .subtract(chargeRate.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Insufficient funds. Cannot provide this service");
            return;
        }

        BigDecimal amount = balance.getAmount().subtract(chargeRate.getAmount()).
                setScale(2, RoundingMode.HALF_EVEN);
        balance.setAmount(amount);
        repositoryService.save(balance);
        if (shouldAudit()) {
            Activity activity = new Activity();
            activity.setTitle("Balance inquiry charge for " +
                    user.getMobileNumber());
            activity.setActivityType(ActivityType.BALANCE_INQUIRY);
            activity.setActor(user);
            activity.setAmount(chargeRate.getAmount());
            activity.setChargeRate(chargeRate);
            repositoryService.save(activity);
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(BalanceController.class);
}
