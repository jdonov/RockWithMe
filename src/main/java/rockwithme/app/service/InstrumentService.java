package rockwithme.app.service;


import rockwithme.app.model.entity.Instrument;
import rockwithme.app.model.entity.InstrumentEnum;

public interface InstrumentService {
    void saveInstrument(InstrumentEnum instrumentEnum);

    Instrument getInstrument(InstrumentEnum instrumentEnum);
    Instrument getInstrumentById(String id);
}
