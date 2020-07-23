package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Instrument;
import rockwithme.app.model.entity.InstrumentEnum;

import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, String> {
    Optional<Instrument> findInstrumentByInstrument(InstrumentEnum instrument);
}
