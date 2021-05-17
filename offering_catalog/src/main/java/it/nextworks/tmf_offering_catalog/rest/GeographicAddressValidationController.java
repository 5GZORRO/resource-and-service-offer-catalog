package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressValidationInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GeographicAddressValidationController implements GeographicAddressValidationInterface {

    private final static Logger log = LoggerFactory.getLogger(GeographicAddressValidationController.class);

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
                                                               @Valid @RequestBody GeographicAddressValidationCreate geographicAddressValidation) {
        return null;
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
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicAddressValidation(@ApiParam(value = "Comma-separated properties to be provided in response")
                                                             @Valid @RequestParam(value = "fields", required = false) String fields,
                                                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                                                             @Valid @RequestParam(value = "offset", required = false) Integer offset,
                                                             @ApiParam(value = "Requested number of resources to be provided in response")
                                                             @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return null;
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
                                                              @Valid @RequestBody GeographicAddressValidationUpdate geographicAddressValidation) {
        return null;
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
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> retrieveGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                                 @PathVariable("id") String id,
                                                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                                                 @Valid @RequestParam(value = "fields", required = false) String fields) {
        return null;
    }

}
