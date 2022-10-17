package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;

public class ExternalProductOrder {

    @JsonProperty("productOrder")
    private final ProductOrder productOrder;

    @JsonProperty("did")
    private final String did;

    @JsonProperty("deleted")
    private final boolean deleted;

    @JsonCreator
    public ExternalProductOrder(@JsonProperty("productOrder") ProductOrder productOrder, @JsonProperty("did") String did, @JsonProperty("deleted") boolean deleted) {
        this.productOrder = productOrder;
        this.did = did;
        this.deleted = deleted;
    }

    public ProductOrder getProductOrder() {
        return productOrder;
    }

    public String getDid() {
        return did;
    }

    public boolean isDeleted() {
        return deleted;
    }

}
