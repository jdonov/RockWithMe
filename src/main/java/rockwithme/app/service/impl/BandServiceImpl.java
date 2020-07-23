package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import javax.transaction.Transactional;
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
        return this.bandRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BandServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BandDetailsDTO getBandDetails(String id) {
        List<String> instr_ids = this.bandRepository.findBandInstruments(id);
        Band band = this.bandRepository.findById(id).orElse(null);
        band.setInstruments(new ArrayList<>());
        instr_ids.forEach(i -> band.getInstruments().add(this.instrumentService.getInstrumentById(i)));
        BandDetailsDTO bandDetailsDTO = this.modelMapper.map(band, BandDetailsDTO.class);
        if(!band.getMembers().isEmpty()) {
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
        if (band.getProducer() != null) {
            bandDetailsDTO.setProducer(band.getProducer().getUsername());
        }
        return bandDetailsDTO;
    }

    @Override
    public BandMyBandDetailsDTO getMyBandDetails(String id) {
        List<String> instr_ids = this.bandRepository.findBandInstruments(id);
        Band band = this.bandRepository.findById(id).orElse(null);
        band.setInstruments(new ArrayList<>());
        instr_ids.forEach(i -> band.getInstruments().add(this.instrumentService.getInstrumentById(i)));
        BandMyBandDetailsDTO myBandDetails = this.modelMapper.map(band, BandMyBandDetailsDTO.class);
        if(!band.getMembers().isEmpty()) {
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
        if(band.getRequests() != null && !band.getRequests().isEmpty()) {
            Set<JoinRequestServiceDTO> reqs = band.getRequests().stream().map(request -> {
                JoinRequestServiceDTO joinRequestServiceDTO = this.modelMapper.map(request, JoinRequestServiceDTO.class);
                joinRequestServiceDTO.setUsername(request.getUser().getUsername());
                return joinRequestServiceDTO;
            }).collect(Collectors.toCollection(HashSet::new));
            myBandDetails.setRequests(reqs);
        }
        myBandDetails.setInstrumentsNeeded(needed);
        return myBandDetails;
    }

    @Override
    @Transactional
    public Band registerBand(BandRegisterDTO bandRegisterDTO) {
        Band band = this.modelMapper.map(bandRegisterDTO, Band.class);
        User founder = this.userService.getUserByUsername(bandRegisterDTO.getFounder());
        Instrument founderInstrument = this.instrumentService.getInstrument(bandRegisterDTO.getFounderInstrument());
        PlayerSkills playerSkills = this.playerSkillsService.getByPlayerIdAndInstrumentId(founder.getId(), founderInstrument.getId());
        band.setMembers(Set.of(playerSkills));
        band.setInstruments(bandRegisterDTO.getInstruments().stream().map(instrumentService::getInstrument).collect(Collectors.toList()));
        band.setGoals(bandRegisterDTO.getGoals());
        band.setStyles(bandRegisterDTO.getStyles());
        band.setNeedMembers(band.getMembers().size() < band.getInstruments().size());
        band = this.bandRepository.saveAndFlush(band);
        this.userService.addBand(founder, band);
        return band;
    }

    @Override
    public Band getBandById(String id) {
        return this.bandRepository.findById(id).orElse(null);
    }

    @Override
    public Set<BandMyAllBandsDTO> getBandByMember(String username) {
        User user = this.userService.getUserByUsername(username);
        Set<BandMyAllBandsDTO> myAllBandsDTOS = new HashSet<>();
        if (!user.getBands().isEmpty()) {
            myAllBandsDTOS = user.getBands().stream()
                    .map(b -> {
                        BandMyAllBandsDTO band = this.modelMapper.map(b, BandMyAllBandsDTO.class);
                        band.setNewRequests(b.getRequests().size());
                        return band;
                    })
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return myAllBandsDTOS;
    }

    @Override
    public void addRequest(Band band, JoinRequest request) {
        band.getRequests().add(request);
        this.bandRepository.save(band);
    }

    @Override
    public void addMember(Band band, PlayerSkills playerSkills) {
        band.getMembers().add(playerSkills);
        this.bandRepository.save(band);
    }

    @Override
    public void addProducer(User user, Band band) {
        band.setProducer(user);
        this.bandRepository.save(band);
    }

    @Override
    public void addEvent(Event event, String bandId) {
        Band band = this.bandRepository.findById(bandId).orElse(null);
        band.getEvents().add(event);
        this.bandRepository.save(band);
    }
}
