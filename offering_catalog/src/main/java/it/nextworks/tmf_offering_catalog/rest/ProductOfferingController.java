package it.nextworks.tmf_offering_catalog.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingCreate;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingUpdate;
import it.nextworks.tmf_offering_catalog.interfaces.ProductOfferingInterface;
import it.nextworks.tmf_offering_catalog.services.ProductOfferingService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

@RestController
public class ProductOfferingController implements ProductOfferingInterface {

    public class Invitation {}
    public class VerifiableCredential {}

    public class PublishProductOfferingRequest {

        @JsonProperty("productOffering")
        private final ProductOffering productOffering;
        @JsonProperty("invitations")
        private Map<String, Invitation> invitations;
        @JsonProperty("verifiableCredentials")
        private Collection<VerifiableCredential> verifiableCredentials;

        @JsonCreator
        public PublishProductOfferingRequest(@JsonProperty("productOffering") ProductOffering productOffering,
                                             @JsonProperty("invitations") Map<String, Invitation> invitations,
                                             @JsonProperty("verifiableCredentials")
                                                     Collection<VerifiableCredential> verifiableCredentials) {
            this.productOffering       = productOffering;
            this.invitations           = invitations;
            this.verifiableCredentials = verifiableCredentials;
        }
    }

    private final static Logger log = LoggerFactory.getLogger(ProductOfferingController.class);

    private static final String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductOfferingService productOfferingService;

    private static final String protocol = "http://";
    @Value("${sc_lcm.hostname}")
    private String hostname;
    @Value("${sc_lcm.port}")
    private String port;
    private static final String requestPath = "/product-offer";

    @Autowired
    public ProductOfferingController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ApiOperation(value = "Creates a ProductOffering", nickname = "createProductOffering",
            notes = "This operation creates a ProductOffering entity.", response = ProductOffering.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            //@ApiResponse(code = 200, message = "OK", response = ProductOffering.class),
            @ApiResponse(code = 201, message = "Created", response = ProductOffering.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrMsg.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?>
    createProductOffering(@ApiParam(value = "The ProductOffering to be created", required = true )
                          @Valid @RequestBody ProductOfferingCreate productOffering) {

        log.info("Web-Server: Received request to create a Product Offering.");

        if(productOffering == null) {
            log.error("Web-Server: Invalid request body (productOffering) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOffering) received."));
        }

        ProductOffering po = productOfferingService.create(productOffering);

        String id = po.getId();
        log.info("Web-Server: Product Offering created with id " + id + ".");

        log.info("Web-Server: Sending publish Product Offer request to " + hostname + ".");

        String request = protocol + hostname + ":" + port + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        PublishProductOfferingRequest publishProductOfferingRequest =
                new PublishProductOfferingRequest(po, null, null);
        String pporJson;
        try {
            pporJson = objectMapper.writeValueAsString(publishProductOfferingRequest);
        } catch (JsonProcessingException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(pporJson);
        } catch (UnsupportedEncodingException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrMsg(e.getMessage()));
        }

        if(response.getStatusLine().getStatusCode() != 200) {
            HttpEntity httpEntity = response.getEntity();
            String responseString;
            if(httpEntity != null) {
                try {
                    responseString = EntityUtils.toString(httpEntity, "UTF-8");
                } catch (IOException e) {
                    log.error("Web-Server: " + "Product Offering " + id
                            + " not published; Error description unavailable:" + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ErrMsg("Product Offering " + id
                                    + " not published; Error description unavailable:" + e.getMessage()));
                }
            }
            else
                responseString = "Error description unavailable.";

            log.error("Web-Server: " + "Product Offering " + id
                    + " not published; " + responseString);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrMsg("Product Offering " + id + " not published; " + responseString));
        }

        log.info("Web-Server: Product Offering " + id + " published.");

