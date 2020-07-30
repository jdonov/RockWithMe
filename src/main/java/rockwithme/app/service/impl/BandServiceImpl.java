package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.binding.BandRemoveMemberBindingDTO;
import rockwithme.app.model.binding.BandRemoveProducerBindingDTO;
import rockwithme.app.model.binding.BandSearchBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.specification.BandSpecificationBuilder;
import rockwithme.app.service.BandService;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BandServiceImpl implements BandService {
    private final BandRepository bandRepository;
    private final UserService userService;
    private final InstrumentService instrumentService;
    private final PlayerSkillsService playerSkillsService;
    private final ModelMapper modelMapper;

    public BandServiceImpl(UserService userService, InstrumentService instrumentService, BandRepository bandRepository, PlayerSkillsService playerSkillsService, ModelMapper modelMapper) {
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.bandRepository = bandRepository;
        this.playerSkillsService = playerSkillsService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BandServiceDTO> getAllBands() {
        return this.bandRepository.findAllByDeletedIsFalse()
                .stream()
                .map(b -> this.modelMapper.map(b, BandServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BandDetailsDTO getBandDetails(String id) {
        Band band = this.bandRepository.findById(id).orElse(null);
        bandInstruments(band);

        BandDetailsDTO bandDetailsDTO = this.modelMapper.map(band, BandDetailsDTO.class);
        if (!band.getMembers().isEmpty()) {
            bandDetailsDTO.setMembers(band.getMembers().stream().map(p -> new PlayerSkillsBandMemberDTO(p.getPlayer().getUsername(), p.getInstrument().getInstrument()))
                    .collect(Collectors.toList()));
        }
        bandDetailsDTO.setInstruments(band.getInstruments().stream().map(Instrument::getInstrument).collect(Collectors.toList()));
        List<InstrumentEnum> needed = new ArrayList<>(bandDetailsDTO.getInstruments());
        bandDetailsDTO.getMembers().stream().map(PlayerSkillsBandMemberDTO::getInstrumentEnum)
                .forEach(i -> {
                    int index = needed.indexOf(i);
                    if (index >= 0) {
                        needed.remove(index);
                    }
                });
        bandDetailsDTO.setInstrumentsNeeded(needed);
        Set<EventServiceDTO> events = band.getEvents().stream()
                .filter(e -> e.getEventType().equals(EventType.PUBLIC) && e.getEventDate().isAfter(LocalDateTime.now()))
                .map(e -> this.modelMapper.map(e, EventServiceDTO.class))
                .sorted(Comparator.comparing(EventServiceDTO::getEventDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        bandDetailsDTO.setEvents(events);
        if (band.getProducer() != null) {
            bandDetailsDTO.setProducer(band.getProducer().getUsername());
        }
        return bandDetailsDTO;
    }

    @Override
    public BandMyBandDetailsDTO getMyBandDetails(String id) {
        Band band = this.bandRepository.findById(id).orElse(null);

        bandInstruments(band);

        BandMyBandDetailsDTO myBandDetails = this.modelMapper.map(band, BandMyBandDetailsDTO.class);
        if (!band.getMembers().isEmpty()) {
            myBandDetails.setMembers(band.getMembers().stream().map(p -> new PlayerSkillsBandMemberDTO(p.getPlayer().getUsername(), p.getInstrument().getInstrument()))
                    .collect(Collectors.toList()));
        }
        myBandDetails.setInstruments(band.getInstruments().stream().map(Instrument::getInstrument).collect(Collectors.toList()));
        List<InstrumentEnum> needed = new ArrayList<>(myBandDetails.getInstruments());
        myBandDetails.getMembers().stream().map(PlayerSkillsBandMemberDTO::getInstrumentEnum)
                .forEach(i -> {
                    int index = needed.indexOf(i);
                    if (index >= 0) {
                        needed.remove(index);
                    }
                });
        if (band.getRequests() != null && !band.getRequests().isEmpty()) {
            Set<JoinRequestServiceDTO> reqs = band.getRequests().stream().map(request -> {
                JoinRequestServiceDTO joinRequestServiceDTO = this.modelMapper.map(request, JoinRequestServiceDTO.class);
                joinRequestServiceDTO.setUsername(request.getUser().getUsername());
                return joinRequestServiceDTO;
            }).collect(Collectors.toCollection(HashSet::new));
            myBandDetails.setRequests(reqs);
        }
        if (band.getProducer() != null) {
            myBandDetails.setProducer(band.getProducer().getUsername());
        }
        myBandDetails.setInstrumentsNeeded(needed);
        return myBandDetails;
    }

    @Override
    @Transactional
    public Band registerBand(BandRegisterDTO bandRegisterDTO) {
        Band band = this.modelMapper.map(bandRegisterDTO, Band.class);
        User founder = this.userService.getUserByUsername(bandRegisterDTO.getFounder());
        Instrument founderInstrument = this.instrumentService.getInstrument(InstrumentEnum.valueOf(bandRegisterDTO.getFounderInstrument()));
        PlayerSkills playerSkills = this.playerSkillsService.getByPlayerIdAndInstrumentId(founder.getId(), founderInstrument.getId());
        band.setMembers(Set.of(playerSkills));
        band.setInstruments(bandRegisterDTO.getInstruments().stream().map(instrumentService::getInstrument).collect(Collectors.toList()));
        band.setGoals(bandRegisterDTO.getGoals());
        band.setStyles(bandRegisterDTO.getStyles());
        band.setNeedMembers(band.getMembers().size() < band.getInstruments().size());
        band.setRegistrationDate(LocalDateTime.now());
        band = this.bandRepository.saveAndFlush(band);
        this.userService.addBand(founder, band);
        return band;
    }

    @Override
    public Band getBandById(String id) {
        return this.bandRepository.findById(id).orElse(null);
    }

    @Override
    public List<BandMyAllBandsDTO> getBandByMember(String username) {
        User user = this.userService.getUserByUsername(username);
        List<BandMyAllBandsDTO> myAllBandsDTOS = new ArrayList<>();
        if (!user.getBands().isEmpty()) {
            myAllBandsDTOS = user.getBands().stream()
                    .sorted(Comparator.comparing(Band::getRegistrationDate).reversed())
                    .map(b -> {
                        BandMyAllBandsDTO band = this.modelMapper.map(b, BandMyAllBandsDTO.class);
                        band.setNewRequests(b.getRequests().size());
                        return band;
                    })
                    .collect(Collectors.toList());
        }
        return myAllBandsDTOS;
    }

    @Override
    public void addRequest(Band band, JoinRequest request) {
        band.getRequests().add(request);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    public void addMember(Band band, PlayerSkills playerSkills) {
        band.getMembers().add(playerSkills);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    @Transactional
    public void removeMember(BandRemoveMemberBindingDTO bandRemoveMemberBindingDTO) {
        Band band = this.bandRepository.findById(bandRemoveMemberBindingDTO.getBandId()).orElse(null);
        User user = this.userService.getUserByUsername(bandRemoveMemberBindingDTO.getUsername());
        Instrument instrument = this.instrumentService.getInstrument(bandRemoveMemberBindingDTO.getInstrument());
        PlayerSkills playerSkills1 = this.playerSkillsService.getByCompositeId(user.getId(), instrument.getId());
        band.getMembers().remove(playerSkills1);
        this.bandRepository.saveAndFlush(band);
        this.userService.removeBand(user, band);
    }

    @Override
    public void addProducer(User user, Band band) {
        band.setProducer(user);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    @Transactional
    public void removeProducer(BandRemoveProducerBindingDTO bandRemoveProducerBindingDTO) {
        User user = this.userService.getUserByUsername(bandRemoveProducerBindingDTO.getProducerUsername());
        Band band = this.bandRepository.findById(bandRemoveProducerBindingDTO.getBandId()).orElse(null);
        band.setProducer(null);
        this.bandRepository.saveAndFlush(band);
        this.userService.removeBand(user, band);
    }

    @Override
    public void addEvent(Event event, String bandId) {
        Band band = this.bandRepository.findById(bandId).orElse(null);
        band.getEvents().add(event);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    public void addLike(Like like, Band band) {
        band.getLikes().add(like);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    public void addPhoto(String bandId, String imgUrl) {
        Band band = this.bandRepository.findById(bandId).orElse(null);
        band.setImgUrl(imgUrl);
        this.bandRepository.saveAndFlush(band);
    }

    @Override
    public BandOfTheWeekServiceDTO getBandOfTheWeek() {
        Band band = this.bandRepository.findMostLikedBand();
        if (band == null) {
            return null;
        }
        BandOfTheWeekServiceDTO bandOfTheWeekServiceDTO = new BandOfTheWeekServiceDTO();
        bandOfTheWeekServiceDTO.setName(band.getName());
        bandOfTheWeekServiceDTO.setId(band.getId());
        bandOfTheWeekServiceDTO.setLikes(band.getLikes().size());
        bandOfTheWeekServiceDTO.setEvents(band.getEvents().stream()
                .filter(event -> event.getEventType().equals(EventType.PUBLIC))
                .map(e -> this.modelMapper.map(e, EventServiceDTO.class))
                .sorted(Comparator.comparing(EventServiceDTO::getEventDate))
                .limit(3)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        return bandOfTheWeekServiceDTO;
    }

    @Override
    public void deleteBandsWithNoMembers() {
        this.bandRepository.deleteAllWithoutMembers();
    }

    @Override
    public List<Band> findAllToDelete() {
        return this.bandRepository.findAllToDelete();
    }

    private void bandInstruments(Band band) {
        List<String> instr_ids = this.bandRepository.findBandInstruments(band.getId());
        band.setInstruments(new ArrayList<>());
        instr_ids.forEach(i -> band.getInstruments().add(this.instrumentService.getInstrumentById(i)));
    }

    @Override
    public int getCountOfAllActiveBands() {
        return this.bandRepository.findAllActive();
    }

    @Override
    public int getCountOfAllDeletedBands() {
        return this.bandRepository.findAllDeleted();
    }

    @Override
    public BandAdminServiceDTO getLastRegistered() {
        return this.modelMapper.map(this.bandRepository.findLastRegistered(), BandAdminServiceDTO.class);
    }

    @Override
    public Page<BandSearchServiceDTO> searchBands(BandSearchBindingDTO bandSearchBindingDTO, Pageable pageable) {

        BandSpecificationBuilder builder = new BandSpecificationBuilder();
        if (bandSearchBindingDTO.getName() != null && !bandSearchBindingDTO.getName().isEmpty()) {
            builder.with("name", ":", bandSearchBindingDTO.getName());
        }
        if (bandSearchBindingDTO.getStyle() != null) {
            builder.with("styles", ":", bandSearchBindingDTO.getStyle());
        }
        if (bandSearchBindingDTO.getGoal() != null) {
            builder.with("goals", ":", bandSearchBindingDTO.getGoal());
        }
        if (bandSearchBindingDTO.getTown() != null) {
            builder.with("town", ":", bandSearchBindingDTO.getTown());
        }

        if (bandSearchBindingDTO.getNeedMembers() != null) {
            builder.with("needMembers", ":", bandSearchBindingDTO.getNeedMembers());
        }

        if (bandSearchBindingDTO.getNeedsProducer() != null) {
            builder.with("needsProducer", ":", bandSearchBindingDTO.getNeedsProducer());
        }

        Specification<Band> spec = builder.build();
        List<BandSearchServiceDTO> bands = this.bandRepository.findAll(spec).stream()
                .map(band -> this.modelMapper.map(band, BandSearchServiceDTO.class))
                .collect(Collectors.toList());

        int startItem = pageable.getPageNumber() * pageable.getPageSize();

        List<BandSearchServiceDTO> list;

        if (bands.size() < startItem) {
            list = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageable.getPageSize(), bands.size());
            list = bands.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, pageable, bands.size());
    }
}
