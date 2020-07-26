package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BandRepository extends JpaRepository<Band, String> {

    List<Band> findAllByDeletedIsFalse();

    Optional<Band> findById(String id);

    @Query(value="SELECT instrument_id FROM bands_instruments WHERE band_id = :id", nativeQuery = true)
    List<String> findBandInstruments(@Param("id") String id);

    List<Band> findByMembers(User user);

    @Query(value = "SELECT * FROM bands\n" +
            "WHERE id = (\n" +
            "    SELECT band_id\n" +
            "    FROM likes INNER JOIN bands ON likes.band_id = bands.id\n" +
            "    WHERE bands.deleted = FALSE\n" +
            "    GROUP BY band_id ORDER BY COUNT(band_id) DESC LIMIT 1)", nativeQuery = true)
    Band findMostLikedBand();

    @Modifying
    @Transactional
    @Query("UPDATE Band b SET b.deleted = TRUE WHERE b.members.size = 0 AND b.producer IS NULL")
    void deleteAllWithoutMembers();

    @Query("SELECT b FROM Band b WHERE b.members.size = 0 AND b.producer IS NULL")
    List<Band> findAllToDelete();

}
