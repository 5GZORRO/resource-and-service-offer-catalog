package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This resource is used to manage address validation request and response Skipped properties: id,href
 */
@ApiModel(description = "This resource is used to manage address validation request and response Skipped properties: id,href")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
public class GeographicAddressValidationUpdate {

    @JsonProperty("provideAlternative")
    private Boolean provideAlternative = null;

    @JsonProperty("validationDate")
    private OffsetDateTime validationDate = null;

    @JsonProperty("validationResult")
    private String validationResult = null;

    @JsonProperty("alternateGeographicAddress")
    @Valid
    private List<GeographicAddress> alternateGeographicAddress = null;

    @JsonProperty("state")
    private TaskStateType state = null;

    @JsonProperty("submittedGeographicAddress")
    private GeographicAddress submittedGeographicAddress = null;

    @JsonProperty("validGeographicAddress")
    private GeographicAddress validGeographicAddress = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public GeographicAddressValidationUpdate provideAlternative(Boolean provideAlternative) {
        this.provideAlternative = provideAlternative;
        return this;
    }

    /**
     * Indicator provided by the requester to specify if alternate addresses must be provided in case of partial or fail result.
     *
     * @return provideAlternative
     **/
    @ApiModelProperty(value = "Indicator provided by the requester to specify if alternate addresses must be provided in case of partial or fail result.")


    public Boolean isProvideAlternative() {
        return provideAlternative;
    }

    public void setProvideAlternative(Boolean provideAlternative) {
        this.provideAlternative = provideAlternative;
    }

    public GeographicAddressValidationUpdate validationDate(OffsetDateTime validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    /**
     * Date when the address validation is performed
     *
     * @return validationDate
     **/
    @ApiModelProperty(value = "Date when the address validation is performed")

    @Valid

    public OffsetDateTime getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(OffsetDateTime validationDate) {
        this.validationDate = validationDate;
    }

    public GeographicAddressValidationUpdate validationResult(String validationResult) {
        this.validationResult = validationResult;
        return this;
    }

    /**
     * Result of the address validation (success, partial, fails)
     *
     * @return validationResult
     **/
    @ApiModelProperty(value = "Result of the address validation (success, partial, fails)")


    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public GeographicAddressValidationUpdate alternateGeographicAddress(List<GeographicAddress> alternateGeographicAddress) {
        this.alternateGeographicAddress = alternateGeographicAddress;
        return this;
    }

    public GeographicAddressValidationUpdate addAlternateGeographicAddressItem(GeographicAddress alternateGeographicAddressItem) {
        if (this.alternateGeographicAddress == null) {
            this.alternateGeographicAddress = new ArrayList<GeographicAddress>();
        }
        this.alternateGeographicAddress.add(alternateGeographicAddressItem);
        return this;
    }

    /**
     * Get alternateGeographicAddress
     *
     * @return alternateGeographicAddress
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<GeographicAddress> getAlternateGeographicAddress() {
        return alternateGeographicAddress;
    }

    public void setAlternateGeographicAddress(List<GeographicAddress> alternateGeographicAddress) {
        this.alternateGeographicAddress = alternateGeographicAddress;
    }

    public GeographicAddressValidationUpdate state(TaskStateType state) {
        this.state = state;
        return this;
    }

    /**
     * Get state
     *
     * @return state
     **/
    @ApiModelProperty(value = "")

    @Valid

    public TaskStateType getState() {
        return state;
    }

    public void setState(TaskStateType state) {
        this.state = state;
    }

    public GeographicAddressValidationUpdate submittedGeographicAddress(GeographicAddress submittedGeographicAddress) {
        this.submittedGeographicAddress = submittedGeographicAddress;
        return this;
    }

    /**
     * the address as submitted to validation
     *
     * @return submittedGeographicAddress
     **/
    @ApiModelProperty(value = "the address as submitted to validation")

    @Valid

    public GeographicAddress getSubmittedGeographicAddress() {
        return submittedGeographicAddress;
    }

    public void setSubmittedGeographicAddress(GeographicAddress submittedGeographicAddress) {
        this.submittedGeographicAddress = submittedGeographicAddress;
    }

    public GeographicAddressValidationUpdate validGeographicAddress(GeographicAddress validGeographicAddress) {
        this.validGeographicAddress = validGeographicAddress;
        return this;
    }

    /**
     * the correct form of the validated address in case of validation success
     *
     * @return validGeographicAddress
     **/
    @ApiModelProperty(value = "the correct form of the validated address in case of validation success")

    @Valid

    public GeographicAddress getValidGeographicAddress() {
        return validGeographicAddress;
    }

    public void setValidGeographicAddress(GeographicAddress validGeographicAddress) {
        this.validGeographicAddress = validGeographicAddress;
    }

    public GeographicAddressValidationUpdate baseType(String baseType) {
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

    public GeographicAddressValidationUpdate schemaLocation(String schemaLocation) {
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

    public GeographicAddressValidationUpdate type(String type) {
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
        GeographicAddressValidationUpdate geographicAddressValidationUpdate = (GeographicAddressValidationUpdate) o;
        return Objects.equals(this.provideAlternative, geographicAddressValidationUpdate.provideAlternative) &&
                Objects.equals(this.validationDate, geographicAddressValidationUpdate.validationDate) &&
                Objects.equals(this.validationResult, geographicAddressValidationUpdate.validationResult) &&
                Objects.equals(this.alternateGeographicAddress, geographicAddressValidationUpdate.alternateGeographicAddress) &&
                Objects.equals(this.state, geographicAddressValidationUpdate.state) &&
                Objects.equals(this.submittedGeographicAddress, geographicAddressValidationUpdate.submittedGeographicAddress) &&
                Objects.equals(this.validGeographicAddress, geographicAddressValidationUpdate.validGeographicAddress) &&
                Objects.equals(this.baseType, geographicAddressValidationUpdate.baseType) &&
                Objects.equals(this.schemaLocation, geographicAddressValidationUpdate.schemaLocation) &&
                Objects.equals(this.type, geographicAddressValidationUpdate.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provideAlternative, validationDate, validationResult, alternateGeographicAddress, state, submittedGeographicAddress, validGeographicAddress, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressValidationUpdate {\n");

        sb.append("    provideAlternative: ").append(toIndentedString(provideAlternative)).append("\n");
        sb.append("    validationDate: ").append(toIndentedString(validationDate)).append("\n");
        sb.append("    validationResult: ").append(toIndentedString(validationResult)).append("\n");
        sb.append("    alternateGeographicAddress: ").append(toIndentedString(alternateGeographicAddress)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    submittedGeographicAddress: ").append(toIndentedString(submittedGeographicAddress)).append("\n");
        sb.append("    validGeographicAddress: ").append(toIndentedString(validGeographicAddress)).append("\n");
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
