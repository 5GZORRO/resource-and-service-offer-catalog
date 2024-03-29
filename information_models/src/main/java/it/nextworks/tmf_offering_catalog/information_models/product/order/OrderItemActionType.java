package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * action to be performed on the product
 */
public enum OrderItemActionType {

    ADD("add"),

    MODIFY("modify"),

    DELETE("delete"),

    NOCHANGE("noChange");

    private final String value;

    OrderItemActionType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static OrderItemActionType fromValue(String text) {
        for (OrderItemActionType b : OrderItemActionType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

