package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Level;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class PlayerSkillsAddDTO {
    private String username;
    private InstrumentEnum instrument;
    private Level level;
    private int yearsPlaying;
    private int bandPlayed;

    public PlayerSkillsAddDTO() {
    }

    public PlayerSkillsAddDTO(InstrumentEnum instrument, Level level, int yearsPlaying, int bandPlayed) {
        this.instrument = instrument;
        this.level = level;
        this.yearsPlaying = yearsPlaying;
        this.bandPlayed = bandPlayed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Instrument can not be empty!")
    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    @NotNull(message = "Level can not be empty!")
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @PositiveOrZero(message = "Years playing must be positive number!")
    public int getYearsPlaying() {
        return yearsPlaying;
    }

    public void setYearsPlaying(int yearsPlaying) {
        this.yearsPlaying = yearsPlaying;
    }

    @PositiveOrZero(message = "Bands played must be positive number!")
    public int getBandPlayed() {
        return bandPlayed;
    }

    public void setBandPlayed(int bandPlayed) {
        this.bandPlayed = bandPlayed;
    }
}
