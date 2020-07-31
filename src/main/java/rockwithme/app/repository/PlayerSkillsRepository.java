package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.entity.PlayerSkillsPK;


import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerSkillsRepository extends JpaRepository<PlayerSkills, PlayerSkillsPK>, JpaSpecificationExecutor<PlayerSkills> {
    @Query("SELECT p FROM PlayerSkills p WHERE p.player.id = :id ORDER BY p.level DESC, p.yearsPlaying DESC, p.bandPlayed DESC")
    List<PlayerSkills> findAllByPlayer(@Param("id") String id);

    @Query("SELECT p FROM PlayerSkills p WHERE p.player.id = :userId AND p.instrument.id = :instrId")
    PlayerSkills findByPlayerIdAndInstrId(@Param("userId") String userId, @Param("instrId") String instrId);

    Optional<PlayerSkills> findById(PlayerSkillsPK id);
}
