package it.nextworks.tmf_offering_catalogue.information_models.product;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.tmf_offering_catalogue.information_models.ResourceCandidateRef;
import it.nextworks.tmf_offering_catalogue.information_models.TimePeriod;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Represents entities that are orderable from the provider of the catalog, this resource includes pricing information.
 */
@ApiModel(description = "Represents entities that are orderable from the provider of the catalog, this resource includes pricing information.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-09T15:56:41.618Z")

@Entity
@Table(name = "product_offerings")
public class ProductOffering {

  @JsonProperty("@baseType")
  @Column(name = "base_type")
  private String baseType = null;

  @JsonProperty("@schemaLocation")
  @Column(name = "schema_location")
  private String schemaLocation = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("agreement")
  @Valid
  @Column(name = "agreement")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<AgreementRef> agreement = null;

  @JsonProperty("attachment")
  @Valid
  @Column(name = "attachment")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<AttachmentRefOrValue> attachment = null;

  @JsonProperty("bundledProductOffering")
  @Valid
  @Column(name = "bundled_product_offering")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<BundledProductOffering> bundledProductOffering = null;

  @JsonProperty("category")
  @Valid
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<CategoryRef> category = null;

  @JsonProperty("channel")
  @Valid
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<ChannelRef> channel = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("isBundle")
  @Column(name = "is_bundle")
  private Boolean isBundle = null;

  @JsonProperty("isSellable")
  @Column(name = "is_sellable")
  private Boolean isSellable = null;

  @JsonProperty("lastUpdate")
  @Column(name = "last_update")
  private String lastUpdate = null;

  @JsonProperty("lifecycleStatus")
  @Column(name = "lifecycle_status")
  private String lifecycleStatus = null;

  @JsonProperty("marketSegment")
  @Valid
  @Column(name = "market_segment")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<MarketSegmentRef> marketSegment = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("place")
  @Valid
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<PlaceRef> place = null;

  @JsonProperty("prodSpecCharValueUse")
  @Valid
  @Column(name = "prod_spec_char_value_use")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse = null;

  @JsonProperty("productOfferingPrice")
  @Valid
  @Column(name = "product_offering_price")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<ProductOfferingPriceRef> productOfferingPrice = null;

  @JsonProperty("productOfferingTerm")
  @Valid
  @Column(name = "product_offering_term")
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productOffering")
  private List<ProductOfferingTerm> productOfferingTerm = null;

