package com.marotech.vending.ws;

import com.marotech.vending.api.*;
import com.marotech.vending.model.ActiveStatus;
import com.marotech.vending.model.Product;
import com.marotech.vending.service.RepositoryService;
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

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductsController extends BaseController{


    @Autowired
    private RepositoryService repositoryService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches product list from system",
            notes = "Fetches product list from system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token is valid. Product list is included"),
            @ApiResponse(code = 400, message = "Bad request. Adjust values before retrying again", response =
                    ServiceResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceResponse.class)
    })
    public ResponseEntity<ServiceResponse>
    getProducts(@RequestBody ProductsRequest request) {
        ServiceResponse response = new ServiceResponse();
        response.setResponseType(ResponseType.PRODUCTS);
        try {
            if (request == null) {
                response.setCode(HttpCode.BAD_REQUEST);
                response.setMessage(NULL_REQUEST_FOUND);
                LOG.error("Error: {} ", NULL_REQUEST_FOUND);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<ProductDTO> products = new ArrayList<>();
            List<Product> productList = repositoryService.fetchProductsByActiveStatus(ActiveStatus.ACTIVE);
            for(Product product : productList){
                ProductDTO dto = new ProductDTO();
                dto.setId(product.getId());
                dto.setName(product.getName());
                dto.setRequiresMetre(product.getRequiresMetre());
                dto.setMinPurchase(product.getMinPurchase());
                dto.setMaxPurchase(product.getMaxPurchase());
                dto.setMerchant(product.getMerchant().getName());
                products.add(dto);
            }
            response.getAdditionalInfo().put(PRODUCTS, GSON.toJson(products));
            response.getAdditionalInfo().put(TTL, config.getProperty(APP_SESSION_TTL));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setCode(HttpCode.INTERNAL_SERVER_ERROR);
            response.setMessage("An error occurred in the system. Please contact our customer service team");
            LOG.error("Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);
}
