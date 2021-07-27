package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.CategoryAlreadyExistingException;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.Category;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.CategoryUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.CategoryInterface;
import it.nextworks.tmf_offering_catalog.services.CategoryService;
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
public class CategoryController implements CategoryInterface {

    private final static Logger log = LoggerFactory.getLogger(CategoryController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public CategoryController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a Category", nickname = "createCategory",
            notes = "This operation creates a Category entity.", response = Category.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = Category.class),
            @ApiResponse(code = 201, message = "Created", response = Category.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found"),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/category",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createCategory(@ApiParam(value = "The Category to be created", required = true)
                   @Valid @RequestBody CategoryCreate category) {

        log.info("Web-Server: Received request to create a Category.");

        if(category == null) {
            log.error("Web-Server: Invalid request body (category) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (category) received."));
        }

        Category c;
        try {
            c = categoryService.create(category);
        } catch (CategoryAlreadyExistingException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Category created with id " + c.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @ApiOperation(value = "Deletes a Category", nickname = "deleteCategory",
            notes = "This operation deletes a Category entity.", authorizations = {
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
    @RequestMapping(value = "/productCatalogManagement/v4/category/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteCategory(@ApiParam(value = "Identifier of the Category", required = true) @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            categoryService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Category " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find Category objects", nickname = "listCategory",
            notes = "This operation list or find Category entities", response = Category.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Category.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/category",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listCategory(@ApiParam(value = "Comma-separated properties to be provided in response")
                 @Valid @RequestParam(value = "fields", required = false) String fields,
                 @ApiParam(value = "Requested number of resources to be provided in response")
                 @Valid @RequestParam(value = "limit", required = false) Integer limit,
                 @ApiParam(value = "Requested index for start of resources to be provided in response")
                 @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.list());
    }

    @ApiOperation(value = "Updates partially a Category", nickname = "patchCategory",
            notes = "This operation updates partially a Category entity.", response = Category.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = Category.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/category/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchCategory(@ApiParam(value = "Identifier of the Category", required = true)
                  @PathVariable("id") String id,
                  @ApiParam(value = "The Category to be updated", required = true )
                  @Valid @RequestBody CategoryUpdate category) {

        log.info("Web-Server: Received request to patch Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(category == null) {
            log.error("Web-Server: Invalid request body (category) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (category) received."));
        }

        Category c;
        try {
            c = categoryService.patch(id, category,
                    OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Category " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(c);
    }

    @ApiOperation(value = "Retrieves a Category by ID", nickname = "retrieveCategory",
            notes = "This operation retrieves a Category entity. Attribute selection is enabled for all first level attributes.",
            response = Category.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Category.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/category/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveCategory(@ApiParam(value = "Identifier of the Category", required = true)
                     @PathVariable("id") String id,
                     @ApiParam(value = "Comma-separated properties to provide in response")
                     @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Category with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        Category c;
        try {
            c = categoryService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Category " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(c);
    }
}
