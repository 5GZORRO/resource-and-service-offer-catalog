package it.nextworks.tmf_offering_catalog.information_models.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class NotificationMethod {

    @JsonProperty("notificationType")
    private NotificationType notificationType = null;

    @JsonProperty("distributionList")
    private String distributionList = null;

    public NotificationMethod notificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public NotificationType getNotificationType() { return notificationType; }

    public void setNotificationType(NotificationType notificationType) { this.notificationType = notificationType; }

    public NotificationMethod distributionList(String distributionList) {
        this.distributionList = distributionList;
        return this;
    }

    public String getDistributionList() { return distributionList; }

    public void setDistributionList(String distributionList) { this.distributionList = distributionList; }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        NotificationMethod notificationMethod = (NotificationMethod) o;
        return Objects.equals(this.notificationType, notificationMethod.notificationType) &&
                Objects.equals(this.distributionList, notificationMethod.distributionList);
    }

    @Override
    public int hashCode() { return Objects.hash(notificationType, distributionList); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class NotificationMethod {\n");
        sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
        sb.append("    distributionList: ").append(toIndentedString(distributionList)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if(o == null)
            return "null";

        return o.toString().replace("\n", "\n    ");
    }
}
