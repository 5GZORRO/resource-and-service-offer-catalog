package it.nextworks.tmf_offering_catalog.information_models.party;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.AttachmentRefOrValue;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Represents our registration of information used as proof of identity by an organization
 */
@ApiModel(description = "Represents our registration of information used as proof of identity by an organization")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-07T10:10:44.963Z")


public class OrganizationIdentification   {
  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("attachment")
  private AttachmentRefOrValue attachment = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("identificationId")
  private String identificationId = null;

  @JsonProperty("identificationType")
  private String identificationType = null;

  @JsonProperty("issuingAuthority")
  private String issuingAuthority = null;

  @JsonProperty("issuingDate")
  private OffsetDateTime issuingDate = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("validFor")
  private TimePeriod validFor = null;

  public OrganizationIdentification baseType(String baseType) {
    this.baseType = baseType;
    return this;
  }

  /**
   * When sub-classing, this defines the super-class
   * @return baseType
  **/
  @ApiModelProperty(value = "When sub-classing, this defines the super-class")


  public String getBaseType() {
    return baseType;
  }

  public void setBaseType(String baseType) {
    this.baseType = baseType;
  }

  public OrganizationIdentification schemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
    return this;
  }

  /**
   * A URI to a JSON-Schema file that defines additional attributes and relationships
   * @return schemaLocation
  **/
  @ApiModelProperty(value = "A URI to a JSON-Schema file that defines additional attributes and relationships")


  public String getSchemaLocation() {
    return schemaLocation;
  }

  public void setSchemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
  }

  public OrganizationIdentification type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public OrganizationIdentification attachment(AttachmentRefOrValue attachment) {
    this.attachment = attachment;
    return this;
  }

  /**
   * Get attachment
   * @return attachment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AttachmentRefOrValue getAttachment() {
    return attachment;
  }

  public void setAttachment(AttachmentRefOrValue attachment) {
    this.attachment = attachment;
  }

  public OrganizationIdentification href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Unique reference of the entity
   * @return href
  **/
  @ApiModelProperty(value = "Unique reference of the entity")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public OrganizationIdentification identificationId(String identificationId) {
    this.identificationId = identificationId;
    return this;
  }

  /**
   * Identifier
   * @return identificationId
  **/
  @ApiModelProperty(value = "Identifier")


  public String getIdentificationId() {
    return identificationId;
  }

  public void setIdentificationId(String identificationId) {
    this.identificationId = identificationId;
  }

  public OrganizationIdentification identificationType(String identificationType) {
    this.identificationType = identificationType;
    return this;
  }

  /**
   * Type of identification information used to identify the company in a country or internationally
   * @return identificationType
  **/
  @ApiModelProperty(value = "Type of identification information used to identify the company in a country or internationally")


  public String getIdentificationType() {
    return identificationType;
  }

  public void setIdentificationType(String identificationType) {
    this.identificationType = identificationType;
  }

  public OrganizationIdentification issuingAuthority(String issuingAuthority) {
    this.issuingAuthority = issuingAuthority;
    return this;
  }

  /**
   * Authority which has issued the identifier (chamber of commerce...)
   * @return issuingAuthority
  **/
  @ApiModelProperty(value = "Authority which has issued the identifier (chamber of commerce...)")


  public String getIssuingAuthority() {
    return issuingAuthority;
  }

  public void setIssuingAuthority(String issuingAuthority) {
    this.issuingAuthority = issuingAuthority;
  }

  public OrganizationIdentification issuingDate(OffsetDateTime issuingDate) {
    this.issuingDate = issuingDate;
    return this;
  }

  /**
   * Date at which the identifier was issued
   * @return issuingDate
  **/
  @ApiModelProperty(value = "Date at which the identifier was issued")

  @Valid

  public OffsetDateTime getIssuingDate() {
    return issuingDate;
  }

  public void setIssuingDate(OffsetDateTime issuingDate) {
    this.issuingDate = issuingDate;
  }

  public OrganizationIdentification uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Get uuid
   * @return uuid
  **/
  @ApiModelProperty(value = "")


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public OrganizationIdentification validFor(TimePeriod validFor) {
    this.validFor = validFor;
    return this;
  }

  /**
   * The period for which the identification information is valid.
   * @return validFor
  **/
  @ApiModelProperty(value = "The period for which the identification information is valid.")

  @Valid

  public TimePeriod getValidFor() {
    return validFor;
  }

  public void setValidFor(TimePeriod validFor) {
    this.validFor = validFor;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationIdentification organizationIdentification = (OrganizationIdentification) o;
    return Objects.equals(this.baseType, organizationIdentification.baseType) &&
        Objects.equals(this.schemaLocation, organizationIdentification.schemaLocation) &&
        Objects.equals(this.type, organizationIdentification.type) &&
        Objects.equals(this.attachment, organizationIdentification.attachment) &&
        Objects.equals(this.href, organizationIdentification.href) &&
        Objects.equals(this.identificationId, organizationIdentification.identificationId) &&
        Objects.equals(this.identificationType, organizationIdentification.identificationType) &&
        Objects.equals(this.issuingAuthority, organizationIdentification.issuingAuthority) &&
        Objects.equals(this.issuingDate, organizationIdentification.issuingDate) &&
        Objects.equals(this.uuid, organizationIdentification.uuid) &&
        Objects.equals(this.validFor, organizationIdentification.validFor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, attachment, href, identificationId, identificationType, issuingAuthority, issuingDate, uuid, validFor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationIdentification {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    identificationId: ").append(toIndentedString(identificationId)).append("\n");
    sb.append("    identificationType: ").append(toIndentedString(identificationType)).append("\n");
    sb.append("    issuingAuthority: ").append(toIndentedString(issuingAuthority)).append("\n");
    sb.append("    issuingDate: ").append(toIndentedString(issuingDate)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    validFor: ").append(toIndentedString(validFor)).append("\n");
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

