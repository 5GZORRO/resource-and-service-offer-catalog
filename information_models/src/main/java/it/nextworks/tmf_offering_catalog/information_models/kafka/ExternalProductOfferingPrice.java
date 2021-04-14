package it.nextworks.tmf_offering_catalog.information_models.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "external_product_offering_prices")
public class ExternalProductOfferingPrice {

    @Id
    @JsonProperty("id")
    private String id;

    public ExternalProductOfferingPrice() {}

    public ExternalProductOfferingPrice(String id) { this.id = id; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ExternalProductOfferingPrice externalProductOfferingPrice = (ExternalProductOfferingPrice) o;
        return Objects.equals(this.id, externalProductOfferingPrice.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ExternalProductOfferingPrice {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
