package it.nextworks.tmf_offering_catalogue.information_models.resource;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

/**
 * ResourceCandidate reference: A resource candidate is an entity that makes a ResourceSpecification available to a catalog.
 */
@ApiModel(description = "ResourceCandidate reference: A resource candidate is an entity that makes a ResourceSpecification available to a catalog.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-10T10:00:31.056Z")

@Entity
@Table(name = "resource_candidate_refs")
public class ResourceCandidateRef {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "jpda_id")
  private Long jpaId;

  @JsonProperty("@baseType")
  @Column(name = "base_type")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  @Column(name = "schema_location")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("version")
  private String version = null;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resource_category_id")
  private ResourceCategory resourceCategory;

  public ResourceCandidateRef baseType(String baseType) {
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

  public ResourceCandidateRef schemaLocation(String schemaLocation) {
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

  public ResourceCandidateRef type(String type) {
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

  public ResourceCandidateRef href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Reference of the resource candidate
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the resource candidate")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public ResourceCandidateRef id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the resource candidate
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the resource candidate")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ResourceCandidateRef name(String name) {
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

  public ResourceCandidateRef uuid(String uuid) {
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

  public ResourceCandidateRef version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the resource candidate
   * @return version
  **/
  @ApiModelProperty(value = "Version of the resource candidate")


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceCandidateRef resourceCandidateRef = (ResourceCandidateRef) o;
    return Objects.equals(this.baseType, resourceCandidateRef.baseType) &&
        Objects.equals(this.schemaLocation, resourceCandidateRef.schemaLocation) &&
        Objects.equals(this.type, resourceCandidateRef.type) &&
        Objects.equals(this.href, resourceCandidateRef.href) &&
        Objects.equals(this.id, resourceCandidateRef.id) &&
        Objects.equals(this.name, resourceCandidateRef.name) &&
        Objects.equals(this.uuid, resourceCandidateRef.uuid) &&
        Objects.equals(this.version, resourceCandidateRef.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, href, id, name, uuid, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceCandidateRef {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

