package rockwithme.app.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "join_requests")
public class JoinRequest extends BaseEntity{
    private Band band;
    private User user;
    private InstrumentEnum instrument;
    private String description;
    private boolean isApproved;
    private boolean isClosed;

    public JoinRequest() {
    }

    @ManyToOne
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "instrument", nullable = false)
    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "is_approved")
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Column(name = "is_closed")
    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
