package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOrderUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOrderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ProductOrderController implements ProductOrderInterface {

    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    @ApiOperation(value = "Creates a ProductOrder", nickname = "createProductOrder", notes = "This operation creates a ProductOrder entity.", response = ProductOrder.class, tags={ "productOrder", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ProductOrder.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productOrder",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    public ResponseEntity<?> createProductOrder(ProductOrderCreate productOrderCreate) {
        return null;
    }

    public ResponseEntity<?> deleteProductOrder(String id) {
        return null;
    }

    public ResponseEntity<?> listProductOrder(String fields, Integer limit, Integer offset) {
        return null;
    }

    public ResponseEntity<?> patchProductOrder(String id, ProductOrderUpdate productOrder) {
        return null;
    }

    public ResponseEntity<?> retrieveProductOrder(String id, String fields) {
        return null;
    }

}
