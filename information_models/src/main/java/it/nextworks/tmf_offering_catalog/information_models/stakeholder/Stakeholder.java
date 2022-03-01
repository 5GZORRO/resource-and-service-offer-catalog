package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Stakeholder {

    @JsonProperty("stakeholderClaim")
    private StakeholderClaim stakeholderClaim = null;

    @JsonProperty("timestamp")
    private String timestamp = null;

    @JsonProperty("state")
    private String state = null;

    @JsonProperty("id_token")
    private String id_token = null;

    @JsonProperty("handler_url")
    private String handler_url = null;

    public Stakeholder stakeholderClaim(StakeholderClaim stakeholderClaim) {
        this.stakeholderClaim = stakeholderClaim;
        return this;
    }

    public StakeholderClaim getStakeholderClaim() { return stakeholderClaim; }

    public void setStakeholderClaim(StakeholderClaim stakeholderClaim) { this.stakeholderClaim = stakeholderClaim; }

    public Stakeholder timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public Stakeholder state(String state) {
        this.state = state;
        return this;
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public Stakeholder id_token(String id_token) {
        this.id_token = id_token;
        return this;
    }

    public String getId_token() { return id_token; }

    public void setId_token(String id_token) { this.id_token = id_token; }

    public Stakeholder handler_url(String handler_url) {
        this.handler_url = handler_url;
        return this;
    }

    public String getHandler_url() { return handler_url; }

    public void setHandler_url(String handler_url) { this.handler_url = handler_url; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Stakeholder stakeholder = (Stakeholder) o;
        return Objects.equals(this.stakeholderClaim, stakeholder.stakeholderClaim) &&
                Objects.equals(this.timestamp, stakeholder.timestamp) &&
                Objects.equals(this.state, stakeholder.state) &&
                Objects.equals(this.id_token, stakeholder.id_token) &&
                Objects.equals(this.handler_url, stakeholder.handler_url);
    }

    @Override
    public int hashCode() { return Objects.hash(stakeholderClaim, timestamp, state, id_token, handler_url); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class stakeholder {\n");
        sb.append("    stakeholderClaim: ").append(toIndentedString(stakeholderClaim)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
        sb.append("    state: ").append(toIndentedString(state)).append("\n");
        sb.append("    id_token: ").append(toIndentedString(id_token)).append("\n");
        sb.append("    handler_url: ").append(toIndentedString(handler_url)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
