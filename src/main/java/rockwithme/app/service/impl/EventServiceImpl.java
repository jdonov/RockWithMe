package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Event;
import rockwithme.app.model.service.EventServiceDTO;
import rockwithme.app.repository.EventRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.EventService;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final BandService bandService;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, BandService bandService, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.bandService = bandService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void createEvent(EventCreateBindingDTO eventCreateBindingDTO) {
        Event event = this.modelMapper.map(eventCreateBindingDTO, Event.class);
        Band band = this.bandService.getBandById(eventCreateBindingDTO.getBandId());
        event.setBand(band);
        event = this.eventRepository.saveAndFlush(event);
        this.bandService.addEvent(event, eventCreateBindingDTO.getBandId());
    }

    @Override
    public EventServiceDTO getEventById(String eventId) {
        return this.modelMapper.map(this.eventRepository.findById(eventId).orElse(null), EventServiceDTO.class);
        //TODO throw exception and handle it if no such event!!!
    }

    @Override
    public EventUpdateBindingDTO getEventToUpdateById(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElse(null);
        return this.modelMapper.map(event, EventUpdateBindingDTO.class);
        //TODO throw exception and handle it if no such event!!!
    }

    @Override
    public void updateEvent(String eventId, EventUpdateBindingDTO eventUpdateBindingDTO) {
        Event event = this.eventRepository.findById(eventId).orElse(null);
        event.setDescription(eventUpdateBindingDTO.getDescription());
        event.setEventType(eventUpdateBindingDTO.getEventType());
        event.setEventDate(eventUpdateBindingDTO.getEventDate());
        this.eventRepository.saveAndFlush(event);
    }

    @Override
    public void cancelEvent(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElse(null);
        event.setCanceled(true);
        this.eventRepository.saveAndFlush(event);
    }
}
