package rockwithme.app.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import rockwithme.app.model.entity.EventCategory;
import rockwithme.app.model.entity.EventType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventUpdateBindingDTO {
    private String id;
    private String bandId;
    private EventType eventType;
    private EventCategory eventCategory;
    private LocalDateTime eventDate;
    private String description;

    public EventUpdateBindingDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    @NotNull(message = "Event type can not be null!")
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "Event date can not be in the past!")
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
