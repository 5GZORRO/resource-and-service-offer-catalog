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
import java.util.Objects;

/**
 * Is an amount, usually of money, that modifies the price charged for an order item.
 */
@ApiModel(description = "Is an amount, usually of money, that modifies the price charged for an order item.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "price_alterations")
public class PriceAlteration {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("applicationDuration")
    private Integer applicationDuration = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("priceType")
    private String priceType = null;

    @JsonProperty("priority")
    private Integer priority = null;

    @JsonProperty("recurringChargePeriod")
    private String recurringChargePeriod = null;

    @JsonProperty("unitOfMeasure")
    private String unitOfMeasure = null;

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

    public PriceAlteration applicationDuration(Integer applicationDuration) {
        this.applicationDuration = applicationDuration;
        return this;
    }

    /**
     * Duration during which the alteration applies on the order item price (for instance 2 months free of charge for the recurring charge)
     *
     * @return applicationDuration
     **/
    @ApiModelProperty(value = "Duration during which the alteration applies on the order item price (for instance 2 months free of charge for the recurring charge)")
    public Integer getApplicationDuration() {
        return applicationDuration;
    }

    public void setApplicationDuration(Integer applicationDuration) {
        this.applicationDuration = applicationDuration;
    }

    public PriceAlteration description(String description) {
        this.description = description;
        return this;
    }

    /**
     * A narrative that explains in detail the semantics of this order item price alteration
     *
     * @return description
     **/
    @ApiModelProperty(value = "A narrative that explains in detail the semantics of this order item price alteration")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriceAlteration name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the order item price alteration
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the order item price alteration")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceAlteration priceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    /**
     * A category that describes the price such as recurring, one time and usage.
     *
     * @return priceType
     **/
    @ApiModelProperty(required = true, value = "A category that describes the price such as recurring, one time and usage.")
    @NotNull
    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public PriceAlteration priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Priority level for applying this alteration among all the defined alterations on the order item price
     *
     * @return priority
     **/
    @ApiModelProperty(value = "Priority level for applying this alteration among all the defined alterations on the order item price")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public PriceAlteration recurringChargePeriod(String recurringChargePeriod) {
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

    public PriceAlteration unitOfMeasure(String unitOfMeasure) {
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

    public PriceAlteration price(Price price) {
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

    public PriceAlteration productOfferingPrice(ProductOfferingPriceRef productOfferingPrice) {
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

    public PriceAlteration baseType(String baseType) {
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

    public PriceAlteration schemaLocation(String schemaLocation) {
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

    public PriceAlteration type(String type) {
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
        PriceAlteration priceAlteration = (PriceAlteration) o;
        return Objects.equals(this.id, priceAlteration.id) &&
                Objects.equals(this.applicationDuration, priceAlteration.applicationDuration) &&
                Objects.equals(this.description, priceAlteration.description) &&
                Objects.equals(this.name, priceAlteration.name) &&
                Objects.equals(this.priceType, priceAlteration.priceType) &&
                Objects.equals(this.priority, priceAlteration.priority) &&
                Objects.equals(this.recurringChargePeriod, priceAlteration.recurringChargePeriod) &&
                Objects.equals(this.unitOfMeasure, priceAlteration.unitOfMeasure) &&
                Objects.equals(this.price, priceAlteration.price) &&
                Objects.equals(this.productOfferingPrice, priceAlteration.productOfferingPrice) &&
                Objects.equals(this.baseType, priceAlteration.baseType) &&
                Objects.equals(this.schemaLocation, priceAlteration.schemaLocation) &&
                Objects.equals(this.type, priceAlteration.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationDuration, description, name, priceType, priority, recurringChargePeriod, unitOfMeasure, price, productOfferingPrice, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PriceAlteration {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    applicationDuration: ").append(toIndentedString(applicationDuration)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    priceType: ").append(toIndentedString(priceType)).append("\n");
        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
        sb.append("    recurringChargePeriod: ").append(toIndentedString(recurringChargePeriod)).append("\n");
        sb.append("    unitOfMeasure: ").append(toIndentedString(unitOfMeasure)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
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
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
