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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<EventServiceDTO> getEventsByBandId(String bandId, boolean upcoming) {
        if (upcoming) {
            return this.eventRepository.findByBand_IdAndEventDateIsAfter(bandId, LocalDateTime.now())
                    .stream()
                    .map(event -> this.modelMapper.map(event, EventServiceDTO.class))
                    .collect(Collectors.toList());
        } else {
            return this.eventRepository.findByBand_IdAndEventDateIsBefore(bandId, LocalDateTime.now())
                    .stream()
                    .map(event -> this.modelMapper.map(event, EventServiceDTO.class))
                    .collect(Collectors.toList());
        }
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
    }

    @Override
    public EventUpdateBindingDTO getEventToUpdateById(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElse(null);
        return this.modelMapper.map(event, EventUpdateBindingDTO.class);
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
    public EventServiceDTO cancelEvent(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElse(null);
        event.setCanceled(true);
        EventServiceDTO eventServiceDTO = this.modelMapper.map(this.eventRepository.saveAndFlush(event), EventServiceDTO.class);
        return eventServiceDTO;
    }
}
