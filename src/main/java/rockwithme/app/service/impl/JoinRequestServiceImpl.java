package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rockwithme.app.exeption.NotRequiredSkills;
import rockwithme.app.model.binding.JoinRequestBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.JoinRequestServiceDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.repository.JoinRequestRepository;
import rockwithme.app.service.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoinRequestServiceImpl implements JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final UserService userService;
    private final BandService bandService;
    private final PlayerSkillsService playerSkillsService;
    private final InstrumentService instrumentService;

    private final ModelMapper modelMapper;

    public JoinRequestServiceImpl(JoinRequestRepository joinRequestRepository, UserService userService, BandService bandService, PlayerSkillsService playerSkillsService, InstrumentService instrumentService, ModelMapper modelMapper) {
        this.joinRequestRepository = joinRequestRepository;
        this.userService = userService;
        this.bandService = bandService;
        this.playerSkillsService = playerSkillsService;
        this.instrumentService = instrumentService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void submitJoinRequest(JoinRequestBindingDTO joinRequestBindingDTO) {
        JoinRequest joinRequest = this.modelMapper.map(joinRequestBindingDTO, JoinRequest.class);
        Band band = this.bandService.getBandById(joinRequestBindingDTO.getBandId());
        User user = this.userService.getUserByUsername(joinRequestBindingDTO.getUsername());
        List<InstrumentEnum> playerSkills = this.playerSkillsService.getByPlayerId(user.getId()).stream().map(PlayerSkillsServiceDTO::getInstrument).collect(Collectors.toList());
        if (playerSkills.contains(joinRequestBindingDTO.getInstrument())) {
            joinRequest.setBand(band);
            joinRequest.setUser(user);
            joinRequest = this.joinRequestRepository.saveAndFlush(joinRequest);
            this.userService.addRequest(user, joinRequest);
            this.bandService.addRequest(band, joinRequest);
        } else {
            throw new NotRequiredSkills("You don't have the required skills");
        }
    }

    @Override
    public List<JoinRequestServiceDTO> getRequestByBandId(String id) {
        List<JoinRequest> joinRequests = this.joinRequestRepository.findByBand_Id(id);
        List<JoinRequestServiceDTO> requests = joinRequests.stream()
                .map(r -> this.modelMapper.map(r, JoinRequestServiceDTO.class))
                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<JoinRequestServiceDTO> getRequestByUserId(String id) {
        List<JoinRequest> joinRequests = this.joinRequestRepository.findByBand_Id(id);
        List<JoinRequestServiceDTO> requests = joinRequests.stream()
                .map(r -> this.modelMapper.map(r, JoinRequestServiceDTO.class))
                .collect(Collectors.toList());
        return null;
    }

    @Override
    @Transactional
    public void approveRequest(String requestId) {
        JoinRequest joinRequest = this.joinRequestRepository.findById(requestId).orElse(null);
        Band band = this.bandService.getBandById(joinRequest.getBand().getId());
        User user = this.userService.getUserByUsername(joinRequest.getUser().getUsername());
        Instrument instrument = this.instrumentService.getInstrument(joinRequest.getInstrument());
        PlayerSkills playerSkills = this.playerSkillsService.getByPlayerIdAndInstrumentId(user.getId(), instrument.getId());
        joinRequest.setApproved(true);
        joinRequest.setClosed(true);
        this.bandService.addMember(band, playerSkills);
        this.userService.addBand(user, band);
        this.joinRequestRepository.save(joinRequest);
    }

    @Override
    public void rejectRequest(String requestId) {
        JoinRequest joinRequest = this.joinRequestRepository.findById(requestId).orElse(null);
        joinRequest.setApproved(false);
        joinRequest.setClosed(true);
        this.joinRequestRepository.save(joinRequest);
    }
}
