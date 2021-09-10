package it.nextworks.tmf_offering_catalog.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.tmf_offering_catalog.common.exception.NotExistingEntityException;
import it.nextworks.tmf_offering_catalog.common.exception.ProductOrderDeleteScLCMException;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderStatusRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ProductOrderCommunicationService {

    public static class Order {

        @JsonProperty("token")
        private final String token;

        @JsonProperty("type")
        private final String type;

        @JsonProperty("claims")
        private final List<?> claims;

        @JsonProperty("handler_url")
        private final String handlerUrl;

        @JsonCreator
        public Order(@JsonProperty("token") String token,
                     @JsonProperty("type") String type,
                     @JsonProperty("claims") List<?> claims,
                     @JsonProperty("handler_url") String handlerUrl) {
            this.token = token;
            this.type = type;
            this.claims = claims;
            this.handlerUrl = handlerUrl;
        }
    }

    public static class PublicationWrapper {

        @JsonProperty("productOrder")
        private final ProductOrder productOrder;

        @JsonProperty("invitations")
        private final Map<String, ?> invitations;

        @JsonProperty("verifiableCredentials")
        private final Collection<?> verifiableCredentials;

        @JsonCreator
        public PublicationWrapper(@JsonProperty("productOrder") ProductOrder productOrder,
                                  @JsonProperty("invitations") Map<String, ?> invitations,
                                  @JsonProperty("verifiableCredentials") Collection<?> verifiableCredentials) {
            this.productOrder = productOrder;
            this.invitations = invitations;
            this.verifiableCredentials = verifiableCredentials;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(ProductOrderCommunicationService.class);

    private static final String protocol = "http://";

    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;

    @Value("${sc_lcm.hostname}")
    private String scLcmHostname;
    @Value("${sc_lcm.port}")
    private String scLcmPort;
    @Value("${sc_lcm.product_order.sc_lcm_request_path}")
    private String scLcmRequestPath;

    @Value("${skip_sc_lcm_post}")
    private boolean skipSCLCMPost;

    private final ObjectMapper objectMapper;

    @Autowired
    private ProductOrderStatusRepository productOrderStatusRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductOfferingPriceService productOfferingPriceService;

    @Autowired
    private PublishProductOrderService publishProductOrderService;

    @Autowired
    public ProductOrderCommunicationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void publish(ProductOrder productOrder) throws IOException {

//        List<ProductOfferingPrice> productOfferingPrices = getProductOfferingPrices(productOrder);

        String pwJson = null;
        if (!skipSCLCMPost) {
            pwJson = objectMapper.writeValueAsString(new ProductOrderCommunicationService.PublicationWrapper(
                    productOrder,
                    null,
                    null
            ));
        }

        publishProductOrderService.publish(productOrder.getId(), pwJson);
    }

//    private List<ProductOfferingPrice> getProductOfferingPrices(ProductOrder po) {
//        if (po.getOrderTotalPrice() == null)
//            return null;
//
//        List<OrderPrice> orderPrices = po.getOrderTotalPrice();
//
//        List<ProductOfferingPriceRef> productOfferingPriceRefs = new ArrayList<>();
//        List<ProductOfferingPrice> productOfferingPrices = new ArrayList<>();
//
//        orderPrices.forEach((orderPrice) -> productOfferingPriceRefs.add(orderPrice.getProductOfferingPrice()));
//
//        for (ProductOfferingPriceRef priceRef : productOfferingPriceRefs) {
//            String id = priceRef.getId();
//            try {
//                productOfferingPrices.add(productOfferingPriceService.get(id));
//            } catch (NotExistingEntityException e) {
//                log.warn("ProductOrderPrice with id " + id + " not found in DB.");
//            }
//        }
//
//        return productOfferingPrices;
//    }

    public void deleteProductOrder(String catalogId) throws NotExistingEntityException, IOException, ProductOrderDeleteScLCMException {

        log.info("Sending delete Product Order request.");

//        Optional<ProductOrderStatus> toDelete = productOrderStatusRepository.findById(catalogId);
//        if (!toDelete.isPresent())
//            throw new NotExistingEntityException("Product Order Status for id " + catalogId + " not found in DB.");
//
//        ProductOrderStatus productOrderStatus = toDelete.get();
//        ProductOrderStatesEnum productOrderStatesEnum = productOrderStatus.getStatus();
//        if (productOrderStatesEnum == ProductOrderStatesEnum.PUBLISHING_FAILED) {
//            productOrderStatusRepository.delete(productOrderStatus);
//
//            log.info("Delete Product Order request accepted.");
//            return;
//        }

        String request = protocol + scLcmHostname + ":" + scLcmPort + scLcmRequestPath + catalogId;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(request);

        httpDelete.setHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpDelete);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new ProductOrderDeleteScLCMException("The Smart Contract LCM entity did not accept the delete request.");
        }

//        productOrderStatusRepository.delete(productOrderStatus);

        log.info("Delete Product Order request accepted.");
    }
}
