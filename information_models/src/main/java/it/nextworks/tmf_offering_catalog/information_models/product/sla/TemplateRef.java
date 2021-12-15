package it.nextworks.tmf_offering_catalog.information_models.product.sla;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TemplateRef {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonCreator
    public TemplateRef(@JsonProperty("id") String id,
                       @JsonProperty("href") String href,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") String description) {
        this.id          = id;
        this.href        = href;
        this.name        = name;
        this.description = description;
    }

    public TemplateRef id(String id) {
        this.id = id;
        return this;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public TemplateRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) { this.href = href; }

    public String getHref() { return href; }

    public TemplateRef name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public TemplateRef description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        TemplateRef template = (TemplateRef) o;

        return Objects.equals(this.id, template.id) &&
                Objects.equals(this.href, template.href) &&
                Objects.equals(this.name, template.name) &&
                Objects.equals(this.description, template.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class TemplateRef {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
