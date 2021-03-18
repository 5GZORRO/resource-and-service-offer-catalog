package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DIDDiscoveryService {

    public class DIDGenerationRequestException extends Exception {
        public DIDGenerationRequestException(String msg) { super(msg); }
    }

    public class Claims {}

    public class Offer {

        @JsonProperty("type")
        private String type;
        @JsonProperty("claims")
        private Claims claims;
        @JsonProperty("handler_url")
        private String handlerUrl;

        @JsonCreator
        public Offer(@JsonProperty("type") String type,
                     @JsonProperty("claims") Claims claims,
                     @JsonProperty("handler_url") String handlerUrl) {
            this.type = type;
            this.claims = claims;
            this.handlerUrl = handlerUrl;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(DIDDiscoveryService.class);

    private static final String protocol = "http://";
    @Value("${did_service.hostname}")
    private String didServiceHostname;
    @Value("${did_service.port}")
    private String didServicePort;
    private static final String requestPath = "/holder/create_did";

    @Value("${did_service.token}")
    private String token;

    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;

    private final ObjectMapper objectMapper;

    @Autowired
    public DIDDiscoveryService(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public void discoverDID() throws IOException, DIDGenerationRequestException {

        log.info("Sending create DID request to " + didServiceHostname + ".");

        String request = protocol + didServiceHostname + ":" + didServicePort + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));

        Offer requestOffer =
                new Offer(null, null,
                        protocol + hostname + ":" + port +
                                "/tmf-api/productCatalogManagement/v4/productOffering/did");
        String roJson = objectMapper.writeValueAsString(requestOffer);

        StringEntity stringEntity = new StringEntity(roJson);

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if(response.getStatusLine().getStatusCode() != 200)
            throw new DIDGenerationRequestException("The DID Service do not accepted the DID creation request.");

        log.info("Create DID request accepted by " + didServiceHostname + ".");
    }
}
