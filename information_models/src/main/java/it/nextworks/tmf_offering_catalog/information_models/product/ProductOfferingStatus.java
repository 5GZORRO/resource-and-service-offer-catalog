package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Objects;

@ApiModel(description = "Represent entities related to the status of Product Offerings.")
@Validated

@Entity
@Table(name = "product_offering_states")
public class ProductOfferingStatus {

    @Id
    @JsonProperty("catalogId")
    @Column(name = "catalog_id")
    private String catalogId;

    @JsonProperty("did")
    private String did;

    @JsonProperty("status")
    private ProductOfferingStatesEnum status;

    public ProductOfferingStatus catalogId(String catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    public void setCatalogId(String catalogId) { this.catalogId = catalogId; }

    public String getCatalogId() { return catalogId; }

    public ProductOfferingStatus did(String did) {
        this.did = did;
        return this;
    }

    public void setDid(String did) { this.did = did; }

    public String getDid() { return did; }

    public ProductOfferingStatus status(ProductOfferingStatesEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProductOfferingStatesEnum status) { this.status = status; }

    public ProductOfferingStatesEnum getStatus() { return status; }

    @Override
    public boolean equals(java.lang.Object o) {

        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ProductOfferingStatus productOfferingStatus = (ProductOfferingStatus) o;
        return Objects.equals(this.catalogId, productOfferingStatus.catalogId) &&
                Objects.equals(this.did, productOfferingStatus.did) &&
                Objects.equals(this.status, productOfferingStatus.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId, did, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductOfferingStatus {\n");

        sb.append("    catalogId: ").append(toIndentedString(catalogId)).append("\n");
        sb.append("    did: ").append(toIndentedString(did)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {

        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
