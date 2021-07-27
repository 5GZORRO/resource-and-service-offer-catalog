package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An amount, usually of money, that represents the actual price paid by a Customer for a purchase, a rent or a lease of a Product. The price is valid for a defined period of time.
 */
@ApiModel(description = "An amount, usually of money, that represents the actual price paid by a Customer for a purchase, a rent or a lease of a Product. The price is valid for a defined period of time.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "product_prices")
public class ProductPrice {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("priceType")
    private String priceType = null;

    @JsonProperty("recurringChargePeriod")
    private String recurringChargePeriod = null;

    @JsonProperty("unitOfMeasure")
    private String unitOfMeasure = null;

    @JsonProperty("billingAccount")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billing_account_ref_id", referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BillingAccountRef billingAccount = null;

    @JsonProperty("price")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Price price = null;

    @JsonProperty("productOfferingPrice")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_offering_price_ref_id", referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductOfferingPriceRef productOfferingPrice = null;

    @JsonProperty("productPriceAlteration")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_price_fk", referencedColumnName = "id")
    private List<PriceAlteration> productPriceAlteration = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @ApiModelProperty(hidden = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductPrice description(String description) {
        this.description = description;
        return this;
    }

    /**
     * A narrative that explains in detail the semantics of this product price.
     *
     * @return description
     **/
    @ApiModelProperty(value = "A narrative that explains in detail the semantics of this product price.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductPrice name(String name) {
        this.name = name;
        return this;
    }

    /**
     * A short descriptive name such as \"Subscription price\".
     *
     * @return name
     **/
    @ApiModelProperty(value = "A short descriptive name such as \"Subscription price\".")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPrice priceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    /**
     * A category that describes the price, such as recurring, discount, allowance, penalty, and so forth.
     *
     * @return priceType
     **/
    @ApiModelProperty(required = true, value = "A category that describes the price, such as recurring, discount, allowance, penalty, and so forth.")
    @NotNull
    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public ProductPrice recurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
        return this;
    }

    /**
     * Could be month, week...
     *
     * @return recurringChargePeriod
     **/
    @ApiModelProperty(value = "Could be month, week...")
    public String getRecurringChargePeriod() {
        return recurringChargePeriod;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public ProductPrice unitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    /**
     * Could be minutes, GB...
     *
     * @return unitOfMeasure
     **/
    @ApiModelProperty(value = "Could be minutes, GB...")
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public ProductPrice billingAccount(BillingAccountRef billingAccount) {
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

    public ProductPrice price(Price price) {
        this.price = price;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public ProductPrice productOfferingPrice(ProductOfferingPriceRef productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
        return this;
    }

    /**
     * Get productOfferingPrice
     *
     * @return productOfferingPrice
     **/
    @ApiModelProperty(value = "")
    @Valid
    public ProductOfferingPriceRef getProductOfferingPrice() {
        return productOfferingPrice;
    }

    public void setProductOfferingPrice(ProductOfferingPriceRef productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
    }

    public ProductPrice productPriceAlteration(List<PriceAlteration> productPriceAlteration) {
        this.productPriceAlteration = productPriceAlteration;
        return this;
    }

    public ProductPrice addProductPriceAlterationItem(PriceAlteration productPriceAlterationItem) {
        if (this.productPriceAlteration == null) {
            this.productPriceAlteration = new ArrayList<PriceAlteration>();
        }
        this.productPriceAlteration.add(productPriceAlterationItem);
        return this;
    }

    /**
     * Get productPriceAlteration
     *
     * @return productPriceAlteration
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<PriceAlteration> getProductPriceAlteration() {
        return productPriceAlteration;
    }

    public void setProductPriceAlteration(List<PriceAlteration> productPriceAlteration) {
        this.productPriceAlteration = productPriceAlteration;
    }

    public ProductPrice baseType(String baseType) {
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

    public ProductPrice schemaLocation(String schemaLocation) {
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

    public ProductPrice type(String type) {
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
        ProductPrice productPrice = (ProductPrice) o;
        return Objects.equals(this.description, productPrice.description) &&
                Objects.equals(this.name, productPrice.name) &&
                Objects.equals(this.priceType, productPrice.priceType) &&
                Objects.equals(this.recurringChargePeriod, productPrice.recurringChargePeriod) &&
                Objects.equals(this.unitOfMeasure, productPrice.unitOfMeasure) &&
                Objects.equals(this.billingAccount, productPrice.billingAccount) &&
                Objects.equals(this.price, productPrice.price) &&
                Objects.equals(this.productOfferingPrice, productPrice.productOfferingPrice) &&
                Objects.equals(this.productPriceAlteration, productPrice.productPriceAlteration) &&
                Objects.equals(this.baseType, productPrice.baseType) &&
                Objects.equals(this.schemaLocation, productPrice.schemaLocation) &&
                Objects.equals(this.type, productPrice.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, priceType, recurringChargePeriod, unitOfMeasure, billingAccount, price, productOfferingPrice, productPriceAlteration, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductPrice {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    priceType: ").append(toIndentedString(priceType)).append("\n");
        sb.append("    recurringChargePeriod: ").append(toIndentedString(recurringChargePeriod)).append("\n");
        sb.append("    unitOfMeasure: ").append(toIndentedString(unitOfMeasure)).append("\n");
        sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    productOfferingPrice: ").append(toIndentedString(productOfferingPrice)).append("\n");
        sb.append("    productPriceAlteration: ").append(toIndentedString(productPriceAlteration)).append("\n");
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
