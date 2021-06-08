package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * This resource is used to manage address validation request and response
 */
@ApiModel(description = "This resource is used to manage address validation request and response")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
@Entity
@Table(name = "geographic_address_validations")
public class GeographicAddressValidation {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("validationDate")
    @Column(name = "validation_date")
    private OffsetDateTime validationDate = null;

    @JsonProperty("validationResult")
    @Column(name = "validation_result")
    private String validationResult = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@schemaLocation")
    @Column(name = "schema_location")
    private String schemaLocation = null;

    @JsonProperty("validGeographicAddress")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "valid_geographic_address_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private GeographicAddress validGeographicAddress = null;

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

    public GeographicAddressValidation status(Status status) {
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

    public void setState(Status status) {
        this.status = status;
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
                Objects.equals(this.status, geographicAddressValidation.status) &&
                Objects.equals(this.validationDate, geographicAddressValidation.validationDate) &&
                Objects.equals(this.validationResult, geographicAddressValidation.validationResult) &&
                Objects.equals(this.validGeographicAddress, geographicAddressValidation.validGeographicAddress) &&
                Objects.equals(this.schemaLocation, geographicAddressValidation.schemaLocation) &&
                Objects.equals(this.type, geographicAddressValidation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, status, validationDate, validationResult, validGeographicAddress, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressValidation {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    validationDate: ").append(toIndentedString(validationDate)).append("\n");
        sb.append("    validationResult: ").append(toIndentedString(validationResult)).append("\n");
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
