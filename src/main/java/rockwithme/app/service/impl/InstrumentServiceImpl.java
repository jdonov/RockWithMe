package rockwithme.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rockwithme.app.model.entity.Instrument;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.repository.InstrumentRepository;
import rockwithme.app.service.InstrumentService;

@Service
public class InstrumentServiceImpl implements InstrumentService {
    private final InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Override
    public void saveInstrument(InstrumentEnum instrumentEnum) {

        this.instrumentRepository.saveAndFlush(new Instrument(instrumentEnum));
    }

    @Override
    public Instrument getInstrument(InstrumentEnum instrumentEnum) {
        return this.instrumentRepository.findInstrumentByInstrument(instrumentEnum).orElse(null);
    }

    @Override
    public Instrument getInstrumentById(String id) {
        return this.instrumentRepository.findById(id).orElse(null);
    }
}
