package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * It&#39;s a Quote item that has been executed previously.
 */
@ApiModel(description = "It's a Quote item that has been executed previously.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "quote_item_refs")
public class QuoteItemRef {

    @JsonProperty("id")
    private String id = null;

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("quoteHref")
    private String quoteHref = null;

    @JsonProperty("quoteId")
    private String quoteId = null;

    @JsonProperty("quoteName")
    private String quoteName = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@referredType")
    private String referredType = null;

    public QuoteItemRef id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Id of an item of a quote
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Id of an item of a quote")
    @NotNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public QuoteItemRef href(String href) {
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

    public QuoteItemRef name(String name) {
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

    public QuoteItemRef quoteHref(String quoteHref) {
        this.quoteHref = quoteHref;
        return this;
    }

    /**
     * Reference of the related entity.
     *
     * @return quoteHref
     **/
    @ApiModelProperty(value = "Reference of the related entity.")
    public String getQuoteHref() {
        return quoteHref;
    }

    public void setQuoteHref(String quoteHref) {
        this.quoteHref = quoteHref;
    }

    public QuoteItemRef quoteId(String quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    /**
     * Unique identifier of a related entity.
     *
     * @return quoteId
     **/
    @ApiModelProperty(required = true, value = "Unique identifier of a related entity.")
    @NotNull
    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public QuoteItemRef quoteName(String quoteName) {
        this.quoteName = quoteName;
        return this;
    }

    /**
     * Name of the related entity.
     *
     * @return quoteName
     **/
    @ApiModelProperty(value = "Name of the related entity.")
    public String getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public QuoteItemRef baseType(String baseType) {
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

    public QuoteItemRef schemaLocation(String schemaLocation) {
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

    public QuoteItemRef type(String type) {
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

    public QuoteItemRef referredType(String referredType) {
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
        QuoteItemRef quoteItemRef = (QuoteItemRef) o;
        return Objects.equals(this.id, quoteItemRef.id) &&
                Objects.equals(this.href, quoteItemRef.href) &&
                Objects.equals(this.name, quoteItemRef.name) &&
                Objects.equals(this.quoteHref, quoteItemRef.quoteHref) &&
                Objects.equals(this.quoteId, quoteItemRef.quoteId) &&
                Objects.equals(this.quoteName, quoteItemRef.quoteName) &&
                Objects.equals(this.baseType, quoteItemRef.baseType) &&
                Objects.equals(this.schemaLocation, quoteItemRef.schemaLocation) &&
                Objects.equals(this.type, quoteItemRef.type) &&
                Objects.equals(this.referredType, quoteItemRef.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, quoteHref, quoteId, quoteName, baseType, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class QuoteItemRef {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    quoteHref: ").append(toIndentedString(quoteHref)).append("\n");
        sb.append("    quoteId: ").append(toIndentedString(quoteId)).append("\n");
        sb.append("    quoteName: ").append(toIndentedString(quoteName)).append("\n");
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
