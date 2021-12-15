package it.nextworks.tmf_offering_catalog.information_models.product.sla;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Rule {

    @JsonProperty("id")
    private String id;

    @JsonProperty("metric")
    private String metric;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("referenceValue")
    private String referenceValue;

    @JsonProperty("operator")
    private String operator;

    @JsonProperty("tolerance")
    private String tolerance;

    @JsonProperty("consequence")
    private String consequence;

    @JsonCreator
    public Rule(@JsonProperty("id") String id,
                @JsonProperty("metric") String metric,
                @JsonProperty("unit") String unit,
                @JsonProperty("referenceValue") String referenceValue,
                @JsonProperty("operator") String operator,
                @JsonProperty("tolerance") String tolerance,
                @JsonProperty("consequence") String consequence) {
        this.id             = id;
        this.metric         = metric;
        this.unit           = unit;
        this.referenceValue = referenceValue;
        this.operator       = operator;
        this.tolerance      = tolerance;
        this.consequence    = consequence;
    }

    public Rule id(String id) {
        this.id = id;
        return this;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public Rule metric(String metric) {
        this.metric = metric;
        return this;
    }

    public void setMetric(String metric) { this.metric = metric; }

    public String getMetric() { return metric; }

    public Rule unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) { this.unit = unit; }

    public String getUnit() { return unit; }

    public Rule referenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
        return this;
    }

    public void setReferenceValue(String referenceValue) { this.referenceValue = referenceValue; }

    public String getReferenceValue() { return referenceValue; }

    public Rule operator(String operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(String operator) { this.operator = operator; }

    public String getOperator() { return operator; }

    public Rule tolerance(String tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    public void setTolerance(String tolerance) { this.tolerance = tolerance; }

    public String getTolerance() { return tolerance; }

    public Rule consequence(String consequence) {
        this.consequence = consequence;
        return this;
    }

    public void setConsequence(String consequence) { this.consequence = consequence; }

    public String getConsequence() { return consequence; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Rule rule = (Rule) o;

        return Objects.equals(this.id, rule.id) &&
                Objects.equals(this.metric, rule.metric) &&
                Objects.equals(this.unit, rule.unit) &&
                Objects.equals(this.referenceValue, rule.referenceValue) &&
                Objects.equals(this.operator, rule.operator) &&
                Objects.equals(this.tolerance, rule.tolerance) &&
                Objects.equals(this.consequence, rule.consequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metric, unit, referenceValue, operator, tolerance, consequence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class Rule {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    metric: ").append(toIndentedString(metric)).append("\n");
        sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
        sb.append("    referenceValue: ").append(toIndentedString(referenceValue)).append("\n");
        sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
        sb.append("    tolerance: ").append(toIndentedString(tolerance)).append("\n");
        sb.append("    consequence: ").append(toIndentedString(consequence)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
