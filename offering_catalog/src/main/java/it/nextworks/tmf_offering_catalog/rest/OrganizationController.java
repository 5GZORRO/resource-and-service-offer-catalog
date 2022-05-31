package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.StakeholderAlreadyRegisteredException;
import it.nextworks.tmf_offering_catalog.information_models.party.*;
import it.nextworks.tmf_offering_catalog.interfaces.OrganizationInterface;
import it.nextworks.tmf_offering_catalog.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@ApiIgnore
public class OrganizationController implements OrganizationInterface {

    private final static Logger log = LoggerFactory.getLogger(OrganizationController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates the Organization", nickname = "createOrganization",
            notes = "This operation creates a Organization entity.", response = OrganizationWrapper.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = Organization.class),
            @ApiResponse(code = 201, message = "Created", response = OrganizationWrapper.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/party/v4/organization",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createOrganization(@ApiParam(value = "The Organization to be created", required = true )
                       @Valid @RequestBody OrganizationCreateWrapper organizationCreateWrapper) {

        log.info("Web-Server: Received request to create an Organization.");

        if(organizationCreateWrapper == null ||
                organizationCreateWrapper.getOrganizationCreate() == null ||
                organizationCreateWrapper.getStakeholderDID() == null ||
                organizationCreateWrapper.getToken() == null) {
            log.error("Web-Server: Invalid request body (organizationCreateWrapper) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (organizationCreateWrapper) received."));
        }

        OrganizationWrapper ow;
        try {
            ow = organizationService.create(organizationCreateWrapper);
        } catch (StakeholderAlreadyRegisteredException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Organization created with id " + ow.getOrganization().getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(ow);
    }

    @ApiOperation(value = "Deletes the Organization", nickname = "deleteOrganization",
            notes = "This operation deletes a Organization entity.", authorizations = {
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
    @RequestMapping(value = "/party/v4/organization",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteOrganization() {

        log.info("Web-Server: Received request to delete Organization.");

        try {
            organizationService.delete();
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Organization deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Updates partially the Organization", nickname = "patchOrganization",
            notes = "This operation updates partially a Organization entity.", response = OrganizationWrapper.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = OrganizationWrapper.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/party/v4/organization",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchOrganization(@ApiParam(value = "The Organization to be updated" ,required = true )
                      @Valid @RequestBody OrganizationUpdate organization) {

        log.info("Web-Server: Received request to patch Organization.");

        if(organization == null) {
            log.error("Web-Server: Invalid request body (organization) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (organization) received."));
        }

        OrganizationWrapper ow;
        try {
            ow = organizationService.patch(organization);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Organization patched.");

        return ResponseEntity.status(HttpStatus.OK).body(ow);
    }

    @ApiOperation(value = "Retrieves the Organization", nickname = "retrieveOrganization",
            notes = "This operation retrieves a Organization entity. Attribute selection is enabled for all first level attributes.",
            response = OrganizationWrapper.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrganizationWrapper.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/party/v4/organization",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveOrganization(@ApiParam(value = "Comma-separated properties to provide in response")
                         @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Organization.");

        OrganizationWrapper ow;
        try {
            ow = organizationService.get();
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Organization retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(ow);
    }
}
