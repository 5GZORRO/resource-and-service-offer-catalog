package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * It&#39;s a Quote that has been executed previously
 */
@ApiModel(description = "It's a Quote that has been executed previously")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "quote_refs")
public class QuoteRef {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@referredType")
    private String referredType = null;

    public QuoteRef id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique identifier of a related entity.
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Unique identifier of a related entity.")
    @NotNull


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuoteRef href(String href) {
        this.href = href;
        return this;
    }

    /**
     * Reference of the related entity.
     *
     * @return href
     **/
    @ApiModelProperty(value = "Reference of the related entity.")


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public QuoteRef name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the related entity.
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the related entity.")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuoteRef baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     *
     * @return baseType
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")


    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public QuoteRef schemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
    }

    /**
     * A URI to a JSON-Schema file that defines additional attributes and relationships
     *
     * @return schemaLocation
     **/
    @ApiModelProperty(value = "A URI to a JSON-Schema file that defines additional attributes and relationships")


    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public QuoteRef type(String type) {
        this.type = type;
        return this;
    }

    /**
     * When sub-classing, this defines the sub-class entity name
     *
     * @return type
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the sub-class entity name")


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QuoteRef referredType(String referredType) {
        this.referredType = referredType;
        return this;
    }

    /**
     * The actual type of the target instance when needed for disambiguation.
     *
     * @return referredType
     **/
    @ApiModelProperty(value = "The actual type of the target instance when needed for disambiguation.")


    public String getReferredType() {
        return referredType;
    }

    public void setReferredType(String referredType) {
        this.referredType = referredType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuoteRef quoteRef = (QuoteRef) o;
        return Objects.equals(this.id, quoteRef.id) &&
                Objects.equals(this.href, quoteRef.href) &&
                Objects.equals(this.name, quoteRef.name) &&
                Objects.equals(this.baseType, quoteRef.baseType) &&
                Objects.equals(this.schemaLocation, quoteRef.schemaLocation) &&
                Objects.equals(this.type, quoteRef.type) &&
                Objects.equals(this.referredType, quoteRef.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, baseType, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class QuoteRef {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    referredType: ").append(toIndentedString(referredType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
