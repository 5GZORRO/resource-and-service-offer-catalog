package it.nextworks.tmf_offering_catalog.rest.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import it.nextworks.tmf_offering_catalog.information_models.product.GeographicAddress_;

import java.util.HashMap;
import java.util.Map;

public class GeographicAddressFilter {

    @JsonProperty("locality")
    @ApiModelProperty("Locality of the Geographic Address to be provided in response.")
    private String locality;

    @JsonProperty("city")
    @ApiModelProperty("City of the Geographic Address to be provided in response.")
    private String city;

    @JsonProperty("country")
    @ApiModelProperty("Country of the Geographic Address to be provided in response.")
    private String country;

    @JsonProperty("postcode")
    @ApiModelProperty("Postcode of the Geographic Address to be provided in response.")
    private String postcode;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Map<String, String> getAllAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(GeographicAddress_.LOCALITY, locality);
        attributes.put(GeographicAddress_.CITY, city);
        attributes.put(GeographicAddress_.COUNTRY, country);
        attributes.put(GeographicAddress_.POSTCODE, postcode);
        return attributes;
    }

}
