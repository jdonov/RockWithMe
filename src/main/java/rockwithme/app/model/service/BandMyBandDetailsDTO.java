package rockwithme.app.model.service;

import rockwithme.app.model.entity.Goal;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Style;
import rockwithme.app.model.entity.Town;

import java.util.List;
import java.util.Set;

public class BandMyBandDetailsDTO {
    private String name;
    private List<PlayerSkillsBandMemberDTO> members;
    private List<InstrumentEnum> instruments;
    private List<InstrumentEnum> instrumentsNeeded;
    private Set<Style> styles;
    private boolean hasStudio;
    private boolean needsProducer;
    private String producer;
    private Set<Goal> goals;
    private Town town;
    private String description;
    private boolean needMembers;
    private Set<JoinRequestServiceDTO> requests;

    public BandMyBandDetailsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerSkillsBandMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<PlayerSkillsBandMemberDTO> members) {
        this.members = members;
    }

    public List<InstrumentEnum> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<InstrumentEnum> instruments) {
        this.instruments = instruments;
    }

    public List<InstrumentEnum> getInstrumentsNeeded() {
        return instrumentsNeeded;
    }

    public void setInstrumentsNeeded(List<InstrumentEnum> instrumentsNeeded) {
        this.instrumentsNeeded = instrumentsNeeded;
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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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

    public boolean isNeedMembers() {
        return needMembers;
    }

    public void setNeedMembers(boolean needMembers) {
        this.needMembers = needMembers;
    }

    public Set<JoinRequestServiceDTO> getRequests() {
        return requests;
    }

    public void setRequests(Set<JoinRequestServiceDTO> requests) {
        this.requests = requests;
    }
}
