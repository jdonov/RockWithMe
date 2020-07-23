package rockwithme.app.model.service;

import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Level;

public class PlayerSkillsServiceDTO {
    private InstrumentEnum instrument;
    private Level level;
    private int yearsPlaying;
    private int bandPlayed;

    public PlayerSkillsServiceDTO() {
    }

    public PlayerSkillsServiceDTO(InstrumentEnum instrument, Level level, int yearsPlaying, int bandPlayed) {
        this.instrument = instrument;
        this.level = level;
        this.yearsPlaying = yearsPlaying;
        this.bandPlayed = bandPlayed;
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

    public int getYearsPlaying() {
        return yearsPlaying;
    }

    public void setYearsPlaying(int yearsPlaying) {
        this.yearsPlaying = yearsPlaying;
    }

    public int getBandPlayed() {
        return bandPlayed;
    }

    public void setBandPlayed(int bandPlayed) {
        this.bandPlayed = bandPlayed;
    }
}
