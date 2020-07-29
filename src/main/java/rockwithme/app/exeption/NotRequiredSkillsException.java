package rockwithme.app.exeption;

import rockwithme.app.model.entity.InstrumentEnum;

import java.util.ArrayList;
import java.util.List;

public class NotRequiredSkillsException extends RuntimeException{
    private String bandId;
    private List<InstrumentEnum> instrumentEnums;
    public NotRequiredSkillsException() {
        this.instrumentEnums = new ArrayList<>();
    }

    public NotRequiredSkillsException(String message) {
        super(message);
        this.instrumentEnums = new ArrayList<>();
    }


    public List<InstrumentEnum> getInstrumentEnums() {
        return instrumentEnums;
    }

    public void setInstrumentEnums(List<InstrumentEnum> instrumentEnums) {
        this.instrumentEnums = instrumentEnums;
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }
}
