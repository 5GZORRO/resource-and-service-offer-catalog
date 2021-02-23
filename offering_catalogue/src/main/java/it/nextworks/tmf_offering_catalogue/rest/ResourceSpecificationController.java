package it.nextworks.tmf_offering_catalogue.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalogue.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecificationCreate;
import it.nextworks.tmf_offering_catalogue.information_models.resource.ResourceSpecificationUpdate;
import it.nextworks.tmf_offering_catalogue.interfaces.ResourceSpecificationInterface;
import it.nextworks.tmf_offering_catalogue.services.ResourceSpecificationService;
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
public class ResourceSpecificationController implements ResourceSpecificationInterface {

    private final static Logger log = LoggerFactory.getLogger(ResourceCandidateController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ResourceSpecificationService resourceSpecificationService;

    @Autowired
    public ResourceSpecificationController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a 'ResourceSpecification'", nickname = "createResourceSpecification", notes = "",
            response = ResourceSpecification.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResourceSpecification.class),
            @ApiResponse(code = 201, message = "Created", response = ResourceSpecification.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceSpecification",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<ResourceSpecification>
    createResourceSpecification(@ApiParam(value = "The ResourceSpecification to be created" ,required=true )
                                @Valid @RequestBody ResourceSpecificationCreate serviceSpecification) {

        log.info("Web-Server: Received request to create a Resource Specification.");

        if(serviceSpecification == null) {
            log.error("Web-Server: Invalid request body (serviceSpecification) received'");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ResourceSpecification rs = resourceSpecificationService.create(serviceSpecification);

        log.info("Web-Server: Resource Specification created with id " + rs.getId() + ".");

        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }

    @ApiOperation(value = "Deletes a 'ResourceSpecification' by Id", nickname = "deleteResourceSpecification", notes = "", authorizations = {
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
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceSpecification/{id}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void>
    deleteResourceSpecification(@ApiParam(value = "Identifier of the ResourceSpecification",required=true)
                                @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Resource Specification with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            resourceSpecificationService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Specification " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find 'ResourceSpecification' objects", nickname = "listResourceSpecification",
            notes = "", response = ResourceSpecification.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceSpecification.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceSpecification",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ResourceSpecification>>
    listResourceSpecification(@ApiParam(value = "For filtering: The (immediate) base class type of this REST resource")
                              @Valid @RequestParam(value = "@baseType", required = false) String baseType,
                              @ApiParam(value = "For filtering: This field provides a link to the schema describing this REST resource")
                              @Valid @RequestParam(value = "@schemaLocation", required = false) String schemaLocation,
                              @ApiParam(value = "For filtering: Class type of this REST resource")
                              @Valid @RequestParam(value = "@type", required = false) String type,
                              @ApiParam(value = "For filtering: Attachment class  type")
                              @Valid @RequestParam(value = "attachment.@type", required = false) String attachmentType,
                              @ApiParam(value = "For filtering: Attachment mime type such as extension file for video, picture and document")
                              @Valid @RequestParam(value = "attachment.mimeType", required = false) String attachmentMimeType,
                              @ApiParam(value = "For filtering: name given to the attachment")
                              @Valid @RequestParam(value = "attachment.name", required = false) String attachmentName,
                              @ApiParam(value = "For filtering: Uniform Resource Identifier (URI) of the attachment")
                              @Valid @RequestParam(value = "attachment.uri", required = false) String attachmentUri,
                              @ApiParam(value = "For filtering: Category of the target resource like NetworkConnectivity, PhysicalLinks, Generic, L2Network and so on.")
                              @Valid @RequestParam(value = "category", required = false) String category,
                              @ApiParam(value = "For filtering: The optional (class) type of the feature")
                              @Valid @RequestParam(value = "feature.@type", required = false) String featureType,
                              @ApiParam(value = "For filtering: A flag indicating if the feature is bundle (true) or not (false).")
                              @Valid @RequestParam(value = "feature.isBundle", required = false) Boolean featureIsBundle,
                              @ApiParam(value = "For filtering: A flag indicating if the feature is enabled (true) or not (false).")
                              @Valid @RequestParam(value = "feature.isEnabled", required = false) Boolean featureIsEnabled,
                              @ApiParam(value = "For filtering: Unique name given to the feature. it is Required if the feature is not introduced as a separate REST resource")
                              @Valid @RequestParam(value = "feature.name", required = false) String featureName,
                              @ApiParam(value = "For filtering: feature version")
                              @Valid @RequestParam(value = "feature.version", required = false) String featureVersion,
                              @ApiParam(value = "Comma separated properties to display in response")
                              @Valid @RequestParam(value = "fields", required = false) String fields,
                              @ApiParam(value = "For filtering: A flag indicates that if this resource specification is a bundled specification (true) or single (false).")
                              @Valid @RequestParam(value = "isBundle", required = false) Boolean isBundle,
                              @ApiParam(value = "For filtering: Date and time of the last update of this REST resource")
                              @Valid @RequestParam(value = "lastUpdate", required = false) OffsetDateTime lastUpdate,
                              @ApiParam(value = "For filtering: Used to indicate the current lifecycle status of the resource specification")
                              @Valid @RequestParam(value = "lifecycleStatus", required = false) String lifecycleStatus,
                              @ApiParam(value = "For filtering: Name given to this REST resource")
                              @Valid @RequestParam(value = "name", required = false) String name,
                              @ApiParam(value = "For filtering: Name of the related party")
                              @Valid @RequestParam(value = "relatedParty.name", required = false) String relatedPartyName,
                              @ApiParam(value = "For filtering: Role of the related party.")
                              @Valid @RequestParam(value = "relatedParty.role", required = false) String relatedPartyRole,
                              @ApiParam(value = "For filtering: A link to the schema describing this characteristic specification")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.@schemaLocation", required = false) String resourceSpecCharacteristicSchemaLocation,
                              @ApiParam(value = "For filtering: (Class) type of the ResourceSpecCharacteristic")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.@type", required = false) String resourceSpecCharacteristicType,
                              @ApiParam(value = "For filtering: This (optional) field provides a link to the schema describing the value type")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.@valueSchemaLocation", required = false) String resourceSpecCharacteristicValueSchemaLocation,
                              @ApiParam(value = "For filtering: If true, the Boolean indicates that the ResourceSpecCharacteristic is configurable")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.configurable", required = false) Boolean resourceSpecCharacteristicConfigurable,
                              @ApiParam(value = "For filtering: An indicator that specifies that the values for the characteristic can be extended by adding new values when instantiating a characteristic for a resource.")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.extensible", required = false) Boolean resourceSpecCharacteristicExtensible,
                              @ApiParam(value = "For filtering: An indicator that specifies if a value is unique for the specification. Possible values are; \"unique while value is in effect\" and \"unique whether value is in effect or not\"")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.isUnique", required = false) Boolean resourceSpecCharacteristicIsUnique,
                              @ApiParam(value = "For filtering: The maximum number of instances a CharacteristicValue can take on. For example, zero to five phone numbers in a group calling plan, where five is the value for the maxCardinality.")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.maxCardinality", required = false) Integer resourceSpecCharacteristicMaxCardinality,
                              @ApiParam(value = "For filtering: The minimum number of instances a CharacteristicValue can take on. For example, zero to five phone numbers in a group calling plan, where zero is the value for the minCardinality.")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.minCardinality", required = false) Integer resourceSpecCharacteristicMinCardinality,
                              @ApiParam(value = "For filtering: A word, term, or phrase by which this characteristic specification is known and distinguished from other characteristic specifications.")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.name", required = false) String resourceSpecCharacteristicName,
                              @ApiParam(value = "For filtering: A rule or principle represented in regular expression used to derive the value of a characteristic value.")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.regex", required = false) String resourceSpecCharacteristicRegex,
                              @ApiParam(value = "For filtering: A kind of value that the characteristic can take on, such as numeric, text and so forth")
                              @Valid @RequestParam(value = "resourceSpecCharacteristic.valueType", required = false) String resourceSpecCharacteristicValueType,
                              @ApiParam(value = "For filtering: The name given to the target resource specification instance")
                              @Valid @RequestParam(value = "resourceSpecRelationship.name", required = false) String resourceSpecRelationshipName,
                              @ApiParam(value = "For filtering: The association role for this resource specification")
                              @Valid @RequestParam(value = "resourceSpecRelationship.role", required = false) String resourceSpecRelationshipRole,
                              @ApiParam(value = "For filtering: Type of relationship such as migration, substitution, dependency, exclusivity")
                              @Valid @RequestParam(value = "resourceSpecRelationship.type", required = false) String resourceSpecRelationshipType,
                              @ApiParam(value = "For filtering: This field provides a link to the schema describing the target resource")
                              @Valid @RequestParam(value = "targetResourceSchema.@schemaLocation", required = false) String targetResourceSchemaSchemaLocation,
                              @ApiParam(value = "For filtering: Class type of the target resource")
                              @Valid @RequestParam(value = "targetResourceSchema.@type", required = false) String targetResourceSchemaType,
                              @ApiParam(value = "For filtering: An instant of time, ending at the TimePeriod.")
                              @Valid @RequestParam(value = "validFor.endDateTime", required = false) OffsetDateTime validForEndDateTime,
                              @ApiParam(value = "For filtering: An instant of time, starting at the TimePeriod")
                              @Valid @RequestParam(value = "validFor.startDateTime", required = false) OffsetDateTime validForStartDateTime,
                              @ApiParam(value = "For filtering: Resource Specification version")
                              @Valid @RequestParam(value = "version", required = false) String version) {
        return ResponseEntity.status(HttpStatus.OK).body(resourceSpecificationService.list());
    }

    @ApiOperation(value = "Updates partially a 'ResourceSpecification' by Id", nickname = "patchResourceSpecification",
            notes = "", response = ResourceSpecification.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResourceSpecification.class),
            @ApiResponse(code = 201, message = "Updated", response = ResourceSpecification.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceSpecification/{id}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<ResourceSpecification>
    patchResourceSpecification(@ApiParam(value = "Identifier of the ResourceSpecification",required = true)
                               @PathVariable("id") String id,
                               @ApiParam(value = "The ResourceSpecification to be updated", required = true )
                               @Valid @RequestBody ResourceSpecificationUpdate serviceSpecification) {

        log.info("Web-Server: Received request to patch Resource Specification with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(serviceSpecification == null) {
            log.error("Web-Server: Invalid request body (serviceSpecification) received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ResourceSpecification rs;
        try {
            rs = resourceSpecificationService.patch(id, serviceSpecification);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Specification " + id + " patched.");

        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }

    @ApiOperation(value = "Retrieves a 'ResourceSpecification' by Id", nickname = "retrieveResourceSpecification",
            notes = "", response = ResourceSpecification.class, responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResourceSpecification.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/resourceCatalogManagement/v2/resourceSpecification/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<ResourceSpecification>>
    retrieveResourceSpecification(@ApiParam(value = "Identifier of the Resource Specification", required = true)
                                  @PathVariable("id") String id) {

        log.info("Web-Server: Received request to retrieve Resource Specification with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<ResourceSpecification> rs = new ArrayList<>();
        try {
            rs.add(resourceSpecificationService.get(id));
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: ");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("Web-Server: Resource Specification " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }
}
