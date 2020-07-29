package rockwithme.app.model.binding;

import rockwithme.app.model.entity.Goal;
import rockwithme.app.model.entity.Style;
import rockwithme.app.model.entity.Town;

public class BandSearchBindingDTO {
    private String name;
    private Style style;
    private Goal goal;
    private Town town;
    private Boolean needMembers;
    private Boolean needsProducer;

    public BandSearchBindingDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Boolean getNeedMembers() {
        return needMembers;
    }

    public void setNeedMembers(Boolean needMembers) {
        this.needMembers = needMembers;
    }

    public Boolean getNeedsProducer() {
        return needsProducer;
    }

    public void setNeedsProducer(Boolean needsProducer) {
        this.needsProducer = needsProducer;
    }
}
