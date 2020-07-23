package rockwithme.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "instruments")
public class Instrument extends BaseEntity{
    private InstrumentEnum instrument;

    public Instrument() {
    }

    public Instrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }

    @Column(name = "instrument", nullable = false, unique = true)
    @Enumerated
    public InstrumentEnum getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentEnum instrument) {
        this.instrument = instrument;
    }
}
