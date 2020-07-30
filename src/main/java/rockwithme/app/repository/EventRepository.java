package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByBand_IdAndEventDateIsAfter(String bandId, LocalDateTime localDateTime);
    List<Event> findByBand_IdAndEventDateIsBefore(String bandId, LocalDateTime localDateTime);
}
