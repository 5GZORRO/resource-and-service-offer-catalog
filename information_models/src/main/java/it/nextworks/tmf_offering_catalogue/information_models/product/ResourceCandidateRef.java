package it.nextworks.tmf_offering_catalogue.information_models.product;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ResourceCandidate reference: A resource candidate is an entity that makes a ResourceSpecification available to a catalog.
 */
@ApiModel(description = "ResourceCandidate reference: A resource candidate is an entity that makes a ResourceSpecification available to a catalog.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-09T15:56:41.618Z")




public class ResourceCandidateRef   {
  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@referredType")
  private String referredType = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("lastUpdate")
  private String lastUpdate = null;

  @JsonProperty("lifecycleStatus")
  private String lifecycleStatus = null;

  /**
   * Gets or Sets lifecycleStatusEnum
   */
  public enum LifecycleStatusEnumEnum {
    IN_STUDY("In study"),
    
    IN_DESIGN("In design"),
    
    IN_TEST("In test"),
    
    ACTIVE("Active"),
    
    LAUNCHED("Launched"),
    
    RETIRED("Retired"),
    
    OBSOLETE("Obsolete"),
    
    REJECTED("Rejected");

    private String value;

    LifecycleStatusEnumEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static LifecycleStatusEnumEnum fromValue(String text) {
      for (LifecycleStatusEnumEnum b : LifecycleStatusEnumEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("lifecycleStatusEnum")
  private LifecycleStatusEnumEnum lifecycleStatusEnum = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("validFor")
  private TimePeriod validFor = null;

  @JsonProperty("version")
  private String version = null;

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

  public ResourceCandidateRef referredType(String referredType) {
    this.referredType = referredType;
    return this;
  }

  /**
   * The actual type of the target instance when needed for disambiguation.
   * @return referredType
  **/
  @ApiModelProperty(value = "The actual type of the target instance when needed for disambiguation.")


  public String getReferredType() {
    return referredType;
  }

  public void setReferredType(String referredType) {
    this.referredType = referredType;
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

  public ResourceCandidateRef description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of this catalog
   * @return description
  **/
  @ApiModelProperty(value = "Description of this catalog")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ResourceCandidateRef href(String href) {
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

  public ResourceCandidateRef id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the resource candidate
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the resource candidate")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ResourceCandidateRef lastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
    return this;
  }

  /**
   * Date and time of the last update
   * @return lastUpdate
  **/
  @ApiModelProperty(value = "Date and time of the last update")


  public String getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public ResourceCandidateRef lifecycleStatus(String lifecycleStatus) {
    this.lifecycleStatus = lifecycleStatus;
    return this;
  }

  /**
   * Used to indicate the current lifecycle status
   * @return lifecycleStatus
  **/
  @ApiModelProperty(value = "Used to indicate the current lifecycle status")


  public String getLifecycleStatus() {
    return lifecycleStatus;
  }

  public void setLifecycleStatus(String lifecycleStatus) {
    this.lifecycleStatus = lifecycleStatus;
  }

  public ResourceCandidateRef lifecycleStatusEnum(LifecycleStatusEnumEnum lifecycleStatusEnum) {
    this.lifecycleStatusEnum = lifecycleStatusEnum;
    return this;
  }

  /**
   * Get lifecycleStatusEnum
   * @return lifecycleStatusEnum
  **/
  @ApiModelProperty(value = "")


  public LifecycleStatusEnumEnum getLifecycleStatusEnum() {
    return lifecycleStatusEnum;
  }

  public void setLifecycleStatusEnum(LifecycleStatusEnumEnum lifecycleStatusEnum) {
    this.lifecycleStatusEnum = lifecycleStatusEnum;
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

  public ResourceCandidateRef validFor(TimePeriod validFor) {
    this.validFor = validFor;
    return this;
  }

  /**
   * The period for which the entity is valid
   * @return validFor
  **/
  @ApiModelProperty(value = "The period for which the entity is valid")

  @Valid

  public TimePeriod getValidFor() {
    return validFor;
  }

  public void setValidFor(TimePeriod validFor) {
    this.validFor = validFor;
  }

  public ResourceCandidateRef version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Entity version
   * @return version
  **/
  @ApiModelProperty(value = "Entity version")


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
        Objects.equals(this.referredType, resourceCandidateRef.referredType) &&
        Objects.equals(this.schemaLocation, resourceCandidateRef.schemaLocation) &&
        Objects.equals(this.type, resourceCandidateRef.type) &&
        Objects.equals(this.description, resourceCandidateRef.description) &&
        Objects.equals(this.href, resourceCandidateRef.href) &&
        Objects.equals(this.id, resourceCandidateRef.id) &&
        Objects.equals(this.lastUpdate, resourceCandidateRef.lastUpdate) &&
        Objects.equals(this.lifecycleStatus, resourceCandidateRef.lifecycleStatus) &&
        Objects.equals(this.lifecycleStatusEnum, resourceCandidateRef.lifecycleStatusEnum) &&
        Objects.equals(this.name, resourceCandidateRef.name) &&
        Objects.equals(this.uuid, resourceCandidateRef.uuid) &&
        Objects.equals(this.validFor, resourceCandidateRef.validFor) &&
        Objects.equals(this.version, resourceCandidateRef.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, referredType, schemaLocation, type, description, href, id, lastUpdate, lifecycleStatus, lifecycleStatusEnum, name, uuid, validFor, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceCandidateRef {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    referredType: ").append(toIndentedString(referredType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    lifecycleStatus: ").append(toIndentedString(lifecycleStatus)).append("\n");
    sb.append("    lifecycleStatusEnum: ").append(toIndentedString(lifecycleStatusEnum)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    validFor: ").append(toIndentedString(validFor)).append("\n");
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

