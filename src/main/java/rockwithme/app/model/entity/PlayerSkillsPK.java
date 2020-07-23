package rockwithme.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerSkillsPK implements Serializable {
    private String playerId;
    private String instrumentId;

    public PlayerSkillsPK() {
    }

    public PlayerSkillsPK(String playerId, String instrumentId) {
        this.playerId = playerId;
        this.instrumentId = instrumentId;
    }

    @Column(name = "player_id")
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Column(name = "instrument_id")
    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerSkillsPK)) return false;
        PlayerSkillsPK that = (PlayerSkillsPK) o;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(instrumentId, that.instrumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, instrumentId);
    }
}
