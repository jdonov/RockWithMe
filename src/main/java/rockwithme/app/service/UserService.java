package rockwithme.app.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserSearchBindingDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.UserAdminServiceDTO;
import rockwithme.app.model.service.UserMyDetailsServiceDTO;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.model.service.UserSearchDetailsDTO;

import java.util.List;

public interface UserService extends UserDetailsService {


    void registerUser(UserRegisterDTO user);

    User getUserByUsername(String username);

    void addNewRole(String userId, Role role);

    List<UserSearchDetailsDTO> searchUsers(UserSearchBindingDTO userSearchBindingDTO);

    void addBand(User user, Band band);

    void removeBand(User user, Band band);

    void updatePlayer(UserUpdateDTO userUpdateDTO);

    void addRequest(User user, JoinRequest request);

    void addLike(Like like, User user);

    UserMyDetailsServiceDTO getUserDetailsByUsername(String username);

    UserRegisterDTO updateUserByUsername(String username);

    UserPublicDetailsServiceDTO getUserPublicDetailsById(String userId);

    boolean checkIfValidOldPassword(String username, String password);

    void changeUserPassword(String username, String password);

    void removeUserRole(String userId, Role role);

    int getCountOfAllUsers();

    UserAdminServiceDTO getLastRegisteredUser();
}
