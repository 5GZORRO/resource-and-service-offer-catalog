package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Provides all amounts (tax included, duty free, tax rate), used currency and percentage to apply for Price Alteration.
 */
@ApiModel(description = "Provides all amounts (tax included, duty free, tax rate), used currency and percentage to apply for Price Alteration.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "prices")
public class Price {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("percentage")
    private Float percentage = null;

    @JsonProperty("taxRate")
    private Float taxRate = null;

    @JsonProperty("dutyFreeAmount")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "dutyFreeAmountValue")),
            @AttributeOverride(name = "unit", column = @Column(name = "dutyFreeAmountUnit"))
    })
    private Money dutyFreeAmount = null;

    @JsonProperty("taxIncludedAmount")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "taxIncludedAmountValue")),
            @AttributeOverride(name = "unit", column = @Column(name = "taxIncludedAmountUnit"))
    })
    private Money taxIncludedAmount = null;

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

    public Price percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    /**
     * Percentage to apply for ProdOfferPriceAlteration
     *
     * @return percentage
     **/
    @ApiModelProperty(value = "Percentage to apply for ProdOfferPriceAlteration")
    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Price taxRate(Float taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    /**
     * Tax rate
     *
     * @return taxRate
     **/
    @ApiModelProperty(value = "Tax rate")
    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public Price dutyFreeAmount(Money dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
        return this;
    }

    /**
     * All taxes excluded amount (expressed in the given currency)
     *
     * @return dutyFreeAmount
     **/
    @ApiModelProperty(value = "All taxes excluded amount (expressed in the given currency)")
    @Valid
    public Money getDutyFreeAmount() {
        return dutyFreeAmount;
    }

    public void setDutyFreeAmount(Money dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public Price taxIncludedAmount(Money taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
        return this;
    }

    /**
     * All taxes included amount (expressed in the given currency)
     *
     * @return taxIncludedAmount
     **/
    @ApiModelProperty(value = "All taxes included amount (expressed in the given currency)")
    @Valid
    public Money getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(Money taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public Price baseType(String baseType) {
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

    public Price schemaLocation(String schemaLocation) {
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

    public Price type(String type) {
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
        Price price = (Price) o;
        return Objects.equals(this.percentage, price.percentage) &&
                Objects.equals(this.taxRate, price.taxRate) &&
                Objects.equals(this.dutyFreeAmount, price.dutyFreeAmount) &&
                Objects.equals(this.taxIncludedAmount, price.taxIncludedAmount) &&
                Objects.equals(this.baseType, price.baseType) &&
                Objects.equals(this.schemaLocation, price.schemaLocation) &&
                Objects.equals(this.type, price.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage, taxRate, dutyFreeAmount, taxIncludedAmount, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Price {\n");

        sb.append("    percentage: ").append(toIndentedString(percentage)).append("\n");
        sb.append("    taxRate: ").append(toIndentedString(taxRate)).append("\n");
        sb.append("    dutyFreeAmount: ").append(toIndentedString(dutyFreeAmount)).append("\n");
        sb.append("    taxIncludedAmount: ").append(toIndentedString(taxIncludedAmount)).append("\n");
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
