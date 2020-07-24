package rockwithme.app.service;

import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
import rockwithme.app.model.service.EventServiceDTO;

public interface EventService {
    void createEvent(EventCreateBindingDTO eventCreateBindingDTO);

    EventServiceDTO getEventById(String eventId);

    EventUpdateBindingDTO getEventToUpdateById(String eventId);

    void updateEvent(String eventId, EventUpdateBindingDTO eventUpdateBindingDTO);

    void cancelEvent(String eventId);
}
