package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductOrderStatesEnum {

    PUBLISHED("Product Order published"),

    PUBLISHING_FAILED("Product Order publishing failed");

    private String value;

    ProductOrderStatesEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ProductOrderStatesEnum fromValue(String text) {
        for (ProductOrderStatesEnum b : ProductOrderStatesEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }

}
