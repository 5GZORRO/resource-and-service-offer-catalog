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
 * This resource is used to manage address validation request and response
 */
@ApiModel(description = "This resource is used to manage address validation request and response")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
public class GeographicAddressValidation {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

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

    public GeographicAddressValidation id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique identifier of the Address Validation
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique identifier of the Address Validation")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeographicAddressValidation href(String href) {
        this.href = href;
        return this;
    }

    /**
     * An URI used to access to the address validation resource
     *
     * @return href
     **/
    @ApiModelProperty(value = "An URI used to access to the address validation resource")


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public GeographicAddressValidation provideAlternative(Boolean provideAlternative) {
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

    public GeographicAddressValidation validationDate(OffsetDateTime validationDate) {
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

    public GeographicAddressValidation validationResult(String validationResult) {
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

    public GeographicAddressValidation alternateGeographicAddress(List<GeographicAddress> alternateGeographicAddress) {
        this.alternateGeographicAddress = alternateGeographicAddress;
        return this;
    }

    public GeographicAddressValidation addAlternateGeographicAddressItem(GeographicAddress alternateGeographicAddressItem) {
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

    public GeographicAddressValidation state(TaskStateType state) {
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

    public GeographicAddressValidation submittedGeographicAddress(GeographicAddress submittedGeographicAddress) {
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

    public GeographicAddressValidation validGeographicAddress(GeographicAddress validGeographicAddress) {
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

    public GeographicAddressValidation baseType(String baseType) {
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

    public GeographicAddressValidation schemaLocation(String schemaLocation) {
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

    public GeographicAddressValidation type(String type) {
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
        GeographicAddressValidation geographicAddressValidation = (GeographicAddressValidation) o;
        return Objects.equals(this.id, geographicAddressValidation.id) &&
                Objects.equals(this.href, geographicAddressValidation.href) &&
                Objects.equals(this.provideAlternative, geographicAddressValidation.provideAlternative) &&
                Objects.equals(this.validationDate, geographicAddressValidation.validationDate) &&
                Objects.equals(this.validationResult, geographicAddressValidation.validationResult) &&
                Objects.equals(this.alternateGeographicAddress, geographicAddressValidation.alternateGeographicAddress) &&
                Objects.equals(this.state, geographicAddressValidation.state) &&
                Objects.equals(this.submittedGeographicAddress, geographicAddressValidation.submittedGeographicAddress) &&
                Objects.equals(this.validGeographicAddress, geographicAddressValidation.validGeographicAddress) &&
                Objects.equals(this.baseType, geographicAddressValidation.baseType) &&
                Objects.equals(this.schemaLocation, geographicAddressValidation.schemaLocation) &&
                Objects.equals(this.type, geographicAddressValidation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, provideAlternative, validationDate, validationResult, alternateGeographicAddress, state, submittedGeographicAddress, validGeographicAddress, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressValidation {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
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
