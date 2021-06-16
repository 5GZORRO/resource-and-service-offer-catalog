package it.nextworks.tmf_offering_catalog.information_models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Related Entity reference. A related place defines a place described by reference or by value linked to a specific entity. The polymorphic attributes @type, @schemaLocation &amp; @referredType are related to the place entity and not the RelatedPlaceRefOrValue class itself
 */
@ApiModel(description = "Related Entity reference. A related place defines a place described by reference or by value linked to a specific entity. The polymorphic attributes @type, @schemaLocation & @referredType are related to the place entity and not the RelatedPlaceRefOrValue class itself")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "related_places")
public class RelatedPlace {

    @JsonProperty("id")
    private String id = null;

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("role")
    private String role = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@referredType")
    private String referredType = null;

    public RelatedPlace id(String id) {
        this.id = id;
        return this;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RelatedPlace href(String href) {
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

    public RelatedPlace name(String name) {
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

    public RelatedPlace role(String role) {
        this.role = role;
        return this;
    }

    /**
     * Get role
     *
     * @return role
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RelatedPlace baseType(String baseType) {
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

    public RelatedPlace schemaLocation(String schemaLocation) {
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

    public RelatedPlace type(String type) {
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

    public RelatedPlace referredType(String referredType) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelatedPlace relatedPlaceRefOrValue = (RelatedPlace) o;
        return Objects.equals(this.id, relatedPlaceRefOrValue.id) &&
                Objects.equals(this.href, relatedPlaceRefOrValue.href) &&
                Objects.equals(this.name, relatedPlaceRefOrValue.name) &&
                Objects.equals(this.role, relatedPlaceRefOrValue.role) &&
                Objects.equals(this.baseType, relatedPlaceRefOrValue.baseType) &&
                Objects.equals(this.schemaLocation, relatedPlaceRefOrValue.schemaLocation) &&
                Objects.equals(this.type, relatedPlaceRefOrValue.type) &&
                Objects.equals(this.referredType, relatedPlaceRefOrValue.referredType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, role, baseType, schemaLocation, type, referredType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RelatedPlaceRefOrValue {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
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
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
