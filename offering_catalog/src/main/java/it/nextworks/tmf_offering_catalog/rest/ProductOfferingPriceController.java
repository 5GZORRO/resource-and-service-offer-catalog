package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOfferingPriceInterface;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingPriceService;
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

@RestController
public class ProductOfferingPriceController implements ProductOfferingPriceInterface {

    private final static Logger log = LoggerFactory.getLogger(ProductOfferingPriceController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @ApiOperation(value = "Creates a ProductOfferingPrice", nickname = "createProductOfferingPrice",
            notes = "This operation creates a ProductOfferingPrice entity.",
            response = ProductOfferingPrice.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = ProductOfferingPrice.class),
            @ApiResponse(code = 201, message = "Created", response = ProductOfferingPrice.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found"),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingPrice",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createProductOfferingPrice(@ApiParam(value = "The ProductOfferingPrice to be created", required = true )
                               @Valid @RequestBody ProductOfferingPriceCreate productOfferingPrice) {

        log.info("Web-Server: Received request to create a Product Offering Price.");

        if(productOfferingPrice == null) {
            log.error("Web-Server: Invalid request body (productOfferingPrice) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOfferingPrice) received."));
        }

        ProductOfferingPrice pop = productOfferingPriceService.create(productOfferingPrice);

        log.info("Web-Server: Product Offering Price created with id " + pop.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(pop);
    }

    @ApiOperation(value = "Deletes a ProductOfferingPrice", nickname = "deleteProductOfferingPrice",
            notes = "This operation deletes a ProductOfferingPrice entity.", authorizations = {
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
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingPrice/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteProductOfferingPrice(@ApiParam(value = "Identifier of the ProductOfferingPrice", required = true)
                               @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Product Offering Price with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            productOfferingPriceService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering Price " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ProductOfferingPrice objects", nickname = "listProductOfferingPrice",
            notes = "This operation list or find ProductOfferingPrice entities", response = ProductOfferingPrice.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOfferingPrice.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingPrice",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listProductOfferingPrice(@ApiParam(value = "Comma-separated properties to be provided in response")
                             @Valid @RequestParam(value = "fields", required = false) String fields,
                             @ApiParam(value = "Requested number of resources to be provided in response")
                             @Valid @RequestParam(value = "limit", required = false) Integer limit,
                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                             @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.status(HttpStatus.OK).body(productOfferingPriceService.list());
    }

    @ApiOperation(value = "Updates partially a ProductOfferingPrice", nickname = "patchProductOfferingPrice",
            notes = "This operation updates partially a ProductOfferingPrice entity.",
            response = ProductOfferingPrice.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ProductOfferingPrice.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingPrice/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchProductOfferingPrice(@ApiParam(value = "Identifier of the ProductOfferingPrice", required = true)
                              @PathVariable("id") String id,
                              @ApiParam(value = "The ProductOfferingPrice to be updated", required = true )
                              @Valid @RequestBody ProductOfferingPriceUpdate productOfferingPrice) {

        log.info("Web-Server: Received request to patch Product Offering Price with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(productOfferingPrice == null) {
            log.error("Web-Server: Invalid request body (productOfferingPrice) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOfferingPrice) received."));
        }

        ProductOfferingPrice pop;
        try {
            pop = productOfferingPriceService.patch(id, productOfferingPrice,
                    OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering Price " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(pop);
    }

    @ApiOperation(value = "Retrieves a ProductOfferingPrice by ID", nickname = "retrieveProductOfferingPrice",
            notes = "This operation retrieves a ProductOfferingPrice entity. Attribute selection is enabled for all first level attributes.",
            response = ProductOfferingPrice.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOfferingPrice.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingPrice/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveProductOfferingPrice(@ApiParam(value = "Identifier of the ProductOfferingPrice", required = true)
                                 @PathVariable("id") String id,
                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                 @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Product Offering Price with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOfferingPrice pop;
        try {
            pop = productOfferingPriceService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering Price " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(pop);
    }
}
