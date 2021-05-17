package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representation of a GeographicSubAddress  It is used for addressing within a property in an urban area (country properties are often defined differently). It may refer to a building, a building cluster, or a floor of a multistory building.
 */
@ApiModel(description = "Representation of a GeographicSubAddress  It is used for addressing within a property in an urban area (country properties are often defined differently). It may refer to a building, a building cluster, or a floor of a multistory building.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
@Entity
@Table(name = "geographic_sub_addresses")
public class GeographicSubAddress {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("buildingName")
    @Column(name = "building_name")
    private String buildingName = null;

    @JsonProperty("levelNumber")
    @Column(name = "level_number")
    private String levelNumber = null;

    @JsonProperty("levelType")
    @Column(name = "level_type")
    private String levelType = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("privateStreetName")
    @Column(name = "private_street_name")
    private String privateStreetName = null;

    @JsonProperty("privateStreetNumber")
    @Column(name = "private_street_number")
    private String privateStreetNumber = null;

    @JsonProperty("subAddressType")
    @Column(name = "sub_address_type")
    private String subAddressType = null;

    @JsonProperty("subUnitNumber")
    @Column(name = "sub_unit_number")
    private String subUnitNumber = null;

    @JsonProperty("subUnitType")
    @Column(name = "sub_unit_type")
    private String subUnitType = null;

    @JsonProperty("@baseType")
    @Column(name = "base_type")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    @Column(name = "schema_location")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    public GeographicSubAddress uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Get uuid
     * @return uuid
     **/
    @ApiModelProperty(value = "")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Unique Identifier of the subAddress
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique Identifier of the subAddress")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeographicSubAddress href(String href) {
        this.href = href;
        return this;
    }

    /**
     * Link to the subAddress
     *
     * @return href
     **/
    @ApiModelProperty(value = "Link to the subAddress")


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public GeographicSubAddress buildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    /**
     * allows for buildings that have well-known names
     *
     * @return buildingName
     **/
    @ApiModelProperty(value = "allows for buildings that have well-known names")


    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public GeographicSubAddress levelNumber(String levelNumber) {
        this.levelNumber = levelNumber;
        return this;
    }

    /**
     * used where a level type may be repeated e.g. BASEMENT 1, BASEMENT 2
     *
     * @return levelNumber
     **/
    @ApiModelProperty(value = "used where a level type may be repeated e.g. BASEMENT 1, BASEMENT 2")


    public String getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(String levelNumber) {
        this.levelNumber = levelNumber;
    }

    public GeographicSubAddress levelType(String levelType) {
        this.levelType = levelType;
        return this;
    }

    /**
     * describes level types within a building
     *
     * @return levelType
     **/
    @ApiModelProperty(value = "describes level types within a building")


    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public GeographicSubAddress name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the subAddress to identify it with a meaningful identification
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the subAddress to identify it with a meaningful identification")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicSubAddress privateStreetName(String privateStreetName) {
        this.privateStreetName = privateStreetName;
        return this;
    }

    /**
     * private streets internal to a property (e.g. a university) may have internal names that are not recorded by the land title office.
     *
     * @return privateStreetName
     **/
    @ApiModelProperty(value = "private streets internal to a property (e.g. a university) may have internal names that are not recorded by the land title office.")


    public String getPrivateStreetName() {
        return privateStreetName;
    }

    public void setPrivateStreetName(String privateStreetName) {
        this.privateStreetName = privateStreetName;
    }

    public GeographicSubAddress privateStreetNumber(String privateStreetNumber) {
        this.privateStreetNumber = privateStreetNumber;
        return this;
    }

    /**
     * private streets numbers internal to a private street
     *
     * @return privateStreetNumber
     **/
    @ApiModelProperty(value = "private streets numbers internal to a private street")


    public String getPrivateStreetNumber() {
        return privateStreetNumber;
    }

    public void setPrivateStreetNumber(String privateStreetNumber) {
        this.privateStreetNumber = privateStreetNumber;
    }

    public GeographicSubAddress subAddressType(String subAddressType) {
        this.subAddressType = subAddressType;
        return this;
    }

    /**
     * Type of subAddress : it can be a subunit or a private street
     *
     * @return subAddressType
     **/
    @ApiModelProperty(value = "Type of subAddress : it can be a subunit or a private street")


    public String getSubAddressType() {
        return subAddressType;
    }

    public void setSubAddressType(String subAddressType) {
        this.subAddressType = subAddressType;
    }

    public GeographicSubAddress subUnitNumber(String subUnitNumber) {
        this.subUnitNumber = subUnitNumber;
        return this;
    }

    /**
     * the discriminator used for the subunit often just a simple number e.g. FLAT 5, may also be a range
     *
     * @return subUnitNumber
     **/
    @ApiModelProperty(value = "the discriminator used for the subunit often just a simple number e.g. FLAT 5, may also be a range")


    public String getSubUnitNumber() {
        return subUnitNumber;
    }

    public void setSubUnitNumber(String subUnitNumber) {
        this.subUnitNumber = subUnitNumber;
    }

    public GeographicSubAddress subUnitType(String subUnitType) {
        this.subUnitType = subUnitType;
        return this;
    }

    /**
     * the type of subunit e.g.BERTH, FLAT, PIER, SUITE, SHOP, TOWER, UNIT, WHARF
     *
     * @return subUnitType
     **/
    @ApiModelProperty(value = "the type of subunit e.g.BERTH, FLAT, PIER, SUITE, SHOP, TOWER, UNIT, WHARF")


    public String getSubUnitType() {
        return subUnitType;
    }

    public void setSubUnitType(String subUnitType) {
        this.subUnitType = subUnitType;
    }

    public GeographicSubAddress baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     *
     * @return baseType
     **/
    @ApiModelProperty(value = "When sub-classing, this defines the super-class")


    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public GeographicSubAddress schemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
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

    public GeographicSubAddress type(String type) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicSubAddress geographicSubAddress = (GeographicSubAddress) o;
        return Objects.equals(this.id, geographicSubAddress.id) &&
                Objects.equals(this.href, geographicSubAddress.href) &&
                Objects.equals(this.buildingName, geographicSubAddress.buildingName) &&
                Objects.equals(this.levelNumber, geographicSubAddress.levelNumber) &&
                Objects.equals(this.levelType, geographicSubAddress.levelType) &&
                Objects.equals(this.name, geographicSubAddress.name) &&
                Objects.equals(this.privateStreetName, geographicSubAddress.privateStreetName) &&
                Objects.equals(this.privateStreetNumber, geographicSubAddress.privateStreetNumber) &&
                Objects.equals(this.subAddressType, geographicSubAddress.subAddressType) &&
                Objects.equals(this.subUnitNumber, geographicSubAddress.subUnitNumber) &&
                Objects.equals(this.subUnitType, geographicSubAddress.subUnitType) &&
                Objects.equals(this.baseType, geographicSubAddress.baseType) &&
                Objects.equals(this.schemaLocation, geographicSubAddress.schemaLocation) &&
                Objects.equals(this.type, geographicSubAddress.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, buildingName, levelNumber, levelType, name, privateStreetName, privateStreetNumber, subAddressType, subUnitNumber, subUnitType, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicSubAddress {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    buildingName: ").append(toIndentedString(buildingName)).append("\n");
        sb.append("    levelNumber: ").append(toIndentedString(levelNumber)).append("\n");
        sb.append("    levelType: ").append(toIndentedString(levelType)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    privateStreetName: ").append(toIndentedString(privateStreetName)).append("\n");
        sb.append("    privateStreetNumber: ").append(toIndentedString(privateStreetNumber)).append("\n");
        sb.append("    subAddressType: ").append(toIndentedString(subAddressType)).append("\n");
        sb.append("    subUnitNumber: ").append(toIndentedString(subUnitNumber)).append("\n");
        sb.append("    subUnitType: ").append(toIndentedString(subUnitType)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
