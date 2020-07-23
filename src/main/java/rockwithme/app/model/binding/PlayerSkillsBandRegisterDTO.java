package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;

public class PlayerSkillsBandRegisterDTO {
    private InstrumentEnum instrument;

    public PlayerSkillsBandRegisterDTO() {
    }

    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }
}
