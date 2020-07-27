package rockwithme.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rockwithme.app.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u")
    int findAllUsersCount();

    @Query(value = "SELECT * FROM users ORDER BY registration_date DESC LIMIT 1", nativeQuery = true)
    User findLastRegistered();
}
