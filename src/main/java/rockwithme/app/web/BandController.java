package rockwithme.app.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.exeption.NotRequiredSkills;
import rockwithme.app.model.binding.JoinRequestBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.*;
import rockwithme.app.service.BandService;
import rockwithme.app.service.JoinRequestService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bands")
public class BandController {
    private final BandService bandService;
    private final PlayerSkillsService playerSkillsService;
    private final JoinRequestService joinRequestService;
    private final UserService userService;

    public BandController(BandService bandService, PlayerSkillsService playerSkillsService, JoinRequestService joinRequestService, UserService userService) {
        this.bandService = bandService;
        this.playerSkillsService = playerSkillsService;
        this.joinRequestService = joinRequestService;
        this.userService = userService;
    }

    @GetMapping
    public String allBands(Model model) {
        if (!model.containsAttribute("allBands")) {
            model.addAttribute("allBands", this.bandService.getAllBands());
        }
        return "bands";
    }

    @GetMapping("/register")
    public String regBand(Model model, Authentication authentication) {
        if (!model.containsAttribute("founderInstruments")) {
            model.addAttribute("founderInstruments",
                    this.playerSkillsService.getByPlayerId(this.userService.getUserByUsername(authentication.getName()).getId())
                            .stream().map(PlayerSkillsServiceDTO::getInstrument).collect(Collectors.toList()));
        }
        return "band-register";
    }

    @GetMapping("/details/{id}")
    public String bandDetails(@PathVariable(name = "id") String id, Model model) {
        BandDetailsDTO bandDetailsDTO = this.bandService.getBandDetails(id);
        if (!model.containsAttribute("bandDetails")) {
            model.addAttribute("bandDetails", bandDetailsDTO);
        }
        model.addAttribute("inMyBands", inMyBands(id));
        if (!model.containsAttribute("joinBand") && bandDetailsDTO.isNeedMembers()) {
            model.addAttribute("joinBand", new JoinRequestBindingDTO());
        }

        return "band-details";
    }

    @GetMapping("/myBands")
    public String getMyBands(Model model, Authentication authentication) {
        Set<BandMyAllBandsDTO> myAllBandsDTOS = this.bandService.getBandByMember(authentication.getName());
        model.addAttribute("myBands", myAllBandsDTOS);
        return "my-bands";
    }

    @GetMapping("/myBands/{id}")
    public String getMyBandDetails(@PathVariable(name = "id") String id, Model model, Authentication authentication) {
        BandMyBandDetailsDTO myBandDetailsDTOS = this.bandService.getMyBandDetails(id);
        model.addAttribute("myBand", this.bandService.getMyBandDetails(id));
        return "my-band-details";
    }

    @PostMapping("/join")
    public String joinBand(@ModelAttribute(name = "joinBand") JoinRequestBindingDTO joinRequestBindingDTO, Principal principal) {

        joinRequestBindingDTO.setUsername(principal.getName());
        try {
            this.joinRequestService.submitJoinRequest(joinRequestBindingDTO);
        } catch (NotRequiredSkills notRequiredSkills) {
            //TODO handle errors!
            return "redirect:/bands";
        }

        return "redirect:/bands";
    }

    @PostMapping("/add/{request}")
    public String addMember(@PathVariable(name = "request") String requestId) {
        this.joinRequestService.approveRequest(requestId);
        return "redirect:/bands/myBands";
    }

    //TODO reject request

    private boolean inMyBands(String bandId) {
        Set<BandMyAllBandsDTO> myAllBandsDTOS = this.bandService.getBandByMember(SecurityContextHolder.getContext().getAuthentication().getName());
        if (myAllBandsDTOS == null || myAllBandsDTOS.isEmpty()) {
            return false;
        }
        return myAllBandsDTOS.stream().map(BaseServiceModel::getId).filter(id -> id.equals(bandId)).findFirst().orElse(null) != null;
    }


}
