package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationUpdate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressValidationInterface;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import javax.validation.Valid;

@RestController
public class GeographicAddressValidationController implements GeographicAddressValidationInterface {

    private final static Logger log = LoggerFactory.getLogger(GeographicAddressValidationController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    @Autowired
    private GeographicAddressValidationService geographicAddressValidationService;

    @ApiOperation(value = "Creates a GeographicAddressValidation", nickname = "createGeographicAddressValidation",
            notes = "This operation creates a GeographicAddressValidation entity.", response = GeographicAddressValidation.class,
            authorizations = {
                    @Authorization(value = "spring_oauth", scopes = {
                            @AuthorizationScope(scope = "read", description = "for read operations"),
                            @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                            @AuthorizationScope(scope = "admin", description = "Access admin API"),
                            @AuthorizationScope(scope = "write", description = "for write operations")
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createGeographicAddressValidation(@ApiParam(value = "The GeographicAddressValidation to be created", required = true)
                                                               @Valid @RequestBody GeographicAddressValidationCreate geographicAddressValidationCreate) {
        log.info("Web-Server: Received request to create a Geographic Address Validation.");

        if (geographicAddressValidationCreate == null) {
            log.error("Web-Server: Invalid request body (geographicAddressValidation) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicAddressValidation) received"));
        }

        GeographicAddressValidation geographicAddressValidation = geographicAddressValidationService.create(geographicAddressValidationCreate);

        log.info("Web-Server: Geographic Address Validation created with id " + geographicAddressValidation.getId() + ".");
        return ResponseEntity.status(HttpStatus.CREATED).body(geographicAddressValidation);
    }

    @ApiOperation(value = "List or find GeographicAddressValidation objects", nickname = "listGeographicAddressValidation",
            notes = "This operation list or find GeographicAddressValidation entities", response = GeographicAddressValidation.class, responseContainer = "List",
            authorizations = {
                    @Authorization(value = "spring_oauth", scopes = {
                            @AuthorizationScope(scope = "read", description = "for read operations"),
                            @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                            @AuthorizationScope(scope = "admin", description = "Access admin API"),
                            @AuthorizationScope(scope = "write", description = "for write operations")
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddressValidation.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicAddressValidation(@ApiParam(value = "Comma-separated properties to be provided in response")
                                                             @Valid @RequestParam(value = "fields", required = false) String fields,
                                                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                                                             @Valid @RequestParam(value = "offset", required = false) Integer offset,
                                                             @ApiParam(value = "Requested number of resources to be provided in response")
                                                             @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressValidationService.list());
    }

    @ApiOperation(value = "Updates partially a GeographicAddressValidation", nickname = "patchGeographicAddressValidation",
            notes = "This operation updates partially a GeographicAddressValidation entity.", response = GeographicAddressValidation.class,
            authorizations = {
                    @Authorization(value = "spring_oauth", scopes = {
                            @AuthorizationScope(scope = "read", description = "for read operations"),
                            @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                            @AuthorizationScope(scope = "admin", description = "Access admin API"),
                            @AuthorizationScope(scope = "write", description = "for write operations")
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation/{id}",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.PATCH)
    public ResponseEntity<?> patchGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                              @PathVariable("id") String id,
                                                              @ApiParam(value = "The GeographicAddressValidation to be updated", required = true)
                                                              @Valid @RequestBody GeographicAddressValidationUpdate geographicAddressValidationUpdate) {
        log.info("Web-Server: Received request to patch Geographic Address Validation with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(geographicAddressValidationUpdate == null) {
            log.error("Web-Server: Invalid request body (geographicAddressValidation) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicAddressValidation) received."));
        }

        GeographicAddressValidation geographicAddressValidation;
        try {
            geographicAddressValidation = geographicAddressValidationService.patch(id, geographicAddressValidationUpdate, OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Geographic Address Validation " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressValidation);
    }

    @ApiOperation(value = "Retrieves a GeographicAddressValidation by ID", nickname = "retrieveGeographicAddressValidation",
            notes = "This operation retrieves a GeographicAddressValidation entity. Attribute selection is enabled for all first level attributes.", response = GeographicAddressValidation.class,
            authorizations = {
                    @Authorization(value = "spring_oauth", scopes = {
                            @AuthorizationScope(scope = "read", description = "for read operations"),
                            @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                            @AuthorizationScope(scope = "admin", description = "Access admin API"),
                            @AuthorizationScope(scope = "write", description = "for write operations")
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> retrieveGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                                 @PathVariable("id") String id,
                                                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                                                 @Valid @RequestParam(value = "fields", required = false) String fields) {
        log.info("Web-Server: Received request to retrieve Geographic Address Validation with id " + id + ".");

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        GeographicAddressValidation geographicAddressValidation;
        try {
            geographicAddressValidation = geographicAddressValidationService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }
        log.info("Web-Server: Geographic Address Validation " + id + " retrieved.");
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressValidation);
    }

}
