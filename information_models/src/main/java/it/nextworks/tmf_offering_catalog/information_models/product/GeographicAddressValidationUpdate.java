package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import java.util.Objects;

/**
 * This resource is used to manage address validation request and response Skipped properties: id,href
 */
@ApiModel(description = "This resource is used to manage address validation request and response Skipped properties: id,href")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
public class GeographicAddressValidationUpdate {

    @JsonProperty("validationDate")
    private OffsetDateTime validationDate = null;

    @JsonProperty("validationResult")
    private String validationResult = null;

    @JsonProperty("state")
    private Status status = null;

    @JsonProperty("validGeographicAddress")
    private GeographicAddress validGeographicAddress = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

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

    public GeographicAddressValidationUpdate status(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Get state
     *
     * @return state
     **/
    @ApiModelProperty(value = "")
    @Valid
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return Objects.equals(this.validationDate, geographicAddressValidationUpdate.validationDate) &&
                Objects.equals(this.validationResult, geographicAddressValidationUpdate.validationResult) &&
                Objects.equals(this.status, geographicAddressValidationUpdate.status) &&
                Objects.equals(this.validGeographicAddress, geographicAddressValidationUpdate.validGeographicAddress) &&
                Objects.equals(this.schemaLocation, geographicAddressValidationUpdate.schemaLocation) &&
                Objects.equals(this.type, geographicAddressValidationUpdate.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationDate, validationResult, status, validGeographicAddress, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressValidationUpdate {\n");

        sb.append("    validationDate: ").append(toIndentedString(validationDate)).append("\n");
        sb.append("    validationResult: ").append(toIndentedString(validationResult)).append("\n");
        sb.append("    state: ").append(toIndentedString(status)).append("\n");
        sb.append("    validGeographicAddress: ").append(toIndentedString(validGeographicAddress)).append("\n");
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
