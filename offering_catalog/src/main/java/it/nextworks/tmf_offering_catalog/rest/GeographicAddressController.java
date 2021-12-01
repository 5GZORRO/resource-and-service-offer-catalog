package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.interfaces.GeographicAddressInterface;
import it.nextworks.tmf_offering_catalog.rest.filter.GeographicAddressFilter;
import it.nextworks.tmf_offering_catalog.services.GeographicAddressService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GeographicAddressController implements GeographicAddressInterface {

    private final static Logger log = LoggerFactory.getLogger(GeographicAddressController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private GeographicAddressService geographicAddressService;

    @Autowired
    public GeographicAddressController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "List or find GeographicAddress objects", nickname = "listGeographicAddress",
            notes = "This operation list or find GeographicAddress entities", response = GeographicAddress.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddress.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?> listGeographicAddress(@ApiParam(value = "Comma-separated properties to be provided in response")
                                                   @Valid @RequestParam(value = "fields", required = false) String fields,
                                                   GeographicAddressFilter geographicAddressFilter) {
        List<GeographicAddress> geographicAddresses = geographicAddressService.list(geographicAddressFilter);
        ResponseEntity<?> responseEntity;
        if (fields != null) {
            List<JSONObject> responseList = new ArrayList<>();
            for (GeographicAddress geographicAddress : geographicAddresses) {
                JSONObject responseJson = new JSONObject();
                for (String field : fields.split(",")) {
                    Class<?> geographicAddressClass = geographicAddress.getClass();
                    Field geographicAddressField;
                    try {
                        geographicAddressField = geographicAddressClass.getDeclaredField(field);
                        geographicAddressField.setAccessible(true);
                        responseJson.put(field, geographicAddressField.get(geographicAddress));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.error("Web-Server: Field " + field + " does not exists.");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Web-Server: Field " + field + " does not exists."));
                    }
                }
                responseList.add(responseJson);
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseList.toString());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(geographicAddresses);
        }
        return responseEntity;

    }

    @ApiOperation(value = "Retrieves a GeographicAddress by ID", nickname = "retrieveGeographicAddress",
            notes = "This operation retrieves a GeographicAddress entity. Attribute selection is enabled for all first level attributes.", response = GeographicAddress.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddress.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class),
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

        ResponseEntity<?> responseEntity;
        if (fields != null) {
            JSONObject responseJson = new JSONObject();
            for (String field : fields.split(",")) {
                Class<?> geographicAddressClass = geographicAddress.getClass();
                Field geographicAddressField;
                try {
                    geographicAddressField = geographicAddressClass.getDeclaredField(field);
                    geographicAddressField.setAccessible(true);
                    responseJson.put(field, geographicAddressField.get(geographicAddress));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("Web-Server: Field " + field + " does not exists.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrMsg("Web-Server: Field " + field + " does not exists."));
                }
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseJson.toString());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(geographicAddress);
        }
        log.info("Web-Server: Geographic Address " + id + " retrieved.");
        return responseEntity;
    }

    @ApiOperation(value = "Retrieves a GeographicAddresses by coordinates.", nickname = "retrieveGeographicAddressByCoo",
            notes = "This operation retrieves a GeographicAddress entities. Attribute selection is enabled for all first level attributes.", response = GeographicAddress.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GeographicAddress.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)})
    @RequestMapping(value = "/geographicAddressManagement/v4/geographicAddress/coordinates",
            produces = {"application/json;charset=utf-8"},
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveGeographicAddressByCoo(@ApiParam(value = "X coordinate", required = true)
                                   @RequestParam String x,
                                   @ApiParam(value = "Y coordinate", required = true)
                                   @RequestParam String y) {

        log.info("Web-Server: Received request to retrieve Geographic Address with coordinates <{}, {}>.", x, y);
        return ResponseEntity.status(HttpStatus.OK).body(geographicAddressService.getByCoordinates(x, y));
    }
}
