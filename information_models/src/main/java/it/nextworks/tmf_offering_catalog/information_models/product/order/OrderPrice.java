package it.nextworks.tmf_offering_catalog.information_models.product.order;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.BillingAccountRef;
import it.nextworks.tmf_offering_catalog.information_models.product.Price;
import it.nextworks.tmf_offering_catalog.information_models.product.PriceAlteration;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingPriceRef;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An amount, usually of money, that represents the actual price paid by the Customer for this item or this order
 */
@ApiModel(description = "An amount, usually of money, that represents the actual price paid by the Customer for this item or this order")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "order_price")
public class OrderPrice {

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
    private BillingAccountRef billingAccount = null;

    @JsonProperty("price")
    private Price price = null;

    @JsonProperty("priceAlteration")
    @Valid
    private List<PriceAlteration> priceAlteration = null;

    @JsonProperty("productOfferingPrice")
    private ProductOfferingPriceRef productOfferingPrice = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public OrderPrice id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderPrice description(String description) {
        this.description = description;
        return this;
    }

    /**
     * A narrative that explains in detail the semantics of this order item price.
     *
     * @return description
     **/
    @ApiModelProperty(value = "A narrative that explains in detail the semantics of this order item price.")


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderPrice name(String name) {
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

    public OrderPrice priceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    /**
     * A category that describes the price, such as recurring, discount, allowance, penalty, and so forth
     *
     * @return priceType
     **/
    @ApiModelProperty(value = "A category that describes the price, such as recurring, discount, allowance, penalty, and so forth")


    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public OrderPrice recurringChargePeriod(String recurringChargePeriod) {
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

    public OrderPrice unitOfMeasure(String unitOfMeasure) {
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

    public OrderPrice billingAccount(BillingAccountRef billingAccount) {
        this.billingAccount = billingAccount;
        return this;
    }

    /**
     * A reference to a billing account used for paid the order price charge
     *
     * @return billingAccount
     **/
    @ApiModelProperty(value = "A reference to a billing account used for paid the order price charge")

    @Valid

    public BillingAccountRef getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccountRef billingAccount) {
        this.billingAccount = billingAccount;
    }

    public OrderPrice price(Price price) {
        this.price = price;
        return this;
    }

    /**
     * a structure used to define price amount
     *
     * @return price
     **/
    @ApiModelProperty(value = "a structure used to define price amount")

    @Valid

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public OrderPrice priceAlteration(List<PriceAlteration> priceAlteration) {
        this.priceAlteration = priceAlteration;
        return this;
    }

    public OrderPrice addPriceAlterationItem(PriceAlteration priceAlterationItem) {
        if (this.priceAlteration == null) {
            this.priceAlteration = new ArrayList<PriceAlteration>();
        }
        this.priceAlteration.add(priceAlterationItem);
        return this;
    }

    /**
     * a strucuture used to describe a price alteration
     *
     * @return priceAlteration
     **/
    @ApiModelProperty(value = "a strucuture used to describe a price alteration")

    @Valid

    public List<PriceAlteration> getPriceAlteration() {
        return priceAlteration;
    }

    public void setPriceAlteration(List<PriceAlteration> priceAlteration) {
        this.priceAlteration = priceAlteration;
    }

    public OrderPrice productOfferingPrice(ProductOfferingPriceRef productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
        return this;
    }

    /**
     * An amount, usually of money, that is asked for or allowed when a ProductOffering is bought, rented, or leased. The price is valid for a defined period of time.
     *
     * @return productOfferingPrice
     **/
    @ApiModelProperty(value = "An amount, usually of money, that is asked for or allowed when a ProductOffering is bought, rented, or leased. The price is valid for a defined period of time.")

    @Valid

    public ProductOfferingPriceRef getProductOfferingPrice() {
        return productOfferingPrice;
    }

    public void setProductOfferingPrice(ProductOfferingPriceRef productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
    }

    public OrderPrice baseType(String baseType) {
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

    public OrderPrice schemaLocation(String schemaLocation) {
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

    public OrderPrice type(String type) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderPrice orderPrice = (OrderPrice) o;
        return Objects.equals(this.description, orderPrice.description) &&
                Objects.equals(this.name, orderPrice.name) &&
                Objects.equals(this.priceType, orderPrice.priceType) &&
                Objects.equals(this.recurringChargePeriod, orderPrice.recurringChargePeriod) &&
                Objects.equals(this.unitOfMeasure, orderPrice.unitOfMeasure) &&
                Objects.equals(this.billingAccount, orderPrice.billingAccount) &&
                Objects.equals(this.price, orderPrice.price) &&
                Objects.equals(this.priceAlteration, orderPrice.priceAlteration) &&
                Objects.equals(this.productOfferingPrice, orderPrice.productOfferingPrice) &&
                Objects.equals(this.baseType, orderPrice.baseType) &&
                Objects.equals(this.schemaLocation, orderPrice.schemaLocation) &&
                Objects.equals(this.type, orderPrice.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, priceType, recurringChargePeriod, unitOfMeasure, billingAccount, price, priceAlteration, productOfferingPrice, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OrderPrice {\n");

        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    priceType: ").append(toIndentedString(priceType)).append("\n");
        sb.append("    recurringChargePeriod: ").append(toIndentedString(recurringChargePeriod)).append("\n");
        sb.append("    unitOfMeasure: ").append(toIndentedString(unitOfMeasure)).append("\n");
        sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    priceAlteration: ").append(toIndentedString(priceAlteration)).append("\n");
        sb.append("    productOfferingPrice: ").append(toIndentedString(productOfferingPrice)).append("\n");
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
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
