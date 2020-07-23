package rockwithme.app.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    //TODO add scheduler to delete old events!
    private EventType eventType;
    private EventCategory eventCategory;
    private LocalDateTime eventDate;
    private String description;
    private Band band;

    public Event() {
    }

    @Column(name = "event_type", nullable = false)
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Column(name = "event_category", nullable = false)
    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    @Column(name = "event_date", nullable = false)
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }
}
