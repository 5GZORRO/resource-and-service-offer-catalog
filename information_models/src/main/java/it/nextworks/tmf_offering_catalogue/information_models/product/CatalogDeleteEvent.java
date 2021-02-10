package it.nextworks.tmf_offering_catalogue.information_models.product;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * The notification data structure
 */
@ApiModel(description = "The notification data structure")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-02-09T15:56:41.618Z")




public class CatalogDeleteEvent   {
  @JsonProperty("correlationId")
  private String correlationId = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("domain")
  private String domain = null;

  @JsonProperty("event")
  private CatalogDeleteEventPayload event = null;

  @JsonProperty("eventId")
  private String eventId = null;

  @JsonProperty("eventTime")
  private OffsetDateTime eventTime = null;

  @JsonProperty("eventType")
  private String eventType = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("priority")
  private String priority = null;

  @JsonProperty("timeOcurred")
  private OffsetDateTime timeOcurred = null;

  @JsonProperty("title")
  private String title = null;

  public CatalogDeleteEvent correlationId(String correlationId) {
    this.correlationId = correlationId;
    return this;
  }

  /**
   * The correlation id for this event.
   * @return correlationId
  **/
  @ApiModelProperty(value = "The correlation id for this event.")


  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public CatalogDeleteEvent description(String description) {
    this.description = description;
    return this;
  }

  /**
   * An explnatory of the event.
   * @return description
  **/
  @ApiModelProperty(value = "An explnatory of the event.")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CatalogDeleteEvent domain(String domain) {
    this.domain = domain;
    return this;
  }

  /**
   * The domain of the event.
   * @return domain
  **/
  @ApiModelProperty(value = "The domain of the event.")


  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public CatalogDeleteEvent event(CatalogDeleteEventPayload event) {
    this.event = event;
    return this;
  }

  /**
   * The event payload linked to the involved resource object
   * @return event
  **/
  @ApiModelProperty(value = "The event payload linked to the involved resource object")

  @Valid

  public CatalogDeleteEventPayload getEvent() {
    return event;
  }

  public void setEvent(CatalogDeleteEventPayload event) {
    this.event = event;
  }

  public CatalogDeleteEvent eventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * The identifier of the notification.
   * @return eventId
  **/
  @ApiModelProperty(value = "The identifier of the notification.")


  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public CatalogDeleteEvent eventTime(OffsetDateTime eventTime) {
    this.eventTime = eventTime;
    return this;
  }

  /**
   * Time of the event occurrence.
   * @return eventTime
  **/
  @ApiModelProperty(value = "Time of the event occurrence.")

  @Valid

  public OffsetDateTime getEventTime() {
    return eventTime;
  }

  public void setEventTime(OffsetDateTime eventTime) {
    this.eventTime = eventTime;
  }

  public CatalogDeleteEvent eventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  /**
   * The type of the notification.
   * @return eventType
  **/
  @ApiModelProperty(value = "The type of the notification.")


  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public CatalogDeleteEvent href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Reference of the ProcessFlow
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the ProcessFlow")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public CatalogDeleteEvent id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of the Process flow
   * @return id
  **/
  @ApiModelProperty(value = "Identifier of the Process flow")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CatalogDeleteEvent priority(String priority) {
    this.priority = priority;
    return this;
  }

  /**
   * A priority.
   * @return priority
  **/
  @ApiModelProperty(value = "A priority.")


  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public CatalogDeleteEvent timeOcurred(OffsetDateTime timeOcurred) {
    this.timeOcurred = timeOcurred;
    return this;
  }

  /**
   * The time the event occured.
   * @return timeOcurred
  **/
  @ApiModelProperty(value = "The time the event occured.")

  @Valid

  public OffsetDateTime getTimeOcurred() {
    return timeOcurred;
  }

  public void setTimeOcurred(OffsetDateTime timeOcurred) {
    this.timeOcurred = timeOcurred;
  }

  public CatalogDeleteEvent title(String title) {
    this.title = title;
    return this;
  }

  /**
   * The title of the event.
   * @return title
  **/
  @ApiModelProperty(value = "The title of the event.")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatalogDeleteEvent catalogDeleteEvent = (CatalogDeleteEvent) o;
    return Objects.equals(this.correlationId, catalogDeleteEvent.correlationId) &&
        Objects.equals(this.description, catalogDeleteEvent.description) &&
        Objects.equals(this.domain, catalogDeleteEvent.domain) &&
        Objects.equals(this.event, catalogDeleteEvent.event) &&
        Objects.equals(this.eventId, catalogDeleteEvent.eventId) &&
        Objects.equals(this.eventTime, catalogDeleteEvent.eventTime) &&
        Objects.equals(this.eventType, catalogDeleteEvent.eventType) &&
        Objects.equals(this.href, catalogDeleteEvent.href) &&
        Objects.equals(this.id, catalogDeleteEvent.id) &&
        Objects.equals(this.priority, catalogDeleteEvent.priority) &&
        Objects.equals(this.timeOcurred, catalogDeleteEvent.timeOcurred) &&
        Objects.equals(this.title, catalogDeleteEvent.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correlationId, description, domain, event, eventId, eventTime, eventType, href, id, priority, timeOcurred, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CatalogDeleteEvent {\n");
    
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    event: ").append(toIndentedString(event)).append("\n");
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    eventTime: ").append(toIndentedString(eventTime)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    timeOcurred: ").append(toIndentedString(timeOcurred)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

