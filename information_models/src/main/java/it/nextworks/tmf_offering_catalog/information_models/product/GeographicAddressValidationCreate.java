package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This resource is used to manage address validation request and response Skipped properties: id,href,alternateGeographicAddress,validGeographicAddress,state,validationResult,validationDate
 */
@ApiModel(description = "This resource is used to manage address validation request and response Skipped properties: id,href,alternateGeographicAddress,validGeographicAddress,state,validationResult,validationDate")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
public class GeographicAddressValidationCreate {

    @JsonProperty("provideAlternative")
    private Boolean provideAlternative = null;

    @JsonProperty("submittedGeographicAddress")
    private GeographicAddress submittedGeographicAddress = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public GeographicAddressValidationCreate provideAlternative(Boolean provideAlternative) {
        this.provideAlternative = provideAlternative;
        return this;
    }

    /**
     * Indicator provided by the requester to specify if alternate addresses must be provided in case of partial or fail result.
     *
     * @return provideAlternative
     **/
    @ApiModelProperty(required = true, value = "Indicator provided by the requester to specify if alternate addresses must be provided in case of partial or fail result.")
    @NotNull


    public Boolean isProvideAlternative() {
        return provideAlternative;
    }

    public void setProvideAlternative(Boolean provideAlternative) {
        this.provideAlternative = provideAlternative;
    }

    public GeographicAddressValidationCreate submittedGeographicAddress(GeographicAddress submittedGeographicAddress) {
        this.submittedGeographicAddress = submittedGeographicAddress;
        return this;
    }

    /**
     * the address as submitted to validation
     *
     * @return submittedGeographicAddress
     **/
    @ApiModelProperty(required = true, value = "the address as submitted to validation")
    @NotNull

    @Valid

    public GeographicAddress getSubmittedGeographicAddress() {
        return submittedGeographicAddress;
    }

    public void setSubmittedGeographicAddress(GeographicAddress submittedGeographicAddress) {
        this.submittedGeographicAddress = submittedGeographicAddress;
    }

    public GeographicAddressValidationCreate baseType(String baseType) {
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

    public GeographicAddressValidationCreate schemaLocation(String schemaLocation) {
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

    public GeographicAddressValidationCreate type(String type) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicAddressValidationCreate geographicAddressValidationCreate = (GeographicAddressValidationCreate) o;
        return Objects.equals(this.provideAlternative, geographicAddressValidationCreate.provideAlternative) &&
                Objects.equals(this.submittedGeographicAddress, geographicAddressValidationCreate.submittedGeographicAddress) &&
                Objects.equals(this.baseType, geographicAddressValidationCreate.baseType) &&
                Objects.equals(this.schemaLocation, geographicAddressValidationCreate.schemaLocation) &&
                Objects.equals(this.type, geographicAddressValidationCreate.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provideAlternative, submittedGeographicAddress, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressValidationCreate {\n");

        sb.append("    provideAlternative: ").append(toIndentedString(provideAlternative)).append("\n");
        sb.append("    submittedGeographicAddress: ").append(toIndentedString(submittedGeographicAddress)).append("\n");
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
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