  @JsonProperty("productSpecification")
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "product_specification_id", referencedColumnName = "uuid")
  private ProductSpecificationRef productSpecification = null;

  @JsonProperty("resourceCandidate")
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "resource_candidate_id", referencedColumnName = "uuid")
  private ResourceCandidateRef resourceCandidate = null;

  @JsonProperty("serviceCandidate")
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "service_candidate_id", referencedColumnName = "uuid")
  private ServiceCandidateRef serviceCandidate = null;

  @JsonProperty("serviceLevelAgreement")
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "service_level_agreement_id", referencedColumnName = "uuid")
  private SLARef serviceLevelAgreement = null;

  @JsonProperty("statusReason")
  @Column(name = "status_reason")
  private String statusReason = null;

  @JsonProperty("uuid")
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

  public ProductOffering baseType(String baseType) {
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

  public ProductOffering schemaLocation(String schemaLocation) {
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

  public ProductOffering type(String type) {
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

  public ProductOffering agreement(List<AgreementRef> agreement) {
    this.agreement = agreement;
    return this;
  }

  public ProductOffering addAgreementItem(AgreementRef agreementItem) {
    if (this.agreement == null) {
      this.agreement = new ArrayList<AgreementRef>();
    }
    this.agreement.add(agreementItem);
    return this;
  }

  /**
   * An agreement represents a contract or arrangement, either written or verbal and sometimes enforceable by law, such as a service level agreement or a customer price agreement. An agreement involves a number of other business entities, such as products, services, and resources and/or their specifications.
   * @return agreement
  **/
  @ApiModelProperty(value = "An agreement represents a contract or arrangement, either written or verbal and sometimes enforceable by law, such as a service level agreement or a customer price agreement. An agreement involves a number of other business entities, such as products, services, and resources and/or their specifications.")

  @Valid

  public List<AgreementRef> getAgreement() {
    return agreement;
  }

  public void setAgreement(List<AgreementRef> agreement) {
    this.agreement = agreement;
  }

  public ProductOffering attachment(List<AttachmentRefOrValue> attachment) {
    this.attachment = attachment;
    return this;
  }

  public ProductOffering addAttachmentItem(AttachmentRefOrValue attachmentItem) {
    if (this.attachment == null) {
      this.attachment = new ArrayList<AttachmentRefOrValue>();
    }
    this.attachment.add(attachmentItem);
    return this;
  }

  /**
   * Complements the description of an element (for instance a product) through video, pictures...
   * @return attachment
  **/
  @ApiModelProperty(value = "Complements the description of an element (for instance a product) through video, pictures...")

  @Valid

  public List<AttachmentRefOrValue> getAttachment() {
    return attachment;
  }

  public void setAttachment(List<AttachmentRefOrValue> attachment) {
    this.attachment = attachment;
  }

  public ProductOffering bundledProductOffering(List<BundledProductOffering> bundledProductOffering) {
    this.bundledProductOffering = bundledProductOffering;
    return this;
  }

  public ProductOffering addBundledProductOfferingItem(BundledProductOffering bundledProductOfferingItem) {
    if (this.bundledProductOffering == null) {
      this.bundledProductOffering = new ArrayList<BundledProductOffering>();
    }
    this.bundledProductOffering.add(bundledProductOfferingItem);
    return this;
  }

  /**
   * A type of ProductOffering that belongs to a grouping of ProductOfferings made available to the market. It inherits of all attributes of ProductOffering.
   * @return bundledProductOffering
  **/
  @ApiModelProperty(value = "A type of ProductOffering that belongs to a grouping of ProductOfferings made available to the market. It inherits of all attributes of ProductOffering.")

  @Valid

  public List<BundledProductOffering> getBundledProductOffering() {
    return bundledProductOffering;
  }

  public void setBundledProductOffering(List<BundledProductOffering> bundledProductOffering) {
    this.bundledProductOffering = bundledProductOffering;
  }

  public ProductOffering category(List<CategoryRef> category) {
    this.category = category;
    return this;
  }

  public ProductOffering addCategoryItem(CategoryRef categoryItem) {
    if (this.category == null) {
      this.category = new ArrayList<CategoryRef>();
    }
    this.category.add(categoryItem);
    return this;
  }

  /**
   * The category resource is used to group product offerings, service and resource candidates in logical containers. Categories can contain other categories and/or product offerings, resource or service candidates.
   * @return category
  **/
  @ApiModelProperty(value = "The category resource is used to group product offerings, service and resource candidates in logical containers. Categories can contain other categories and/or product offerings, resource or service candidates.")

  @Valid

  public List<CategoryRef> getCategory() {
    return category;
  }

  public void setCategory(List<CategoryRef> category) {
    this.category = category;
  }

  public ProductOffering channel(List<ChannelRef> channel) {
    this.channel = channel;
    return this;
  }

  public ProductOffering addChannelItem(ChannelRef channelItem) {
    if (this.channel == null) {
      this.channel = new ArrayList<ChannelRef>();
    }
    this.channel.add(channelItem);
    return this;
  }

  /**
   * The channel defines the channel for selling product offerings.
   * @return channel
  **/
  @ApiModelProperty(value = "The channel defines the channel for selling product offerings.")

  @Valid

  public List<ChannelRef> getChannel() {
    return channel;
  }

  public void setChannel(List<ChannelRef> channel) {
    this.channel = channel;
  }

  public ProductOffering description(String description) {
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

  public ProductOffering href(String href) {
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

  public ProductOffering id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the productOffering
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the productOffering")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProductOffering isBundle(Boolean isBundle) {
    this.isBundle = isBundle;
    return this;
  }

  /**
   * isBundle determines whether a productOffering represents a single productOffering (false), or a bundle of productOfferings (true).
   * @return isBundle
  **/
  @ApiModelProperty(value = "isBundle determines whether a productOffering represents a single productOffering (false), or a bundle of productOfferings (true).")


  public Boolean isIsBundle() {
    return isBundle;
  }

  public void setIsBundle(Boolean isBundle) {
    this.isBundle = isBundle;
  }

  public ProductOffering isSellable(Boolean isSellable) {
    this.isSellable = isSellable;
    return this;
  }

  /**
   * A flag indicating if this product offer can be sold stand-alone for sale or not. If this flag is false it indicates that the offer can only be sold within a bundle.
   * @return isSellable
  **/
  @ApiModelProperty(value = "A flag indicating if this product offer can be sold stand-alone for sale or not. If this flag is false it indicates that the offer can only be sold within a bundle.")


  public Boolean isIsSellable() {
    return isSellable;
  }

  public void setIsSellable(Boolean isSellable) {
    this.isSellable = isSellable;
  }

  public ProductOffering lastUpdate(String lastUpdate) {
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

  public ProductOffering lifecycleStatus(String lifecycleStatus) {
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

  public ProductOffering marketSegment(List<MarketSegmentRef> marketSegment) {
    this.marketSegment = marketSegment;
    return this;
  }

  public ProductOffering addMarketSegmentItem(MarketSegmentRef marketSegmentItem) {
    if (this.marketSegment == null) {
      this.marketSegment = new ArrayList<MarketSegmentRef>();
    }
    this.marketSegment.add(marketSegmentItem);
    return this;
  }

  /**
   * provides references to the corresponding market segment as target of product offerings. A market segment is grouping of Parties, GeographicAreas, SalesChannels, and so forth.
   * @return marketSegment
  **/
  @ApiModelProperty(value = "provides references to the corresponding market segment as target of product offerings. A market segment is grouping of Parties, GeographicAreas, SalesChannels, and so forth.")

  @Valid

  public List<MarketSegmentRef> getMarketSegment() {
    return marketSegment;
  }

  public void setMarketSegment(List<MarketSegmentRef> marketSegment) {
    this.marketSegment = marketSegment;
  }

  public ProductOffering name(String name) {
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

  public ProductOffering place(List<PlaceRef> place) {
    this.place = place;
    return this;
  }

  public ProductOffering addPlaceItem(PlaceRef placeItem) {
    if (this.place == null) {
      this.place = new ArrayList<PlaceRef>();
    }
    this.place.add(placeItem);
    return this;
  }

  /**
   * Place defines the places where the products are sold or delivered.
   * @return place
  **/
  @ApiModelProperty(value = "Place defines the places where the products are sold or delivered.")

  @Valid

  public List<PlaceRef> getPlace() {
    return place;
  }

  public void setPlace(List<PlaceRef> place) {
    this.place = place;
  }

  public ProductOffering prodSpecCharValueUse(List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse) {
    this.prodSpecCharValueUse = prodSpecCharValueUse;
    return this;
  }

  public ProductOffering addProdSpecCharValueUseItem(ProductSpecificationCharacteristicValueUse prodSpecCharValueUseItem) {
    if (this.prodSpecCharValueUse == null) {
      this.prodSpecCharValueUse = new ArrayList<ProductSpecificationCharacteristicValueUse>();
    }
    this.prodSpecCharValueUse.add(prodSpecCharValueUseItem);
    return this;
  }

  /**
   * A use of the ProductSpecificationCharacteristicValue by a ProductOffering to which additional properties (attributes) apply or override the properties of similar properties contained in ProductSpecificationCharacteristicValue. It should be noted that characteristics which their value(s) addressed by this object must exist in corresponding product specification. The available characteristic values for a ProductSpecificationCharacteristic in a Product specification can be modified at the ProductOffering level. For example, a characteristic 'Color' might have values White, Blue, Green, and Red. But, the list of values can be restricted to e.g. White and Blue in an associated product offering. It should be noted that the list of values in 'ProductSpecificationCharacteristicValueUse' is a strict subset of the list of values as defined in the corresponding product specification characteristics.
   * @return prodSpecCharValueUse
  **/
  @ApiModelProperty(value = "A use of the ProductSpecificationCharacteristicValue by a ProductOffering to which additional properties (attributes) apply or override the properties of similar properties contained in ProductSpecificationCharacteristicValue. It should be noted that characteristics which their value(s) addressed by this object must exist in corresponding product specification. The available characteristic values for a ProductSpecificationCharacteristic in a Product specification can be modified at the ProductOffering level. For example, a characteristic 'Color' might have values White, Blue, Green, and Red. But, the list of values can be restricted to e.g. White and Blue in an associated product offering. It should be noted that the list of values in 'ProductSpecificationCharacteristicValueUse' is a strict subset of the list of values as defined in the corresponding product specification characteristics.")

  @Valid

  public List<ProductSpecificationCharacteristicValueUse> getProdSpecCharValueUse() {
    return prodSpecCharValueUse;
  }

  public void setProdSpecCharValueUse(List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse) {
    this.prodSpecCharValueUse = prodSpecCharValueUse;
  }

  public ProductOffering productOfferingPrice(List<ProductOfferingPriceRef> productOfferingPrice) {
    this.productOfferingPrice = productOfferingPrice;
    return this;
  }

  public ProductOffering addProductOfferingPriceItem(ProductOfferingPriceRef productOfferingPriceItem) {
    if (this.productOfferingPrice == null) {
      this.productOfferingPrice = new ArrayList<ProductOfferingPriceRef>();
    }
    this.productOfferingPrice.add(productOfferingPriceItem);
    return this;
  }

  /**
   * An amount, usually of money, that is asked for or allowed when a ProductOffering is bought, rented, or leased. The price is valid for a defined period of time and may not represent the actual price paid by a customer.
   * @return productOfferingPrice
  **/
  @ApiModelProperty(value = "An amount, usually of money, that is asked for or allowed when a ProductOffering is bought, rented, or leased. The price is valid for a defined period of time and may not represent the actual price paid by a customer.")

  @Valid

  public List<ProductOfferingPriceRef> getProductOfferingPrice() {
    return productOfferingPrice;
  }

  public void setProductOfferingPrice(List<ProductOfferingPriceRef> productOfferingPrice) {
    this.productOfferingPrice = productOfferingPrice;
  }

  public ProductOffering productOfferingTerm(List<ProductOfferingTerm> productOfferingTerm) {
    this.productOfferingTerm = productOfferingTerm;
    return this;
  }

  public ProductOffering addProductOfferingTermItem(ProductOfferingTerm productOfferingTermItem) {
    if (this.productOfferingTerm == null) {
      this.productOfferingTerm = new ArrayList<ProductOfferingTerm>();
    }
    this.productOfferingTerm.add(productOfferingTermItem);
    return this;
  }

  /**
   * A condition under which a ProductOffering is made available to Customers. For instance, a productOffering can be offered with multiple commitment periods.
   * @return productOfferingTerm
  **/
  @ApiModelProperty(value = "A condition under which a ProductOffering is made available to Customers. For instance, a productOffering can be offered with multiple commitment periods.")

  @Valid

  public List<ProductOfferingTerm> getProductOfferingTerm() {
    return productOfferingTerm;
  }

  public void setProductOfferingTerm(List<ProductOfferingTerm> productOfferingTerm) {
    this.productOfferingTerm = productOfferingTerm;
  }

  public ProductOffering productSpecification(ProductSpecificationRef productSpecification) {
    this.productSpecification = productSpecification;
    return this;
  }

  /**
   * A ProductSpecification is a detailed description of a tangible or intangible object made available externally in the form of a ProductOffering to customers or other parties playing a party role.
   * @return productSpecification
  **/
  @ApiModelProperty(value = "A ProductSpecification is a detailed description of a tangible or intangible object made available externally in the form of a ProductOffering to customers or other parties playing a party role.")

  @Valid

  public ProductSpecificationRef getProductSpecification() {
    return productSpecification;
  }

  public void setProductSpecification(ProductSpecificationRef productSpecification) {
    this.productSpecification = productSpecification;
  }

  public ProductOffering resourceCandidate(ResourceCandidateRef resourceCandidate) {
    this.resourceCandidate = resourceCandidate;
    return this;
  }

  /**
   * A resource candidate is an entity that makes a ResourceSpecification available to a catalog.
   * @return resourceCandidate
  **/
  @ApiModelProperty(value = "A resource candidate is an entity that makes a ResourceSpecification available to a catalog.")

  @Valid

  public ResourceCandidateRef getResourceCandidate() {
    return resourceCandidate;
  }

  public void setResourceCandidate(ResourceCandidateRef resourceCandidate) {
    this.resourceCandidate = resourceCandidate;
  }

  public ProductOffering serviceCandidate(ServiceCandidateRef serviceCandidate) {
    this.serviceCandidate = serviceCandidate;
    return this;
  }

  /**
   * ServiceCandidate is an entity that makes a ServiceSpecification available to a catalog.
   * @return serviceCandidate
  **/
  @ApiModelProperty(value = "ServiceCandidate is an entity that makes a ServiceSpecification available to a catalog.")

  @Valid

  public ServiceCandidateRef getServiceCandidate() {
    return serviceCandidate;
  }

  public void setServiceCandidate(ServiceCandidateRef serviceCandidate) {
    this.serviceCandidate = serviceCandidate;
  }

  public ProductOffering serviceLevelAgreement(SLARef serviceLevelAgreement) {
    this.serviceLevelAgreement = serviceLevelAgreement;
    return this;
  }

  /**
   * A service level agreement (SLA) is a type of agreement that represents a formal negotiated agreement between two parties designed to create a common understanding about products, services, priorities, responsibilities, and so forth. The SLA is a set of appropriate procedures and targets formally or informally agreed between parties in order to achieve and maintain specified Quality of Service.
   * @return serviceLevelAgreement
  **/
  @ApiModelProperty(value = "A service level agreement (SLA) is a type of agreement that represents a formal negotiated agreement between two parties designed to create a common understanding about products, services, priorities, responsibilities, and so forth. The SLA is a set of appropriate procedures and targets formally or informally agreed between parties in order to achieve and maintain specified Quality of Service.")

  @Valid

  public SLARef getServiceLevelAgreement() {
    return serviceLevelAgreement;
  }

  public void setServiceLevelAgreement(SLARef serviceLevelAgreement) {
    this.serviceLevelAgreement = serviceLevelAgreement;
  }

  public ProductOffering statusReason(String statusReason) {
    this.statusReason = statusReason;
    return this;
  }

  /**
   * A string providing a complementary information on the value of the lifecycle status attribute.
   * @return statusReason
  **/
  @ApiModelProperty(value = "A string providing a complementary information on the value of the lifecycle status attribute.")


  public String getStatusReason() {
    return statusReason;
  }

  public void setStatusReason(String statusReason) {
    this.statusReason = statusReason;
  }

  public ProductOffering uuid(String uuid) {
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

  public ProductOffering validFor(TimePeriod validFor) {
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

  public ProductOffering version(String version) {
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
    ProductOffering productOffering = (ProductOffering) o;
    return Objects.equals(this.baseType, productOffering.baseType) &&
        Objects.equals(this.schemaLocation, productOffering.schemaLocation) &&
        Objects.equals(this.type, productOffering.type) &&
        Objects.equals(this.agreement, productOffering.agreement) &&
        Objects.equals(this.attachment, productOffering.attachment) &&
        Objects.equals(this.bundledProductOffering, productOffering.bundledProductOffering) &&
        Objects.equals(this.category, productOffering.category) &&
        Objects.equals(this.channel, productOffering.channel) &&
        Objects.equals(this.description, productOffering.description) &&
        Objects.equals(this.href, productOffering.href) &&
        Objects.equals(this.id, productOffering.id) &&
        Objects.equals(this.isBundle, productOffering.isBundle) &&
        Objects.equals(this.isSellable, productOffering.isSellable) &&
        Objects.equals(this.lastUpdate, productOffering.lastUpdate) &&
        Objects.equals(this.lifecycleStatus, productOffering.lifecycleStatus) &&
        Objects.equals(this.marketSegment, productOffering.marketSegment) &&
        Objects.equals(this.name, productOffering.name) &&
        Objects.equals(this.place, productOffering.place) &&
        Objects.equals(this.prodSpecCharValueUse, productOffering.prodSpecCharValueUse) &&
        Objects.equals(this.productOfferingPrice, productOffering.productOfferingPrice) &&
        Objects.equals(this.productOfferingTerm, productOffering.productOfferingTerm) &&
        Objects.equals(this.productSpecification, productOffering.productSpecification) &&
        Objects.equals(this.resourceCandidate, productOffering.resourceCandidate) &&
        Objects.equals(this.serviceCandidate, productOffering.serviceCandidate) &&
        Objects.equals(this.serviceLevelAgreement, productOffering.serviceLevelAgreement) &&
        Objects.equals(this.statusReason, productOffering.statusReason) &&
        Objects.equals(this.uuid, productOffering.uuid) &&
        Objects.equals(this.validFor, productOffering.validFor) &&
        Objects.equals(this.version, productOffering.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, schemaLocation, type, agreement, attachment, bundledProductOffering, category, channel, description, href, id, isBundle, isSellable, lastUpdate, lifecycleStatus, marketSegment, name, place, prodSpecCharValueUse, productOfferingPrice, productOfferingTerm, productSpecification, resourceCandidate, serviceCandidate, serviceLevelAgreement, statusReason, uuid, validFor, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductOffering {\n");
    
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    agreement: ").append(toIndentedString(agreement)).append("\n");
    sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");
    sb.append("    bundledProductOffering: ").append(toIndentedString(bundledProductOffering)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isBundle: ").append(toIndentedString(isBundle)).append("\n");
    sb.append("    isSellable: ").append(toIndentedString(isSellable)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    lifecycleStatus: ").append(toIndentedString(lifecycleStatus)).append("\n");
    sb.append("    marketSegment: ").append(toIndentedString(marketSegment)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    place: ").append(toIndentedString(place)).append("\n");
    sb.append("    prodSpecCharValueUse: ").append(toIndentedString(prodSpecCharValueUse)).append("\n");
    sb.append("    productOfferingPrice: ").append(toIndentedString(productOfferingPrice)).append("\n");
    sb.append("    productOfferingTerm: ").append(toIndentedString(productOfferingTerm)).append("\n");
    sb.append("    productSpecification: ").append(toIndentedString(productSpecification)).append("\n");
    sb.append("    resourceCandidate: ").append(toIndentedString(resourceCandidate)).append("\n");
    sb.append("    serviceCandidate: ").append(toIndentedString(serviceCandidate)).append("\n");
    sb.append("    serviceLevelAgreement: ").append(toIndentedString(serviceLevelAgreement)).append("\n");
    sb.append("    statusReason: ").append(toIndentedString(statusReason)).append("\n");
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

