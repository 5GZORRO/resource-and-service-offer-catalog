package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOffering;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPrice;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductSpecification;
import it.nextworks.tmf_offering_catalog.information_models.resource.ResourceSpecification;
import it.nextworks.tmf_offering_catalog.information_models.service.ServiceSpecification;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExternalProductOffering {

    public class Invitation {}

    public class VerifiableCredential {}

    public enum UpdateType {
        CREATE_UPDATE,
        RETIRE
    }

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

    @JsonProperty
    private final List<GeographicAddress> geographicAddresses;

    @JsonProperty("identifier")
    private final String identifier;

    @JsonProperty("verifiableCredentials")
    private final Collection<VerifiableCredential> verifiableCredentials;

    @JsonProperty("updateType")
    private final UpdateType updateType;

    @JsonProperty("deduplicationId")
    private final String deduplicationId;

    @JsonProperty("did")
    private final String did;

    @JsonProperty("deleted")
    private final boolean deleted;

    @JsonCreator
    public ExternalProductOffering(@JsonProperty("productOffering") ProductOffering productOffering,
                                   @JsonProperty("productOfferingPrices") List<ProductOfferingPrice> productOfferingPrices,
                                   @JsonProperty("productSpecification") ProductSpecification productSpecification,
                                   @JsonProperty("resourceSpecifications") List<ResourceSpecification> resourceSpecifications,
                                   @JsonProperty("serviceSpecifications") List<ServiceSpecification> serviceSpecifications,
                                   @JsonProperty("invitations") Map<String, Invitation> invitations,
                                   @JsonProperty("geographicAddresses") List<GeographicAddress> geographicAddresses,
                                   @JsonProperty("identifier") String identifier,
                                   @JsonProperty("verifiableCredentials") Collection<VerifiableCredential> verifiableCredentials,
                                   @JsonProperty("updateType") UpdateType updateType,
                                   @JsonProperty("deduplicationId") String deduplicationId,
                                   @JsonProperty("did") String did,
                                   @JsonProperty("deleted") boolean deleted) {
        this.productOffering        = productOffering;
        this.productOfferingPrices  = productOfferingPrices;
        this.productSpecification   = productSpecification;
        this.resourceSpecifications = resourceSpecifications;
        this.serviceSpecifications  = serviceSpecifications;
        this.invitations            = invitations;
        this.geographicAddresses    = geographicAddresses;
        this.identifier             = identifier;
        this.verifiableCredentials  = verifiableCredentials;
        this.updateType             = updateType;
        this.deduplicationId        = deduplicationId;
        this.did                    = did;
        this.deleted = deleted;
    }

    public ProductOffering getProductOffering() { return productOffering; }

    public List<ProductOfferingPrice> getProductOfferingPrices() { return productOfferingPrices; }

    public ProductSpecification getProductSpecification() { return productSpecification; }

    public List<ResourceSpecification> getResourceSpecifications() { return resourceSpecifications; }

    public List<ServiceSpecification> getServiceSpecifications() { return serviceSpecifications; }

    public Map<String, Invitation> getInvitations() { return invitations; }

    public List<GeographicAddress> getGeographicAddresses() {
        return geographicAddresses;
    }

    public String getIdentifier() { return identifier; }

    public Collection<VerifiableCredential> getVerifiableCredentials() { return verifiableCredentials; }

    public UpdateType getUpdateType() { return updateType; }

    public String getDeduplicationId() { return deduplicationId; }

    public String getDid() { return did; }

    public boolean isDeleted() {
        return deleted;
    }
}
