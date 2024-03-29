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
 * It's a productOfferingQualification item that has been executed previously.
 */
@ApiModel(description = "It's a productOfferingQualification item that has been executed previously.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "product_offering_qualification_item_refs")
public class ProductOfferingQualificationItemRef {

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

    @JsonProperty("productOfferingQualificationHref")
    private String productOfferingQualificationHref = null;

    @JsonProperty("productOfferingQualificationId")
    private String productOfferingQualificationId = null;

    @JsonProperty("productOfferingQualificationName")
    private String productOfferingQualificationName = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@referredType")
    private String referredType = null;

    public ProductOfferingQualificationItemRef id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Id of an item of a product offering qualification
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Id of an item of a product offering qualification")
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

    public ProductOfferingQualificationItemRef href(String href) {
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

    public ProductOfferingQualificationItemRef name(String name) {
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

    public ProductOfferingQualificationItemRef productOfferingQualificationHref(String productOfferingQualificationHref) {
        this.productOfferingQualificationHref = productOfferingQualificationHref;
        return this;
    }

    /**
     * Reference of the related entity.
     *
     * @return productOfferingQualificationHref
     **/
    @ApiModelProperty(value = "Reference of the related entity.")
    public String getProductOfferingQualificationHref() {
        return productOfferingQualificationHref;
    }

    public void setProductOfferingQualificationHref(String productOfferingQualificationHref) {
        this.productOfferingQualificationHref = productOfferingQualificationHref;
    }

    public ProductOfferingQualificationItemRef productOfferingQualificationId(String productOfferingQualificationId) {
        this.productOfferingQualificationId = productOfferingQualificationId;
        return this;
    }

    /**
     * Unique identifier of a related entity.
     *
     * @return productOfferingQualificationId
     **/
    @ApiModelProperty(required = true, value = "Unique identifier of a related entity.")
    @NotNull
    public String getProductOfferingQualificationId() {
        return productOfferingQualificationId;
    }

    public void setProductOfferingQualificationId(String productOfferingQualificationId) {
        this.productOfferingQualificationId = productOfferingQualificationId;
    }

    public ProductOfferingQualificationItemRef productOfferingQualificationName(String productOfferingQualificationName) {
        this.productOfferingQualificationName = productOfferingQualificationName;
        return this;
    }

    /**
     * Name of the related entity.
     *
     * @return productOfferingQualificationName
     **/
    @ApiModelProperty(value = "Name of the related entity.")
    public String getProductOfferingQualificationName() {
        return productOfferingQualificationName;
    }

    public void setProductOfferingQualificationName(String productOfferingQualificationName) {
        this.productOfferingQualificationName = productOfferingQualificationName;
    }

    public ProductOfferingQualificationItemRef baseType(String baseType) {
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

    public ProductOfferingQualificationItemRef schemaLocation(String schemaLocation) {
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

    public ProductOfferingQualificationItemRef type(String type) {
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

    public ProductOfferingQualificationItemRef referredType(String referredType) {
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
        ProductOfferingQualificationItemRef productOfferingQualificationItemRef = (ProductOfferingQualificationItemRef) o;
        return Objects.equals(this.id, productOfferingQualificationItemRef.id) &&
                Objects.equals(this.href, productOfferingQualificationItemRef.href) &&
                Objects.equals(this.name, productOfferingQualificationItemRef.name) &&
                Objects.equals(this.productOfferingQualificationHref, productOfferingQualificationItemRef.productOfferingQualificationHref) &&
                Objects.equals(this.productOfferingQualificationId, productOfferingQualificationItemRef.productOfferingQualificationId) &&
                Objects.equals(this.productOfferingQualificationName, productOfferingQualificationItemRef.productOfferingQualificationName) &&
                Objects.equals(this.baseType, productOfferingQualificationItemRef.baseType) &&
                Objects.equals(this.schemaLocation, productOfferingQualificationItemRef.schemaLocation) &&
                Objects.equals(this.type, productOfferingQualificationItemRef.type) &&
                Objects.equals(this.referredType, productOfferingQualificationItemRef.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, productOfferingQualificationHref, productOfferingQualificationId, productOfferingQualificationName, baseType, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductOfferingQualificationItemRef {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    productOfferingQualificationHref: ").append(toIndentedString(productOfferingQualificationHref)).append("\n");
        sb.append("    productOfferingQualificationId: ").append(toIndentedString(productOfferingQualificationId)).append("\n");
        sb.append("    productOfferingQualificationName: ").append(toIndentedString(productOfferingQualificationName)).append("\n");
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
