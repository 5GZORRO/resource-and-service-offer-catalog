package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class StakeholderProfile {

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("ledgerIdentity")
    private String ledgerIdentity = null;

    @JsonProperty("address")
    private String address = null;

    @JsonProperty("notificationMethod")
    private NotificationMethod notificationMethod = null;

    public StakeholderProfile name(String name) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public StakeholderProfile ledgerIdentity(String ledgerIdentity) {
        this.ledgerIdentity = ledgerIdentity;
        return this;
    }

    public String getLedgerIdentity() { return ledgerIdentity; }

    public void setLedgerIdentity(String ledgerIdentity) { this.ledgerIdentity = ledgerIdentity; }

    public StakeholderProfile address(String address) {
        this.address = address;
        return this;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public StakeholderProfile notificationMethod(NotificationMethod notificationMethod) {
        this.notificationMethod = notificationMethod;
        return this;
    }

    public NotificationMethod getNotificationMethod() { return notificationMethod; }

    public void setNotificationMethod(NotificationMethod notificationMethod) { this.notificationMethod = notificationMethod; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        StakeholderProfile stakeholderProfile = (StakeholderProfile) o;
        return Objects.equals(this.name, stakeholderProfile.name) &&
                Objects.equals(this.ledgerIdentity, stakeholderProfile.ledgerIdentity) &&
                Objects.equals(this.address, stakeholderProfile.address) &&
                Objects.equals(this.notificationMethod, stakeholderProfile.notificationMethod);
    }

    @Override
    public int hashCode() { return Objects.hash(name, ledgerIdentity, address, notificationMethod); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class StakeholderProfile {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    ledgerIdentity: ").append(toIndentedString(ledgerIdentity)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    notificationMethod: ").append(toIndentedString(notificationMethod)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
