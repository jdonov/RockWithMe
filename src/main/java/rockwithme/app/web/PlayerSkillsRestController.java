package rockwithme.app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/skills")
public class PlayerSkillsRestController {
    private final UserService userService;
    private final PlayerSkillsService playerSkillsService;

    public PlayerSkillsRestController(UserService userService, PlayerSkillsService playerSkillsService) {
        this.userService = userService;
        this.playerSkillsService = playerSkillsService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<PlayerSkillsServiceDTO>> getSkills(/*@RequestParam String username*/) {
        User user = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(this.playerSkillsService.getByPlayerId(user.getId()));
    }

}
