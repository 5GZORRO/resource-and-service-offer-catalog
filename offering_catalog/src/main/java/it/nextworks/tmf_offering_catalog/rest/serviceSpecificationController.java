package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecificationUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ServiceSpecificationInterface;
import it.nextworks.tmf_offering_catalog.services.ServiceSpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class serviceSpecificationController implements ServiceSpecificationInterface {

    private final static Logger log = LoggerFactory.getLogger(ResourceCandidateController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ServiceSpecificationService serviceSpecificationService;

    @Autowired
    public serviceSpecificationController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ServiceSpecification", nickname = "createServiceSpecification", notes = "This operation creates a ServiceSpecification entity.", response = ServiceSpecification.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceSpecification.class),
            @ApiResponse(code = 201, message = "Created", response = ServiceSpecification.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceSpecification",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    public ResponseEntity<ServiceSpecification>
    createServiceSpecification(@ApiParam(value = "The ServiceSpecification to be created", required = true )
                               @Valid @RequestBody ServiceSpecificationCreate serviceSpecification) {

        log.info("Web-Server: Received request to create a Service Specification.");

        if(serviceSpecification == null) {
            log.error("Web-Server: Invalid request body (serviceSpecification) received'");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceSpecification ss = serviceSpecificationService.create(serviceSpecification);

        log.info("Web-Server: Service Specification created with id " + ss.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(ss);
    }

    @ApiOperation(value = "Deletes a ServiceSpecification", nickname = "deleteServiceSpecification",
            notes = "This operation deletes a ServiceSpecification entity.", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceSpecification/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void>
    deleteServiceSpecification(@ApiParam(value = "Identifier of the ServiceSpecification", required = true)
                               @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Service Specification with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            serviceSpecificationService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Specification " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ServiceSpecification objects", nickname = "listServiceSpecification",
            notes = "This operation list or find ServiceSpecification entities", response = ServiceSpecification.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceSpecification.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceSpecification",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ServiceSpecification>>
    listServiceSpecification(@ApiParam(value = "Comma-separated properties to be provided in response")
                             @Valid @RequestParam(value = "fields", required = false) String fields,
                             @ApiParam(value = "Requested number of resources to be provided in response")
                             @Valid @RequestParam(value = "limit", required = false) Integer limit,
                             @ApiParam(value = "Requested index for start of resources to be provided in response")
                             @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceSpecificationService.list());
    }

    @ApiOperation(value = "Updates partially a ServiceSpecification", nickname = "patchServiceSpecification",
            notes = "This operation updates partially a ServiceSpecification entity.",
            response = ServiceSpecification.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ServiceSpecification.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceSpecification/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<ServiceSpecification>
    patchServiceSpecification(@ApiParam(value = "Identifier of the ServiceSpecification",required = true)
                              @PathVariable("id") String id,
                              @ApiParam(value = "The ServiceSpecification to be updated" ,required = true )
                              @Valid @RequestBody ServiceSpecificationUpdate serviceSpecification) {

        log.info("Web-Server: Received request to patch Service Specification with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(serviceSpecification == null) {
            log.error("Web-Server: Invalid request body (serviceSpecification) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceSpecification ss;
        try {
            ss = serviceSpecificationService.patch(id, serviceSpecification);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Specification " + id + " patched.");

        return ResponseEntity.status(HttpStatus.CREATED).body(ss);
    }

    @ApiOperation(value = "Retrieves a ServiceSpecification by ID", nickname = "retrieveServiceSpecification",
            notes = "This operation retrieves a ServiceSpecification entity. Attribute selection is enabled for all first level attributes.",
            response = ServiceSpecification.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceSpecification.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceSpecification/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<ServiceSpecification>
    retrieveServiceSpecification(@ApiParam(value = "Identifier of the ServiceSpecification", required = true)
                                 @PathVariable("id") String id,
                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                 @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Service Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceSpecification ss;
        try {
            ss = serviceSpecificationService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Candidate " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(ss);
    }
}
