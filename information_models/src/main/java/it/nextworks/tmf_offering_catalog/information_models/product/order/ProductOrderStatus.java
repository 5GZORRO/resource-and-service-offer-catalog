package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import it.nextworks.tmf_offering_catalog.information_models.common.converter.ProductOrderStatesEnumConverter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Objects;

@ApiModel(description = "Represent entities related to the status of Product Orders.")
@Validated
@Entity
@Table(name = "product_order_states")
public class ProductOrderStatus {

    @Id
    @JsonProperty("catalogId")
    @Column(name = "catalog_id")
    private String catalogId;

    @JsonProperty("did")
    private String did;

    @JsonProperty("status")
    @Convert(converter = ProductOrderStatesEnumConverter.class)
    private ProductOrderStatesEnum status;

    public ProductOrderStatus catalogId(String catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public ProductOrderStatus did(String did) {
        this.did = did;
        return this;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDid() {
        return did;
    }

    public ProductOrderStatus status(ProductOrderStatesEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProductOrderStatesEnum status) {
        this.status = status;
    }

    public ProductOrderStatesEnum getStatus() {
        return status;
    }

    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ProductOrderStatus productOfferingStatus = (ProductOrderStatus) o;
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

        return "class ProductOfferingStatus {\n" +
                "    catalogId: " + toIndentedString(catalogId) + "\n" +
                "    did: " + toIndentedString(did) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {

        if (o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }

}
