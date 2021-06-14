package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.Quantity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Description of a productTerm linked to this orderItem. This represent a commitment with a duration
 */
@ApiModel(description = "Description of a productTerm linked to this orderItem. This represent a commitment with a duration")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
public class OrderTerm {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("duration")
    private Quantity duration = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public OrderTerm description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Description of the productOrderTerm
     *
     * @return description
     **/
    @ApiModelProperty(value = "Description of the productOrderTerm")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderTerm name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the productOrderTerm
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the productOrderTerm")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderTerm duration(Quantity duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Duration of the productOrderTerm
     *
     * @return duration
     **/
    @ApiModelProperty(value = "Duration of the productOrderTerm")
    @Valid
    public Quantity getDuration() {
        return duration;
    }

    public void setDuration(Quantity duration) {
        this.duration = duration;
    }

    public OrderTerm baseType(String baseType) {
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

    public OrderTerm schemaLocation(String schemaLocation) {
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

    public OrderTerm type(String type) {
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
        OrderTerm orderTerm = (OrderTerm) o;
        return Objects.equals(this.description, orderTerm.description) &&
                Objects.equals(this.name, orderTerm.name) &&
                Objects.equals(this.duration, orderTerm.duration) &&
                Objects.equals(this.baseType, orderTerm.baseType) &&
                Objects.equals(this.schemaLocation, orderTerm.schemaLocation) &&
                Objects.equals(this.type, orderTerm.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, duration, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrderTerm {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
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

