package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * This resource is used to manage address request.
 */
@ApiModel(description = "This resource is used to manage address request.")
@Validated
public class GeographicAddressCreate {

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("country")
    private String country = null;

    @JsonProperty("locality")
    private String locality = null;

    @JsonProperty("postcode")
    private String postcode = null;

    @JsonProperty("stateOrProvince")
    @Column(name = "state_or_province")
    private String stateOrProvince = null;

    @JsonProperty("streetName")
    @Column(name = "street_name")
    private String streetName = null;

    @JsonProperty("streetNr")
    @Column(name = "street_nr")
    private String streetNr = null;

    @JsonProperty("streetNrLast")
    @Column(name = "street_nr_last")
    private String streetNrLast = null;

    @JsonProperty("streetNrLastSuffix")
    @Column(name = "street_nr_last_suffix")
    private String streetNrLastSuffix = null;

    @JsonProperty("streetNrSuffix")
    @Column(name = "street_nr_suffix")
    private String streetNrSuffix = null;

    @JsonProperty("streetSuffix")
    @Column(name = "street_suffix")
    private String streetSuffix = null;

    @JsonProperty("streetType")
    @Column(name = "street_type")
    private String streetType = null;

    @JsonProperty("geographicLocation")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "geographic_location_id", referencedColumnName = "uuid")
    private GeographicLocation geographicLocation = null;

    @JsonProperty("@schemaLocation")
    @Column(name = "schema_location")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public GeographicAddressCreate city(String city) {
        this.city = city;
        return this;
    }

    /**
     * City that the address is in
     *
     * @return city
     **/
    @ApiModelProperty(value = "City that the address is in")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GeographicAddressCreate country(String country) {
        this.country = country;
        return this;
    }

    /**
     * Country that the address is in
     *
     * @return country
     **/
    @ApiModelProperty(value = "Country that the address is in")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeographicAddressCreate locality(String locality) {
        this.locality = locality;
        return this;
    }

    /**
     * An area of defined or undefined boundaries within a local authority or other legislatively defined area, usually rural or semi rural in nature. [ANZLIC-STREET], or a suburb, a bounded locality within a city, town or shire principally of urban character [ANZLICSTREET]
     *
     * @return locality
     **/
    @ApiModelProperty(value = "An area of defined or undefined boundaries within a local authority or other legislatively defined area, usually rural or semi rural in nature. [ANZLIC-STREET], or a suburb, a bounded locality within a city, town or shire principally of urban character [ANZLICSTREET]")
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public GeographicAddressCreate postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    /**
     * descriptor for a postal delivery area, used to speed and simplify the delivery of mail (also know as zipcode)
     *
     * @return postcode
     **/
    @ApiModelProperty(value = "descriptor for a postal delivery area, used to speed and simplify the delivery of mail (also know as zipcode)")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public GeographicAddressCreate stateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    /**
     * the State or Province that the address is in
     *
     * @return stateOrProvince
     **/
    @ApiModelProperty(value = "the State or Province that the address is in")
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public GeographicAddressCreate streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    /**
     * Name of the street or other street type
     *
     * @return streetName
     **/
    @ApiModelProperty(value = "Name of the street or other street type")
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public GeographicAddressCreate streetNr(String streetNr) {
        this.streetNr = streetNr;
        return this;
    }

    /**
     * Number identifying a specific property on a public street. It may be combined with streetNrLast for ranged addresses
     *
     * @return streetNr
     **/
    @ApiModelProperty(value = "Number identifying a specific property on a public street. It may be combined with streetNrLast for ranged addresses")
    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    public GeographicAddressCreate streetNrLast(String streetNrLast) {
        this.streetNrLast = streetNrLast;
        return this;
    }

    /**
     * Last number in a range of street numbers allocated to a property
     *
     * @return streetNrLast
     **/
    @ApiModelProperty(value = "Last number in a range of street numbers allocated to a property")
    public String getStreetNrLast() {
        return streetNrLast;
    }

    public void setStreetNrLast(String streetNrLast) {
        this.streetNrLast = streetNrLast;
    }

    public GeographicAddressCreate streetNrLastSuffix(String streetNrLastSuffix) {
        this.streetNrLastSuffix = streetNrLastSuffix;
        return this;
    }

    /**
     * Last street number suffix for a ranged address
     *
     * @return streetNrLastSuffix
     **/
    @ApiModelProperty(value = "Last street number suffix for a ranged address")
    public String getStreetNrLastSuffix() {
        return streetNrLastSuffix;
    }

    public void setStreetNrLastSuffix(String streetNrLastSuffix) {
        this.streetNrLastSuffix = streetNrLastSuffix;
    }

    public GeographicAddressCreate streetNrSuffix(String streetNrSuffix) {
        this.streetNrSuffix = streetNrSuffix;
        return this;
    }

    /**
     * the first street number suffix
     *
     * @return streetNrSuffix
     **/
    @ApiModelProperty(value = "the first street number suffix")
    public String getStreetNrSuffix() {
        return streetNrSuffix;
    }

    public void setStreetNrSuffix(String streetNrSuffix) {
        this.streetNrSuffix = streetNrSuffix;
    }

    public GeographicAddressCreate streetSuffix(String streetSuffix) {
        this.streetSuffix = streetSuffix;
        return this;
    }

    /**
     * A modifier denoting a relative direction
     *
     * @return streetSuffix
     **/
    @ApiModelProperty(value = "A modifier denoting a relative direction")
    public String getStreetSuffix() {
        return streetSuffix;
    }

    public void setStreetSuffix(String streetSuffix) {
        this.streetSuffix = streetSuffix;
    }

    public GeographicAddressCreate streetType(String streetType) {
        this.streetType = streetType;
        return this;
    }

    /**
     * alley, avenue, boulevard, brae, crescent, drive, highway, lane, terrace, parade, place, tarn, way, wharf
     *
     * @return streetType
     **/
    @ApiModelProperty(value = "alley, avenue, boulevard, brae, crescent, drive, highway, lane, terrace, parade, place, tarn, way, wharf ")
    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public GeographicAddressCreate geographicLocation(GeographicLocation geographicLocation) {
        this.geographicLocation = geographicLocation;
        return this;
    }

    /**
     * Get geographicLocation
     *
     * @return geographicLocation
     **/
    @ApiModelProperty(value = "")
    @Valid
    public GeographicLocation getGeographicLocation() {
        return geographicLocation;
    }

    public void setGeographicLocation(GeographicLocation geographicLocation) {
        this.geographicLocation = geographicLocation;
    }

    /**
     * A URI to a JSON-Schema file that defines additional attributes and relationships
     *
     * @return schemaLocation
     **/
    @ApiModelProperty(value = "A URI to a JSON-Schema file that defines additional attributes and relationships")
    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public GeographicAddressCreate type(String type) {
        this.type = type;
        return this;
    }

    /**
     * When sub-classing, this defines the sub-class entity name
     *
     * @return type
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the sub-class entity name")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicAddressCreate geographicAddress = (GeographicAddressCreate) o;
        return Objects.equals(this.city, geographicAddress.city) &&
                Objects.equals(this.country, geographicAddress.country) &&
                Objects.equals(this.locality, geographicAddress.locality) &&
                Objects.equals(this.postcode, geographicAddress.postcode) &&
                Objects.equals(this.stateOrProvince, geographicAddress.stateOrProvince) &&
                Objects.equals(this.streetName, geographicAddress.streetName) &&
                Objects.equals(this.streetNr, geographicAddress.streetNr) &&
                Objects.equals(this.streetNrLast, geographicAddress.streetNrLast) &&
                Objects.equals(this.streetNrLastSuffix, geographicAddress.streetNrLastSuffix) &&
                Objects.equals(this.streetNrSuffix, geographicAddress.streetNrSuffix) &&
                Objects.equals(this.streetSuffix, geographicAddress.streetSuffix) &&
                Objects.equals(this.streetType, geographicAddress.streetType) &&
                Objects.equals(this.geographicLocation, geographicAddress.geographicLocation) &&
                Objects.equals(this.schemaLocation, geographicAddress.schemaLocation) &&
                Objects.equals(this.type, geographicAddress.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, locality, postcode, stateOrProvince, streetName, streetNr, streetNrLast, streetNrLastSuffix, streetNrSuffix, streetSuffix, streetType, geographicLocation, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicAddressCreate {\n");

        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    locality: ").append(toIndentedString(locality)).append("\n");
        sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
        sb.append("    stateOrProvince: ").append(toIndentedString(stateOrProvince)).append("\n");
        sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
        sb.append("    streetNr: ").append(toIndentedString(streetNr)).append("\n");
        sb.append("    streetNrLast: ").append(toIndentedString(streetNrLast)).append("\n");
        sb.append("    streetNrLastSuffix: ").append(toIndentedString(streetNrLastSuffix)).append("\n");
        sb.append("    streetNrSuffix: ").append(toIndentedString(streetNrSuffix)).append("\n");
        sb.append("    streetSuffix: ").append(toIndentedString(streetSuffix)).append("\n");
        sb.append("    streetType: ").append(toIndentedString(streetType)).append("\n");
        sb.append("    geographicLocation: ").append(toIndentedString(geographicLocation)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
