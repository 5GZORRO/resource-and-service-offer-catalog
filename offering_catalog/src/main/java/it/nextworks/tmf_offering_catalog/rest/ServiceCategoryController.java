package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.CategoryAlreadyExistingException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategory;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceCategoryUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ServiceCategoryInterface;
import it.nextworks.tmf_offering_catalog.services.ServiceCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@ApiIgnore
public class ServiceCategoryController implements ServiceCategoryInterface {

    private final static Logger log = LoggerFactory.getLogger(ServiceCategoryController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    public ServiceCategoryController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ServiceCategory", nickname = "createServiceCategory",
            notes = "This operation creates a ServiceCategory entity.", response = ServiceCategory.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = ServiceCategory.class),
            @ApiResponse(code = 201, message = "Created", response = ServiceCategory.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found"),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createServiceCategory(@ApiParam(value = "The ServiceCategory to be created", required = true )
                          @Valid @RequestBody ServiceCategoryCreate serviceCategory) {

        log.info("Web-Server: Received request to create a Service Category.");

        if(serviceCategory == null) {
            log.error("Web-Server: Invalid request body (serviceCategory) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (serviceCategory) received."));
        }

        ServiceCategory sc;
        try {
            sc = serviceCategoryService.create(serviceCategory);
        } catch (CategoryAlreadyExistingException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Service Category created with id " + sc.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(sc);
    }

    @ApiOperation(value = "Deletes a ServiceCategory", nickname = "deleteServiceCategory",
            notes = "This operation deletes a ServiceCategory entity.", authorizations = {
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
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteServiceCategory(@ApiParam(value = "Identifier of the ServiceCategory", required = true)
                          @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Service Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            serviceCategoryService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Service Category " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ServiceCategory objects", nickname = "listServiceCategory",
            notes = "This operation list or find ServiceCategory entities", response = ServiceCategory.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceCategory.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            //@ApiResponse(code = 413, message = "Not modified", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listServiceCategory(@ApiParam(value = "Comma-separated properties to be provided in response")
                        @Valid @RequestParam(value = "fields", required = false) String fields,
                        @ApiParam(value = "Requested number of resources to be provided in response")
                        @Valid @RequestParam(value = "limit", required = false) Integer limit,
                        @ApiParam(value = "Requested index for start of resources to be provided in response")
                        @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.status(HttpStatus.OK).body(serviceCategoryService.list());
    }

    @ApiOperation(value = "Find ServiceCategory object by name for Service Candidate NSD ",
            nickname = "findServiceCategoryByName", notes = "This operation list or find ServiceCategory entities",
            response = ServiceCategory.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceCategory.class),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            //@ApiResponse(code = 413, message = "Not modified", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory/filter",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    findServiceCategoryByName(@ApiParam(value = "For filtering: Name of the category")
                              @Valid @RequestParam(value = "name") String name) {

        log.info("Web-Server: Received request to retrieve Service Category with name " + name + ".");

        ServiceCategory sc;
        try {
            sc = serviceCategoryService.findByName(name);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Service Category with name " + name + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }

    @ApiOperation(value = "Updates partially a ServiceCategory", nickname = "patchServiceCategory",
            notes = "This operation updates partially a ServiceCategory entity.",
            response = ServiceCategory.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ServiceCategory.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchServiceCategory(@ApiParam(value = "Identifier of the ServiceCategory", required = true)
                         @PathVariable("id") String id,
                         @ApiParam(value = "The ServiceCategory to be updated", required = true )
                         @Valid @RequestBody ServiceCategoryUpdate serviceCategory) {

        log.info("Web-Server: Received request to patch Service Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(serviceCategory == null) {
            log.error("Web-Server: Invalid request body (serviceCategory) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (serviceCategory) received."));
        }

        ServiceCategory sc;
        try {
            sc = serviceCategoryService.patch(id, serviceCategory,
                    OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Service Category " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }

    @ApiOperation(value = "Retrieves a ServiceCategory by ID", nickname = "retrieveServiceCategory",
            notes = "This operation retrieves a ServiceCategory entity. Attribute selection is enabled for all first level attributes.",
            response = ServiceCategory.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServiceCategory.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/serviceCatalogManagement/v4/serviceCategory/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveServiceCategory(@ApiParam(value = "Identifier of the ServiceCategory", required = true)
                            @PathVariable("id") String id,
                            @ApiParam(value = "Comma-separated properties to provide in response")
                            @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Service Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ServiceCategory sc;
        try {
            sc = serviceCategoryService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Service Category " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(sc);
    }
}
