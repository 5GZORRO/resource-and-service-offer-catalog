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

/**
 * An aggregation, migration, substitution, dependency or exclusivity relationship between/among productSpecificationCharacteristics.
 */
@ApiModel(description = "An aggregation, migration, substitution, dependency or exclusivity relationship between/among productSpecificationCharacteristics.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-09T15:56:41.618Z")




public class ProductSpecificationCharacteristicRelationship   {
  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("charSpecSeq")
  private Integer charSpecSeq = null;

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

  @JsonProperty("relationshipType")
  private String relationshipType = null;

  @JsonProperty("uuid")
  private String uuid = null;

  @JsonProperty("validFor")
  private TimePeriod validFor = null;

  @JsonProperty("version")
  private String version = null;

  public ProductSpecificationCharacteristicRelationship baseType(String baseType) {
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

  public ProductSpecificationCharacteristicRelationship schemaLocation(String schemaLocation) {
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

  public ProductSpecificationCharacteristicRelationship type(String type) {
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

  public ProductSpecificationCharacteristicRelationship charSpecSeq(Integer charSpecSeq) {
    this.charSpecSeq = charSpecSeq;
    return this;
  }

  /**
   * The order in which a CharacteristicSpecification appears within another CharacteristicSpecification that defines a grouping of CharacteristicSpecifications.  For example, a grouping may represent the name of an individual. The given name is first, the middle name is second, and the last name is third.
   * @return charSpecSeq
  **/
  @ApiModelProperty(value = "The order in which a CharacteristicSpecification appears within another CharacteristicSpecification that defines a grouping of CharacteristicSpecifications.  For example, a grouping may represent the name of an individual. The given name is first, the middle name is second, and the last name is third.")


  public Integer getCharSpecSeq() {
    return charSpecSeq;
  }

  public void setCharSpecSeq(Integer charSpecSeq) {
    this.charSpecSeq = charSpecSeq;
  }

  public ProductSpecificationCharacteristicRelationship description(String description) {
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

  public ProductSpecificationCharacteristicRelationship href(String href) {
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

  public ProductSpecificationCharacteristicRelationship id(String id) {
    this.id = id;
    return this;
  }

  /**
   * the identifier of the associated product specification
   * @return id
  **/
  @ApiModelProperty(value = "the identifier of the associated product specification")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProductSpecificationCharacteristicRelationship lastUpdate(String lastUpdate) {
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

  public ProductSpecificationCharacteristicRelationship lifecycleStatus(String lifecycleStatus) {
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

  public ProductSpecificationCharacteristicRelationship lifecycleStatusEnum(LifecycleStatusEnumEnum lifecycleStatusEnum) {
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

  public ProductSpecificationCharacteristicRelationship name(String name) {
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

  public ProductSpecificationCharacteristicRelationship relationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
    return this;
  }

  /**
   * Type of relationship such as aggregation, migration, substitution, dependency, exclusivity
   * @return relationshipType
  **/
  @ApiModelProperty(value = "Type of relationship such as aggregation, migration, substitution, dependency, exclusivity")


  public String getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public ProductSpecificationCharacteristicRelationship uuid(String uuid) {
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

  public ProductSpecificationCharacteristicRelationship validFor(TimePeriod validFor) {
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

  public ProductSpecificationCharacteristicRelationship version(String version) {
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
    ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship = (ProductSpecificationCharacteristicRelationship) o;
    return Objects.equals(this.baseType, productSpecificationCharacteristicRelationship.baseType) &&
        Objects.equals(this.schemaLocation, productSpecificationCharacteristicRelationship.schemaLocation) &&
        Objects.equals(this.type, productSpecificationCharacteristicRelationship.type) &&
        Objects.equals(this.charSpecSeq, productSpecificationCharacteristicRelationship.charSpecSeq) &&
        Objects.equals(this.description, productSpecificationCharacteristicRelationship.description) &&
        Objects.equals(this.href, productSpecificationCharacteristicRelationship.href) &&
        Objects.equals(this.id, productSpecificationCharacteristicRelationship.id) &&
        Objects.equals(this.lastUpdate, productSpecificationCharacteristicRelationship.lastUpdate) &&
        Objects.equals(this.lifecycleStatus, productSpecificationCharacteristicRelationship.lifecycleStatus) &&
        Objects.equals(this.lifecycleStatusEnum, productSpecificationCharacteristicRelationship.lifecycleStatusEnum) &&
        Objects.equals(this.name, productSpecificationCharacteristicRelationship.name) &&
        Objects.equals(this.relationshipType, productSpecificationCharacteristicRelationship.relationshipType) &&
        Objects.equals(this.uuid, productSpecificationCharacteristicRelationship.uuid) &&
        Objects.equals(this.validFor, productSpecificationCharacteristicRelationship.validFor) &&
        Objects.equals(this.version, productSpecificationCharacteristicRelationship.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, charSpecSeq, description, href, id, lastUpdate, lifecycleStatus, lifecycleStatusEnum, name, relationshipType, uuid, validFor, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductSpecificationCharacteristicRelationship {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    charSpecSeq: ").append(toIndentedString(charSpecSeq)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    lifecycleStatus: ").append(toIndentedString(lifecycleStatus)).append("\n");
    sb.append("    lifecycleStatusEnum: ").append(toIndentedString(lifecycleStatusEnum)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    relationshipType: ").append(toIndentedString(relationshipType)).append("\n");
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

