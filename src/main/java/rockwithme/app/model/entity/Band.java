package rockwithme.app.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bands")
public class Band extends BaseEntity {
    private String name;
    private Set<PlayerSkills> members;
    private List<Instrument> instruments;
    private Set<Style> styles;
    private boolean hasStudio;
    private boolean needsProducer;
    private User producer;
    private Set<Goal> goals;
    private Town town;
    private String description;
    private Set<JoinRequest> requests;
    private Set<Event> events;
    private boolean needMembers;
    private Set<Like> likes;
    private String imgUrl;
    private boolean deleted;
    private LocalDateTime registrationDate;

    public Band() {
        this.members = new HashSet<>();
        this.instruments = new ArrayList<>();
        this.styles = new HashSet<>();
        this.goals = new HashSet<>();
        this.requests = new HashSet<>();
        this.events = new HashSet<>();
    }

    @Column(name = "name", nullable = false, unique = true)
    @Length(min = 1, message = "Band name must be at least 1 character long!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<PlayerSkills> getMembers() {
        return members;
    }

    public void setMembers(Set<PlayerSkills> members) {
        this.members = members;
    }

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bands_instruments",
            joinColumns = @JoinColumn(name = "band_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id", referencedColumnName = "id"))
    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    public Set<Style> getStyles() {
        return styles;
    }

    public void setStyles(Set<Style> styles) {
        this.styles = styles;
    }

    @Column(name = "has_own_studio")
    public boolean isHasStudio() {
        return hasStudio;
    }

    public void setHasStudio(boolean hasStudio) {
        this.hasStudio = hasStudio;
    }

    @Column(name = "needs_producer")
    public boolean isNeedsProducer() {
        return needsProducer;
    }

    public void setNeedsProducer(boolean needsProducer) {
        this.needsProducer = needsProducer;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id", referencedColumnName = "id")
    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
        this.producer = producer;
    }

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    @Column(name = "town", nullable = false)
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<JoinRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<JoinRequest> requests) {
        this.requests = requests;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Column(name = "needs_members")
    public boolean isNeedMembers() {
        return needMembers;
    }

    public void setNeedMembers(boolean needMembers) {
        this.needMembers = needMembers;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @Column(name = "pic")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "registration_date")
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
