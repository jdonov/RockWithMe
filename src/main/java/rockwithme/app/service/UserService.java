package rockwithme.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserSearchBindingDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;

import java.util.List;

public interface UserService extends UserDetailsService {


    UserServiceDTO registerUser(UserRegisterDTO user);

    User getUserByUsername(String username);

    UserServiceDTO addNewRole(String userId, Role role);

    List<UserSearchDetailsDTO> searchUsers(UserSearchBindingDTO userSearchBindingDTO);

    UserServiceDTO addBand(User user, Band band);

    UserServiceDTO removeBand(User user, Band band);

    UserServiceDTO updatePlayer(UserUpdateDTO userUpdateDTO);

    UserServiceDTO addRequest(User user, JoinRequest request);

    UserServiceDTO addLike(Like like, User user);

    UserMyDetailsServiceDTO getUserDetailsByUsername(String username);

    UserRegisterDTO updateUserByUsername(String username);

    UserPublicDetailsServiceDTO getUserPublicDetailsById(String userId);

    boolean checkIfValidOldPassword(String username, String password);

    boolean changeUserPassword(String username, String password);

    UserServiceDTO removeUserRole(String userId, Role role);

    int getCountOfAllUsers();

    UserAdminServiceDTO getLastRegisteredUser();
}
