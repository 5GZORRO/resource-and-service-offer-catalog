package it.nextworks.tmf_offering_catalog.information_models.party;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "stakeholder_platform_info")
public class StakeholderPlatformInfo {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("stakeholderDID")
    private String stakeholderDID;

    @JsonProperty("token")
    private String token;

    public StakeholderPlatformInfo() {}

    public StakeholderPlatformInfo(String id, String stakeholderDID, String token) {
        this.id             = id;
        this.stakeholderDID = stakeholderDID;
        this.token          = token;
    }

    public String getId() { return id; }

    public String getStakeholderDID() { return stakeholderDID; }

    public String getToken() { return token; }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        StakeholderPlatformInfo stakeholderPlatformInfo = (StakeholderPlatformInfo) o;
        return Objects.equals(this.id, stakeholderPlatformInfo.id) &&
                Objects.equals(this.stakeholderDID, stakeholderPlatformInfo.stakeholderDID) &&
                Objects.equals(this.token, stakeholderPlatformInfo.token);
    }

    @Override
    public int hashCode() { return Objects.hash(id, stakeholderDID, token); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class StakeholderPlatformInfo {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    stakeholderDID: ").append(toIndentedString(stakeholderDID)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
