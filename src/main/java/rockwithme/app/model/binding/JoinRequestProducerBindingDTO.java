package rockwithme.app.model.binding;

import rockwithme.app.model.entity.InstrumentEnum;

public class JoinRequestProducerBindingDTO {
    private String bandId;
    private String username;
    private String description;
    private boolean becomeProducer;

    public JoinRequestProducerBindingDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBecomeProducer() {
        return becomeProducer;
    }

    public void setBecomeProducer(boolean becomeProducer) {
        this.becomeProducer = becomeProducer;
    }
}
