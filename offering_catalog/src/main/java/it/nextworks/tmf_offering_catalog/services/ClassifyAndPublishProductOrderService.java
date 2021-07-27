package it.nextworks.tmf_offering_catalog.services;


import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatesEnum;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderStatus;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class ClassifyAndPublishProductOrderService {

    private static final Logger log = LoggerFactory.getLogger(ClassifyAndPublishProductOrderService.class);

    private static final String protocol = "http://";

    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.product_order.sc_lcm_request_path}")
    private String scLcmRequestPath;
    @Value("${skip_sc_lcm_post}")
    private boolean skipSCLCMPost;

    @Value("${srsd.hostname}")
    private String srsdHostname;
    @Value("${srsd.port}")
    private String srsdPort;
    @Value("${srsd.product_order.srsd_request_path}")
    private String srsdRequestPath;
    @Value("${skip_srsd_post}")
    private boolean skipSRSDPost;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Async
    public void classifyAndPublish(String catalogId, String cwJson, String pwJson) {
        Optional<ProductOrderStatus> toClassify = productOrderStatusRepository.findById(catalogId);
        if (!toClassify.isPresent()) {
            log.error("Product Order Status for id " + catalogId + " not found in DB.");
            return;
        }

        ProductOrderStatus productOrderStatus = toClassify.get();

        if (skipSRSDPost)
            log.info("Skipping POST request to Smart Resource & Service Discovery.");
        else {
            if (!classifyProductOrder(catalogId, cwJson)) {
                productOrderStatus.setStatus(ProductOrderStatesEnum.CLASSIFICATION_FAILED);
                productOrderStatusRepository.save(productOrderStatus);
                return;
            }

            productOrderStatus.setStatus(ProductOrderStatesEnum.CLASSIFIED);
            productOrderStatusRepository.save(productOrderStatus);
        }

        if (skipSCLCMPost)
            log.info("Skipping POST request to Smart Contract Lifecycle Manager.");
        else {
            if (!publishProductOrder(catalogId, pwJson)) {
                productOrderStatus.setStatus(ProductOrderStatesEnum.PUBLISHING_FAILED);
                productOrderStatusRepository.save(productOrderStatus);
                return;
            }

            productOrderStatus.setStatus(ProductOrderStatesEnum.PUBLISHED);
            productOrderStatusRepository.save(productOrderStatus);
        }
    }

    private boolean classifyProductOrder(String catalogId, String cwJson) {

        log.info("Classifying Product Order with id " + catalogId + ".");

        String request = protocol + srsdHostname + ":" + srsdPort + srsdRequestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(cwJson);
        } catch (UnsupportedEncodingException e) {
            log.error("Cannot encode json body in String Entity: " + e.getMessage());
            return false;
        }

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            log.error("Cannot send POST request: " + e.getMessage());
            return false;
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            HttpEntity httpEntity = response.getEntity();
            String responseString;

            if (httpEntity != null) {
                try {
                    responseString = EntityUtils.toString(httpEntity, "UTF-8");
                } catch (IOException e) {
                    log.error("Product Order with id " + catalogId + " not classified; Error description unavailable:"
                            + e.getMessage());
                    return false;
                }
            } else
                responseString = "Error description unavailable.";

            log.error("Product Order with id " + catalogId + " not classified; " + responseString);
            return false;
        }

        log.info("Product Order with id " + catalogId + " classified.");

        return true;
    }

    private boolean publishProductOrder(String catalogId, String pwJson) {

        log.info("Publishing Product Order with id " + catalogId + ".");

        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request);

        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(pwJson);
        } catch (UnsupportedEncodingException e) {
            log.error("Cannot encode json body in String Entity: " + e.getMessage());
            return false;
        }

        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            log.error("Cannot send POST request: " + e.getMessage());
            return false;
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            HttpEntity httpEntity = response.getEntity();
            String responseString;

            if (httpEntity != null) {
                try {
                    responseString = EntityUtils.toString(httpEntity, "UTF-8");
                } catch (IOException e) {
                    log.error("Product Order with id " + catalogId + " not published; Error description unavailable:"
                            + e.getMessage());
                    return false;
                }
            } else
                responseString = "Error description unavailable.";

            log.error("Product Order with id " + catalogId + " not published; " + responseString);
            return false;
        }

        log.info("Product Order with id " + catalogId + " published.");

        return true;
    }

}
