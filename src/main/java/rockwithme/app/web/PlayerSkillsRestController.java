package rockwithme.app.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.model.binding.BandSearchBindingDTO;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.binding.PlayerSkillsSearchBindingDTO;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.BandSearchServiceDTO;
import rockwithme.app.model.service.PlayerSkillsSearchDTO;
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

    @PostMapping("/search")
    public Page<PlayerSkillsSearchDTO> getBands(@RequestBody PlayerSkillsSearchBindingDTO playerSkillsSearchBindingDTO,
                                                @RequestParam("page") Integer page,
                                                @RequestParam("size") Integer size) {

        int currentPage = page == null ? 1 : page;
        int pageSize = size == null ? 5 : size;

        return this.playerSkillsService.searchPlayerSkills(playerSkillsSearchBindingDTO, PageRequest.of(currentPage-1, pageSize));
    }

}
