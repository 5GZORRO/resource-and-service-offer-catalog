package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NSSORequestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NSSOCommunicationService {

    private class UserData {

        @JsonProperty(value = "transaction_id")
        private final String transactionId;

        @JsonProperty(value = "product_id")
        private final String productId;

        @JsonCreator
        public UserData(@JsonProperty(value = "transaction_id") String transactionId,
                        @JsonProperty(value = "product_id") String productId) {
            this.transactionId = transactionId;
            this.productId = productId;
        }
    }

    private class VSInstantiationRequest {

        @JsonProperty(value = "name")
        private final String name;

        @JsonProperty(value = "description")
        private final String description;

        @JsonProperty(value = "vsdId")
        private final String vsdId;

        @JsonProperty(value = "tenantId")
        private final String tenantId;

        @JsonProperty(value = "userData")
        private final UserData userData;

        @JsonCreator
        public VSInstantiationRequest(@JsonProperty(value = "name") String name,
                                      @JsonProperty(value = "description") String description,
                                      @JsonProperty(value = "vsdId") String vsdId,
                                      @JsonProperty(value = "tenantId") String tenantId,
                                      @JsonProperty(value = "userData") UserData userData) {
            this.name = name;
            this.description = description;
            this.vsdId = vsdId;
            this.tenantId = tenantId;
            this.userData = userData;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(NSSOCommunicationService.class);

    private static final String protocol = "http://";
    @Value("${nsso.url}")
    private String nssoUrl;

    private final ObjectMapper objectMapper;

    @Autowired
    public NSSOCommunicationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String login(String usr, String psw) throws NSSORequestException, IOException {
        log.info("Authenticate to NSSO with admin credentials.");

        String request = protocol + nssoUrl + "/login?username=" + usr + "&password=" + psw;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost, context);
        } catch(IOException e) {
            String msg = "NSSO Unreachable.";
            log.error(msg);
            throw new NSSORequestException(msg);
        }

        if(response.getStatusLine().getStatusCode() != 200) {
            HttpEntity httpEntity = response.getEntity();
            String msg = "Authentication failed: " + EntityUtils.toString(httpEntity, "UTF-8");
            log.error(msg);
            throw new NSSORequestException(msg);
        }

        List<Cookie> cookies = context.getCookieStore().getCookies();
        if(cookies.isEmpty()) {
            String msg = "Authentication failed: no cookie.";
            log.error(msg);
            throw new NSSORequestException(msg);
        }
        if(cookies.size() > 1) {
            String msg = "Authentication failed: too many cookies.";
            log.error(msg);
            throw new NSSORequestException(msg);
        }
        String jSessionId = cookies.get(0).getValue();

        log.info("Authenticated, JSESSIONID: " + jSessionId);

        return jSessionId;
    }

    public void instantiateVS(String vsdId, String tenantId, String productOrderId,
                              String productOfferId, String jSessionId)
            throws IOException, NSSORequestException {
        log.info("Request VS " + vsdId + " instantiation for ProductOrder " + productOrderId + ".");

        String request = protocol + nssoUrl + "/vs/basic/vslcm/vs/";

        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie jSessionIdCookie = new BasicClientCookie("JSESSIONID", jSessionId);
        jSessionIdCookie.setDomain(nssoUrl.split(":")[0]);
        jSessionIdCookie.setPath("/");
        cookieStore.addCookie(jSessionIdCookie);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(request);

        VSInstantiationRequest vsInstantiationRequest = new VSInstantiationRequest(
                "Order " + productOrderId,
                "Order " + productOrderId + ", Offer " + productOfferId + ", VS " + vsdId,
                tenantId,
                null,
                new UserData(productOrderId, productOfferId));
        String vsInstantiationRequestJson = objectMapper.writeValueAsString(vsInstantiationRequest);
        StringEntity stringEntity = new StringEntity(vsInstantiationRequestJson);

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch(IOException e) {
            String msg = "NSSO Unreachable.";
            log.error(msg);
            throw new NSSORequestException(msg);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode != 200) {
            HttpEntity httpEntity = response.getEntity();
            String msg = "Instantiation failed [status:" + statusCode + "]: " +
                    EntityUtils.toString(httpEntity, "UTF-8");
            log.info(msg);
            throw new NSSORequestException(msg);
        }

        log.info("VS " + vsdId + " instantiation requested.");
    }
}
