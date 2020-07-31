package rockwithme.app.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/skills")
public class PlayerSkillsController {

    private final PlayerSkillsService playerSkillsService;
    private final UserService userService;

    public PlayerSkillsController(PlayerSkillsService playerSkillsService, UserService userService) {
        this.playerSkillsService = playerSkillsService;
        this.userService = userService;
    }

    @GetMapping
    public String skills(Model model) {
        if (!model.containsAttribute("addPlayerSkill")) {
            model.addAttribute("addPlayerSkill", new PlayerSkillsAddDTO());
        }
        if (!model.containsAttribute("currentPlayerSkills")) {
            User user = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            List<PlayerSkillsServiceDTO> playerSkillsServiceDTOS = this.playerSkillsService.getByPlayerId(user.getId());
            model.addAttribute("currentPlayerSkills", playerSkillsServiceDTOS);
            model.addAttribute("currentInstruments", playerSkillsServiceDTOS
                    .stream()
                    .map(PlayerSkillsServiceDTO::getInstrument)
                    .collect(Collectors.toList()));
        }
        return "skills";
    }

    @PostMapping
    public ModelAndView addSkill(@Valid @ModelAttribute("addPlayerSkill") PlayerSkillsAddDTO playerSkillsAddDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPlayerSkill", playerSkillsAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPlayerSkill", bindingResult);
        } else {
            playerSkillsAddDTO.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            this.playerSkillsService.registerPlayerSkills(playerSkillsAddDTO);
        }
        modelAndView.setViewName("redirect:/skills");
        return modelAndView;
    }

    @PutMapping("/update")
    public ModelAndView updateSkill(@Valid @ModelAttribute PlayerSkillsAddDTO playerSkillsAddDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPlayerSkill", playerSkillsAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPlayerSkill", bindingResult);
        } else {
            this.playerSkillsService.updatePlayerSkills(playerSkillsAddDTO);
        }
        modelAndView.setViewName("redirect:/skills");
        return modelAndView;
    }

}
