package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
