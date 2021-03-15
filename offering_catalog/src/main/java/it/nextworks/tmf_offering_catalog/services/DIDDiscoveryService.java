package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

    public class DIDNotGeneratedException extends Exception {
        public DIDNotGeneratedException(String msg) { super(msg); }
    }

    public class EmptyResponseException extends Exception {
        public EmptyResponseException(String msg) { super(msg); }
    }

    public static class ApiResponse {

        @JsonProperty("code")
        private final int code;
        @JsonProperty("type")
        private final String type;
        @JsonProperty("message")
        private final String message;

        @JsonCreator
        public ApiResponse(@JsonProperty("code") int code,
                           @JsonProperty("type") String type,
                           @JsonProperty("message") String message) {
            this.code    = code;
            this.type    = type;
            this.message = message;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(DIDDiscoveryService.class);

    private static final String protocol = "http://";
    @Value("${did_service.hostname}")
    private String hostname;
    @Value("${did_service.port}")
    private String port;
    private static final String requestPath = "/holder/create_did";

    @Value("${did_service.token}")
    private String token;

    private final ObjectMapper objectMapper;

    @Autowired
    public DIDDiscoveryService(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public String discoverDID() throws IOException, DIDNotGeneratedException, EmptyResponseException {

        log.info("Sending create DID request to " + hostname + ".");

        String request = protocol + hostname + ":" + port + requestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        CloseableHttpResponse response = httpClient.execute(httpPost);

        if(response.getStatusLine().getStatusCode() != 200)
            throw new DIDNotGeneratedException("The DID Service do not generated the DID.");

        HttpEntity httpEntity = response.getEntity();
        if(httpEntity == null)
            throw new EmptyResponseException("The DID Service returned an empty response: DID not available.");

        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
        ApiResponse apiResponse = objectMapper.readValue(responseString, ApiResponse.class);

        return apiResponse.message;
    }
}
