package rockwithme.app.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.JoinRequest;
import rockwithme.app.model.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User registerUser(UserRegisterDTO user);

    User getUserByUsername(String username);

    List<User> searchUsers(Specification<User> specification);

    void addBand(User user, Band band);

    void updatePlayer(UserUpdateDTO userUpdateDTO);

    void addRequest(User user, JoinRequest request);

}
