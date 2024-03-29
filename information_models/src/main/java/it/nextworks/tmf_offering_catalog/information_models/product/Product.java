package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.party.Characteristic;
import it.nextworks.tmf_offering_catalog.information_models.product.order.RelatedProductOrderItem;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A product offering procured by a customer or other interested party playing a party role. A product is realized as one or more service(s) and / or resource(s).
 */
@ApiModel(description = "A product offering procured by a customer or other interested party playing a party role. A product is realized as one or more service(s) and / or resource(s).")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isBundle")
    private Boolean isBundle = null;

    @JsonProperty("isCustomerVisible")
    private Boolean isCustomerVisible = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("orderDate")
    private OffsetDateTime orderDate = null;

    @JsonProperty("productSerialNumber")
    private String productSerialNumber = null;

    @JsonProperty("startDate")
    private OffsetDateTime startDate = null;

    @JsonProperty("terminationDate")
    private OffsetDateTime terminationDate = null;

    @JsonProperty("agreement")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<AgreementItemRef> agreement = null;

    @JsonProperty("billingAccount")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billing_account_ref_id", referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BillingAccountRef billingAccount = null;

    @JsonProperty("place")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<RelatedPlace> place = null;

    @JsonProperty("product")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<Product> product = null;

    @JsonProperty("productCharacteristic")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<Characteristic> productCharacteristic = null;

    @JsonProperty("productOffering")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_offering_ref_id", referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductOfferingRef productOffering = null;

    @JsonProperty("productOrderItem")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<RelatedProductOrderItem> productOrderItem = null;

    @JsonProperty("productPrice")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<ProductPrice> productPrice = null;

    @JsonProperty("productRelationship")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<ProductRelationship> productRelationship = null;

    @JsonProperty("productSpecification")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_specification_ref_id", referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductSpecificationRef productSpecification = null;

    @JsonProperty("productTerm")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<ProductTerm> productTerm = null;

    @JsonProperty("realizingResource")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<ResourceRef> realizingResource = null;

    @JsonProperty("realizingService")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<ServiceRef> realizingService = null;

    @JsonProperty("relatedParty")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<RelatedParty> relatedParty = null;

    @JsonProperty("status")
    private ProductStatusType status = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public Product id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique identifier of the product
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique identifier of the product", hidden = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product href(String href) {
        this.href = href;
        return this;
    }

    /**
     * Reference of the product
     *
     * @return href
     **/
    @ApiModelProperty(value = "Reference of the product")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Is the description of the product. It could be copied from the description of the Product Offering.
     *
     * @return description
     **/
    @ApiModelProperty(value = "Is the description of the product. It could be copied from the description of the Product Offering.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product isBundle(Boolean isBundle) {
        this.isBundle = isBundle;
        return this;
    }

    /**
     * If true, the product is a ProductBundle which is an instantiation of a BundledProductOffering. If false, the product is a ProductComponent which is an instantiation of a SimpleProductOffering.
     *
     * @return isBundle
     **/
    @ApiModelProperty(value = "If true, the product is a ProductBundle which is an instantiation of a BundledProductOffering. If false, the product is a ProductComponent which is an instantiation of a SimpleProductOffering.")
    public Boolean isIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public Product isCustomerVisible(Boolean isCustomerVisible) {
        this.isCustomerVisible = isCustomerVisible;
        return this;
    }

    /**
     * If true, the product is visible by the customer.
     *
     * @return isCustomerVisible
     **/
    @ApiModelProperty(value = "If true, the product is visible by the customer.")
    public Boolean isIsCustomerVisible() {
        return isCustomerVisible;
    }

    public void setIsCustomerVisible(Boolean isCustomerVisible) {
        this.isCustomerVisible = isCustomerVisible;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the product. It could be the same as the name of the product offering
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the product. It could be the same as the name of the product offering")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product orderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * Is the date when the product was ordered
     *
     * @return orderDate
     **/
    @ApiModelProperty(value = "Is the date when the product was ordered")
    @Valid
    public OffsetDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Product productSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
        return this;
    }

    /**
     * Is the serial number for the product. This is typically applicable to tangible products e.g. Broadband Router.
     *
     * @return productSerialNumber
     **/
    @ApiModelProperty(value = "Is the serial number for the product. This is typically applicable to tangible products e.g. Broadband Router.")
    public String getProductSerialNumber() {
        return productSerialNumber;
    }

    public void setProductSerialNumber(String productSerialNumber) {
        this.productSerialNumber = productSerialNumber;
    }

    public Product startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Is the date from which the product starts
     *
     * @return startDate
     **/
    @ApiModelProperty(value = "Is the date from which the product starts")
    @Valid
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public Product terminationDate(OffsetDateTime terminationDate) {
        this.terminationDate = terminationDate;
        return this;
    }

    /**
     * Is the date when the product was terminated
     *
     * @return terminationDate
     **/
    @ApiModelProperty(value = "Is the date when the product was terminated")
    @Valid
    public OffsetDateTime getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(OffsetDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Product agreement(List<AgreementItemRef> agreement) {
        this.agreement = agreement;
        return this;
    }

    public Product addAgreementItem(AgreementItemRef agreementItem) {
        if (this.agreement == null) {
            this.agreement = new ArrayList<AgreementItemRef>();
        }
        this.agreement.add(agreementItem);
        return this;
    }

    /**
     * Get agreement
     *
     * @return agreement
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<AgreementItemRef> getAgreement() {
        return agreement;
    }

    public void setAgreement(List<AgreementItemRef> agreement) {
        this.agreement = agreement;
    }

    public Product billingAccount(BillingAccountRef billingAccount) {
        this.billingAccount = billingAccount;
        return this;
    }

    /**
     * Get billingAccount
     *
     * @return billingAccount
     **/
    @ApiModelProperty(value = "")
    @Valid
    public BillingAccountRef getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccountRef billingAccount) {
        this.billingAccount = billingAccount;
    }

    public Product place(List<RelatedPlace> place) {
        this.place = place;
        return this;
    }

    public Product addPlaceItem(RelatedPlace placeItem) {
        if (this.place == null) {
            this.place = new ArrayList<RelatedPlace>();
        }
        this.place.add(placeItem);
        return this;
    }

    /**
     * Get place
     *
     * @return place
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<RelatedPlace> getPlace() {
        return place;
    }

    public void setPlace(List<RelatedPlace> place) {
        this.place = place;
    }

    public Product product(List<Product> product) {
        this.product = product;
        return this;
    }

    public Product addProductItem(Product productItem) {
        if (this.product == null) {
            this.product = new ArrayList<Product>();
        }
        this.product.add(productItem);
        return this;
    }

    /**
     * Get product
     *
     * @return product
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Product productCharacteristic(List<Characteristic> productCharacteristic) {
        this.productCharacteristic = productCharacteristic;
        return this;
    }

    public Product addProductCharacteristicItem(Characteristic productCharacteristicItem) {
        if (this.productCharacteristic == null) {
            this.productCharacteristic = new ArrayList<Characteristic>();
        }
        this.productCharacteristic.add(productCharacteristicItem);
        return this;
    }

    /**
     * Get productCharacteristic
     *
     * @return productCharacteristic
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<Characteristic> getProductCharacteristic() {
        return productCharacteristic;
    }

    public void setProductCharacteristic(List<Characteristic> productCharacteristic) {
        this.productCharacteristic = productCharacteristic;
    }

    public Product productOffering(ProductOfferingRef productOffering) {
        this.productOffering = productOffering;
        return this;
    }

    /**
     * Get productOffering
     *
     * @return productOffering
     **/
    @ApiModelProperty(value = "")
    @Valid
    public ProductOfferingRef getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(ProductOfferingRef productOffering) {
        this.productOffering = productOffering;
    }

    public Product productOrderItem(List<RelatedProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
        return this;
    }

    public Product addProductOrderItemItem(RelatedProductOrderItem productOrderItemItem) {
        if (this.productOrderItem == null) {
            this.productOrderItem = new ArrayList<RelatedProductOrderItem>();
        }
        this.productOrderItem.add(productOrderItemItem);
        return this;
    }

    /**
     * Get productOrderItem
     *
     * @return productOrderItem
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<RelatedProductOrderItem> getProductOrderItem() {
        return productOrderItem;
    }

    public void setProductOrderItem(List<RelatedProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
    }

    public Product productPrice(List<ProductPrice> productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public Product addProductPriceItem(ProductPrice productPriceItem) {
        if (this.productPrice == null) {
            this.productPrice = new ArrayList<ProductPrice>();
        }
        this.productPrice.add(productPriceItem);
        return this;
    }

    /**
     * Get productPrice
     *
     * @return productPrice
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ProductPrice> getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(List<ProductPrice> productPrice) {
        this.productPrice = productPrice;
    }

    public Product productRelationship(List<ProductRelationship> productRelationship) {
        this.productRelationship = productRelationship;
        return this;
    }

    public Product addProductRelationshipItem(ProductRelationship productRelationshipItem) {
        if (this.productRelationship == null) {
            this.productRelationship = new ArrayList<ProductRelationship>();
        }
        this.productRelationship.add(productRelationshipItem);
        return this;
    }

    /**
     * Get productRelationship
     *
     * @return productRelationship
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ProductRelationship> getProductRelationship() {
        return productRelationship;
    }

    public void setProductRelationship(List<ProductRelationship> productRelationship) {
        this.productRelationship = productRelationship;
    }

    public Product productSpecification(ProductSpecificationRef productSpecification) {
        this.productSpecification = productSpecification;
        return this;
    }

    /**
     * Get productSpecification
     *
     * @return productSpecification
     **/
    @ApiModelProperty(value = "")
    @Valid
    public ProductSpecificationRef getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecificationRef productSpecification) {
        this.productSpecification = productSpecification;
    }

    public Product productTerm(List<ProductTerm> productTerm) {
        this.productTerm = productTerm;
        return this;
    }

    public Product addProductTermItem(ProductTerm productTermItem) {
        if (this.productTerm == null) {
            this.productTerm = new ArrayList<ProductTerm>();
        }
        this.productTerm.add(productTermItem);
        return this;
    }

    /**
     * Get productTerm
     *
     * @return productTerm
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ProductTerm> getProductTerm() {
        return productTerm;
    }

    public void setProductTerm(List<ProductTerm> productTerm) {
        this.productTerm = productTerm;
    }

    public Product realizingResource(List<ResourceRef> realizingResource) {
        this.realizingResource = realizingResource;
        return this;
    }

    public Product addRealizingResourceItem(ResourceRef realizingResourceItem) {
        if (this.realizingResource == null) {
            this.realizingResource = new ArrayList<ResourceRef>();
        }
        this.realizingResource.add(realizingResourceItem);
        return this;
    }

    /**
     * Get realizingResource
     *
     * @return realizingResource
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ResourceRef> getRealizingResource() {
        return realizingResource;
    }

    public void setRealizingResource(List<ResourceRef> realizingResource) {
        this.realizingResource = realizingResource;
    }

    public Product realizingService(List<ServiceRef> realizingService) {
        this.realizingService = realizingService;
        return this;
    }

    public Product addRealizingServiceItem(ServiceRef realizingServiceItem) {
        if (this.realizingService == null) {
            this.realizingService = new ArrayList<ServiceRef>();
        }
        this.realizingService.add(realizingServiceItem);
        return this;
    }

    /**
     * Get realizingService
     *
     * @return realizingService
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ServiceRef> getRealizingService() {
        return realizingService;
    }

    public void setRealizingService(List<ServiceRef> realizingService) {
        this.realizingService = realizingService;
    }

    public Product relatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
        return this;
    }

    public Product addRelatedPartyItem(RelatedParty relatedPartyItem) {
        if (this.relatedParty == null) {
            this.relatedParty = new ArrayList<RelatedParty>();
        }
        this.relatedParty.add(relatedPartyItem);
        return this;
    }

    /**
     * Get relatedParty
     *
     * @return relatedParty
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<RelatedParty> getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
    }

    public Product status(ProductStatusType status) {
        this.status = status;
        return this;
    }

    /**
     * Is the lifecycle status of the product.
     *
     * @return status
     **/
    @ApiModelProperty(value = "Is the lifecycle status of the product.")
    @Valid
    public ProductStatusType getStatus() {
        return status;
    }

    public void setStatus(ProductStatusType status) {
        this.status = status;
    }

    public Product baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     *
     * @return baseType
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")
    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Product schemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
    }

    /**
     * A URI to a JSON-Schema file that defines additional attributes and relationships
     *
     * @return schemaLocation
     **/
    @ApiModelProperty(value = "A URI to a JSON-Schema file that defines additional attributes and relationships")
    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    /**
     * When sub-classing, this defines the sub-class entity name
     *
     * @return type
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the sub-class entity name")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(this.id, product.id) &&
                Objects.equals(this.href, product.href) &&
                Objects.equals(this.description, product.description) &&
                Objects.equals(this.isBundle, product.isBundle) &&
                Objects.equals(this.isCustomerVisible, product.isCustomerVisible) &&
                Objects.equals(this.name, product.name) &&
                Objects.equals(this.orderDate, product.orderDate) &&
                Objects.equals(this.productSerialNumber, product.productSerialNumber) &&
                Objects.equals(this.startDate, product.startDate) &&
                Objects.equals(this.terminationDate, product.terminationDate) &&
                Objects.equals(this.agreement, product.agreement) &&
                Objects.equals(this.billingAccount, product.billingAccount) &&
                Objects.equals(this.place, product.place) &&
                Objects.equals(this.product, product.product) &&
                Objects.equals(this.productCharacteristic, product.productCharacteristic) &&
                Objects.equals(this.productOffering, product.productOffering) &&
                Objects.equals(this.productOrderItem, product.productOrderItem) &&
                Objects.equals(this.productPrice, product.productPrice) &&
                Objects.equals(this.productRelationship, product.productRelationship) &&
                Objects.equals(this.productSpecification, product.productSpecification) &&
                Objects.equals(this.productTerm, product.productTerm) &&
                Objects.equals(this.realizingResource, product.realizingResource) &&
                Objects.equals(this.realizingService, product.realizingService) &&
                Objects.equals(this.relatedParty, product.relatedParty) &&
                Objects.equals(this.status, product.status) &&
                Objects.equals(this.baseType, product.baseType) &&
                Objects.equals(this.schemaLocation, product.schemaLocation) &&
                Objects.equals(this.type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, description, isBundle, isCustomerVisible, name, orderDate, productSerialNumber, startDate, terminationDate, agreement, billingAccount, place, product, productCharacteristic, productOffering, productOrderItem, productPrice, productRelationship, productSpecification, productTerm, realizingResource, realizingService, relatedParty, status, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Product {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    isBundle: ").append(toIndentedString(isBundle)).append("\n");
        sb.append("    isCustomerVisible: ").append(toIndentedString(isCustomerVisible)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
        sb.append("    productSerialNumber: ").append(toIndentedString(productSerialNumber)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    terminationDate: ").append(toIndentedString(terminationDate)).append("\n");
        sb.append("    agreement: ").append(toIndentedString(agreement)).append("\n");
        sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
        sb.append("    place: ").append(toIndentedString(place)).append("\n");
        sb.append("    product: ").append(toIndentedString(product)).append("\n");
        sb.append("    productCharacteristic: ").append(toIndentedString(productCharacteristic)).append("\n");
        sb.append("    productOffering: ").append(toIndentedString(productOffering)).append("\n");
        sb.append("    productOrderItem: ").append(toIndentedString(productOrderItem)).append("\n");
        sb.append("    productPrice: ").append(toIndentedString(productPrice)).append("\n");
        sb.append("    productRelationship: ").append(toIndentedString(productRelationship)).append("\n");
        sb.append("    productSpecification: ").append(toIndentedString(productSpecification)).append("\n");
        sb.append("    productTerm: ").append(toIndentedString(productTerm)).append("\n");
        sb.append("    realizingResource: ").append(toIndentedString(realizingResource)).append("\n");
        sb.append("    realizingService: ").append(toIndentedString(realizingService)).append("\n");
        sb.append("    relatedParty: ").append(toIndentedString(relatedParty)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
