package rockwithme.app.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.entity.Goal;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Style;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.service.BandService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@SessionScope
@RequestMapping("/bands/register")
public class BandRegisterController {

    private List<InstrumentEnum> instruments;
    private Set<Goal> goals;
    private Set<Style> styles;
    private final BandService bandService;
    private final PlayerSkillsService playerSkillsService;
    private final UserService userService;

    public BandRegisterController(BandService bandService, PlayerSkillsService playerSkillsService, UserService userService) {
        this.bandService = bandService;
        this.playerSkillsService = playerSkillsService;
        this.userService = userService;
        this.instruments = new ArrayList<>();
        this.goals = new LinkedHashSet<>();
        this.styles = new LinkedHashSet<>();
    }

    @GetMapping
    public String regBand(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("founderInstruments")) {
            model.addAttribute("founderInstruments",
                    this.playerSkillsService.getByPlayerId(this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId())
                            .stream().map(PlayerSkillsServiceDTO::getInstrument).collect(Collectors.toList()));
        }
        if (!model.containsAttribute("bandRegister")) {
            model.addAttribute("bandRegister", new BandRegisterDTO());
        }
        model.addAttribute("bandInstruments", this.instruments);
        model.addAttribute("bandGoals", this.goals);
        model.addAttribute("bandStyles", this.styles);
        return "band-register-session";
    }

    @PostMapping
    public ModelAndView registerBand(@Valid @ModelAttribute("bandRegister") BandRegisterDTO bandRegisterDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Authentication authentication,
                                     ModelAndView modelAndView) {

        checkBandRegister(bandRegisterDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bandRegister", bindingResult);
            modelAndView.setViewName("redirect:/bands/register");
        } else {
            bandRegisterDTO.setFounder(authentication.getName());
            this.bandService.registerBand(bandRegisterDTO);
            this.clear();
            modelAndView.setViewName("redirect:/bands");
        }
        return modelAndView;
    }

    @PostMapping("/addInstr")
    public String addInstr(@RequestParam("instrument") InstrumentEnum instrument,
                           @ModelAttribute BandRegisterDTO bandRegisterDTO,
                           RedirectAttributes redirectAttributes) {
        if(instrument != null) {
            this.instruments.add(instrument);
        }
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeInstr")
    public String removeInstr(@RequestParam("instr") InstrumentEnum instrumentEnum,
                              @ModelAttribute BandRegisterDTO bandRegisterDTO,
                              RedirectAttributes redirectAttributes) {
        this.instruments.remove(instrumentEnum);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/addStyle")
    public String addStyle(@RequestParam("style") Style style,
                           @ModelAttribute BandRegisterDTO bandRegisterDTO,
                           RedirectAttributes redirectAttributes) {
        if(style != null) {
            this.styles.add(style);
        }
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeStyle")
    public String removeStyle(@RequestParam("style") Style style,
                              @ModelAttribute BandRegisterDTO bandRegisterDTO,
                              RedirectAttributes redirectAttributes) {
        this.styles.remove(style);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/addGoal")
    public String addGoal(@RequestParam("goal") Goal goal,
                          @ModelAttribute BandRegisterDTO bandRegisterDTO,
                          RedirectAttributes redirectAttributes) {
        if(goal != null)
        this.goals.add(goal);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeGoal")
    public String removeGoal(@RequestParam("goal") Goal goal,
                             @ModelAttribute BandRegisterDTO bandRegisterDTO,
                             RedirectAttributes redirectAttributes) {
        this.goals.remove(goal);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    private void checkBandRegister(BandRegisterDTO bandRegisterDTO, BindingResult bindingResult) {
        if (this.instruments.size() == 0) {
            FieldError err = new FieldError("bandRegister", "instruments", "Select at least 1 instrument!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setInstruments(this.instruments);
        }
        if (this.styles.size() == 0) {
            FieldError err = new FieldError("bandRegister", "styles", "Select at least 1 style!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setStyles(this.styles);
        }
        if (this.goals.size() == 0) {
            FieldError err = new FieldError("bandRegister", "goals", "Select at least 1 goal!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setGoals(this.goals);
        }
    }

    private void clear() {
        this.instruments.clear();
        this.styles.clear();
        this.goals.clear();
    }
}
