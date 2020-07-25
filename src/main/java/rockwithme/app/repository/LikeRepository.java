package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.Like;

import java.util.Set;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Set<Like> findByUser_Username(String username);
}
