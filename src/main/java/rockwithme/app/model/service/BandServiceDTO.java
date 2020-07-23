package rockwithme.app.model.service;

import rockwithme.app.model.entity.*;
import java.util.Set;

public class BandServiceDTO extends BaseServiceModel {
    private String name;
    private Set<Style> styles;
    private Set<Goal> goals;
    private Town town;
    private boolean needMembers;

    public BandServiceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Style> getStyles() {
        return styles;
    }

    public void setStyles(Set<Style> styles) {
        this.styles = styles;
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

    public boolean isNeedMembers() {
        return needMembers;
    }

    public void setNeedMembers(boolean needMembers) {
        this.needMembers = needMembers;
    }
}
