package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Possible values for the state of the order
 */
public enum ProductOrderStateType {

    ACKNOWLEDGED("acknowledged"),

    REJECTED("rejected"),

    PENDING("pending"),

    HELD("held"),

    INPROGRESS("inProgress"),

    CANCELLED("cancelled"),

    COMPLETED("completed"),

    FAILED("failed"),

    PARTIAL("partial"),

    ASSESSINGCANCELLATION("assessingCancellation"),

    PENDINGCANCELLATION("pendingCancellation");

    private final String value;

    ProductOrderStateType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ProductOrderStateType fromValue(String text) {
        for (ProductOrderStateType b : ProductOrderStateType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

