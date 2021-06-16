package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ProductOrderController {
    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ProductOrder", nickname = "createProductOrder",
            notes = "This operation creates a ProductOrder entity.", response = ProductOrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ProductOrder.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.POST)
    ResponseEntity<?> createProductOrder(@ApiParam(value = "The ProductOrder to be created", required = true)
                                         @Valid @RequestBody ProductOrderCreate productOrderCreate) {
        log.info("Web-Server: Received request to create a Product Order.");

        if (productOrderCreate == null) {
            log.error("Web-Server: Invalid request body (productOrder) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (productOrder) received"));
        }

        ProductOrder productOrder = productOrderService.create(productOrderCreate);

        log.info("Web-Server: Product Order created with id " + productOrder.getId() + ".");
        return ResponseEntity.status(HttpStatus.CREATED).body(productOrder);
    }

    @ApiOperation(value = "List or find ProductOrder objects", nickname = "listProductOrder",
            notes = "This operation list or find ProductOrder entities", response = ProductOrder.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOrder.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    ResponseEntity<?> listProductOrder(@ApiParam(value = "Comma-separated properties to be provided in response")
                                       @Valid @RequestParam(value = "fields", required = false) String fields) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productOrderService.list());
    }

}
