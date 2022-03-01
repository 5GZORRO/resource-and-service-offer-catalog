package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    Trader("Trader"),

    Regulator("Regulator");

    private String value;

    Role(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static Role fromValue(String text) {
        for(Role b : Role.values()) {
            if(String.valueOf(b.value).equals(text))
                return b;
        }

        return null;
    }
}
