package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A GeographicLocation is a pure-virtual super-class to the GeoJSON-aligned geometries of Point (addresses and locations), MultiPoint, LineString (streets, highways and boundaries), MultiLineString and Polygon (countries, provinces, tracts of land). Use the @type attribute to specify which of these is being specified by the geometry attribute.
 */
@ApiModel(description = "A GeographicLocation is a pure-virtual super-class to the GeoJSON-aligned geometries of Point (addresses and locations), MultiPoint, LineString (streets, highways and boundaries), MultiLineString and Polygon (countries, provinces, tracts of land). Use the @type attribute to specify which of these is being specified by the geometry attribute.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
@Entity
@Table(name = "geographic_location_refs_or_values")
public class GeographicLocationRefOrValue {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("@baseType")
    @Column(name = "base_type")
    private String baseType = null;

    @JsonProperty("bbox")
    @Valid
    @Transient //TODO: transform to string?
    private List<BigDecimal> bbox = null;

    @JsonProperty("@schemaLocation")
    @Column(name = "schema_location")
    private String schemaLocation = null;

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    /**
     * The name of the GeoJSON structure used in the geometry attribute
     */
    public enum TypeEnum {
        GEOJSONPOINT("GeoJsonPoint"),

        GEOJSONMULTIPOINT("GeoJsonMultiPoint"),

        GEOJSONLINESTRING("GeoJsonLineString"),

        GEOJSONMULTILINESTRING("GeoJsonMultiLineString"),

        GEOJSONPOLYGON("GeoJsonPolygon");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TypeEnum fromValue(String text) {
            for (TypeEnum b : TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("@type")
    private TypeEnum type = null;

    @JsonProperty("@referredType")
    @Column(name = "referred_type")
    private String referredType = null;

    public GeographicLocationRefOrValue uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Get uuid
     *
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
     * Unique identifier of the place
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique identifier of the place")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeographicLocationRefOrValue href(String href) {
        this.href = href;
        return this;
    }

    /**
     * Unique reference of the place
     *
     * @return href
     **/
    @ApiModelProperty(value = "Unique reference of the place")


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public GeographicLocationRefOrValue name(String name) {
        this.name = name;
        return this;
    }

    /**
     * A user-friendly name for the place, such as [Paris Store], [London Store], [Main Home]
     *
     * @return name
     **/
    @ApiModelProperty(value = "A user-friendly name for the place, such as [Paris Store], [London Store], [Main Home]")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicLocationRefOrValue baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    /**
     * When sub-classing, this defines the super-class
     *
     * @return baseType
     **/
    @ApiModelProperty(example = "ResourceSpecification", value = "When sub-classing, this defines the super-class")


    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public GeographicLocationRefOrValue bbox(List<BigDecimal> bbox) {
        this.bbox = bbox;
        return this;
    }

    public GeographicLocationRefOrValue addBboxItem(BigDecimal bboxItem) {
        if (this.bbox == null) {
            this.bbox = new ArrayList<BigDecimal>();
        }
        this.bbox.add(bboxItem);
        return this;
    }

    /**
     * A bounding box array that contains the geometry. The axes order follows the axes order of the geometry
     *
     * @return bbox
     **/
    @ApiModelProperty(value = "A bounding box array that contains the geometry. The axes order follows the axes order of the geometry")

    @Valid

    public List<BigDecimal> getBbox() {
        return bbox;
    }

    public void setBbox(List<BigDecimal> bbox) {
        this.bbox = bbox;
    }

    public GeographicLocationRefOrValue schemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
    }

    /**
     * A URI to a JSON-Schema file that defines additional attributes and relationships
     *
     * @return schemaLocation
     **/
    @ApiModelProperty(example = "https://mycsp.com:8080/tmf-api/schema/Resource/LogicalResourceSpecification.schema.json", value = "A URI to a JSON-Schema file that defines additional attributes and relationships")


    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public GeographicLocationRefOrValue type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * The name of the GeoJSON structure used in the geometry attribute
     *
     * @return type
     **/
    @ApiModelProperty(required = true, value = "The name of the GeoJSON structure used in the geometry attribute")
    @NotNull


    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public GeographicLocationRefOrValue referredType(String referredType) {
        this.referredType = referredType;
        return this;
    }

    /**
     * The actual type of the target instance when needed for disambiguation.
     *
     * @return referredType
     **/
    @ApiModelProperty(value = "The actual type of the target instance when needed for disambiguation.")


    public String getReferredType() {
        return referredType;
    }

    public void setReferredType(String referredType) {
        this.referredType = referredType;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicLocationRefOrValue geographicLocationRefOrValue = (GeographicLocationRefOrValue) o;
        return Objects.equals(this.id, geographicLocationRefOrValue.id) &&
                Objects.equals(this.href, geographicLocationRefOrValue.href) &&
                Objects.equals(this.name, geographicLocationRefOrValue.name) &&
                Objects.equals(this.baseType, geographicLocationRefOrValue.baseType) &&
                Objects.equals(this.bbox, geographicLocationRefOrValue.bbox) &&
                Objects.equals(this.schemaLocation, geographicLocationRefOrValue.schemaLocation) &&
                Objects.equals(this.type, geographicLocationRefOrValue.type) &&
                Objects.equals(this.referredType, geographicLocationRefOrValue.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, baseType, bbox, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicLocationRefOrValue {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
        sb.append("    bbox: ").append(toIndentedString(bbox)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    referredType: ").append(toIndentedString(referredType)).append("\n");
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
