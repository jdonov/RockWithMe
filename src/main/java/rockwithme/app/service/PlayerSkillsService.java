package rockwithme.app.service;

import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;

import java.util.List;

public interface PlayerSkillsService {
    void registerPlayerSkills(PlayerSkillsAddDTO playerSkills);
    List<PlayerSkillsServiceDTO> getByPlayerId(String playerId);
    PlayerSkills getByPlayerIdAndInstrumentId(String playerId, String instrumentId);
    void updatePlayerSkills(PlayerSkillsAddDTO playerSkillsAddDTO);
}
