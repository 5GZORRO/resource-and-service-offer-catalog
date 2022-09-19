package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderUpdate;
import it.nextworks.tmf_offering_catalog.services.ProductOrderCommunicationService;
import it.nextworks.tmf_offering_catalog.services.ProductOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ProductOrderController {
    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductOrderCommunicationService productOrderCommunicationService;

    @Autowired
    public ProductOrderController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.request = request;
    }

    @ApiOperation(value = "Creates a ProductOrder", nickname = "createProductOrder",
            notes = "This operation creates a ProductOrder entity.", response = ProductOrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ProductOrder.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class),})
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

        ProductOrder productOrder;
        try {
            productOrder = productOrderService.create(productOrderCreate);
        } catch (IOException e) {
            log.error("Web-Server: DID request via CommunicationService failed; " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrMsg("DID request via CommunicationService failed; " + e.getMessage()));
        } catch (DIDGenerationRequestException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrMsg(e.getMessage()));
        } catch (StakeholderNotRegisteredException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Order created with id " + productOrder.getId() + ".");
        return ResponseEntity.status(HttpStatus.CREATED).body(productOrder);
    }

    @ApiOperation(value = "DID creation web-hook", nickname = "handleDIDReceiving",
            notes = "Handle the ID&P callback in order to receive the DID; then," +
                    "starts a thread to publish and classify the ProductOrdering with the requested DID.",
            authorizations = {
                    @Authorization(value = "spring_oauth", scopes = {
                            @AuthorizationScope(scope = "read", description = "for read operations"),
                            @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                            @AuthorizationScope(scope = "admin", description = "Access admin API"),
                            @AuthorizationScope(scope = "write", description = "for write operations")})
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrMsg.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder/did/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?>
    handleDIDReceiving(@ApiParam(value = "Identifier of the ProductOrdering", required = true)
                       @PathVariable("id") String id,
                       @ApiParam(value = "The request body containing the DID sent by the ID&P", required = true)
                       @Valid @RequestBody JsonNode jsonNode) {

        log.info("Web-Server: Received request to handle ID&P DID retrieval.");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(jsonNode == null) {
            log.error("Web-Server: Invalid request body (jsonNode) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (jsonNode) received."));
        }

        JsonNode credentialSubject = jsonNode.get("credentialSubject");
        if(credentialSubject == null) {
            log.error("Web-Server: Invalid request body (jsonNode) content received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (jsonNode) content received."));
        }

        JsonNode did = credentialSubject.get("id");
        if(did == null || !did.isTextual()) {
            log.error("Web-Server: Invalid request body (jsonNode) content received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (jsonNode) content received."));
        }

        try {
            productOrderCommunicationService.handleDIDReceiving(id, did.asText());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        } catch (DIDAlreadyRequestedForProductException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        } catch (IOException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: DID received successfully from ID&P.");

        return new ResponseEntity(HttpStatus.OK);
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
        log.info("Web-Server: Received request to retrieve all Product Orders");
        return ResponseEntity.status(HttpStatus.OK).body(productOrderService.list());
    }

    @ApiOperation(value = "Retrieves a ProductOrder by ID", nickname = "retrieveProductOrder",
            notes = "This operation retrieves a ProductOrder entity. Attribute selection is enabled for all first level attributes.", response = ProductOrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOrder.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    ResponseEntity<?> retrieveProductOrder(@ApiParam(value = "Identifier of the ProductOrder", required = true)
                                           @PathVariable("id") String id,
                                           @ApiParam(value = "Comma-separated properties to provide in response")
                                           @Valid @RequestParam(value = "fields", required = false) String fields) {
        log.info("Web-Server: Received request to retrieve Product Order with id " + id + ".");
        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOrder productOrder;
        try {
            productOrder = productOrderService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Order " + id + " retrieved.");
        return ResponseEntity.status(HttpStatus.OK).body(productOrder);
    }

    @ApiOperation(value = "Updates partially a ProductOrder", nickname = "patchProductOrder",
            notes = "This operation updates partially a ProductOrder entity.", response = ProductOrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ProductOrder.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder/{id}",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.PATCH)
    ResponseEntity<?> patchProductOrder(@ApiParam(value = "Identifier of the ProductOrder", required = true)
                                        @PathVariable("id") String id,
                                        @ApiParam(value = "The ProductOrder to be updated", required = true)
                                        @Valid @RequestBody ProductOrderUpdate productOrderUpdate) {

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        return null;
    }

    @ApiOperation(value = "Ends a ProductOrder", nickname = "endProductOrder",
            notes = "This operation ends a ProductOrder entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Ended"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
    @RequestMapping(value = "/productOrderingManagement/v4/productOrder/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.PUT)
    ResponseEntity<?> endProductOrder(@ApiParam(value = "Identifier of the ProductOrder", required = true)
                                         @PathVariable("id") String id) {
        log.info("Web-Server: Received request to end Product Order with id " + id + ".");
        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }
        try {
            productOrderService.end(id);
        } catch (NotExistingEntityException | IOException | ProductOrderDeleteScLCMException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg("Exception occurred during publication to SCLCM: " + e.getMessage()));
        }
        log.info("Web-Server: Product Order " + id + " deleted.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
