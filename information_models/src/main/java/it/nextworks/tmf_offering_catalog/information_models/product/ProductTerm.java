package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Description of a productTerm linked to this product. This represent a commitment with a duration
 */
@ApiModel(description = "Description of a productTerm linked to this product. This represent a commitment with a duration")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "product_terms")
public class ProductTerm {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("duration")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quantity_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Quantity duration = null;

    @JsonProperty("validFor")
    private TimePeriod validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @ApiModelProperty(hidden = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductTerm description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Description of the productTerm
     *
     * @return description
     **/
    @ApiModelProperty(value = "Description of the productTerm")


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductTerm name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the productTerm
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the productTerm")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductTerm duration(Quantity duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Duration of the productTerm
     *
     * @return duration
     **/
    @ApiModelProperty(value = "Duration of the productTerm")
    @Valid
    public Quantity getDuration() {
        return duration;
    }

    public void setDuration(Quantity duration) {
        this.duration = duration;
    }

    public ProductTerm validFor(TimePeriod validFor) {
        this.validFor = validFor;
        return this;
    }

    /**
     * productTerm validity period
     *
     * @return validFor
     **/
    @ApiModelProperty(value = "productTerm validity period")
    @Valid
    public TimePeriod getValidFor() {
        return validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    public ProductTerm baseType(String baseType) {
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

    public ProductTerm schemaLocation(String schemaLocation) {
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

    public ProductTerm type(String type) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductTerm productTerm = (ProductTerm) o;
        return Objects.equals(this.description, productTerm.description) &&
                Objects.equals(this.name, productTerm.name) &&
                Objects.equals(this.duration, productTerm.duration) &&
                Objects.equals(this.validFor, productTerm.validFor) &&
                Objects.equals(this.baseType, productTerm.baseType) &&
                Objects.equals(this.schemaLocation, productTerm.schemaLocation) &&
                Objects.equals(this.type, productTerm.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, duration, validFor, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductTerm {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
        sb.append("    validFor: ").append(toIndentedString(validFor)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
