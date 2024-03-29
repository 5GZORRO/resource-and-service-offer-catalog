package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.product.*;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProductOrderCreate {

    @JsonProperty("cancellationDate")
    private String cancellationDate = null;

    @JsonProperty("cancellationReason")
    private String cancellationReason = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("externalId")
    private String externalId = null;

    @JsonProperty("notificationContact")
    private String notificationContact = null;

    @JsonProperty("priority")
    private String priority = null;

    @JsonProperty("requestedCompletionDate")
    private String requestedCompletionDate = null;

    @JsonProperty("requestedStartDate")
    private String requestedStartDate = null;

    @JsonProperty("agreement")
    @Valid
    private List<AgreementRef> agreement = null;

    @JsonProperty("billingAccount")
    private BillingAccountRef billingAccount = null;

    @JsonProperty("channel")
    @Valid
    private List<RelatedChannel> channel = null;

    @JsonProperty("note")
    @Valid
    private List<Note> note = null;

    @JsonProperty("orderTotalPrice")
    @Valid
    private List<OrderPrice> orderTotalPrice = null;

    @JsonProperty("payment")
    @Valid
    private List<PaymentRef> payment = null;

    @JsonProperty("productOfferingQualification")
    @Valid
    private List<ProductOfferingQualificationRef> productOfferingQualification = null;

    @JsonProperty("productOrderItem")
    @Valid
    private List<ProductOrderItem> productOrderItem = new ArrayList<>();

    @JsonProperty("quote")
    @Valid
    private List<QuoteRef> quote = null;

    @JsonProperty("relatedParty")
    @Valid
    private List<RelatedParty> relatedParty = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public ProductOrderCreate cancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
        return this;
    }

    /**
     * Date when the order is cancelled. This is used when order is cancelled.
     *
     * @return cancellationDate
     **/
    @ApiModelProperty(value = "Date when the order is cancelled. This is used when order is cancelled. ")
    @Valid
    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public ProductOrderCreate cancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
        return this;
    }

    /**
     * Reason why the order is cancelled. This is used when order is cancelled.
     *
     * @return cancellationReason
     **/
    @ApiModelProperty(value = "Reason why the order is cancelled. This is used when order is cancelled. ")
    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public ProductOrderCreate category(String category) {
        this.category = category;
        return this;
    }

    /**
     * Used to categorize the order from a business perspective that can be useful for the OM system (e.g. \"enterprise\", \"residential\", ...)
     *
     * @return category
     **/
    @ApiModelProperty(value = "Used to categorize the order from a business perspective that can be useful for the OM system (e.g. \"enterprise\", \"residential\", ...)")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductOrderCreate description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Description of the product order
     *
     * @return description
     **/
    @ApiModelProperty(value = "Description of the product order")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductOrderCreate externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * ID given by the consumer and only understandable by him (to facilitate his searches afterwards)
     *
     * @return externalId
     **/
    @ApiModelProperty(value = "ID given by the consumer and only understandable by him (to facilitate his searches afterwards)")
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ProductOrderCreate notificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
        return this;
    }

    /**
     * Contact attached to the order to send back information regarding this order
     *
     * @return notificationContact
     **/
    @ApiModelProperty(value = "Contact attached to the order to send back information regarding this order")
    public String getNotificationContact() {
        return notificationContact;
    }

    public void setNotificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
    }

    public ProductOrderCreate priority(String priority) {
        this.priority = priority;
        return this;
    }

    /**
     * A way that can be used by consumers to prioritize orders in OM system (from 0 to 4 : 0 is the highest priority, and 4 the lowest)
     *
     * @return priority
     **/
    @ApiModelProperty(value = "A way that can be used by consumers to prioritize orders in OM system (from 0 to 4 : 0 is the highest priority, and 4 the lowest)")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ProductOrderCreate requestedCompletionDate(String requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
        return this;
    }

    /**
     * Requested delivery date from the requestor perspective
     *
     * @return requestedCompletionDate
     **/
    @ApiModelProperty(value = "Requested delivery date from the requestor perspective")
    @Valid
    public String getRequestedCompletionDate() {
        return requestedCompletionDate;
    }

    public void setRequestedCompletionDate(String requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
    }

    public ProductOrderCreate requestedStartDate(String requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
        return this;
    }

    /**
     * Order fulfillment start date wished by the requestor. This is used when, for any reason, requestor cannot allow seller to begin to operationally begin the fulfillment before a date.
     *
     * @return requestedStartDate
     **/
    @ApiModelProperty(value = "Order fulfillment start date wished by the requestor. This is used when, for any reason, requestor cannot allow seller to begin to operationally begin the fulfillment before a date. ")
    @Valid
    public String getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(String requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public ProductOrderCreate agreement(List<AgreementRef> agreement) {
        this.agreement = agreement;
        return this;
    }

    public ProductOrderCreate addAgreementItem(AgreementRef agreementItem) {
        if (this.agreement == null) {
            this.agreement = new ArrayList<>();
        }
        this.agreement.add(agreementItem);
        return this;
    }

    /**
     * A reference to an agreement defined in the context of the product order
     *
     * @return agreement
     **/
    @ApiModelProperty(value = "A reference to an agreement defined in the context of the product order")
    @Valid
    public List<AgreementRef> getAgreement() {
        return agreement;
    }

    public void setAgreement(List<AgreementRef> agreement) {
        this.agreement = agreement;
    }

    public ProductOrderCreate billingAccount(BillingAccountRef billingAccount) {
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

    public ProductOrderCreate channel(List<RelatedChannel> channel) {
        this.channel = channel;
        return this;
    }

    public ProductOrderCreate addChannelItem(RelatedChannel channelItem) {
        if (this.channel == null) {
            this.channel = new ArrayList<>();
        }
        this.channel.add(channelItem);
        return this;
    }

    /**
     * Get channel
     *
     * @return channel
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<RelatedChannel> getChannel() {
        return channel;
    }

    public void setChannel(List<RelatedChannel> channel) {
        this.channel = channel;
    }

    public ProductOrderCreate note(List<Note> note) {
        this.note = note;
        return this;
    }

    public ProductOrderCreate addNoteItem(Note noteItem) {
        if (this.note == null) {
            this.note = new ArrayList<>();
        }
        this.note.add(noteItem);
        return this;
    }

    /**
     * Get note
     *
     * @return note
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public ProductOrderCreate orderTotalPrice(List<OrderPrice> orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
        return this;
    }

    public ProductOrderCreate addOrderTotalPriceItem(OrderPrice orderTotalPriceItem) {
        if (this.orderTotalPrice == null) {
            this.orderTotalPrice = new ArrayList<>();
        }
        this.orderTotalPrice.add(orderTotalPriceItem);
        return this;
    }

    /**
     * Get orderTotalPrice
     *
     * @return orderTotalPrice
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<OrderPrice> getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(List<OrderPrice> orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public ProductOrderCreate payment(List<PaymentRef> payment) {
        this.payment = payment;
        return this;
    }

    public ProductOrderCreate addPaymentItem(PaymentRef paymentItem) {
        if (this.payment == null) {
            this.payment = new ArrayList<>();
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

    public ProductOrderCreate productOfferingQualification(List<ProductOfferingQualificationRef> productOfferingQualification) {
        this.productOfferingQualification = productOfferingQualification;
        return this;
    }

    public ProductOrderCreate addProductOfferingQualificationItem(ProductOfferingQualificationRef productOfferingQualificationItem) {
        if (this.productOfferingQualification == null) {
            this.productOfferingQualification = new ArrayList<>();
        }
        this.productOfferingQualification.add(productOfferingQualificationItem);
        return this;
    }

    /**
     * Get productOfferingQualification
     *
     * @return productOfferingQualification
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<ProductOfferingQualificationRef> getProductOfferingQualification() {
        return productOfferingQualification;
    }

    public void setProductOfferingQualification(List<ProductOfferingQualificationRef> productOfferingQualification) {
        this.productOfferingQualification = productOfferingQualification;
    }

    public ProductOrderCreate productOrderItem(List<ProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
        return this;
    }

    public ProductOrderCreate addProductOrderItemItem(ProductOrderItem productOrderItemItem) {
        this.productOrderItem.add(productOrderItemItem);
        return this;
    }

    /**
     * Get productOrderItem
     *
     * @return productOrderItem
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    @Size(min = 1)
    public List<ProductOrderItem> getProductOrderItem() {
        return productOrderItem;
    }

    public void setProductOrderItem(List<ProductOrderItem> productOrderItem) {
        this.productOrderItem = productOrderItem;
    }

    public ProductOrderCreate quote(List<QuoteRef> quote) {
        this.quote = quote;
        return this;
    }

    public ProductOrderCreate addQuoteItem(QuoteRef quoteItem) {
        if (this.quote == null) {
            this.quote = new ArrayList<>();
        }
        this.quote.add(quoteItem);
        return this;
    }

    /**
     * Get quote
     *
     * @return quote
     **/
    @ApiModelProperty(value = "")
    @Valid
    public List<QuoteRef> getQuote() {
        return quote;
    }

    public void setQuote(List<QuoteRef> quote) {
        this.quote = quote;
    }

    public ProductOrderCreate relatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
        return this;
    }

    public ProductOrderCreate addRelatedPartyItem(RelatedParty relatedPartyItem) {
        if (this.relatedParty == null) {
            this.relatedParty = new ArrayList<>();
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

    public ProductOrderCreate baseType(String baseType) {
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

    public ProductOrderCreate schemaLocation(String schemaLocation) {
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

    public ProductOrderCreate type(String type) {
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
        ProductOrderCreate productOrderCreate = (ProductOrderCreate) o;
        return Objects.equals(this.cancellationDate, productOrderCreate.cancellationDate) &&
                Objects.equals(this.cancellationReason, productOrderCreate.cancellationReason) &&
                Objects.equals(this.category, productOrderCreate.category) &&
                Objects.equals(this.description, productOrderCreate.description) &&
                Objects.equals(this.externalId, productOrderCreate.externalId) &&
                Objects.equals(this.notificationContact, productOrderCreate.notificationContact) &&
                Objects.equals(this.priority, productOrderCreate.priority) &&
                Objects.equals(this.requestedCompletionDate, productOrderCreate.requestedCompletionDate) &&
                Objects.equals(this.requestedStartDate, productOrderCreate.requestedStartDate) &&
                Objects.equals(this.agreement, productOrderCreate.agreement) &&
                Objects.equals(this.billingAccount, productOrderCreate.billingAccount) &&
                Objects.equals(this.channel, productOrderCreate.channel) &&
                Objects.equals(this.note, productOrderCreate.note) &&
                Objects.equals(this.orderTotalPrice, productOrderCreate.orderTotalPrice) &&
                Objects.equals(this.payment, productOrderCreate.payment) &&
                Objects.equals(this.productOfferingQualification, productOrderCreate.productOfferingQualification) &&
                Objects.equals(this.productOrderItem, productOrderCreate.productOrderItem) &&
                Objects.equals(this.quote, productOrderCreate.quote) &&
                Objects.equals(this.relatedParty, productOrderCreate.relatedParty) &&
                Objects.equals(this.baseType, productOrderCreate.baseType) &&
                Objects.equals(this.schemaLocation, productOrderCreate.schemaLocation) &&
                Objects.equals(this.type, productOrderCreate.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cancellationDate, cancellationReason, category, description, externalId, notificationContact, priority, requestedCompletionDate, requestedStartDate, agreement, billingAccount, channel, note, orderTotalPrice, payment, productOfferingQualification, productOrderItem, quote, relatedParty, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {

        return "class ProductOrderCreate {\n" +
                "    cancellationDate: " + toIndentedString(cancellationDate) + "\n" +
                "    cancellationReason: " + toIndentedString(cancellationReason) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    externalId: " + toIndentedString(externalId) + "\n" +
                "    notificationContact: " + toIndentedString(notificationContact) + "\n" +
                "    priority: " + toIndentedString(priority) + "\n" +
                "    requestedCompletionDate: " + toIndentedString(requestedCompletionDate) + "\n" +
                "    requestedStartDate: " + toIndentedString(requestedStartDate) + "\n" +
                "    agreement: " + toIndentedString(agreement) + "\n" +
                "    billingAccount: " + toIndentedString(billingAccount) + "\n" +
                "    channel: " + toIndentedString(channel) + "\n" +
                "    note: " + toIndentedString(note) + "\n" +
                "    orderTotalPrice: " + toIndentedString(orderTotalPrice) + "\n" +
                "    payment: " + toIndentedString(payment) + "\n" +
                "    productOfferingQualification: " + toIndentedString(productOfferingQualification) + "\n" +
                "    productOrderItem: " + toIndentedString(productOrderItem) + "\n" +
                "    quote: " + toIndentedString(quote) + "\n" +
                "    relatedParty: " + toIndentedString(relatedParty) + "\n" +
                "    baseType: " + toIndentedString(baseType) + "\n" +
                "    schemaLocation: " + toIndentedString(schemaLocation) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
                "}";
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
