package rockwithme.app.service;

import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
import rockwithme.app.model.service.EventServiceDTO;

import java.util.List;

public interface EventService {
    void createEvent(EventCreateBindingDTO eventCreateBindingDTO);

    EventServiceDTO getEventById(String eventId);

    EventUpdateBindingDTO getEventToUpdateById(String eventId);

    void updateEvent(String eventId, EventUpdateBindingDTO eventUpdateBindingDTO);

    EventServiceDTO cancelEvent(String eventId);

    List<EventServiceDTO> getEventsByBandId(String bandId, boolean upcoming);
}
