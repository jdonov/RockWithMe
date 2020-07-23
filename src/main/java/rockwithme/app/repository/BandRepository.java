package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BandRepository extends JpaRepository<Band, String> {

    Optional<Band> findById(String id);

    @Query(value="SELECT instrument_id FROM bands_instruments WHERE band_id = :id", nativeQuery = true)
    List<String> findBandInstruments(@Param("id") String id);

    List<Band> findByMembers(User user);
}
