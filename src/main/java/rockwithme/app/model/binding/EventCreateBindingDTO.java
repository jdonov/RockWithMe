package rockwithme.app.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.EventCategory;
import rockwithme.app.model.entity.EventType;

import java.time.LocalDateTime;

public class EventCreateBindingDTO {
    private EventType eventType;
    private EventCategory eventCategory;
    private LocalDateTime eventDate;
    private String description;
    private String bandId;

    public EventCreateBindingDTO() {
    }

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

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }
}
