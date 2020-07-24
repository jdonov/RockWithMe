package rockwithme.app.model.service;

import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.EventCategory;
import rockwithme.app.model.entity.EventType;


import java.time.LocalDateTime;

public class EventServiceDTO extends BaseServiceModel{
    private EventType eventType;
    private EventCategory eventCategory;
    private LocalDateTime eventDate;
    private String description;

    public EventServiceDTO() {
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
