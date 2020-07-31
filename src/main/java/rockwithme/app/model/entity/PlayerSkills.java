package rockwithme.app.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "player_skills")
public class PlayerSkills {
    private PlayerSkillsPK id;
    private User player;
    private Instrument instrument;
    private Level level;
    private int yearsPlaying;
    private int bandPlayed;

    public PlayerSkills() {
    }

    @EmbeddedId
    public PlayerSkillsPK getId() {
        return id;
    }

    public void setId(PlayerSkillsPK id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("playerId")
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("instrumentId")
    @JoinColumn(name = "instrument_id", referencedColumnName = "id")
    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Column(name = "level", nullable = false)
    @Enumerated
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Column(name = "years_playing", nullable = false)
    @Min(0)
    public int getYearsPlaying() {
        return yearsPlaying;
    }

    public void setYearsPlaying(int yearsPlaying) {
        this.yearsPlaying = yearsPlaying;
    }

    @Column(name = "bands_played", nullable = false)
    @Min(0)
    public int getBandPlayed() {
        return bandPlayed;
    }

    public void setBandPlayed(int bandPlayed) {
        this.bandPlayed = bandPlayed;
    }


}
