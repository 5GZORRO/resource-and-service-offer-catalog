package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCandidateUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ResourceCandidateInterface;
import it.nextworks.tmf_offering_catalog.services.ResourceCandidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ResourceCandidateController implements ResourceCandidateInterface {

    private final static Logger log = LoggerFactory.getLogger(ResourceCandidateController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ResourceCandidateService resourceCandidateService;

    @Autowired
    public ResourceCandidateController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a 'ResourceCandidate'", nickname = "createResourceCandidate", notes = "",
            response = ResourceCandidate.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = ResourceCandidate.class),
            @ApiResponse(code = 201, message = "Created", response = ResourceCandidate.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized"),
            //@ApiResponse(code = 403, message = "Forbidden"),
            //@ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCandidate",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createResourceCandidate(@ApiParam(value = "The Resource Candidate to be created", required = true)
                            @Valid @RequestBody ResourceCandidateCreate resourceCandidate) {

        log.info("Web-Server: Received request to create a Resource Candidate.");

        if(resourceCandidate == null) {
            log.error("Web-Server: Invalid request body (resourceCandidate) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (resourceCandidate) received."));
        }

        ResourceCandidate rc = resourceCandidateService.create(resourceCandidate);

        log.info("Web-Server: Resource Candidate created with id " + rc.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(rc);
    }

    @ApiOperation(value = "Deletes a 'ResourceCandidate' by Id", nickname = "deleteResourceCandidate", notes = "",
            authorizations = {
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
            //@ApiResponse(code = 401, message = "Unauthorized"),
            //@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCandidate/{id}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteResourceCandidate(@ApiParam(value = "Identifier of the Resource Candidate", required=true)
                            @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Resource Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            resourceCandidateService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Resource Candidate " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find 'ResourceCandidate' objects", nickname = "listResourceCandidate", notes = "",
            response = ResourceCandidate.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceCandidate.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized"),
            //@ApiResponse(code = 403, message = "Forbidden"),
            //@ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCandidate",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listResourceCandidate(@ApiParam(value = "For filtering: The (immediate) base class type of this REST resource")
                          @Valid @RequestParam(value = "@baseType", required = false) String baseType,
                          @ApiParam(value = "For filtering: This field provides a link to the schema describing this REST resource")
                          @Valid @RequestParam(value = "@schemaLocation", required = false) String schemaLocation,
                          @ApiParam(value = "For filtering: Class type of this REST resource")
                          @Valid @RequestParam(value = "@type", required = false) String type,
                          @ApiParam(value = "For filtering: the class type of the category")
                          @Valid @RequestParam(value = "category.@type", required = false) String categoryType,
                          @ApiParam(value = "For filtering: Name of the category")
                          @Valid @RequestParam(value = "category.name", required = false) String categoryName,
                          @ApiParam(value = "For filtering: Category version")
                          @Valid @RequestParam(value = "category.version", required = false) String categoryVersion,
                          @ApiParam(value = "Comma separated properties to display in response")
                          @Valid @RequestParam(value = "fields", required = false) String fields,
                          @ApiParam(value = "For filtering: Date and time of the last update of this REST resource")
                          @Valid @RequestParam(value = "lastUpdate", required = false) OffsetDateTime lastUpdate,
                          @ApiParam(value = "For filtering: Used to indicate the current lifecycle status of the resource candidate.")
                          @Valid @RequestParam(value = "lifecycleStatus", required = false) String lifecycleStatus,
                          @ApiParam(value = "For filtering: Name given to this REST resource")
                          @Valid @RequestParam(value = "name", required = false) String name,
                          @ApiParam(value = "For filtering: The (class) type of the ResourceSpecification")
                          @Valid @RequestParam(value = "resourceSpecification.@type", required = false) String resourceSpecificationType,
                          @ApiParam(value = "For filtering: Name given to the ResourceSpecification")
                          @Valid @RequestParam(value = "resourceSpecification.name", required = false) String resourceSpecificationName,
                          @ApiParam(value = "For filtering: ResourceSpecification version")
                          @Valid @RequestParam(value = "resourceSpecification.version", required = false) String resourceSpecificationVersion,
                          @ApiParam(value = "For filtering: An instant of time, ending at the TimePeriod.")
                          @Valid @RequestParam(value = "validFor.endDateTime", required = false) OffsetDateTime validForEndDateTime,
                          @ApiParam(value = "For filtering: An instant of time, starting at the TimePeriod")
                          @Valid @RequestParam(value = "validFor.startDateTime", required = false) OffsetDateTime validForStartDateTime,
                          @ApiParam(value = "For filtering: the version of resource candidate")
                          @Valid @RequestParam(value = "version", required = false) String version) {

        return ResponseEntity.status(HttpStatus.OK).body(resourceCandidateService.list());
    }

    @ApiOperation(value = "Updates partially a 'ResourceCandidate' by Id", nickname = "patchResourceCandidate",
            notes = "", response = ResourceCandidate.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ResourceCandidate.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized"),
            //@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCandidate/{id}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchResourceCandidate(@ApiParam(value = "Identifier of the Resource Candidate", required = true)
                           @PathVariable("id") String id,
                           @ApiParam(value = "The Resource Candidate to be updated", required = true )
                           @Valid @RequestBody ResourceCandidateUpdate resourceCandidate) {

        log.info("Web-Server: Received request to patch Resource Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(resourceCandidate == null) {
            log.error("Web-Server: Invalid request body (resourceCandidate) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (resourceCandidate) received."));
        }

        ResourceCandidate rc;
        try {
            rc = resourceCandidateService.patch(id, resourceCandidate);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Resource Candidate " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(rc);
    }

    @ApiOperation(value = "Retrieves a 'ResourceCandidate' by Id", nickname = "retrieveResourceCandidate",
            notes = "", response = ResourceCandidate.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceCandidate.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized"),
            //@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCandidate/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveResourceCandidate(@ApiParam(value = "Identifier of the Resource Candidate", required = true)
                              @PathVariable("id") String id) {

        log.info("Web-Server: Received request to retrieve Resource Candidate with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        List<ResourceCandidate> rc = new ArrayList<>();
        try {
            rc.add(resourceCandidateService.get(id));
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Resource Candidate " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(rc);
    }
}
