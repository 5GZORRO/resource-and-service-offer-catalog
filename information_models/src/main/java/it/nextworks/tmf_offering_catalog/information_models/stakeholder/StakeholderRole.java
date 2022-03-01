package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StakeholderRole {

    @JsonProperty("role")
    private Role role = null;

    @JsonProperty("assets")
    private List<Asset> assets = null;

    public StakeholderRole role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) { this.role = role; }

    public Role getRole() { return role; }

    public StakeholderRole assets(List<Asset> assets) {
        this.assets = assets;
        return this;
    }

    public StakeholderRole addAsset(Asset asset) {
        if(this.assets == null)
            this.assets = new ArrayList<>();

        this.assets.add(asset);
        return this;
    }

    public void setAssets(List<Asset> assets) { this.assets = assets; }

    public List<Asset> getAssets() { return assets; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        StakeholderRole stakeholderRole = (StakeholderRole) o;
        return Objects.equals(this.role, stakeholderRole.role) &&
                Objects.equals(this.assets, stakeholderRole.assets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, assets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class StakeholderRole {\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    assets: ").append(toIndentedString(assets)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
