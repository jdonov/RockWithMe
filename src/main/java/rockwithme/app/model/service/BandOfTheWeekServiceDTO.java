package rockwithme.app.model.service;

import java.util.Set;

public class BandOfTheWeekServiceDTO extends BaseServiceModel{
    private String id;
    private String name;
    private int likes;
    private Set<EventServiceDTO> events;

    public BandOfTheWeekServiceDTO() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<EventServiceDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<EventServiceDTO> events) {
        this.events = events;
    }
}
