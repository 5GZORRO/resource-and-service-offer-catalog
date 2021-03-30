package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductOfferingStatesEnum {

    DID_REQUESTED("Product Offering DID requested"),

    STORED_WITH_DID("Product Offering stored with DID"),

    CLASSIFIED("Product Offering classified"),

    CLASSIFICATION_FAILED("Product Offering classification failed"),

    PUBLISHED("Product Offering published"),

    PUBLISHING_FAILED("Product Offering publishing failed"),

    EXTERNAL("External Product Offering");

    private String value;

    ProductOfferingStatesEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static ProductOfferingStatesEnum fromValue(String text) {
        for(ProductOfferingStatesEnum b : ProductOfferingStatesEnum.values()) {
            if(String.valueOf(b.value).equals(text))
                return b;
        }

        return null;
    }

}
