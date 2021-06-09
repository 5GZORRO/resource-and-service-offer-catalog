package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidation;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddressValidationUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressValidationInterface;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressValidationService;
import org.json.JSONObject;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class GeographicAddressValidationController implements GeographicAddressValidationInterface {

    private final static Logger log = LoggerFactory.getLogger(GeographicAddressValidationController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public GeographicAddressValidationController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Autowired
    private GeographicAddressValidationService geographicAddressValidationService;

    @ApiOperation(value = "Creates a GeographicAddressValidation", nickname = "createGeographicAddressValidation",
            notes = "This operation creates a GeographicAddressValidation entity.", response = GeographicAddressValidation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createGeographicAddressValidation(@ApiParam(value = "The GeographicAddressValidation to be created", required = true)
                                                               @Valid @RequestBody GeographicAddressValidationCreate geographicAddressValidationCreate) {
        log.info("Web-Server: Received request to create a Geographic Address Validation.");

        if (geographicAddressValidationCreate == null) {
            log.error("Web-Server: Invalid request body (geographicAddressValidation) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicAddressValidation) received"));
        }

        GeographicAddressCreate submittedGeographicAddress = geographicAddressValidationCreate.getSubmittedGeographicAddress();
        if (submittedGeographicAddress == null) {
            log.error("Web-Server: Invalid request body (submittedGeographicAddress) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (submittedGeographicAddress) received"));
        }


        if (submittedGeographicAddress.getGeographicLocation() != null
                && submittedGeographicAddress.getGeographicLocation().getId() != null
                && !submittedGeographicAddress.getGeographicLocation().getId().matches(uuidRegex)) {
            log.error("Web-Server: Invalid request body (geographicLocation) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicLocation) received"));
        }

        if (submittedGeographicAddress.getGeographicLocation() != null
                && (submittedGeographicAddress.getGeographicLocation().getGeometry() != null && !submittedGeographicAddress.getGeographicLocation().getGeometry().isEmpty())) {
            AtomicBoolean invalidPoint = new AtomicBoolean(false);
            submittedGeographicAddress.getGeographicLocation().getGeometry().forEach((point) -> {
                if (point.getId() != null && !point.getId().matches(uuidRegex)) {
                    invalidPoint.set(true);
                }
            });
            if (invalidPoint.get()) {
                log.error("Web-Server: Invalid request body (geographicPoint) received.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicPoint) received"));
            }
        }

        GeographicAddressValidation geographicAddressValidation = geographicAddressValidationService.create(geographicAddressValidationCreate);

        log.info("Web-Server: Geographic Address Validation created with id " + geographicAddressValidation.getId() + ".");
        return ResponseEntity.status(HttpStatus.CREATED).body(geographicAddressValidation);
    }

    @ApiOperation(value = "List or find GeographicAddressValidation objects", nickname = "listGeographicAddressValidation",
            notes = "This operation list or find GeographicAddressValidation entities", response = GeographicAddressValidation.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddressValidation.class, responseContainer = "List")})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicAddressValidation(@ApiParam(value = "Comma-separated properties to be provided in response")
                                                             @Valid @RequestParam(value = "fields", required = false) String fields) {
        List<GeographicAddressValidation> geographicAddressValidations = geographicAddressValidationService.list();
        ResponseEntity<?> responseEntity;
        if (fields != null) {
            List<JSONObject> responseList = new ArrayList<>();
            for (GeographicAddressValidation geographicAddressValidation : geographicAddressValidations) {
                JSONObject responseJson = new JSONObject();
                for (String field : fields.split(",")) {
                    Class<?> geographicAddressValidationClass = geographicAddressValidation.getClass();
                    Field geographicAddressValidationField;
                    try {
                        geographicAddressValidationField = geographicAddressValidationClass.getDeclaredField(field);
                        geographicAddressValidationField.setAccessible(true);
                        responseJson.put(field, geographicAddressValidationField.get(geographicAddressValidation));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.error("Web-Server: Field " + field + " does not exists.");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Web-Server: Field " + field + " does not exists."));
                    }
                }
                responseList.add(responseJson);
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseList.toString());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(geographicAddressValidations);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updates partially a GeographicAddressValidation", nickname = "patchGeographicAddressValidation",
            notes = "This operation updates partially a GeographicAddressValidation entity.", response = GeographicAddressValidation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Updated", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation/{id}",
            produces = {"application/json;charset=utf-8"},
            consumes = {"application/json;charset=utf-8"},
            method = RequestMethod.PATCH)
    public ResponseEntity<?> patchGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                              @PathVariable("id") String id,
                                                              @ApiParam(value = "The GeographicAddressValidation to be updated", required = true)
                                                              @Valid @RequestBody GeographicAddressValidationUpdate geographicAddressValidationUpdate) {
        log.info("Web-Server: Received request to patch Geographic Address Validation with id " + id + ".");

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if (geographicAddressValidationUpdate == null) {
            log.error("Web-Server: Invalid request body (geographicAddressValidation) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid request body (geographicAddressValidation) received."));
        }

        GeographicAddressValidation geographicAddressValidation;
        try {
            geographicAddressValidation = geographicAddressValidationService.patch(id, geographicAddressValidationUpdate, OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Geographic Address Validation " + id + " patched.");

        return ResponseEntity.status(HttpStatus.CREATED).body(geographicAddressValidation);
    }

    @ApiOperation(value = "Retrieves a GeographicAddressValidation by ID", nickname = "retrieveGeographicAddressValidation",
            notes = "This operation retrieves a GeographicAddressValidation entity. Attribute selection is enabled for all first level attributes.", response = GeographicAddressValidation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddressValidation.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation/{id}",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> retrieveGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                                 @PathVariable("id") String id,
                                                                 @ApiParam(value = "Comma-separated properties to provide in response")
                                                                 @Valid @RequestParam(value = "fields", required = false) String fields) {
        log.info("Web-Server: Received request to retrieve Geographic Address Validation with id " + id + ".");

        if (!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Invalid path variable (id) request received."));
        }

        GeographicAddressValidation geographicAddressValidation;
        try {
            geographicAddressValidation = geographicAddressValidationService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        ResponseEntity<?> responseEntity;
        if (fields != null) {
            JSONObject responseJson = new JSONObject();
            for (String field : fields.split(",")) {
                Class<?> geographicAddressValidationClass = geographicAddressValidation.getClass();
                Field geographicAddressValidationField;
                try {
                    geographicAddressValidationField = geographicAddressValidationClass.getDeclaredField(field);
                    geographicAddressValidationField.setAccessible(true);
                    responseJson.put(field, geographicAddressValidationField.get(geographicAddressValidation));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Web-Server: Field " + field + " does not exists.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Web-Server: Field " + field + " does not exists."));
                }
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseJson.toString());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(geographicAddressValidation);
        }
        log.info("Web-Server: Geographic Address Validation " + id + " retrieved.");
        return responseEntity;
    }

    @ApiOperation(value = "Deletes a GeographicAddressValidation", nickname = "deleteGeographicAddressValidation",
            notes = "This operation deletes a GeographicAddressValidation entity.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Deleted", response = GeographicAddressValidation.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddressValidation/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGeographicAddressValidation(@ApiParam(value = "Identifier of the GeographicAddressValidation", required = true)
                                                               @PathVariable("id") String id) {
        geographicAddressValidationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
