package rockwithme.app.model.service;

import rockwithme.app.model.entity.InstrumentEnum;

public class PlayerSkillsBandMemberDTO {
    private String username;
    private InstrumentEnum instrumentEnum;

    public PlayerSkillsBandMemberDTO() {
    }

    public PlayerSkillsBandMemberDTO(String username, InstrumentEnum instrumentEnum) {
        this.username = username;
        this.instrumentEnum = instrumentEnum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InstrumentEnum getInstrumentEnum() {
        return instrumentEnum;
    }

    public void setInstrumentEnum(InstrumentEnum instrumentEnum) {
        this.instrumentEnum = instrumentEnum;
    }
}
