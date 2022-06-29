package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOrderStatusInterface;
import it.nextworks.tmf_offering_catalog.services.ProductOrderStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductOrderStatusController implements ProductOrderStatusInterface {

    public class Status {

        @JsonProperty("id")
        private final String id;

        @JsonProperty("did")
        private final String did;

        @JsonProperty("status")
        private final String status;

        @JsonProperty("instanceId")
        private final String instanceId;

        @JsonProperty("description")
        private final String description;

        @JsonCreator
        public Status(@JsonProperty("id") String id,
                      @JsonProperty("did") String did,
                      @JsonProperty("status") String status,
                      @JsonProperty("instanceId") String instanceId,
                      @JsonProperty("description") String description) {
            this.id = id;
            this.did = did;
            this.status = status;
            this.instanceId = instanceId;
            this.description = description;
        }
    }

    private final static Logger log = LoggerFactory.getLogger(ProductOrderStatusController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private ProductOrderStatusService productOrderStatusService;

    @Autowired
    public ProductOrderStatusController(ObjectMapper objectMapper,
                                        HttpServletRequest request,
                                        ProductOrderStatusService productOrderStatusService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.productOrderStatusService = productOrderStatusService;
    }

    @Override
    @ApiOperation(value = "Retrieve a Product Order Status given an id", nickname = "retrieveProductOrderStatus",
            notes = "This operation retrieve a Product Order Status given an id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOrderStatusController.Status.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @RequestMapping(value = "/productOrderingManagement/v4/productOrderStatus/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveProductOrderStatus(@ApiParam(value = "Identifier of the ProductOrderStatus", required = true)
                               @PathVariable("id") String id) {

        log.info("Web-Server: Received request to retrieve Product Order Status with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOrderStatus pos;
        try {
            pos = productOrderStatusService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        Status s = new Status(pos.getCatalogId(), pos.getDid(), pos.getStatus().name(),
                pos.getInstanceId(), pos.getStatus().toString());

        log.info("Web-Server: Product Order Status " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @Override
    @ApiOperation(value = "Update a Product Order Status given an id", nickname = "updateProductOrderStatus",
            notes = "This operation update a Product Order Status given an id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @RequestMapping(value = "/productOrderingManagement/v4/updateProductOrderStatus/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    public ResponseEntity<?>
    updateProductOrderStatus(@ApiParam(value = "Identifier of the ProductOrderStatus", required = true)
                             @PathVariable("id") String id,
                             @ApiParam(value = "Instance ID of this Product Order", required = true)
                             @RequestParam("instanceId") String instanceId) {

        log.info("Web-Server: Received request to update Product Order Status with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            productOrderStatusService.updateProductOrderStatus(id, instanceId);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Order Status " + id + " updated.");

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
