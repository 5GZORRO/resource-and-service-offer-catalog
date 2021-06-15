package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An identified part of the order. A product order is decomposed into one or more order items.
 */
@ApiModel(description = "An identified part of the order. A product order is decomposed into one or more order items.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "product_order_items")
public class ProductOrderItem {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("quantity")
    private Integer quantity = null;

    @JsonProperty("action")
    private OrderItemActionType action = null;

    @JsonProperty("appointment")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "appointment_ref_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AppointmentRef appointment = null;

    @JsonProperty("billingAccount")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billing_account_ref_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BillingAccountRef billingAccount = null;

    @JsonProperty("itemPrice")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<OrderPrice> itemPrice = null;

    @JsonProperty("itemTerm")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<OrderTerm> itemTerm = null;

    @JsonProperty("itemTotalPrice")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<OrderPrice> itemTotalPrice = null;

    @JsonProperty("payment")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<PaymentRef> payment = null;

    @JsonProperty("product")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_ref_or_value_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductRefOrValue product = null;

    @JsonProperty("productOffering")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_offering_ref_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductOfferingRef productOffering = null;

    @JsonProperty("productOfferingQualificationItem")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_offering_qualification_item_ref_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProductOfferingQualificationItemRef productOfferingQualificationItem = null;

    @JsonProperty("productOrderItem")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<ProductOrderItem> productOrderItem = null;

    @JsonProperty("productOrderItemRelationship")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<OrderItemRelationship> productOrderItemRelationship = null;

    @JsonProperty("qualification")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_order_item_fk", referencedColumnName = "id")
    private List<ProductOfferingQualificationRef> qualification = null;

    @JsonProperty("quoteItem")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quote_item_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private QuoteItemRef quoteItem = null;

    @JsonProperty("state")
    private ProductOrderItemStateType state = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public ProductOrderItem id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Identifier of the line item (generally it is a sequence number 01, 02, 03, ...)
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "Identifier of the line item (generally it is a sequence number 01, 02, 03, ...)", hidden = true)
    @NotNull


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductOrderItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Quantity ordered
     *
     * @return quantity
     **/
    @ApiModelProperty(value = "Quantity ordered")


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductOrderItem action(OrderItemActionType action) {
        this.action = action;
        return this;
    }

    /**
     * The action to be carried out on the Product. Can be: add, modify, delete, noChange
     *
     * @return action
     **/
    @ApiModelProperty(required = true, value = "The action to be carried out on the Product. Can be: add, modify, delete, noChange")
    @NotNull

    @Valid

    public OrderItemActionType getAction() {
        return action;
    }

    public void setAction(OrderItemActionType action) {
        this.action = action;
    }

    public ProductOrderItem appointment(AppointmentRef appointment) {
        this.appointment = appointment;
        return this;
    }

    /**
     * Get appointment
     *
     * @return appointment
     **/
    @ApiModelProperty(value = "")

    @Valid

    public AppointmentRef getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentRef appointment) {
        this.appointment = appointment;
    }

    public ProductOrderItem billingAccount(BillingAccountRef billingAccount) {
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

    public ProductOrderItem itemPrice(List<OrderPrice> itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public ProductOrderItem addItemPriceItem(OrderPrice itemPriceItem) {
        if (this.itemPrice == null) {
            this.itemPrice = new ArrayList<OrderPrice>();
        }
        this.itemPrice.add(itemPriceItem);
        return this;
    }

    /**
     * Get itemPrice
     *
     * @return itemPrice
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<OrderPrice> getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(List<OrderPrice> itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ProductOrderItem itemTerm(List<OrderTerm> itemTerm) {
        this.itemTerm = itemTerm;
        return this;
    }

    public ProductOrderItem addItemTermItem(OrderTerm itemTermItem) {
        if (this.itemTerm == null) {
            this.itemTerm = new ArrayList<OrderTerm>();
        }
        this.itemTerm.add(itemTermItem);
        return this;
    }

    /**
     * Get itemTerm
     *
     * @return itemTerm
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<OrderTerm> getItemTerm() {
        return itemTerm;
    }

    public void setItemTerm(List<OrderTerm> itemTerm) {
        this.itemTerm = itemTerm;
    }

    public ProductOrderItem itemTotalPrice(List<OrderPrice> itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
        return this;
    }

    public ProductOrderItem addItemTotalPriceItem(OrderPrice itemTotalPriceItem) {
        if (this.itemTotalPrice == null) {
            this.itemTotalPrice = new ArrayList<OrderPrice>();
        }
        this.itemTotalPrice.add(itemTotalPriceItem);
        return this;
    }

    /**
     * Get itemTotalPrice
     *
     * @return itemTotalPrice
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<OrderPrice> getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(List<OrderPrice> itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public ProductOrderItem payment(List<PaymentRef> payment) {
        this.payment = payment;
        return this;
    }

    public ProductOrderItem addPaymentItem(PaymentRef paymentItem) {
        if (this.payment == null) {
            this.payment = new ArrayList<PaymentRef>();
        }
        this.payment.add(paymentItem);
        return this;
    }

    /**
     * Get payment
     *
     * @return payment
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<PaymentRef> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentRef> payment) {
        this.payment = payment;
    }

    public ProductOrderItem product(ProductRefOrValue product) {
        this.product = product;
        return this;
    }

    /**
     * Get product
     *
     * @return product
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ProductRefOrValue getProduct() {
        return product;
    }

    public void setProduct(ProductRefOrValue product) {
        this.product = product;
    }

    public ProductOrderItem productOffering(ProductOfferingRef productOffering) {
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

    public ProductOrderItem productOfferingQualificationItem(ProductOfferingQualificationItemRef productOfferingQualificationItem) {
        this.productOfferingQualificationItem = productOfferingQualificationItem;
        return this;
    }

    /**
     * Get productOfferingQualificationItem
     *
     * @return productOfferingQualificationItem
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ProductOfferingQualificationItemRef getProductOfferingQualificationItem() {
        return productOfferingQualificationItem;
    }

    public void setProductOfferingQualificationItem(ProductOfferingQualificationItemRef productOfferingQualificationItem) {
        this.productOfferingQualificationItem = productOfferingQualificationItem;
    }

    public ProductOrderItem productOrderItem(List<ProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
        return this;
    }

    public ProductOrderItem addProductOrderItemItem(ProductOrderItem productOrderItemItem) {
        if (this.productOrderItem == null) {
            this.productOrderItem = new ArrayList<ProductOrderItem>();
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

    public List<ProductOrderItem> getProductOrderItem() {
        return productOrderItem;
    }

    public void setProductOrderItem(List<ProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
    }

    public ProductOrderItem productOrderItemRelationship(List<OrderItemRelationship> productOrderItemRelationship) {
        this.productOrderItemRelationship = productOrderItemRelationship;
        return this;
    }

    public ProductOrderItem addProductOrderItemRelationshipItem(OrderItemRelationship productOrderItemRelationshipItem) {
        if (this.productOrderItemRelationship == null) {
            this.productOrderItemRelationship = new ArrayList<OrderItemRelationship>();
        }
        this.productOrderItemRelationship.add(productOrderItemRelationshipItem);
        return this;
    }

    /**
     * Get productOrderItemRelationship
     *
     * @return productOrderItemRelationship
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<OrderItemRelationship> getProductOrderItemRelationship() {
        return productOrderItemRelationship;
    }

    public void setProductOrderItemRelationship(List<OrderItemRelationship> productOrderItemRelationship) {
        this.productOrderItemRelationship = productOrderItemRelationship;
    }

    public ProductOrderItem qualification(List<ProductOfferingQualificationRef> qualification) {
        this.qualification = qualification;
        return this;
    }

    public ProductOrderItem addQualificationItem(ProductOfferingQualificationRef qualificationItem) {
        if (this.qualification == null) {
            this.qualification = new ArrayList<ProductOfferingQualificationRef>();
        }
        this.qualification.add(qualificationItem);
        return this;
    }

    /**
     * Get qualification
     *
     * @return qualification
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<ProductOfferingQualificationRef> getQualification() {
        return qualification;
    }

    public void setQualification(List<ProductOfferingQualificationRef> qualification) {
        this.qualification = qualification;
    }

    public ProductOrderItem quoteItem(QuoteItemRef quoteItem) {
        this.quoteItem = quoteItem;
        return this;
    }

    /**
     * Get quoteItem
     *
     * @return quoteItem
     **/
    @ApiModelProperty(value = "")

    @Valid

    public QuoteItemRef getQuoteItem() {
        return quoteItem;
    }

    public void setQuoteItem(QuoteItemRef quoteItem) {
        this.quoteItem = quoteItem;
    }

    public ProductOrderItem state(ProductOrderItemStateType state) {
        this.state = state;
        return this;
    }

    /**
     * State of the order item : described in the state machine diagram
     *
     * @return state
     **/
    @ApiModelProperty(value = "State of the order item : described in the state machine diagram")

    @Valid

    public ProductOrderItemStateType getState() {
        return state;
    }

    public void setState(ProductOrderItemStateType state) {
        this.state = state;
    }

    public ProductOrderItem baseType(String baseType) {
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

    public ProductOrderItem schemaLocation(String schemaLocation) {
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

    public ProductOrderItem type(String type) {
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
        ProductOrderItem productOrderItem = (ProductOrderItem) o;
        return Objects.equals(this.id, productOrderItem.id) &&
                Objects.equals(this.quantity, productOrderItem.quantity) &&
                Objects.equals(this.action, productOrderItem.action) &&
                Objects.equals(this.appointment, productOrderItem.appointment) &&
                Objects.equals(this.billingAccount, productOrderItem.billingAccount) &&
                Objects.equals(this.itemPrice, productOrderItem.itemPrice) &&
                Objects.equals(this.itemTerm, productOrderItem.itemTerm) &&
                Objects.equals(this.itemTotalPrice, productOrderItem.itemTotalPrice) &&
                Objects.equals(this.payment, productOrderItem.payment) &&
                Objects.equals(this.product, productOrderItem.product) &&
                Objects.equals(this.productOffering, productOrderItem.productOffering) &&
                Objects.equals(this.productOfferingQualificationItem, productOrderItem.productOfferingQualificationItem) &&
                Objects.equals(this.productOrderItem, productOrderItem.productOrderItem) &&
                Objects.equals(this.productOrderItemRelationship, productOrderItem.productOrderItemRelationship) &&
                Objects.equals(this.qualification, productOrderItem.qualification) &&
                Objects.equals(this.quoteItem, productOrderItem.quoteItem) &&
                Objects.equals(this.state, productOrderItem.state) &&
                Objects.equals(this.baseType, productOrderItem.baseType) &&
                Objects.equals(this.schemaLocation, productOrderItem.schemaLocation) &&
                Objects.equals(this.type, productOrderItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, action, appointment, billingAccount, itemPrice, itemTerm, itemTotalPrice, payment, product, productOffering, productOfferingQualificationItem, productOrderItem, productOrderItemRelationship, qualification, quoteItem, state, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductOrderItem {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
        sb.append("    action: ").append(toIndentedString(action)).append("\n");
        sb.append("    appointment: ").append(toIndentedString(appointment)).append("\n");
        sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
        sb.append("    itemPrice: ").append(toIndentedString(itemPrice)).append("\n");
        sb.append("    itemTerm: ").append(toIndentedString(itemTerm)).append("\n");
        sb.append("    itemTotalPrice: ").append(toIndentedString(itemTotalPrice)).append("\n");
        sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
        sb.append("    product: ").append(toIndentedString(product)).append("\n");
        sb.append("    productOffering: ").append(toIndentedString(productOffering)).append("\n");
        sb.append("    productOfferingQualificationItem: ").append(toIndentedString(productOfferingQualificationItem)).append("\n");
        sb.append("    productOrderItem: ").append(toIndentedString(productOrderItem)).append("\n");
        sb.append("    productOrderItemRelationship: ").append(toIndentedString(productOrderItemRelationship)).append("\n");
        sb.append("    qualification: ").append(toIndentedString(qualification)).append("\n");
        sb.append("    quoteItem: ").append(toIndentedString(quoteItem)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
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
