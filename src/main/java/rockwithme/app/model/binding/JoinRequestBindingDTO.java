package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;

public class JoinRequestBindingDTO {
    private String bandId;
    private String username;
    private InstrumentEnum instrument;
    private String description;
    private boolean isApproved;

    public JoinRequestBindingDTO() {
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
