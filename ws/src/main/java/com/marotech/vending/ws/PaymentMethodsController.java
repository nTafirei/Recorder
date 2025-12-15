package com.marotech.vending.ws;

import com.marotech.vending.api.HttpCode;
import com.marotech.vending.api.PaymentMethodDTO;
import com.marotech.vending.api.ResponseType;
import com.marotech.vending.api.ServiceResponse;
import com.marotech.vending.model.PaymentMethod;
import com.marotech.vending.model.Platform;
import com.marotech.vending.service.RepositoryService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodsController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches payment methods from the system",
            notes = "Fetches payment methods from the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment methods data is included"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceResponse.class)
    })
    public ResponseEntity<ServiceResponse>
    fetchPaymentMethods() {
        ServiceResponse response = new ServiceResponse();
        response.setResponseType(ResponseType.PAYMENT_METHODS);
        try {
            String str = config.getProperty(Constants.ENV_DEPLOYMENT);
            Platform platform = Platform.valueOf(str);
            List<PaymentMethod> methods = repositoryService.
                    fetchImplementedNonMerchantPaymentMethodsForPlatform(platform);
            List<PaymentMethodDTO>list = new ArrayList<>();

            for(PaymentMethod paymentMethod : methods){
                PaymentMethodDTO dto = new PaymentMethodDTO();
                dto.setName(paymentMethod.getName());
                dto.setPaymentType(paymentMethod.getPaymentType());
                list.add(dto);
            }
            response.getAdditionalInfo().put(PAYMENT_METHODS, GSON.toJson(list));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            response.setMessage("An error occurred in the system. Please contact our customer service team");
            LOG.error("Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodsController.class);
}
