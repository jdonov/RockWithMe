package rockwithme.app.web;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.UserSearchDetailsDTO;
import rockwithme.app.repository.specification.UserSpecificationsBuilder;
import rockwithme.app.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchDetailsDTO>> getUser(@RequestParam(value = "search") String search) {
//        UserSpecification spec1 =
//                new UserSpecification(new SearchCriteria("firstName", ":", "Ja"));
//        UserSpecification spec2 =
//                new UserSpecification(new SearchCriteria("lastName", ":", "Hatfield"));
//
//        List<User> results =
//                userService.searchUsers(Specification.where(spec1).and(spec2));
//        return ResponseEntity.ok(results);

        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<User> spec = builder.build();
        return ResponseEntity.ok(this.userService.searchUsers(spec));
    }

//    @PostMapping
//    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
//        User user = new User();
//        user.setFirstName(userRegisterDTO.getFirstName());
//        user.setLastName(userRegisterDTO.getLastName());
//        user.setUsername(userRegisterDTO.getUsername());
//        user.setAuthorities(Set.of(userRegisterDTO.getRole()));
//        return ResponseEntity.ok(user);
//    }
}
