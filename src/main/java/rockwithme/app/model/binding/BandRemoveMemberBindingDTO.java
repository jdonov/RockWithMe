package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;

public class BandRemoveMemberBindingDTO {
    private String bandId;
    private String username;
    private InstrumentEnum instrument;

    public BandRemoveMemberBindingDTO() {
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
}
