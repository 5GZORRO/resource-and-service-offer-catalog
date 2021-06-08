package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingStatus;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOfferingStatusInterface;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductOfferingStatusController implements ProductOfferingStatusInterface {

    public class Status {

        @JsonProperty("id")
        private final String id;

        @JsonProperty("did")
        private final String did;

        @JsonProperty("status")
        private final String status;

        @JsonProperty("description")
        private final String description;

        @JsonCreator
        public Status(@JsonProperty("id") String id,
                      @JsonProperty("did") String did,
                      @JsonProperty("status") String status,
                      @JsonProperty("description") String description) {
            this.id = id;
            this.did = did;
            this.status = status;
            this.description = description;
        }
    }

    private final static Logger log = LoggerFactory.getLogger(ProductOfferingStatusController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductOfferingStatusService productOfferingStatusService;

    @Autowired
    public ProductOfferingStatusController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    @ApiOperation(value = "Retrieve a Product Offering Status given an id", nickname = "retrieveProductOfferingStatus",
            notes = "This operation retrieve a Product Offering Status given an id.", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Status.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @RequestMapping(value = "/productCatalogManagement/v4/productOfferingStatus/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveProductOfferingStatus(@ApiParam(value = "Identifier of the ProductOfferingStatus", required = true)
                                  @PathVariable("id") String id) {

        log.info("Web-Server: Received request to retrieve Product Offering Status with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOfferingStatus pos;
        try {
            pos = productOfferingStatusService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        Status s = new Status(pos.getCatalogId(), pos.getDid(), pos.getStatus().name(), pos.getStatus().toString());

        log.info("Web-Server: Product Offering Status " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(s);
    }
}
