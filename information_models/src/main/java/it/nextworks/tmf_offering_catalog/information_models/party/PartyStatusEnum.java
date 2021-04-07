package it.nextworks.tmf_offering_catalog.information_models.party;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PartyStatusEnum {

    INITIALIZED("initialized"),

    VALIDATED("validated"),

    CLOSED("closed");

    private String value;

    PartyStatusEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PartyStatusEnum fromValue(String text) {
        for (PartyStatusEnum b : PartyStatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}