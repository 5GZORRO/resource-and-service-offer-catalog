package it.nextworks.tmf_offering_catalog.information_models.product.sla;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.tmf_offering_catalog.information_models.common.RelatedParty;
import it.nextworks.tmf_offering_catalog.information_models.common.TimePeriod;

import java.util.List;
import java.util.Objects;

public class ServiceLevelAgreement {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("validFor")
    private TimePeriod validFor;

    @JsonProperty("template")
    private TemplateRef template;

    @JsonProperty("state")
    private String state;

    @JsonProperty("approved")
    private Boolean approved;

    @JsonProperty("approvalDate")
    private String approvalDate;

    @JsonProperty("rule")
    private List<Rule> rule;

    @JsonProperty("relatedParty")
    private List<RelatedParty> relatedParty;

    @JsonCreator
    public ServiceLevelAgreement(@JsonProperty("id") String id,
                                 @JsonProperty("href") String href,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("description") String description,
                                 @JsonProperty("version") String version,
                                 @JsonProperty("validFor") TimePeriod validFor,
                                 @JsonProperty("template") TemplateRef template,
                                 @JsonProperty("state") String state,
                                 @JsonProperty("approved") Boolean approved,
                                 @JsonProperty("approvalDate") String approvalDate,
                                 @JsonProperty("rule") List<Rule> rule,
                                 @JsonProperty("relatedParty") List<RelatedParty> relatedParty) {
        this.id           = id;
        this.href         = href;
        this.name         = name;
        this.description  = description;
        this.version      = version;
        this.validFor     = validFor;
        this.template     = template;
        this.state        = state;
        this.approved     = approved;
        this.approvalDate = approvalDate;
        this.rule         = rule;
        this.relatedParty = relatedParty;
    }

    public ServiceLevelAgreement id(String id) {
        this.id = id;
        return this;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public ServiceLevelAgreement href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) { this.href = href; }

    public String getHref() { return href; }

    public ServiceLevelAgreement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public ServiceLevelAgreement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() { return description; }

    public ServiceLevelAgreement version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) { this.version = version; }

    public String getVersion() { return version; }

    public ServiceLevelAgreement validFor(TimePeriod validFor) {
        this.validFor = validFor;
        return this;
    }

    public void setValidFor(TimePeriod validFor) { this.validFor = validFor; }

    public TimePeriod getValidFor() { return validFor; }


    public ServiceLevelAgreement template(TemplateRef template) {
        this.template = template;
        return this;
    }

    public void setTemplate(TemplateRef template) { this.template = template; }

    public TemplateRef getTemplate() { return template; }

    public ServiceLevelAgreement state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) { this.state = state; }

    public String getState() { return state; }

    public ServiceLevelAgreement approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) { this.approved = approved; }

    public Boolean getApproved() { return approved; }

    public ServiceLevelAgreement approvalDate(String approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public void setApprovalDate(String approvalDate) { this.approvalDate = approvalDate; }

    public String getApprovalDate() { return approvalDate; }

    public ServiceLevelAgreement rule(List<Rule> rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(List<Rule> rule) { this.rule = rule; }

    public List<Rule> getRule() { return rule; }

    public ServiceLevelAgreement relatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
        return this;
    }

    public void setRelatedParty(List<RelatedParty> relatedParty) { this.relatedParty = relatedParty; }

    public List<RelatedParty> getRelatedParty() { return relatedParty; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ServiceLevelAgreement serviceLevelAgreement = (ServiceLevelAgreement) o;

        return Objects.equals(this.id, serviceLevelAgreement.id) &&
                Objects.equals(this.href, serviceLevelAgreement.href) &&
                Objects.equals(this.name, serviceLevelAgreement.name) &&
                Objects.equals(this.description, serviceLevelAgreement.description) &&
                Objects.equals(this.version, serviceLevelAgreement.version) &&
                Objects.equals(this.validFor, serviceLevelAgreement.validFor) &&
                Objects.equals(this.template, serviceLevelAgreement.template) &&
                Objects.equals(this.state, serviceLevelAgreement.state) &&
                Objects.equals(this.approved, serviceLevelAgreement.approved) &&
                Objects.equals(this.approvalDate, serviceLevelAgreement.approvalDate) &&
                Objects.equals(this.rule, serviceLevelAgreement.rule) &&
                Objects.equals(this.relatedParty, serviceLevelAgreement.relatedParty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, description, version, validFor, template, state, approved, approvalDate, rule, relatedParty);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ServiceLevelAgreement {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    href: ").append(toIndentedString(href)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    validFor: ").append(toIndentedString(validFor)).append("\n");
        sb.append("    template: ").append(toIndentedString(template)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    approved: ").append(toIndentedString(approved)).append("\n");
        sb.append("    approvalDate: ").append(toIndentedString(approvalDate)).append("\n");
        sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
        sb.append("    relatedParty: ").append(toIndentedString(relatedParty)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
