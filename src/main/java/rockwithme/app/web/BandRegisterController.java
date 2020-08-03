package rockwithme.app.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.exeption.BandAlreadyExistsException;
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
@RequestMapping("/bands/register")
public class BandRegisterController {

    private final BandService bandService;
    private final PlayerSkillsService playerSkillsService;
    private final UserService userService;

    public BandRegisterController(BandService bandService, PlayerSkillsService playerSkillsService, UserService userService) {
        this.bandService = bandService;
        this.playerSkillsService = playerSkillsService;
        this.userService = userService;
    }


    @GetMapping
    public String registerBand(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("founderInstruments")) {
            model.addAttribute("founderInstruments",
                    this.playerSkillsService.getByPlayerId(this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId())
                            .stream().map(PlayerSkillsServiceDTO::getInstrument).collect(Collectors.toList()));
        }
        if (!model.containsAttribute("bandRegister")) {
            model.addAttribute("bandRegister", new BandRegisterDTO());
        }
        if (httpSession.getAttribute("bandInstruments") == null) {
            httpSession.setAttribute("bandInstruments", new ArrayList<InstrumentEnum>());
        }
        if (httpSession.getAttribute("bandGoals") == null) {
            httpSession.setAttribute("bandGoals", new LinkedHashSet<Goal>());
        }
        if (httpSession.getAttribute("bandStyles") == null) {
            httpSession.setAttribute("bandStyles", new LinkedHashSet<Style>());
        }

        return "band-register";
    }

    @PostMapping
    public ModelAndView registerBandConfirm(@Valid @ModelAttribute("bandRegister") BandRegisterDTO bandRegisterDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession httpSession,
                                     Authentication authentication,
                                     ModelAndView modelAndView) {

        checkBandRegister(bandRegisterDTO, bindingResult, httpSession);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bandRegister", bindingResult);
            modelAndView.setViewName("redirect:/bands/register");
        } else {
            bandRegisterDTO.setFounder(authentication.getName());
            try {
                this.bandService.registerBand(bandRegisterDTO);
            } catch (BandAlreadyExistsException e) {
                FieldError fieldError = new FieldError("bandRegister", "name", e.getMessage());
                bindingResult.addError(fieldError);
                redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bandRegister", bindingResult);
                modelAndView.setViewName("redirect:/bands/register");
                return modelAndView;
            }
            this.clear(httpSession);
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @PostMapping("/addInstr")
    public String addInstr(@RequestParam("instrument") InstrumentEnum instrument,
                           @ModelAttribute BandRegisterDTO bandRegisterDTO,
                           RedirectAttributes redirectAttributes,
                           HttpSession httpSession) {
        if(instrument != null) {
            List<InstrumentEnum> instruments = (List<InstrumentEnum>) httpSession.getAttribute("bandInstruments");
            instruments.add(instrument);
            httpSession.setAttribute("bandInstruments", instruments);
        }
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeInstr")
    public String removeInstr(@RequestParam("instr") InstrumentEnum instrumentEnum,
                              @ModelAttribute BandRegisterDTO bandRegisterDTO,
                              RedirectAttributes redirectAttributes,
                              HttpSession httpSession) {
        List<InstrumentEnum> instruments = (List<InstrumentEnum>) httpSession.getAttribute("bandInstruments");
        instruments.remove(instrumentEnum);
        httpSession.setAttribute("bandInstruments", instruments);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/addStyle")
    public String addStyle(@RequestParam("style") Style style,
                           @ModelAttribute BandRegisterDTO bandRegisterDTO,
                           RedirectAttributes redirectAttributes,
                           HttpSession httpSession) {
        if(style != null) {
            LinkedHashSet<Style> styles = (LinkedHashSet<Style>) httpSession.getAttribute("bandStyles");
            styles.add(style);
            httpSession.setAttribute("bandStyles", styles);
        }
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeStyle")
    public String removeStyle(@RequestParam("style") Style style,
                              @ModelAttribute BandRegisterDTO bandRegisterDTO,
                              RedirectAttributes redirectAttributes,
                              HttpSession httpSession) {
        LinkedHashSet<Style> styles = (LinkedHashSet<Style>) httpSession.getAttribute("bandStyles");
        styles.remove(style);
        httpSession.setAttribute("bandStyles", styles);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/addGoal")
    public String addGoal(@RequestParam("goal") Goal goal,
                          @ModelAttribute BandRegisterDTO bandRegisterDTO,
                          RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if(goal != null) {
            LinkedHashSet<Goal> goals = (LinkedHashSet<Goal>) httpSession.getAttribute("bandGoals");
            goals.add(goal);
            httpSession.setAttribute("bandGoals", goals);
        }
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    @PostMapping("/removeGoal")
    public String removeGoal(@RequestParam("goal") Goal goal,
                             @ModelAttribute BandRegisterDTO bandRegisterDTO,
                             RedirectAttributes redirectAttributes,
                             HttpSession httpSession) {
        LinkedHashSet<Goal> goals = (LinkedHashSet<Goal>) httpSession.getAttribute("bandGoals");
        goals.remove(goal);
        httpSession.setAttribute("bandGoals", goals);
        redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
        return "redirect:/bands/register";
    }

    private void checkBandRegister(BandRegisterDTO bandRegisterDTO, BindingResult bindingResult, HttpSession httpSession) {
        List<InstrumentEnum> instruments = (List<InstrumentEnum>) httpSession.getAttribute("bandInstruments");
        LinkedHashSet<Style> styles = (LinkedHashSet<Style>) httpSession.getAttribute("bandStyles");
        LinkedHashSet<Goal> goals = (LinkedHashSet<Goal>) httpSession.getAttribute("bandGoals");

        if (instruments.size() == 0) {
            FieldError err = new FieldError("bandRegister", "instruments", "Select at least 1 instrument!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setInstruments(instruments);
        }
        if (styles.size() == 0) {
            FieldError err = new FieldError("bandRegister", "styles", "Select at least 1 style!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setStyles(styles);
        }
        if (goals.size() == 0) {
            FieldError err = new FieldError("bandRegister", "goals", "Select at least 1 goal!");
            bindingResult.addError(err);
        } else {
            bandRegisterDTO.setGoals(goals);
        }
    }

    private void clear(HttpSession session) {
        session.removeAttribute("bandInstruments");
        session.removeAttribute("bandStyles");
        session.removeAttribute("bandGoals");
    }
}
