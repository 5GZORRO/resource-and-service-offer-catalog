package it.nextworks.tmf_offering_catalog.information_models.party;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Parent references of an organization in a structure of organizations.
 */
@ApiModel(description = "Parent references of an organization in a structure of organizations.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-07T10:10:44.963Z")


public class OrganizationParentRelationship   {
  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("organization")
  private OrganizationRef organization = null;

  @JsonProperty("relationshipType")
  private String relationshipType = null;

  @JsonProperty("uuid")
  private String uuid = null;

  public OrganizationParentRelationship baseType(String baseType) {
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

  public OrganizationParentRelationship schemaLocation(String schemaLocation) {
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

  public OrganizationParentRelationship type(String type) {
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

  public OrganizationParentRelationship href(String href) {
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

  public OrganizationParentRelationship name(String name) {
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

  public OrganizationParentRelationship organization(OrganizationRef organization) {
    this.organization = organization;
    return this;
  }

  /**
   * Get organization
   * @return organization
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OrganizationRef getOrganization() {
    return organization;
  }

  public void setOrganization(OrganizationRef organization) {
    this.organization = organization;
  }

  public OrganizationParentRelationship relationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
    return this;
  }

  /**
   * Type of the relationship. Could be juridical, hierarchical, geographical, functional for example.
   * @return relationshipType
  **/
  @ApiModelProperty(value = "Type of the relationship. Could be juridical, hierarchical, geographical, functional for example.")


  public String getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public OrganizationParentRelationship uuid(String uuid) {
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
    OrganizationParentRelationship organizationParentRelationship = (OrganizationParentRelationship) o;
    return Objects.equals(this.baseType, organizationParentRelationship.baseType) &&
        Objects.equals(this.schemaLocation, organizationParentRelationship.schemaLocation) &&
        Objects.equals(this.type, organizationParentRelationship.type) &&
        Objects.equals(this.href, organizationParentRelationship.href) &&
        Objects.equals(this.name, organizationParentRelationship.name) &&
        Objects.equals(this.organization, organizationParentRelationship.organization) &&
        Objects.equals(this.relationshipType, organizationParentRelationship.relationshipType) &&
        Objects.equals(this.uuid, organizationParentRelationship.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, href, name, organization, relationshipType, uuid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationParentRelationship {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    organization: ").append(toIndentedString(organization)).append("\n");
    sb.append("    relationshipType: ").append(toIndentedString(relationshipType)).append("\n");
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

