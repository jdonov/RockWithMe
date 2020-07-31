package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Level;

public class PlayerSkillsSearchBindingDTO {
    private String userId;
    private InstrumentEnum instrument;
    private Level level;

    public PlayerSkillsSearchBindingDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
