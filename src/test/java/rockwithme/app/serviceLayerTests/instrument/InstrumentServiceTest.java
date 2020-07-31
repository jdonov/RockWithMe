package rockwithme.app.serviceLayerTests.instrument;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rockwithme.app.model.entity.Instrument;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.repository.InstrumentRepository;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.impl.InstrumentServiceImpl;

public class InstrumentServiceTest {
    private Instrument testInstrument;
    private InstrumentService instrumentService;
    private InstrumentRepository mockedInstrumentRepository;

    @Before
    public void init() {
        this.mockedInstrumentRepository = Mockito.mock(InstrumentRepository.class);
        this.instrumentService = new InstrumentServiceImpl(this.mockedInstrumentRepository);
        this.testInstrument = new Instrument(InstrumentEnum.GUITAR);
    }

    @Test
    public void getInstrument_ShouldReturnCorrect() {
        Mockito.when(this.mockedInstrumentRepository.findInstrumentByInstrument(InstrumentEnum.GUITAR))
                .thenReturn(java.util.Optional.ofNullable(this.testInstrument));
        Instrument actual = this.instrumentService.getInstrument(InstrumentEnum.GUITAR);
        Assert.assertEquals(InstrumentEnum.GUITAR, actual.getInstrument());
    }

    @Test
    public void getInstrumentById_ShouldReturnCorrectId() {
        Mockito.when(this.mockedInstrumentRepository.findById("TEST"))
                .thenReturn(java.util.Optional.ofNullable(testInstrument));
        Instrument actual = this.instrumentService.getInstrumentById("TEST");
        Assert.assertEquals(InstrumentEnum.GUITAR, actual.getInstrument());
    }
}
