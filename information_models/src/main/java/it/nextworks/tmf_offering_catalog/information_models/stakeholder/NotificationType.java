package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {

    EMAIL("EMAIL");

    private String value;

    NotificationType(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static NotificationType fromValue(String text) {
        for(NotificationType b : NotificationType.values()) {
            if(String.valueOf(b.value).equals(text))
                return b;
        }

        return null;
    }
}
