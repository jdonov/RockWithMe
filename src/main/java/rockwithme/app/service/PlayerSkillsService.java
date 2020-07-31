package rockwithme.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.binding.PlayerSkillsSearchBindingDTO;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.service.PlayerSkillsSearchDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;

import java.util.List;

public interface PlayerSkillsService {
    void registerPlayerSkills(PlayerSkillsAddDTO playerSkills);

    List<PlayerSkillsServiceDTO> getByPlayerId(String playerId);

    PlayerSkills getByPlayerIdAndInstrumentId(String playerId, String instrumentId);

    void updatePlayerSkills(PlayerSkillsAddDTO playerSkillsAddDTO);

    PlayerSkills getByCompositeId(String userId, String instrumentId);

    Page<PlayerSkillsSearchDTO> searchPlayerSkills(PlayerSkillsSearchBindingDTO playerSkillsSearchBindingDTO, Pageable pageable);
}
