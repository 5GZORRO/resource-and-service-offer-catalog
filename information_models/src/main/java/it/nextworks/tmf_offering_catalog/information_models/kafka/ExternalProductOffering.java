package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;

import java.util.Map;

public class ExternalProductOffering {

    public class Invitation {}

    @JsonProperty("productOffering")
    private final ProductOffering productOffering;

    @JsonProperty("invitations")
    private final Map<String, Invitation> invitations;

    @JsonProperty("did")
    private final String did;

    @JsonCreator
    public ExternalProductOffering(@JsonProperty("productOffering") ProductOffering productOffering,
                                   @JsonProperty("invitations") Map<String, Invitation> invitations,
                                   @JsonProperty("did") String did) {
        this.productOffering = productOffering;
        this.invitations = invitations;
        this.did = did;
    }

    public ProductOffering getProductOffering() { return productOffering; }

    public Map<String, Invitation> getInvitations() { return invitations; }

    public String getDid() { return did; }
}
