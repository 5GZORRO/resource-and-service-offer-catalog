package it.nextworks.tmf_offering_catalog.information_models.product;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.tmf_offering_catalog.information_models.common.LifecycleStatusEnumEnum;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;
import it.nextworks.tmf_offering_catalog.information_models.common.converter.LifecycleStatusEnumEnumConverter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * The category resource is used to group product offerings, service and resource candidates in logical containers. Categories can contain other categories and/or product offerings, resource or service candidates.
 */
@ApiModel(description = "The category resource is used to group product offerings, service and resource candidates in logical containers. Categories can contain other categories and/or product offerings, resource or service candidates.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-09T15:56:41.618Z")

@Entity
@Table(name = "categories")
public class Category {

  @JsonProperty("@baseType")
  @Column(name = "base_type")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  @Column(name = "schema_location")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("isRoot")
  @Column(name = "is_root")
  private Boolean isRoot = null;

  @JsonProperty("lastUpdate")
  @Column(name = "last_update")
  private String lastUpdate = null;

  @JsonProperty("lifecycleStatus")
  @Column(name = "lifecycle_status")
  private String lifecycleStatus = null;

  @JsonProperty("lifecycleStatusEnum")
  @Column(name = "lifecycle_status_enum")
  @Convert(converter = LifecycleStatusEnumEnumConverter.class)
  private LifecycleStatusEnumEnum lifecycleStatusEnum = null;

  @JsonProperty("name")
  @Column(unique = true)
  private String name = null;

  @JsonProperty("parentId")
  @Column(name = "parent_id")
  private String parentId = null;

  @JsonProperty("productOffering")
  @Valid
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "category_fk", referencedColumnName = "uuid")
  private List<ProductOfferingRef> productOffering = null;

  @JsonProperty("subCategory")
  @Valid
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "category_fk", referencedColumnName = "uuid")
  private List<CategoryRef> subCategory = null;

  @JsonIgnore
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String uuid = null;

  @JsonProperty("validFor")
  @Column(name = "valid_for")
  @Embedded
  private TimePeriod validFor = null;

  @JsonProperty("version")
  private String version = null;

  public Category baseType(String baseType) {
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

  public Category schemaLocation(String schemaLocation) {
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

  public Category type(String type) {
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

  public Category description(String description) {
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

  public Category href(String href) {
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

  public Category id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Category isRoot(Boolean isRoot) {
    this.isRoot = isRoot;
    return this;
  }

  /**
   * If true, this Boolean indicates that the category is a root of categories
   * @return isRoot
  **/
  @ApiModelProperty(value = "If true, this Boolean indicates that the category is a root of categories")


  public Boolean isIsRoot() {
    return isRoot;
  }

  public void setIsRoot(Boolean isRoot) {
    this.isRoot = isRoot;
  }

  public Category lastUpdate(String lastUpdate) {
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

  public Category lifecycleStatus(String lifecycleStatus) {
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

  public Category lifecycleStatusEnum(LifecycleStatusEnumEnum lifecycleStatusEnum) {
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

  public Category name(String name) {
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

  public Category parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * Unique identifier of the parent category
   * @return parentId
  **/
  @ApiModelProperty(value = "Unique identifier of the parent category")


  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Category productOffering(List<ProductOfferingRef> productOffering) {
    this.productOffering = productOffering;
    return this;
  }

  public Category addProductOfferingItem(ProductOfferingRef productOfferingItem) {
    if (this.productOffering == null) {
      this.productOffering = new ArrayList<ProductOfferingRef>();
    }
    this.productOffering.add(productOfferingItem);
    return this;
  }

  /**
   * A product offering represents entities that are orderable from the provider of the catalog, this resource includes pricing information.
   * @return productOffering
  **/
  @ApiModelProperty(value = "A product offering represents entities that are orderable from the provider of the catalog, this resource includes pricing information.")

  @Valid

  public List<ProductOfferingRef> getProductOffering() {
    return productOffering;
  }

  public void setProductOffering(List<ProductOfferingRef> productOffering) {
    this.productOffering = productOffering;
  }

  public Category subCategory(List<CategoryRef> subCategory) {
    this.subCategory = subCategory;
    return this;
  }

  public Category addSubCategoryItem(CategoryRef subCategoryItem) {
    if (this.subCategory == null) {
      this.subCategory = new ArrayList<CategoryRef>();
    }
    this.subCategory.add(subCategoryItem);
    return this;
  }

  /**
   * List of child categories in the tree for in this category
   * @return subCategory
  **/
  @ApiModelProperty(value = "List of child categories in the tree for in this category")

  @Valid

  public List<CategoryRef> getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(List<CategoryRef> subCategory) {
    this.subCategory = subCategory;
  }

  public Category uuid(String uuid) {
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

  public Category validFor(TimePeriod validFor) {
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

  public Category version(String version) {
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
    Category category = (Category) o;
    return Objects.equals(this.baseType, category.baseType) &&
        Objects.equals(this.schemaLocation, category.schemaLocation) &&
        Objects.equals(this.type, category.type) &&
        Objects.equals(this.description, category.description) &&
        Objects.equals(this.href, category.href) &&
        Objects.equals(this.id, category.id) &&
        Objects.equals(this.isRoot, category.isRoot) &&
        Objects.equals(this.lastUpdate, category.lastUpdate) &&
        Objects.equals(this.lifecycleStatus, category.lifecycleStatus) &&
        Objects.equals(this.lifecycleStatusEnum, category.lifecycleStatusEnum) &&
        Objects.equals(this.name, category.name) &&
        Objects.equals(this.parentId, category.parentId) &&
        Objects.equals(this.productOffering, category.productOffering) &&
        Objects.equals(this.subCategory, category.subCategory) &&
        Objects.equals(this.uuid, category.uuid) &&
        Objects.equals(this.validFor, category.validFor) &&
        Objects.equals(this.version, category.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, description, href, id, isRoot, lastUpdate, lifecycleStatus, lifecycleStatusEnum, name, parentId, productOffering, subCategory, uuid, validFor, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isRoot: ").append(toIndentedString(isRoot)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    lifecycleStatus: ").append(toIndentedString(lifecycleStatus)).append("\n");
    sb.append("    lifecycleStatusEnum: ").append(toIndentedString(lifecycleStatusEnum)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    productOffering: ").append(toIndentedString(productOffering)).append("\n");
    sb.append("    subCategory: ").append(toIndentedString(subCategory)).append("\n");
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

