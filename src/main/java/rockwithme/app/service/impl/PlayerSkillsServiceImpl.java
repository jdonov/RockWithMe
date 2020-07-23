package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.repository.PlayerSkillsRepository;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlayerSkillsServiceImpl implements PlayerSkillsService {
    private final PlayerSkillsRepository playerSkillsRepository;
    private final UserService userService;
    private final InstrumentService instrumentService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerSkillsServiceImpl(PlayerSkillsRepository playerSkillsRepository, UserService userService, InstrumentService instrumentService, ModelMapper modelMapper) {
        this.playerSkillsRepository = playerSkillsRepository;
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerPlayerSkills(PlayerSkillsAddDTO playerSkillsAddDTO) {
        PlayerSkills playerSkills = this.modelMapper.map(playerSkillsAddDTO, PlayerSkills.class);
        User player = this.userService.getUserByUsername(playerSkillsAddDTO.getUsername());
        playerSkills.setId(new PlayerSkillsPK(player.getId(), playerSkills.getInstrument().getId()));
        playerSkills.setPlayer(player);
        playerSkills.setInstrument(this.instrumentService.getInstrument(playerSkillsAddDTO.getInstrument()));
        this.playerSkillsRepository.saveAndFlush(playerSkills);
    }

    @Override
    public void updatePlayerSkills(PlayerSkillsAddDTO playerSkillsAddDTO) {
        User player = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Instrument instrument = this.instrumentService.getInstrument(playerSkillsAddDTO.getInstrument());
        PlayerSkills playerSkills = this.playerSkillsRepository.findByPlayerIdAndInstrId(player.getId(), instrument.getId());
        playerSkills.setLevel(playerSkillsAddDTO.getLevel());
        playerSkills.setYearsPlaying(playerSkillsAddDTO.getYearsPlaying());
        playerSkills.setBandPlayed(playerSkillsAddDTO.getBandPlayed());
        this.playerSkillsRepository.save(playerSkills);
    }

    @Override
    public List<PlayerSkillsServiceDTO> getByPlayerId(String playerId) {
        return this.playerSkillsRepository.findAllByPlayer(playerId).stream()
                .map(playerSkills -> {
                    PlayerSkillsServiceDTO ps = this.modelMapper.map(playerSkills, PlayerSkillsServiceDTO.class);
//                    ps.setInstrument(playerSkills.getInstrument().getInstrument());
                    return ps;
                })
//                .sorted((p1,p2) -> p2.getLevel().compareTo(p1.getLevel()))
                .collect(Collectors.toList());
    }

    @Override
    public PlayerSkills getByPlayerIdAndInstrumentId(String playerId, String instrumentId) {
        return this.playerSkillsRepository.findByPlayerIdAndInstrId(playerId, instrumentId);
    }
}
