package rockwithme.app.model.service;

import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Level;

public class PlayerSkillsSearchDTO {
    private String userId;
    private String username;
    private InstrumentEnum instrument;
    private Level level;

    public PlayerSkillsSearchDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
