package rockwithme.app.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Event;
import rockwithme.app.model.entity.EventCategory;
import rockwithme.app.model.entity.EventType;
import rockwithme.app.model.service.EventServiceDTO;
import rockwithme.app.repository.EventRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.EventService;
import rockwithme.app.service.impl.EventServiceImpl;

public class EventServiceTest {
    private Event testEvent;
    private Band testBand;
    private EventService eventService;
    private EventRepository mockedEventRepository;
    private BandService mockedBandService;
    private ModelMapper mockedModelMapper;

    @Before
    public void init() {
        this.mockedEventRepository = Mockito.mock(EventRepository.class);
        this.mockedBandService = Mockito.mock(BandService.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.eventService = new EventServiceImpl(this.mockedEventRepository, this.mockedBandService, this.mockedModelMapper);
        this.testBand = new Band(){{
            setName("Metallica");
        }};
        this.testEvent = new Event(){{
           setBand(testBand);
           setEventType(EventType.PUBLIC);
           setEventCategory(EventCategory.LIVE);
        }};
    }

    @Test
    public void getEventById_ShouldReturnCorrect() {
        Mockito.when(this.mockedEventRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(testEvent));
        Mockito.when(this.mockedModelMapper.map(testEvent, EventServiceDTO.class))
                .thenReturn(new EventServiceDTO(){{
                    setEventType(EventType.PUBLIC);
                    setEventCategory(EventCategory.LIVE);
                }});
        EventServiceDTO actual = this.eventService.getEventById("TEST_ID");
        Assert.assertEquals(EventType.PUBLIC, actual.getEventType());
        Assert.assertEquals(EventCategory.LIVE, actual.getEventCategory());
    }

    @Test
    public void getEventToUpdateById_ShouldReturnCorrect() {
        Mockito.when(this.mockedEventRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(testEvent));
        Mockito.when(this.mockedModelMapper.map(testEvent, EventUpdateBindingDTO.class))
                .thenReturn(new EventUpdateBindingDTO(){{
                    setEventType(EventType.PUBLIC);
                    setEventCategory(EventCategory.LIVE);
                }});
        EventUpdateBindingDTO actual = this.eventService.getEventToUpdateById("TEST_ID");
        Assert.assertEquals(EventType.PUBLIC, actual.getEventType());
        Assert.assertEquals(EventCategory.LIVE, actual.getEventCategory());
    }

    @Test
    public void updateEvent_ShouldReturnCorrect() {
        Mockito.when(this.mockedEventRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(testEvent));
        Mockito.when(this.mockedEventRepository.saveAndFlush(testEvent))
                .thenReturn(testEvent);
        this.eventService.updateEvent("TEST_ID", new EventUpdateBindingDTO(){{
            setEventType(EventType.MEMBERS_ONLY);
        }});
        Assert.assertEquals(EventType.MEMBERS_ONLY, testEvent.getEventType());
    }

    @Test
    public void cancelEvent_ShouldReturnCorrect() {
        Mockito.when(this.mockedEventRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(testEvent));
        Mockito.when(this.mockedEventRepository.saveAndFlush(testEvent))
                .thenReturn(testEvent);
        this.eventService.cancelEvent("TEST_ID");
        Assert.assertTrue(testEvent.isCanceled());
    }

}
