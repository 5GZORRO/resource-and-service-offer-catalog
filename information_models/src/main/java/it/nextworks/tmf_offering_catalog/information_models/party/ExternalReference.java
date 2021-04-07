package it.nextworks.tmf_offering_catalog.information_models.party;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * External reference of the individual or reference in other system
 */
@ApiModel(description = "External reference of the individual or reference in other system")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-07T10:10:44.963Z")

@Entity
@Table(name = "external_references")
public class ExternalReference {

  @JsonProperty("@baseType")
  @Column(name = "base_type")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  @Column(name = "schema_location")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("externalReferenceType")
  @Column(name = "external_reference_type")
  private String externalReferenceType = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  public ExternalReference baseType(String baseType) {
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

  public ExternalReference schemaLocation(String schemaLocation) {
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

  public ExternalReference type(String type) {
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

  public ExternalReference externalReferenceType(String externalReferenceType) {
    this.externalReferenceType = externalReferenceType;
    return this;
  }

  /**
   * Type of the external reference
   * @return externalReferenceType
  **/
  @ApiModelProperty(value = "Type of the external reference")


  public String getExternalReferenceType() {
    return externalReferenceType;
  }

  public void setExternalReferenceType(String externalReferenceType) {
    this.externalReferenceType = externalReferenceType;
  }

  public ExternalReference href(String href) {
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

  public ExternalReference name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the entity
   * @return name
  **/
  @ApiModelProperty(value = "Name of the entity")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ExternalReference uuid(String uuid) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExternalReference externalReference = (ExternalReference) o;
    return Objects.equals(this.baseType, externalReference.baseType) &&
        Objects.equals(this.schemaLocation, externalReference.schemaLocation) &&
        Objects.equals(this.type, externalReference.type) &&
        Objects.equals(this.externalReferenceType, externalReference.externalReferenceType) &&
        Objects.equals(this.href, externalReference.href) &&
        Objects.equals(this.name, externalReference.name) &&
        Objects.equals(this.uuid, externalReference.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, externalReferenceType, href, name, uuid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExternalReference {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    externalReferenceType: ").append(toIndentedString(externalReferenceType)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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

