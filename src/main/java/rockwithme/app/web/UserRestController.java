package rockwithme.app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.model.binding.UserSearchBindingDTO;
import rockwithme.app.model.service.UserSearchDetailsDTO;
import rockwithme.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserSearchDetailsDTO>> getUser(@RequestBody UserSearchBindingDTO userSearchBindingDTO) {
        List<UserSearchDetailsDTO> users = this.userService.searchUsers(userSearchBindingDTO);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
}
