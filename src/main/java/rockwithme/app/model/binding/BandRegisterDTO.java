package rockwithme.app.model.binding;

import org.hibernate.validator.constraints.Length;
import rockwithme.app.constraint.EnumValue;
import rockwithme.app.model.entity.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class BandRegisterDTO {
    private String name;
    private String founder;
    private String founderInstrument;
    private List<InstrumentEnum> instruments;
    private Set<Style> styles;
    private boolean hasStudio;
    private boolean needsProducer;
    private Set<Goal> goals;
    private String town;
    private String description;

    public BandRegisterDTO() {
    }

    public BandRegisterDTO(String name, String founder, String founderInstrument, List<InstrumentEnum> instruments, Set<Style> styles, boolean hasStudio, boolean needsProducer, Set<Goal> goals, String town, String description) {
        this.name = name;
        this.founder = founder;
        this.founderInstrument = founderInstrument;
        this.instruments = instruments;
        this.styles = styles;
        this.hasStudio = hasStudio;
        this.needsProducer = needsProducer;
        this.goals = goals;
        this.town = town;
        this.description = description;
    }

    @NotBlank(message = "Band name can not be blank!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    @EnumValue(enumClass = InstrumentEnum.class, message = "Select your instrument!")
    public String getFounderInstrument() {
        return founderInstrument;
    }

    public void setFounderInstrument(String founderInstrument) {
        this.founderInstrument = founderInstrument;
    }

    @NotNull(message = "select at least 1 instrument!")
    public List<InstrumentEnum> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<InstrumentEnum> instruments) {
        this.instruments = instruments;
    }

    @NotNull(message = "Select at least 1 style!")
    public Set<Style> getStyles() {
        return styles;
    }

    public void setStyles(Set<Style> styles) {
        this.styles = styles;
    }

    @NotNull(message = "Mark if the band has own studio!")
    public boolean isHasStudio() {
        return hasStudio;
    }

    public void setHasStudio(boolean hasStudio) {
        this.hasStudio = hasStudio;
    }

    @NotNull(message = "Mark if the band needs producer!")
    public boolean isNeedsProducer() {
        return needsProducer;
    }

    public void setNeedsProducer(boolean needsProducer) {
        this.needsProducer = needsProducer;
    }

    @NotNull(message = "Select at least 1 goal!")
    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    @EnumValue(enumClass = Town.class, message = "Select valid town!")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Length(max = 255, message = "Description should be max 255 char long!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
