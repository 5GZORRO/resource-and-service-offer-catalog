package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.*;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOfferingInterface;
import it.nextworks.tmf_offering_catalog.services.CommunicationService;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ProductOfferingController implements ProductOfferingInterface {

    private final static Logger log = LoggerFactory.getLogger(ProductOfferingController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    public ProductOfferingController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ProductOffering", nickname = "createProductOffering",
            notes = "This operation creates a ProductOffering entity.", response = ProductOffering.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = ProductOffering.class),
            @ApiResponse(code = 201, message = "Created", response = ProductOffering.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrMsg.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = ErrMsg.class )})
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createProductOffering(@ApiParam(value = "The ProductOffering to be created", required = true )
                          @Valid @RequestBody ProductOfferingCreate productOffering) {

        log.info("Web-Server: Received request to create a Product Offering.");

        if(productOffering == null) {
            log.error("Web-Server: Invalid request body (productOffering) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOffering) received."));
        }

        ProductOffering po;
        try {
            po = productOfferingService.create(productOffering);
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

        log.info("Web-Server: Product Offering created with id " + po.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(po);
    }

    @ApiOperation(value = "DID creation web-hook", nickname = "handleDIDReceiving",
            notes = "Handle the ID&P callback in order to receive the DID; then," +
                    "starts a thread to publish and classify the ProductOffering with the requested DID.",
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
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/did/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?>
    handleDIDReceiving(@ApiParam(value = "Identifier of the ProductOffering", required = true)
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
            communicationService.handleDIDReceiving(id, did.asText());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        } catch (DIDAlreadyRequestedForProductException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: DID received successfully from ID&P.");

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes a ProductOffering", nickname = "deleteProductOffering",
            notes = "This operation deletes a ProductOffering entity.", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrMsg.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrMsg.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                          @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            productOfferingService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        } catch (ProductOfferingInPublicationException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrMsg(e.getMessage()));
        } catch (IOException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        } catch (ProductOfferingDeleteScLCMException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ProductOffering objects", nickname = "listProductOffering",
            notes = "This operation list or find ProductOffering entities", response = ProductOffering.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOffering.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listProductOffering(@ApiParam(value = "Comma-separated properties to be provided in response")
                        @Valid @RequestParam(value = "fields", required = false) String fields,
                        @ApiParam(value = "Requested number of resources to be provided in response")
                        @Valid @RequestParam(value = "limit", required = false) Integer limit,
                        @ApiParam(value = "Requested index for start of resources to be provided in response")
                        @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.status(HttpStatus.OK).body(productOfferingService.list());
    }

    @ApiOperation(value = "List ProductOffering objects using filters", nickname = "FilteredListProductOffering",
            notes = "This operation list or find ProductOffering entities using filters", response = ProductOffering.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOffering.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/filtered",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    FilteredListProductOffering(Filter filter) {

        if(filter == null)
            return ResponseEntity.status(HttpStatus.OK).body(productOfferingService.list());

        Float minPrice = filter.getMinPrice();
        Float maxPrice = filter.getMaxPrice();
        String currency = filter.getCurrency();
        if(minPrice != null && maxPrice != null) {
            if(minPrice.compareTo(maxPrice) > 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrMsg("The minimum price specified is greater then the maximum one."));

            if(currency == null || !currency.isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Currency not specified."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(productOfferingService.filteredList(filter));
    }

    @ApiOperation(value = "Updates partially a ProductOffering", nickname = "patchProductOffering",
            notes = "This operation updates partially a ProductOffering entity.", response = ProductOffering.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ProductOffering.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                         @PathVariable("id") String id,
                         @ApiParam(value = "The ProductOffering to be updated", required = true )
                         @Valid @RequestBody ProductOfferingUpdate productOffering) {

        log.info("Web-Server: Received request to patch Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(productOffering == null) {
            log.error("Web-Server: Invalid request body (productOffering) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOffering) received."));
        }

        ProductOffering po;
        try {
            po = productOfferingService.patch(id, productOffering,
                    OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(po);
    }

    @ApiOperation(value = "Retrieves a ProductOffering by ID", nickname = "retrieveProductOffering",
            notes = "This operation retrieves a ProductOffering entity. Attribute selection is enabled for all first level attributes.",
            response = ProductOffering.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOffering.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                            @PathVariable("id") String id,
                            @ApiParam(value = "Comma-separated properties to provide in response")
                            @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOffering po;
        try {
            po = productOfferingService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(po);
    }
}
