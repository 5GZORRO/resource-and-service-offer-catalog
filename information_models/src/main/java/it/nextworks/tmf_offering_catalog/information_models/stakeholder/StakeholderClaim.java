package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StakeholderClaim {

    @JsonProperty("governanceBoardDID")
    private String governanceBoardDID = null;

    @JsonProperty("stakeholderRoles")
    private List<StakeholderRole> stakeholderRoles = null;

    @JsonProperty("stakeholderProfile")
    private StakeholderProfile stakeholderProfile = null;

    @JsonProperty("stakeholderDID")
    private String stakeholderDID = null;

    public StakeholderClaim governanceBoardDID(String governanceBoardDID) {
        this.governanceBoardDID = governanceBoardDID;
        return this;
    }

    public String getGovernanceBoardDID() { return governanceBoardDID; }

    public void setGovernanceBoardDID(String governanceBoardDID) { this.governanceBoardDID = governanceBoardDID; }

    public StakeholderClaim stakeholderRoles(List<StakeholderRole> stakeholderRoles) {
        this.stakeholderRoles = stakeholderRoles;
        return this;
    }

    public StakeholderClaim addStakeholderRole(StakeholderRole stakeholderRole) {
        if(this.stakeholderRoles == null)
            this.stakeholderRoles = new ArrayList<>();

        this.stakeholderRoles.add(stakeholderRole);
        return this;
    }

    public List<StakeholderRole> getStakeholderRoles() { return stakeholderRoles; }

    public void setStakeholderRoles(List<StakeholderRole> stakeholderRoles) { this.stakeholderRoles = stakeholderRoles; }

    public StakeholderClaim stakeholderProfile(StakeholderProfile stakeholderProfile) {
        this.stakeholderProfile = stakeholderProfile;
        return this;
    }

    public StakeholderProfile getStakeholderProfile() { return stakeholderProfile; }

    public void setStakeholderProfile(StakeholderProfile stakeholderProfile) { this.stakeholderProfile = stakeholderProfile; }

    public StakeholderClaim stakeholderDID(String stakeholderDID) {
        this.stakeholderDID = stakeholderDID;
        return this;
    }

    public String getStakeholderDID() { return stakeholderDID; }

    public void setStakeholderDID(String stakeholderDID) { this.stakeholderDID = stakeholderDID; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        StakeholderClaim stakeholderClaim = (StakeholderClaim) o;
        return Objects.equals(this.governanceBoardDID, stakeholderClaim.governanceBoardDID) &&
                Objects.equals(this.stakeholderRoles, stakeholderClaim.stakeholderRoles) &&
                Objects.equals(this.stakeholderProfile, stakeholderClaim.stakeholderProfile) &&
                Objects.equals(this.stakeholderDID, stakeholderClaim.stakeholderDID);
    }

    @Override
    public int hashCode() { return Objects.hash(governanceBoardDID, stakeholderRoles, stakeholderProfile, stakeholderDID); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class StakeholderClaim {\n");
        sb.append("    governanceBoardDID: ").append(toIndentedString(governanceBoardDID)).append("\n");
        sb.append("    stakeholderRoles: ").append(toIndentedString(stakeholderRoles)).append("\n");
        sb.append("    stakeholderProfile: ").append(toIndentedString(stakeholderProfile)).append("\n");
        sb.append("    stakeholderDID: ").append(toIndentedString(stakeholderDID)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