        return ResponseEntity.status(HttpStatus.CREATED).body(po);
    }

    @ApiOperation(value = "Deletes a ProductOffering", nickname = "deleteProductOffering",
            notes = "This operation deletes a ProductOffering entity.", authorizations = {
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
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?>
    deleteProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                          @PathVariable("id") String id) {

        log.info("Web-Server: Received request to delete Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        try {
            productOfferingService.delete(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " deleted.");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "List or find ProductOffering objects", nickname = "listProductOffering",
            notes = "This operation list or find ProductOffering entities", response = ProductOffering.class,
            responseContainer = "List", authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOffering.class, responseContainer = "List"),
            //@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            //@ApiResponse(code = 404, message = "Not Found", response = Error.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    listProductOffering(@ApiParam(value = "Comma-separated properties to be provided in response")
                        @Valid @RequestParam(value = "fields", required = false) String fields,
                        @ApiParam(value = "Requested number of resources to be provided in response")
                        @Valid @RequestParam(value = "limit", required = false) Integer limit,
                        @ApiParam(value = "Requested index for start of resources to be provided in response")
                        @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.status(HttpStatus.OK).body(productOfferingService.list());
    }

    @ApiOperation(value = "Updates partially a ProductOffering", nickname = "patchProductOffering",
            notes = "This operation updates partially a ProductOffering entity.", response = ProductOffering.class,
            authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = ProductOffering.class),
            //@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            consumes = { "application/json;charset=utf-8" },
            method = RequestMethod.PATCH)
    public ResponseEntity<?>
    patchProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                         @PathVariable("id") String id,
                         @ApiParam(value = "The ProductOffering to be updated", required = true )
                         @Valid @RequestBody ProductOfferingUpdate productOffering) {

        log.info("Web-Server: Received request to patch Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        if(productOffering == null) {
            log.error("Web-Server: Invalid request body (productOffering) received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid request body (productOffering) received."));
        }

        ProductOffering po;
        try {
            po = productOfferingService.patch(id, productOffering);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " patched.");

        return ResponseEntity.status(HttpStatus.OK).body(po);
    }

    @ApiOperation(value = "Retrieves a ProductOffering by ID", nickname = "retrieveProductOffering",
            notes = "This operation retrieves a ProductOffering entity. Attribute selection is enabled for all first level attributes.",
            response = ProductOffering.class, authorizations = {
            @Authorization(value = "spring_oauth", scopes = {
                    @AuthorizationScope(scope = "read", description = "for read operations"),
                    @AuthorizationScope(scope = "openapi", description = "Access openapi API"),
                    @AuthorizationScope(scope = "admin", description = "Access admin API"),
                    @AuthorizationScope(scope = "write", description = "for write operations")
            })
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductOffering.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrMsg.class),
            //@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            //@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrMsg.class),
            //@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
            //@ApiResponse(code = 409, message = "Conflict", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @RequestMapping(value = "/productCatalogManagement/v4/productOffering/{id}",
            produces = { "application/json;charset=utf-8" },
            method = RequestMethod.GET)
    public ResponseEntity<?>
    retrieveProductOffering(@ApiParam(value = "Identifier of the ProductOffering", required = true)
                            @PathVariable("id") String id,
                            @ApiParam(value = "Comma-separated properties to provide in response")
                            @Valid @RequestParam(value = "fields", required = false) String fields) {

        log.info("Web-Server: Received request to retrieve Product Offering with id " + id + ".");

        if(!id.matches(uuidRegex)) {
            log.error("Web-Server: Invalid path variable (id) request received.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrMsg("Invalid path variable (id) request received."));
        }

        ProductOffering po;
        try {
            po = productOfferingService.get(id);
        } catch (NotExistingEntityException e) {
            log.error("Web-Server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrMsg(e.getMessage()));
        }

        log.info("Web-Server: Product Offering " + id + " retrieved.");

        return ResponseEntity.status(HttpStatus.OK).body(po);
    }
}
