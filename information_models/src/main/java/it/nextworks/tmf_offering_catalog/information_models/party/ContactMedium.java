package it.nextworks.tmf_offering_catalog.information_models.party;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Indicates the contact medium that could be used to contact the party.
 */
@ApiModel(description = "Indicates the contact medium that could be used to contact the party.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-07T10:10:44.963Z")

@Entity
@Table(name = "contact_mediums")
public class ContactMedium {

  @JsonProperty("@baseType")
  @Column(name = "base_type")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  @Column(name = "schema_location")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("characteristic")
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "characteristic_id", referencedColumnName = "uuid")
  private MediumCharacteristic characteristic = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("mediumType")
  @Column(name = "medium_type")
  private String mediumType = null;

  @JsonProperty("preferred")
  private Boolean preferred = null;

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("validFor")
  @Column(name = "valid_for")
  @Embedded
  private TimePeriod validFor = null;

  public ContactMedium baseType(String baseType) {
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

  public ContactMedium schemaLocation(String schemaLocation) {
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

  public ContactMedium type(String type) {
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

  public ContactMedium characteristic(MediumCharacteristic characteristic) {
    this.characteristic = characteristic;
    return this;
  }

  /**
   * Any additional characteristic(s) of this contact medium
   * @return characteristic
  **/
  @ApiModelProperty(required = true, value = "Any additional characteristic(s) of this contact medium")
  @NotNull

  @Valid

  public MediumCharacteristic getCharacteristic() {
    return characteristic;
  }

  public void setCharacteristic(MediumCharacteristic characteristic) {
    this.characteristic = characteristic;
  }

  public ContactMedium href(String href) {
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

  public ContactMedium mediumType(String mediumType) {
    this.mediumType = mediumType;
    return this;
  }

  /**
   * Type of the contact medium, such as: email address, telephone number, postal address
   * @return mediumType
  **/
  @ApiModelProperty(required = true, value = "Type of the contact medium, such as: email address, telephone number, postal address")
  @NotNull


  public String getMediumType() {
    return mediumType;
  }

  public void setMediumType(String mediumType) {
    this.mediumType = mediumType;
  }

  public ContactMedium preferred(Boolean preferred) {
    this.preferred = preferred;
    return this;
  }

  /**
   * If true, indicates that is the preferred contact medium
   * @return preferred
  **/
  @ApiModelProperty(value = "If true, indicates that is the preferred contact medium")


  public Boolean isPreferred() {
    return preferred;
  }

  public void setPreferred(Boolean preferred) {
    this.preferred = preferred;
  }

  public ContactMedium uuid(String uuid) {
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

  public ContactMedium validFor(TimePeriod validFor) {
    this.validFor = validFor;
    return this;
  }

  /**
   * The time period that the contact medium is valid for
   * @return validFor
  **/
  @ApiModelProperty(value = "The time period that the contact medium is valid for")

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
    ContactMedium contactMedium = (ContactMedium) o;
    return Objects.equals(this.baseType, contactMedium.baseType) &&
        Objects.equals(this.schemaLocation, contactMedium.schemaLocation) &&
        Objects.equals(this.type, contactMedium.type) &&
        Objects.equals(this.characteristic, contactMedium.characteristic) &&
        Objects.equals(this.href, contactMedium.href) &&
        Objects.equals(this.mediumType, contactMedium.mediumType) &&
        Objects.equals(this.preferred, contactMedium.preferred) &&
        Objects.equals(this.uuid, contactMedium.uuid) &&
        Objects.equals(this.validFor, contactMedium.validFor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, characteristic, href, mediumType, preferred, uuid, validFor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactMedium {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    characteristic: ").append(toIndentedString(characteristic)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    mediumType: ").append(toIndentedString(mediumType)).append("\n");
    sb.append("    preferred: ").append(toIndentedString(preferred)).append("\n");
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

