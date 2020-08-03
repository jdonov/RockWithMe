package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.binding.PlayerSkillsSearchBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.BandSearchServiceDTO;
import rockwithme.app.model.service.PlayerSkillsSearchDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.repository.PlayerSkillsRepository;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.specification.PlayerSkillsSpecificationBuilder;

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

    @Override
    public PlayerSkills getByCompositeId(String userId, String instrumentId) {
        PlayerSkillsPK id = new PlayerSkillsPK(userId, instrumentId);
        return this.playerSkillsRepository.findById(id).orElse(null);
    }

    @Override
    public Page<PlayerSkillsSearchDTO> searchPlayerSkills(PlayerSkillsSearchBindingDTO playerSkillsSearchBindingDTO, Pageable pageable) {

        PlayerSkillsSpecificationBuilder builder = new PlayerSkillsSpecificationBuilder();


        if (playerSkillsSearchBindingDTO.getInstrument() != null) {
            builder.with("instrument", ":", this.instrumentService.getInstrument(playerSkillsSearchBindingDTO.getInstrument()));
        }

        if (playerSkillsSearchBindingDTO.getLevel() != null) {
            builder.with("level", ":", playerSkillsSearchBindingDTO.getLevel());
        }

        Specification<PlayerSkills> spec = builder.build();
        List<PlayerSkills> players = this.playerSkillsRepository.findAll(spec);
        List<PlayerSkillsSearchDTO> playersMapped = players.stream()
                .map(p -> new PlayerSkillsSearchDTO() {{
                    setUserId(p.getId().getPlayerId());
                    setUsername(p.getPlayer().getUsername());
                    setInstrument(p.getInstrument().getInstrument());
                    setLevel(p.getLevel());
                }})
                .collect(Collectors.toList());

        int startItem = pageable.getPageNumber() * pageable.getPageSize();

        List<PlayerSkillsSearchDTO> list;

        if (players.size() < startItem) {
            list = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageable.getPageSize(), players.size());
            list = playersMapped.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, pageable, players.size());
    }
}
