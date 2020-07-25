package rockwithme.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity{
    private User user;
    private Band band;

    public Like() {
    }

    public Like(User user, Band band) {
        this.user = user;
        this.band = band;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }
}
