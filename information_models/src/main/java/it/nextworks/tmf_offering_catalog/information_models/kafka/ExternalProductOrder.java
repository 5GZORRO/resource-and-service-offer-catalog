package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;

public class ExternalProductOrder {

    @JsonProperty("productOrder")
    private final ProductOrder productOrder;

    @JsonProperty("did")
    private final String did;

    @JsonCreator
    public ExternalProductOrder(@JsonProperty("productOrder") ProductOrder productOrder, @JsonProperty("did") String did) {
        this.productOrder = productOrder;
        this.did = did;
    }

    public ProductOrder getProductOrder() {
        return productOrder;
    }

    public String getDid() {
        return did;
    }

}
