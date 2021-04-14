package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;

import java.util.List;
import java.util.Map;

public class ExternalProductOffering {

    public class Invitation {}

    @JsonProperty("productOffering")
    private final ProductOffering productOffering;

    @JsonProperty("productOfferingPrices")
    private final List<ProductOfferingPrice> productOfferingPrices;

    @JsonProperty("productSpecification")
    private final ProductSpecification productSpecification;

    @JsonProperty("resourceSpecifications")
    private final List<ResourceSpecification> resourceSpecifications;

    @JsonProperty("serviceSpecifications")
    private final List<ServiceSpecification> serviceSpecifications;

    @JsonProperty("invitations")
    private final Map<String, Invitation> invitations;

    @JsonProperty("did")
    private final String did;

    @JsonCreator
    public ExternalProductOffering(@JsonProperty("productOffering") ProductOffering productOffering,
                                   @JsonProperty("productOfferingPrices") List<ProductOfferingPrice> productOfferingPrices,
                                   @JsonProperty("productSpecification") ProductSpecification productSpecification,
                                   @JsonProperty("resourceSpecifications") List<ResourceSpecification> resourceSpecifications,
                                   @JsonProperty("serviceSpecifications") List<ServiceSpecification> serviceSpecifications,
                                   @JsonProperty("invitations") Map<String, Invitation> invitations,
                                   @JsonProperty("did") String did) {
        this.productOffering        = productOffering;
        this.productOfferingPrices  = productOfferingPrices;
        this.productSpecification   = productSpecification;
        this.resourceSpecifications = resourceSpecifications;
        this.serviceSpecifications  = serviceSpecifications;
        this.invitations            = invitations;
        this.did                    = did;
    }

    public ProductOffering getProductOffering() { return productOffering; }

    public List<ProductOfferingPrice> getProductOfferingPrices() { return productOfferingPrices; }

    public ProductSpecification getProductSpecification() { return productSpecification; }

    public List<ResourceSpecification> getResourceSpecifications() { return resourceSpecifications; }

    public List<ServiceSpecification> getServiceSpecifications() { return serviceSpecifications; }

    public Map<String, Invitation> getInvitations() { return invitations; }

    public String getDid() { return did; }
}
