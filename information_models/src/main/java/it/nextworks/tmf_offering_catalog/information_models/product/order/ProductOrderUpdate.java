package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.ProductOfferingUpdate;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProductOrderUpdate {
    @JsonProperty("cancellationDate")
    private LocalDateTime cancellationDate = null;

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

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public ProductOrderUpdate cancellationDate(LocalDateTime cancellationDate) {
        this.cancellationDate = cancellationDate;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return cancellationDate
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public LocalDateTime getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(LocalDateTime cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public ProductOrderUpdate cancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return cancellationReason
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public ProductOrderUpdate category(String category) {
        this.category = category;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return category
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductOrderUpdate description(String description) {
        this.description = description;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return description
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductOrderUpdate externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return description
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ProductOrderUpdate notificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return notificationContact
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

    public String getNotificationContact() {
        return notificationContact;
    }

    public void setNotificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
    }




    public ProductOrderUpdate baseType(String baseType) {
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

    public ProductOrderUpdate schemaLocation(String schemaLocation) {
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

    public ProductOrderUpdate type(String type) {
        this.type = type;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     * @return type
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")

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
        ProductOrderUpdate productOrderUpdate = (ProductOrderUpdate) o;
        return Objects.equals(this.cancellationDate, productOrderUpdate.cancellationDate)&&
                Objects.equals(this.cancellationReason, productOrderUpdate.cancellationReason)&&
                Objects.equals(this.category, productOrderUpdate.category)&&
                Objects.equals(this.description, productOrderUpdate.description)&&
                Objects.equals(this.externalId, productOrderUpdate.externalId)&&
                Objects.equals(this.notificationContact, productOrderUpdate.notificationContact)&&
                Objects.equals(this.baseType, productOrderUpdate.baseType)&&
                Objects.equals(this.schemaLocation, productOrderUpdate.schemaLocation)&&
                Objects.equals(this.type, productOrderUpdate.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cancellationDate, cancellationReason, category, description, externalId, notificationContact, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductOrderUpdate {\n");

        sb.append("    cancellationDate: ").append(toIndentedString(cancellationDate)).append("\n");
        sb.append("    cancellationReason: ").append(toIndentedString(cancellationReason)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
        sb.append("    notificationContact: ").append(toIndentedString(notificationContact)).append("\n");
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
