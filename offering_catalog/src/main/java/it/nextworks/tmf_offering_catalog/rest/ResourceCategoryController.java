package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategory;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceCategoryUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ResourceCategoryInterface;
import it.nextworks.tmf_offering_catalog.services.ResourceCategoryService;
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
public class ResourceCategoryController implements ResourceCategoryInterface {

    private final static Logger log = LoggerFactory.getLogger(ResourceCategoryController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @Autowired
    public ResourceCategoryController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a 'ResourceCategory'", nickname = "createResourceCategory", notes = "",
            response = ResourceCategory.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResourceCategory.class),
            @ApiResponse(code = 201, message = "Created", response = ResourceCategory.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCategory",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<ResourceCategory>
    createResourceCategory(@ApiParam(value = "The ServiceCategory to be created", required = true )
                           @Valid @RequestBody ResourceCategoryCreate resCategory) {

        log.info("Web-Server: Received request to create a Resource Category.");

        if(resCategory == null) {
            log.error("Web-Server: Invalid request body (resCategory) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ResourceCategory rc = resourceCategoryService.create(resCategory);

        log.info("Web-Server: Resource Category created with id " + rc.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(rc);
    }

    @ApiOperation(value = "Deletes a 'ResourceCategory' by Id", nickname = "deleteResourceCategory", notes = "",
            authorizations = {
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
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCategory/{id}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void>
    deleteResourceCategory(@ApiParam(value = "Identifier of the Resource Category", required = true)
                           @PathVariable("id") String id) {

        log.info("Web-Server Received request to delete Resource Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            resourceCategoryService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Category " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find 'ResourceCategory' objects", nickname = "listResourceCategory", notes = "",
            response = ResourceCategory.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceCategory.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCategory",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ResourceCategory>>
    listResourceCategory(@ApiParam(value = "For filtering: Immediate base class type of this category")
                         @Valid @RequestParam(value = "@baseType", required = false) String baseType,
                         @ApiParam(value = "For filtering: This field provides a link to the schema describing this REST resource")
                         @Valid @RequestParam(value = "@schemalLocation", required = false) String schemalLocation,
                         @ApiParam(value = "For filtering: The (class) type of this category")
                         @Valid @RequestParam(value = "@type", required = false) String type,
                         @ApiParam(value = "For filtering: the class type of the category")
                         @Valid @RequestParam(value = "category.@type", required = false) String categoryType,
                         @ApiParam(value = "For filtering: Name of the category")
                         @Valid @RequestParam(value = "category.name", required = false) String categoryName,
                         @ApiParam(value = "For filtering: Category version")
                         @Valid @RequestParam(value = "category.version", required = false) String categoryVersion,
                         @ApiParam(value = "Comma separated properties to display in response")
                         @Valid @RequestParam(value = "fields", required = false) String fields,
                         @ApiParam(value = "For filtering: If true, this Boolean indicates that the category is a root of categories")
                         @Valid @RequestParam(value = "isRoot", required = false) Boolean isRoot,
                         @ApiParam(value = "For filtering: Date and time of the last update")
                         @Valid @RequestParam(value = "lastUpdate", required = false) OffsetDateTime lastUpdate,
                         @ApiParam(value = "For filtering: Used to indicate the current lifecycle status")
                         @Valid @RequestParam(value = "lifecycleStatus", required = false) String lifecycleStatus,
                         @ApiParam(value = "For filtering: Name of the category")
                         @Valid @RequestParam(value = "name", required = false) String name,
                         @ApiParam(value = "For filtering: Unique identifier of the parent category")
                         @Valid @RequestParam(value = "parentId", required = false) String parentId,
                         @ApiParam(value = "For filtering: Name of the related party")
                         @Valid @RequestParam(value = "relatedParty.name", required = false) String relatedPartyName,
                         @ApiParam(value = "For filtering: Role of the related party.")
                         @Valid @RequestParam(value = "relatedParty.role", required = false) String relatedPartyRole,
                         @ApiParam(value = "For filtering: The (class) type of the ResourceCandidate")
                         @Valid @RequestParam(value = "resourceCandidate.@type", required = false) String resourceCandidateType,
                         @ApiParam(value = "For filtering: Name given to the ResourceCandidate")
                         @Valid @RequestParam(value = "resourceCandidate.name", required = false) String resourceCandidateName,
                         @ApiParam(value = "For filtering: ResourceCandidate version")
                         @Valid @RequestParam(value = "resourceCandidate.version", required = false) String resourceCandidateVersion,
                         @ApiParam(value = "For filtering: An instant of time, ending at the TimePeriod.")
                         @Valid @RequestParam(value = "validFor.endDateTime", required = false) OffsetDateTime validForEndDateTime,
                         @ApiParam(value = "For filtering: An instant of time, starting at the TimePeriod")
                         @Valid @RequestParam(value = "validFor.startDateTime", required = false) OffsetDateTime validForStartDateTime,
                         @ApiParam(value = "For filtering: Category version")
                         @Valid @RequestParam(value = "version", required = false) String version) {

        return ResponseEntity.status(HttpStatus.OK).body(resourceCategoryService.list());
    }

    @ApiOperation(value = "Updates partially a 'ResourceCategory' by Id", nickname = "patchResourceCategory",
            notes = "", response = ResourceCategory.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResourceCategory.class),
            @ApiResponse(code = 201, message = "Updated", response = ResourceCategory.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCategory/{id}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<ResourceCategory>
    patchResourceCategory(@ApiParam(value = "Identifier of the Resource Category", required = true)
                          @PathVariable("id") String id,
                          @ApiParam(value = "The Resource Category to be updated" ,required = true )
                          @Valid @RequestBody ResourceCategoryUpdate resourceCategory) {

        log.info("Web-Server: Received request to patch Resource Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(resourceCategory == null){
            log.error("Web-Server: Invalid request body (resourceCategory) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ResourceCategory rc;
        try {
            rc = resourceCategoryService.patch(id, resourceCategory);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Category " + id + " patched.");

        return ResponseEntity.status(HttpStatus.CREATED).body(rc);
    }

    @ApiOperation(value = "Retrieves a 'ResourceCategory' by Id", nickname = "retrieveResourceCategory", notes = "",
            response = ResourceCategory.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceCategory.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceCategory/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ResourceCategory>>
    retrieveResourceCategory(@ApiParam(value = "Identifier of the Resource Category",required = true)
                             @PathVariable("id") String id) {

        log.info("Web-Server: Received request to retrieve Resource Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<ResourceCategory> rc = new ArrayList<>();
        try {
            rc.add(resourceCategoryService.get(id));
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Category " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(rc);
    }
}
