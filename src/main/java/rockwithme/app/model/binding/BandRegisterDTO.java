package rockwithme.app.model.binding;

import rockwithme.app.model.entity.*;

import java.util.List;
import java.util.Set;

public class BandRegisterDTO {
    private String name;
    private String founder;
    private InstrumentEnum founderInstrument;
    private List<InstrumentEnum> instruments;
    private Set<Style> styles;
    private boolean hasStudio;
    private boolean needsProducer;
    private Set<Goal> goals;
    private Town town;
    private String description;

    public BandRegisterDTO() {
    }

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

    public InstrumentEnum getFounderInstrument() {
        return founderInstrument;
    }

    public void setFounderInstrument(InstrumentEnum founderInstrument) {
        this.founderInstrument = founderInstrument;
    }

    public List<InstrumentEnum> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<InstrumentEnum> instruments) {
        this.instruments = instruments;
    }

    public Set<Style> getStyles() {
        return styles;
    }

    public void setStyles(Set<Style> styles) {
        this.styles = styles;
    }

    public boolean isHasStudio() {
        return hasStudio;
    }

    public void setHasStudio(boolean hasStudio) {
        this.hasStudio = hasStudio;
    }

    public boolean isNeedsProducer() {
        return needsProducer;
    }

    public void setNeedsProducer(boolean needsProducer) {
        this.needsProducer = needsProducer;
    }

    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
