package it.nextworks.tmf_offering_catalog.information_models.party;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * An individual might be evaluated for its worthiness and this evaluation might be based on a credit rating given by a credit agency.
 */
@ApiModel(description = "An individual might be evaluated for its worthiness and this evaluation might be based on a credit rating given by a credit agency.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-07T10:10:44.963Z")


public class PartyCreditProfile   {
  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("creditAgencyName")
  private String creditAgencyName = null;

  @JsonProperty("creditAgencyType")
  private String creditAgencyType = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("ratingReference")
  private String ratingReference = null;

  @JsonProperty("ratingScore")
  private Integer ratingScore = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("validFor")
  private TimePeriod validFor = null;

  public PartyCreditProfile baseType(String baseType) {
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

  public PartyCreditProfile schemaLocation(String schemaLocation) {
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

  public PartyCreditProfile type(String type) {
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

  public PartyCreditProfile creditAgencyName(String creditAgencyName) {
    this.creditAgencyName = creditAgencyName;
    return this;
  }

  /**
   * Name of the credit agency giving the score
   * @return creditAgencyName
  **/
  @ApiModelProperty(value = "Name of the credit agency giving the score")


  public String getCreditAgencyName() {
    return creditAgencyName;
  }

  public void setCreditAgencyName(String creditAgencyName) {
    this.creditAgencyName = creditAgencyName;
  }

  public PartyCreditProfile creditAgencyType(String creditAgencyType) {
    this.creditAgencyType = creditAgencyType;
    return this;
  }

  /**
   * Type of the credit agency giving the score
   * @return creditAgencyType
  **/
  @ApiModelProperty(value = "Type of the credit agency giving the score")


  public String getCreditAgencyType() {
    return creditAgencyType;
  }

  public void setCreditAgencyType(String creditAgencyType) {
    this.creditAgencyType = creditAgencyType;
  }

  public PartyCreditProfile href(String href) {
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

  public PartyCreditProfile ratingReference(String ratingReference) {
    this.ratingReference = ratingReference;
    return this;
  }

  /**
   * Reference corresponding to the credit rating
   * @return ratingReference
  **/
  @ApiModelProperty(value = "Reference corresponding to the credit rating")


  public String getRatingReference() {
    return ratingReference;
  }

  public void setRatingReference(String ratingReference) {
    this.ratingReference = ratingReference;
  }

  public PartyCreditProfile ratingScore(Integer ratingScore) {
    this.ratingScore = ratingScore;
    return this;
  }

  /**
   * A measure of a party’s creditworthiness calculated on the basis of a combination of factors such as their income and credit history
   * @return ratingScore
  **/
  @ApiModelProperty(value = "A measure of a party’s creditworthiness calculated on the basis of a combination of factors such as their income and credit history")


  public Integer getRatingScore() {
    return ratingScore;
  }

  public void setRatingScore(Integer ratingScore) {
    this.ratingScore = ratingScore;
  }

  public PartyCreditProfile uuid(String uuid) {
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

  public PartyCreditProfile validFor(TimePeriod validFor) {
    this.validFor = validFor;
    return this;
  }

  /**
   * Get validFor
   * @return validFor
  **/
  @ApiModelProperty(value = "")

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
    PartyCreditProfile partyCreditProfile = (PartyCreditProfile) o;
    return Objects.equals(this.baseType, partyCreditProfile.baseType) &&
        Objects.equals(this.schemaLocation, partyCreditProfile.schemaLocation) &&
        Objects.equals(this.type, partyCreditProfile.type) &&
        Objects.equals(this.creditAgencyName, partyCreditProfile.creditAgencyName) &&
        Objects.equals(this.creditAgencyType, partyCreditProfile.creditAgencyType) &&
        Objects.equals(this.href, partyCreditProfile.href) &&
        Objects.equals(this.ratingReference, partyCreditProfile.ratingReference) &&
        Objects.equals(this.ratingScore, partyCreditProfile.ratingScore) &&
        Objects.equals(this.uuid, partyCreditProfile.uuid) &&
        Objects.equals(this.validFor, partyCreditProfile.validFor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, creditAgencyName, creditAgencyType, href, ratingReference, ratingScore, uuid, validFor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartyCreditProfile {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    creditAgencyName: ").append(toIndentedString(creditAgencyName)).append("\n");
    sb.append("    creditAgencyType: ").append(toIndentedString(creditAgencyType)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    ratingReference: ").append(toIndentedString(ratingReference)).append("\n");
    sb.append("    ratingScore: ").append(toIndentedString(ratingScore)).append("\n");
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

