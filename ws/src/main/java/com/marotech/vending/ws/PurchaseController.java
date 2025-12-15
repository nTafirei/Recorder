package com.marotech.vending.ws;

import com.marotech.vending.api.*;
import com.marotech.vending.integration.BasePaymentService;
import com.marotech.vending.integration.PaymentServiceResolver;
import com.marotech.vending.model.*;
import com.marotech.vending.service.RateCalculatorService;
import com.marotech.vending.service.RepositoryService;
import com.marotech.vending.service.VoucherProcessingService;
import com.marotech.vending.service.WalletService;
import com.marotech.vending.util.Constants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet-purchase")
@Transactional(rollbackFor = Exception.class)
public class PurchaseController extends BaseController {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private VoucherProcessingService voucherProcessingService;
    @Autowired
    private PaymentServiceResolver paymentServiceResolver;
    @Autowired
    private RateCalculatorService rateCalculatorService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Purchase a product", notes = "Purchase a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token is valid. Product has been purchased"),
            @ApiResponse(code = 400, message = "Bad request. Adjust values before retrying again", response = ServiceResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized access", response = ServiceResponse.class),
            @ApiResponse(code = 402, message = "Insufficient funds", response = ServiceResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceResponse.class)
    })
    public ResponseEntity<ServiceResponse> walletPurchase(@Valid @RequestBody WalletPurchaseRequest request) {
        try {
            ValidationResult validation = validateRequest(request);
            if (!validation.isValid()) {
                return ResponseEntity.badRequest().body(validation.getResponse());
            }

            AuthUser authUser = validateUserAuth(request);
            User user = authUser.getUser();
            if (user == null) {
                LOG.error("Null user found for: {}", request);
                ServiceResponse errorResponse = new ServiceResponse();
                errorResponse.setCode(HttpCode.INTERNAL_SERVER_ERROR);
                errorResponse.setMessage("Null user found for request");
                errorResponse.setResponseType(ResponseType.WALLET_RESPONSE);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            validateUserAccount(user);

            validateSufficientFunds(user, request.getAmount());

            Cart cart = buildCart(request, authUser.getMobileNumber());
            ServiceResponse paymentResponse = processPayment(cart);
            if (paymentResponse.getCode() != HttpCode.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentResponse);
            }

            String voucherNumber = extractVoucherNumber(paymentResponse);
            ServiceResponse rateResponse = rateCalculatorService.calculateCommissionsAndTaxes(cart, user, config);
            if (rateResponse.getCode() != HttpCode.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rateResponse);
            }
            cart.setTotalCharges((BigDecimal) rateResponse.getAdditionalInfo().get(Constants.TOTAL));

            User targetUser = repositoryService.fetchUserByMobilePhone(cart.getBeneficiaryMobileNumber());
            if (targetUser == null) {
                LOG.error("Null target user found for: {}", request);
                ServiceResponse errorResponse = new ServiceResponse();
                errorResponse.setCode(HttpCode.INTERNAL_SERVER_ERROR);
                errorResponse.setMessage("Null target user found for request");
                errorResponse.setResponseType(ResponseType.WALLET_RESPONSE);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            ServiceResponse voucherResponse = processVoucher(voucherNumber, cart, targetUser, user);
            if (voucherResponse.getCode() != HttpCode.OK) {
                LOG.error("Failed to process voucher: request:{}, response:{}", request, voucherResponse);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(voucherResponse);
            }
            return ResponseEntity.ok(buildSuccessResponse(voucherResponse, request));
        } catch (UnauthorizedException e) {
            LOG.error("Error: {}", e.getResponse());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getResponse());
        } catch (InsufficientFundsException e) {
            LOG.error("Error: {}", e.getResponse());
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(e.getResponse());
        } catch (ValidationException e) {
            LOG.error("Error: {}", e.getResponse());
            return ResponseEntity.badRequest().body(e.getResponse());
        } catch (Exception e) {
            LOG.error("Unexpected error during wallet purchase", e);
            ServiceResponse errorResponse = new ServiceResponse();
            errorResponse.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            errorResponse.setMessage("An error occurred. Please contact customer service team");
            errorResponse.setResponseType(ResponseType.WALLET_RESPONSE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private ValidationResult validateRequest(WalletPurchaseRequest request) {
        ServiceResponse response = new ServiceResponse();
        response.setResponseType(ResponseType.WALLET_RESPONSE);

        if (StringUtils.isEmpty(request.getProductId())) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("ProductId is required");
            return new ValidationResult(false, response);
        }
        if (StringUtils.isEmpty(request.getPaymentMethod())) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("PaymentMethod is required");
            return new ValidationResult(false, response);
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Valid amount is required");
            return new ValidationResult(false, response);
        }
        if (StringUtils.isEmpty(request.getBeneficiaryMobileNumber())) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Beneficiary mobile number is required");
            return new ValidationResult(false, response);
        }
        if (StringUtils.isEmpty(request.getMobileNumber())) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Buyer mobile number is required");
            return new ValidationResult(false, response);
        }

        Product product = repositoryService.fetchProductById(request.getProductId());
        if (product == null || !product.getIsAvailable()) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Product not available");
            return new ValidationResult(false, response);
        }

        if (product.getRequiresMetre() && StringUtils.isBlank(request.getMeterNumber())) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Meter number is required for this product");
            return new ValidationResult(false, response);
        }

        PaymentMethod paymentMethod = repositoryService.fetchPaymentMethodByName(request.getPaymentMethod());
        if (paymentMethod == null) {
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Payment method not found");
            return new ValidationResult(false, response);
        }

        if (paymentMethod.getPaymentType() != PaymentType.SYSTEM_WALLET) {
            if (StringUtils.isEmpty(request.getAccountName()) ||
                    StringUtils.isEmpty(request.getAccountNumber()) ||
                    StringUtils.isEmpty(request.getAccountPin())) {
                response.setCode(HttpCode.BAD_REQUEST);
                response.setMessage("Account details required for this payment method");
                return new ValidationResult(false, response);
            }
        }

        return new ValidationResult(true, response);
    }

    private AuthUser validateUserAuth(WalletPurchaseRequest request) throws Exception {
        AuthUser authUser = repositoryService.fetchAuthUserByMobileNumber(request.getMobileNumber());
        if (authUser == null) {
            ServiceResponse response = new ServiceResponse();
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("User not found. Please register first");
            response.setResponseType(ResponseType.WALLET_RESPONSE);
            throw new ValidationException(response);
        }

        String encodedPassword = AuthUser.encodedPassword(request.getPassword());
        if (!encodedPassword.equals(authUser.getPassword())) {
            ServiceResponse response = new ServiceResponse();
            response.setCode(HttpCode.UN_AUTHORISED);
            response.setMessage("Invalid credentials");
            response.setResponseType(ResponseType.WALLET_RESPONSE);
            throw new UnauthorizedException(response);
        }
        return authUser;
    }

    private void validateUserAccount(User user) {
        if (user.getAccount() == null) {
            ServiceResponse response = new ServiceResponse();
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            response.setMessage("Account not found for user");
            response.setResponseType(ResponseType.WALLET_RESPONSE);
            throw new ValidationException(response);
        }
    }

    private void validateSufficientFunds(User user, BigDecimal amount) {
        AvailableBalance balance = user.getAccount().getAvailableBalance();
        if (balance.getAmount().compareTo(amount) < 0) {
            ServiceResponse response = new ServiceResponse();
            response.setCode(HttpCode.BAD_REQUEST);
            response.setMessage("Insufficient funds");
            response.setResponseType(ResponseType.WALLET_RESPONSE);
            throw new InsufficientFundsException(response);
        }
    }

    private ServiceResponse processPayment(Cart cart) throws Exception {
        PaymentMethod paymentMethod = cart.getPaymentMethod();
        BasePaymentService paymentService = paymentServiceResolver.resolve(
                applicationContext, paymentMethod);  // Note: applicationContext still needed here

        if (paymentService == null) {
            ServiceResponse response = new ServiceResponse();
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            response.setMessage("Payment service unavailable");
            response.setResponseType(ResponseType.WALLET_RESPONSE);
            return response;
        }
        return paymentService.requestVoucher(cart);
    }

    private String extractVoucherNumber(ServiceResponse response) {
        String voucherNumber = (String) response.getAdditionalInfo().get(Constants.VOUCHER_NUMBER);
        if (StringUtils.isBlank(voucherNumber)) {
            throw new ValidationException("Voucher number not generated");
        }
        return voucherNumber;
    }

    private ServiceResponse processVoucher(String voucherNumber, Cart cart, User targetUser, User buyer) {
        ActivityType activityType = ActivityType.WALLET_PURCHASE_VIA_MOBILE_OK;
        User voucherUser = (targetUser != null && buyer.getIsAgent()) ? targetUser : buyer;
        User agentUser = (buyer.getIsAgent()) ? buyer : null;
        return voucherProcessingService.processVoucher(voucherNumber, cart, voucherUser, activityType, agentUser);
    }

    private ServiceResponse buildSuccessResponse(ServiceResponse voucherResponse, WalletPurchaseRequest request) {
        ServiceResponse success = new ServiceResponse();
        String voucherId = (String) voucherResponse.getAdditionalInfo().get(Constants.VOUCHER_ID);
        Voucher voucher = repositoryService.fetchVoucherById(voucherId);

        Product product = repositoryService.fetchProductById(request.getProductId());
        VoucherDTO dto = new VoucherDTO();
        dto.setAmount(request.getAmount());
        dto.setProduct(product.getName());
        dto.setCurrency(voucher.getCurrency());
        dto.setDate(voucher.getFormattedDateCreated());
        dto.setVoucherNumber(voucher.getVoucherNumber());
        dto.setId(voucherId);
        dto.setMeter(voucher.getMeterNumber());
        dto.setMerchant(product.getMerchant().getName());
        dto.setBuyerMobileNumber(request.getMobileNumber());
        dto.setBeneficiaryMobileNumber(request.getBeneficiaryMobileNumber());

        success.setMessage(product.getName() + " purchased successfully. Voucher: " + voucher.getVoucherNumber());
        success.setResponseType(ResponseType.VOUCHER);
        success.getAdditionalInfo().put(VOUCHER, dto);
        success.getAdditionalInfo().put(TTL, config.getProperty(APP_SESSION_TTL));
        return success;
    }

    private Cart buildCart(WalletPurchaseRequest request, String buyerMobileNumber) {
        Product product = repositoryService.fetchProductById(request.getProductId());
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setAmount(request.getAmount());
        cart.setPin(request.getAccountPin());
        cart.setCurrency(request.getCurrency());
        cart.setBuyerMobileNumber(buyerMobileNumber);
        cart.setMeterNumber(request.getMeterNumber());
        cart.setAccountName(request.getAccountName());
        cart.setCardNumber(request.getAccountNumber());
        cart.setBeneficiaryMobileNumber(request.getBeneficiaryMobileNumber());
        cart.setPaymentMethod(repositoryService.fetchPaymentMethodByName(request.getPaymentMethod()));
        return cart;
    }

    // Custom exceptions for proper error handling
    class ValidationException extends RuntimeException {
        private final ServiceResponse response;

        public ValidationException(String message) {
            super(message);
            this.response = createErrorResponse(HttpCode.BAD_REQUEST, message);
        }

        public ValidationException(ServiceResponse response) {
            super(response.getMessage());
            this.response = response;
        }

        public ServiceResponse getResponse() {
            return response;
        }

        ServiceResponse createErrorResponse(int code, String message) {
            ServiceResponse resp = new ServiceResponse();
            resp.setCode(code);
            resp.setMessage(message);
            resp.setResponseType(ResponseType.WALLET_RESPONSE);
            return resp;
        }
    }

    class UnauthorizedException extends ValidationException {
        public UnauthorizedException(ServiceResponse response) {
            super(response);
        }

        public UnauthorizedException(String message) {
            super(message);
            createErrorResponse(HttpCode.UN_AUTHORISED, message);
        }
    }

    class InsufficientFundsException extends ValidationException {
        public InsufficientFundsException(ServiceResponse response) {
            super(response);
        }

        public InsufficientFundsException(String message) {
            super(message);
            createErrorResponse(HttpCode.BAD_REQUEST, message);
        }
    }

    class ValidationResult {
        private final boolean valid;
        private final ServiceResponse response;

        public ValidationResult(boolean valid, ServiceResponse response) {
            this.valid = valid;
            this.response = response;
        }

        public boolean isValid() {
            return valid;
        }

        public ServiceResponse getResponse() {
            return response;
        }

    }

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseController.class);
}