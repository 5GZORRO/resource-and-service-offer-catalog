package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Asset {

    Edge("Edge"),

    Cloud("Cloud"),

    Spectrum("Spectrum"),

    RadioAccessNetwork("RAN"),

    VirtualNetworkFunction("VNF"),

    NetworkSlice("Slice"),

    NetworkService("Network Service");

    private String value;

    Asset(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static Asset fromValue(String text) {
        for(Asset b : Asset.values()) {
            if(String.valueOf(b.value).equals(text))
                return b;
        }

        return null;
    }
}
