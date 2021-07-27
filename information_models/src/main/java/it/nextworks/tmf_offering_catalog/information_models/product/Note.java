package it.nextworks.tmf_offering_catalog.information_models.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Extra information about a given entity
 */
@ApiModel(description = "Extra information about a given entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-13T14:59:13.201Z")
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id = null;

    @JsonProperty("author")
    private String author = null;

    @JsonProperty("date")
    private OffsetDateTime date = null;

    @JsonProperty("text")
    private String text = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    public Note id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Identifier of the note within its containing entity (may or may not be globally unique, depending on provider implementation)
     *
     * @return id
     **/
    @ApiModelProperty(value = "Identifier of the note within its containing entity (may or may not be globally unique, depending on provider implementation)", hidden = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Note author(String author) {
        this.author = author;
        return this;
    }

    /**
     * Author of the note
     *
     * @return author
     **/
    @ApiModelProperty(value = "Author of the note")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Note date(OffsetDateTime date) {
        this.date = date;
        return this;
    }

    /**
     * Date of the note
     *
     * @return date
     **/
    @ApiModelProperty(value = "Date of the note")
    @Valid
    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Note text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Text of the note
     *
     * @return text
     **/
    @ApiModelProperty(required = true, value = "Text of the note")
    @NotNull
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Note baseType(String baseType) {
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

    public Note schemaLocation(String schemaLocation) {
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

    public Note type(String type) {
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
        Note note = (Note) o;
        return Objects.equals(this.id, note.id) &&
                Objects.equals(this.author, note.author) &&
                Objects.equals(this.date, note.date) &&
                Objects.equals(this.text, note.text) &&
                Objects.equals(this.baseType, note.baseType) &&
                Objects.equals(this.schemaLocation, note.schemaLocation) &&
                Objects.equals(this.type, note.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, date, text, baseType, schemaLocation, type);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Note {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
