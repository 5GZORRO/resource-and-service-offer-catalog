package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicSubAddress;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressInterface;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GeographicAddressController implements GeographicAddressInterface {

    private final static Logger log = LoggerFactory.getLogger(GeographicAddressController.class);

    @Autowired
    private GeographicAddressService geographicAddressService;

    @ApiOperation(value = "List or find GeographicAddress objects", nickname = "listGeographicAddress",
            notes = "This operation list or find GeographicAddress entities", response = GeographicAddress.class,
            responseContainer = "List", tags = {"geographicAddress"}, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddress.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listGeographicAddress(@ApiParam(value = "Comma-separated properties to be provided in response")
                          @Valid @RequestParam(value = "fields", required = false) String fields,
                          @ApiParam(value = "Requested index for start of resources to be provided in response")
                          @Valid @RequestParam(value = "offset", required = false) Integer offset,
                          @ApiParam(value = "Requested number of resources to be provided in response")
                          @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressService.list());
    }

    @ApiOperation(value = "List or find GeographicSubAddress objects", nickname = "listGeographicSubAddress",
            notes = "This operation list or find GeographicSubAddress entities", response = GeographicSubAddress.class,
            responseContainer = "List", tags={ "geographicSubAddress"}, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicSubAddress.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{geographicAddressId}/geographicSubAddress",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listGeographicSubAddress(@ApiParam(value = "Identifier of the parent GeographicAddress entity",required=true)
                             @PathVariable("geographicAddressId") String geographicAddressId,
                             @ApiParam(value = "Comma-separated properties to be provided in response")
                             @Valid @RequestParam(value = "fields", required = false) String fields,
                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                             @Valid @RequestParam(value = "offset", required = false) Integer offset,
                             @ApiParam(value = "Requested number of resources to be provided in response")
                             @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return null;
    }

    @ApiOperation(value = "Retrieves a GeographicAddress by ID", nickname = "retrieveGeographicAddress",
            notes = "This operation retrieves a GeographicAddress entity. Attribute selection is enabled for all first level attributes.", response = GeographicAddress.class,
            tags={ "geographicAddress", }, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddress.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveGeographicAddress(@ApiParam(value = "Identifier of the GeographicAddress",required=true)
                              @PathVariable("id") String id,
                              @ApiParam(value = "Comma-separated properties to provide in response")
                              @Valid @RequestParam(value = "fields", required = false) String fields) {
        return null;
    }

    @ApiOperation(value = "Retrieves a GeographicSubAddress by ID", nickname = "retrieveGeographicSubAddress",
            notes = "This operation retrieves a GeographicSubAddress entity. Attribute selection is enabled for all first level attributes.", response = GeographicSubAddress.class,
            tags={ "geographicSubAddress", }, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicSubAddress.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{geographicAddressId}/geographicSubAddress/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveGeographicSubAddress(@ApiParam(value = "Identifier of the parent GeographicAddress entity",required=true)
                                 @PathVariable("geographicAddressId") String geographicAddressId,
                                 @ApiParam(value = "Identifier of the GeographicSubAddress",required=true)
                                 @PathVariable("id") String id,
                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                 @Valid @RequestParam(value = "fields", required = false) String fields) {
        return null;
    }

}
