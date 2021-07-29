package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A geographicLocation allows referring a geographicLocation through an id, or, describing through coordinate(s) a point, a line or a space.
 */
@ApiModel(description = "A GeographicLocation is a pure-virtual super-class to the GeoJSON-aligned geometries of Point (addresses and locations), MultiPoint, LineString (streets, highways and boundaries), MultiLineString and Polygon (countries, provinces, tracts of land). Use the @type attribute to specify which of these is being specified by the geometry attribute.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-14T06:22:11.015Z")
@Entity
@Table(name = "geographic_locations")
public class GeographicLocation {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("geometryType")
    @Column(name = "geometry_type")
    private String geometryType = null;

    @JsonProperty("accuracy")
    private String accuracy = null;

    @JsonProperty("spatialRef")
    @Column(name = "spatial_ref")
    private String spatialRef = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@schemaLocation")
    @Column(name = "schema_location")
    private String schemaLocation = null;

    @JsonProperty("geometry")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "geographic_location_fk", referencedColumnName = "uuid")
    private List<GeographicPoint> geometry = null;

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    public GeographicLocation id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique identifier of the geographic location
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique identifier of the geographic location", hidden = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeographicLocation href(String href) {
        this.href = href;
        return this;
    }

    /**
     * An URI used to access to the geographic location resource
     *
     * @return href
     **/
    @ApiModelProperty(value = "An URI used to access to the geographic location resource")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public GeographicLocation name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the geographic location
     *
     * @return name
     **/
    @ApiModelProperty(value = "Name of the geographic location")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicLocation geometryType(String geometryType) {
        this.geometryType = geometryType;
        return this;
    }

    /**
     * Type allows describing Geographic Location form: it could be a point, a line, a polygon, a cylinder, etc ...
     *
     * @return geometryType
     **/
    @ApiModelProperty(value = "Type allows describing Geographic Location form: it could be a point, a line, a polygon, a cylinder, etc ...")
    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public GeographicLocation accuracy(String accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    /**
     * Accuracy of the coordinate specified
     *
     * @return geometryType
     **/
    @ApiModelProperty(value = "Accuracy of the coordinate specified")
    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public GeographicLocation type(String type) {
        this.type = type;
        return this;
    }

    public GeographicLocation spatialRef(String spatialRef) {
        this.spatialRef = spatialRef;
        return this;
    }

    /**
     * Geocoding referential
     *
     * @return spatialRef
     **/
    @ApiModelProperty(value = "Geocoding referential")
    public String getSpatialRef() {
        return accuracy;
    }

    public void setSpatialRef(String spatialRef) {
        this.spatialRef = spatialRef;
    }

    /**
     * Indicates the type of resource
     *
     * @return type
     **/
    @ApiModelProperty(required = true, value = "Indicates the type of resource")
    @NotNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * A link to the schema describing this REST Resource
     *
     * @return schemaLocation
     **/
    @ApiModelProperty(required = true, value = "A link to the schema describing this REST Resource")
    @NotNull
    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public GeographicLocation geometry(List<GeographicPoint> geometry) {
        this.geometry = geometry;
        return this;
    }

    public GeographicLocation addGeometryItem(GeographicPoint geometryItem) {
        if (this.geometry == null) {
            this.geometry = new ArrayList<GeographicPoint>();
        }
        this.geometry.add(geometryItem);
        return this;
    }

    /**
     * A list of geographic points (GeographicPoint [*]). A GeographicPoint defines a geographic point through coordinates
     *
     * @return geometry
     **/
    @ApiModelProperty(value = "A list of geographic points (GeographicPoint [*]). A GeographicPoint defines a geographic point through coordinates")
    public List<GeographicPoint> getGeometry() {
        return geometry;
    }

    public void setGeometry(List<GeographicPoint> geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicLocation geographicLocation = (GeographicLocation) o;
        return Objects.equals(this.id, geographicLocation.id) &&
                Objects.equals(this.name, geographicLocation.name) &&
                Objects.equals(this.href, geographicLocation.href) &&
                Objects.equals(this.geometryType, geographicLocation.geometryType) &&
                Objects.equals(this.accuracy, geographicLocation.accuracy) &&
                Objects.equals(this.spatialRef, geographicLocation.spatialRef) &&
                Objects.equals(this.type, geographicLocation.type) &&
                Objects.equals(this.schemaLocation, geographicLocation.schemaLocation) &&
                Objects.equals(this.geometry, geographicLocation.geometry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, href, geometryType, accuracy, spatialRef, type, schemaLocation, geometry);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GeographicLocation {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    geometryType: ").append(toIndentedString(geometryType)).append("\n");
        sb.append("    accuracy: ").append(toIndentedString(accuracy)).append("\n");
        sb.append("    spatialRef: ").append(toIndentedString(spatialRef)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
        sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
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
