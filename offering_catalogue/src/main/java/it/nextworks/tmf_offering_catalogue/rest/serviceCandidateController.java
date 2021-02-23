package it.nextworks.tmf_offering_catalogue.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalogue.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidate;
import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidateCreate;
import it.nextworks.tmf_offering_catalogue.information_models.service.ServiceCandidateUpdate;
import it.nextworks.tmf_offering_catalogue.interfaces.ServiceCandidateInterface;
import it.nextworks.tmf_offering_catalogue.services.ServiceCandidateService;
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
public class serviceCandidateController implements ServiceCandidateInterface {

    private final static Logger log = LoggerFactory.getLogger(ResourceCandidateController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ServiceCandidateService serviceCandidateService;

    @Autowired
    public serviceCandidateController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ServiceCandidate", nickname = "createServiceCandidate",
            notes = "This operation creates a ServiceCandidate entity.", response = ServiceCandidate.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceCandidate.class),
            @ApiResponse(code = 201, message = "Created", response = ServiceCandidate.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCandidate",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    public ResponseEntity<ServiceCandidate> createServiceCandidate(@ApiParam(value = "The ServiceCandidate to be created",
            required = true ) @Valid @RequestBody ServiceCandidateCreate serviceCandidate) {

        log.info("Web-Server: Received request to create a Service Specification.");

        if(serviceCandidate == null) {
            log.error("Web-Server: Invalid request body (serviceCandidate) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceCandidate sc = serviceCandidateService.create(serviceCandidate);

        log.info("Web-Server: Service Candidate created with id " + sc.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(sc);
    }

    @ApiOperation(value = "Deletes a ServiceCandidate", nickname = "deleteServiceCandidate",
            notes = "This operation deletes a ServiceCandidate entity.", authorizations = {
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
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCandidate/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteServiceCandidate(@ApiParam(value = "Identifier of the ServiceCandidate",
            required = true) @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Service Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            serviceCandidateService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Candidate " + id + " deleted");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ServiceCandidate objects", nickname = "listServiceCandidate",
            notes = "This operation list or find ServiceCandidate entities", response = ServiceCandidate.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceCandidate.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCandidate",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ServiceCandidate>>
    listServiceCandidate(@ApiParam(value = "Comma-separated properties to be provided in response")
                         @Valid @RequestParam(value = "fields", required = false) String fields,
                         @ApiParam(value = "Requested number of resources to be provided in response")
                         @Valid @RequestParam(value = "limit", required = false) Integer limit,
                         @ApiParam(value = "Requested index for start of resources to be provided in response")
                         @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceCandidateService.list());
    }

    @ApiOperation(value = "Updates partially a ServiceCandidate", nickname = "patchServiceCandidate",
            notes = "This operation updates partially a ServiceCandidate entity.",
            response = ServiceCandidate.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ServiceCandidate.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCandidate/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<ServiceCandidate>
    patchServiceCandidate(@ApiParam(value = "Identifier of the ServiceCandidate",required = true)
                          @PathVariable("id") String id,
                          @ApiParam(value = "The ServiceCandidate to be updated" ,required = true )
                          @Valid @RequestBody ServiceCandidateUpdate serviceCandidate) {

        log.info("Web-Server: Received request to patch Service Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(serviceCandidate == null) {
            log.error("Web-Server: Invalid request body (serviceCandidate) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceCandidate sc;
        try {
            sc = serviceCandidateService.patch(id, serviceCandidate);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Candidate " + id + " patched.");

        return ResponseEntity.status(HttpStatus.CREATED).body(sc);
    }

    @ApiOperation(value = "Retrieves a ServiceCandidate by ID", nickname = "retrieveServiceCandidate",
            notes = "This operation retrieves a ServiceCandidate entity. Attribute selection is enabled for all first level attributes.",
            response = ServiceCandidate.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceCandidate.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCandidate/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<ServiceCandidate>
    retrieveServiceCandidate(@ApiParam(value = "Identifier of the ServiceCandidate", required = true)
                             @PathVariable("id") String id,
                             @ApiParam(value = "Comma-separated properties to provide in response")
                             @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Service Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ServiceCandidate sc;
        try {
            sc = serviceCandidateService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Service Candidate " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }
}
