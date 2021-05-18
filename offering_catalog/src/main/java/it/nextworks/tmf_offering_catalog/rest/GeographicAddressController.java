package it.nextworks.tmf_offering_catalog.rest;

import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicSubAddress;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressInterface;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import it.nextworks.tmf_offering_catalog.services.GeographicSubAddressService;
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

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    @Autowired
    private GeographicAddressService geographicAddressService;

    @Autowired
    private GeographicSubAddressService geographicSubAddressService;

    @ApiOperation(value = "List or find GeographicAddress objects", nickname = "listGeographicAddress",
            notes = "This operation list or find GeographicAddress entities", response = GeographicAddress.class,
            responseContainer = "List", authorizations = {
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
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicAddress(@ApiParam(value = "Comma-separated properties to be provided in response")
                          @Valid @RequestParam(value = "fields", required = false) String fields,
                          @ApiParam(value = "Requested index for start of resources to be provided in response")
                          @Valid @RequestParam(value = "offset", required = false) Integer offset,
                          @ApiParam(value = "Requested number of resources to be provided in response")
                          @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressService.list());
    }

    @ApiOperation(value = "List or find GeographicSubAddress objects", nickname = "listGeographicSubAddress",
            notes = "This operation list or find GeographicSubAddress entities", response = GeographicSubAddress.class,
            responseContainer = "List", authorizations = {
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
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{geographicAddressId}/geographicSubAddress",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicSubAddress(@ApiParam(value = "Identifier of the parent GeographicAddress entity", required = true)
                             @PathVariable("geographicAddressId") String geographicAddressId,
                             @ApiParam(value = "Comma-separated properties to be provided in response")
                             @Valid @RequestParam(value = "fields", required = false) String fields,
                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                             @Valid @RequestParam(value = "offset", required = false) Integer offset,
                             @ApiParam(value = "Requested number of resources to be provided in response")
                             @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(geographicSubAddressService.list());
    }

    @ApiOperation(value = "Retrieves a GeographicAddress by ID", nickname = "retrieveGeographicAddress",
            notes = "This operation retrieves a GeographicAddress entity. Attribute selection is enabled for all first level attributes.", response = GeographicAddress.class,
            authorizations = {
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
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> retrieveGeographicAddress(@ApiParam(value = "Identifier of the GeographicAddress", required = true)
                              @PathVariable("id") String id,
                              @ApiParam(value = "Comma-separated properties to provide in response")
                              @Valid @RequestParam(value = "fields", required = false) String fields) {
        log.info("Web-Server: Received request to retrieve Geographic Address with id " + id + ".");

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        GeographicAddress geographicAddress;
        try {
            geographicAddress = geographicAddressService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }
        log.info("Web-Server: Geographic Address " + id + " retrieved.");
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddress);
    }

    @ApiOperation(value = "Retrieves a GeographicSubAddress by ID", nickname = "retrieveGeographicSubAddress",
            notes = "This operation retrieves a GeographicSubAddress entity. Attribute selection is enabled for all first level attributes.", response = GeographicSubAddress.class,
            authorizations = {
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
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/{geographicAddressId}/geographicSubAddress/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> retrieveGeographicSubAddress(@ApiParam(value = "Identifier of the parent GeographicAddress entity", required = true)
                                 @PathVariable("geographicAddressId") String geographicAddressId,
                                 @ApiParam(value = "Identifier of the GeographicSubAddress", required = true)
                                 @PathVariable("id") String id,
                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                 @Valid @RequestParam(value = "fields", required = false) String fields) {
        log.info("Web-Server: Received request to retrieve Geographic Sub Address with id " + id + ".");

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        GeographicSubAddress geographicSubAddress;
        try {
            geographicSubAddress = geographicSubAddressService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }
        log.info("Web-Server: Geographic Sub Address " + id + " retrieved.");
        return ResponseEntity.status(HttpStatus.OK).body(geographicSubAddress);
    }

}
