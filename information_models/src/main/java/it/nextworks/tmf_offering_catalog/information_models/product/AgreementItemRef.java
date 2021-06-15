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

@ApiModel(description = "Agreement reference. An agreement represents a contract or arrangement, either written or verbal and sometimes enforceable by law, such as a service level agreement or a customer price agreement. An agreement involves a number of other business entities, such as products, services, and resources and/or their specifications.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "agreement_item_refs")
public class AgreementItemRef {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("agreementItemId")
    private String agreementItemId = null;

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

    public AgreementItemRef id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique identifier of a related entity.
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Unique identifier of a related entity.", hidden = true)
    @NotNull


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AgreementItemRef href(String href) {
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

    public AgreementItemRef agreementItemId(String agreementItemId) {
        this.agreementItemId = agreementItemId;
        return this;
    }

    /**
     * Identifier of the agreement
     *
     * @return agreementItemId
     **/
    @ApiModelProperty(value = "Identifier of the agreement")


    public String getAgreementItemId() {
        return agreementItemId;
    }

    public void setAgreementItemId(String agreementItemId) {
        this.agreementItemId = agreementItemId;
    }

    public AgreementItemRef name(String name) {
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

    public AgreementItemRef baseType(String baseType) {
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

    public AgreementItemRef schemaLocation(String schemaLocation) {
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

    public AgreementItemRef type(String type) {
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

    public AgreementItemRef referredType(String referredType) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AgreementItemRef agreementItemRef = (AgreementItemRef) o;
        return Objects.equals(this.id, agreementItemRef.id) &&
                Objects.equals(this.href, agreementItemRef.href) &&
                Objects.equals(this.agreementItemId, agreementItemRef.agreementItemId) &&
                Objects.equals(this.name, agreementItemRef.name) &&
                Objects.equals(this.baseType, agreementItemRef.baseType) &&
                Objects.equals(this.schemaLocation, agreementItemRef.schemaLocation) &&
                Objects.equals(this.type, agreementItemRef.type) &&
                Objects.equals(this.referredType, agreementItemRef.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, agreementItemId, name, baseType, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {

        return "class AgreementItemRef {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    href: " + toIndentedString(href) + "\n" +
                "    agreementItemId: " + toIndentedString(agreementItemId) + "\n" +
                "    name: " + toIndentedString(name) + "\n" +
                "    baseType: " + toIndentedString(baseType) + "\n" +
                "    schemaLocation: " + toIndentedString(schemaLocation) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
                "    referredType: " + toIndentedString(referredType) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
