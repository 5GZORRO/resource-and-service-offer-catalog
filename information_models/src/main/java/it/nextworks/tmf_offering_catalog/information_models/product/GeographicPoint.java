package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A GeographicPoint defines a geographic point through coordinates
 */
@ApiModel(description = "A GeographicPoint defines a geographic point through coordinates")
@Validated
@Entity
@Table(name = "geographic_points")
public class GeographicPoint {

    @JsonIgnoreProperties(allowGetters = true)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("x")
    private String x;

    @JsonProperty("y")
    private String y;

    @JsonProperty("z")
    private String z;

    /**
     * Unique identifier of the geographic point
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique identifier of the geographic point")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeographicPoint id(String id) {
        this.id = id;
        return this;
    }

    /**
     * x coordinate (usually latitude)
     *
     * @return x
     **/
    @ApiModelProperty("x coordinate (usually latitude)")
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public GeographicPoint x(String x) {
        this.x = x;
        return this;
    }

    /**
     * y coordinate (usually longitude)
     *
     * @return y
     **/
    @ApiModelProperty("y coordinate (usually longitude)")
    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public GeographicPoint y(String y) {
        this.y = y;
        return this;
    }

    /**
     * z coordinate (usually elevation)
     *
     * @return z
     **/
    @ApiModelProperty("z coordinate (usually elevation)")
    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public GeographicPoint z(String z) {
        this.z = z;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeographicPoint that = (GeographicPoint) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(z, that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, z);
    }

    @Override
    public String toString() {
        return "GeographicPoint{" +
                "id='" + id + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }

}
