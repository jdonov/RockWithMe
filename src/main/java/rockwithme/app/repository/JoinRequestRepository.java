package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.JoinRequest;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, String> {
    List<JoinRequest> findByBand_Id(String id);

    List<JoinRequest> findByUser_Id(String id);
}
