package it.nextworks.tmf_offering_catalog.information_models.product.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductOrderStatesEnum {

    DID_REQUESTED("Product Order DID requested"),

    STORED_WITH_DID("Product Order stored with DID"),

    PUBLISHED("Product Order published"),

    PUBLISHING_FAILED("Product Order publishing failed"),

    EXTERNAL("External Product Order"),

    INSTANTIATED("Product Order instantiated"),

    REJECTED("Product Order rejected");

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
