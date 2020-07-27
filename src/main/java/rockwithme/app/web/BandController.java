package rockwithme.app.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.exeption.NotRequiredSkillsException;
import rockwithme.app.model.binding.*;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.service.*;
import rockwithme.app.service.BandService;
import rockwithme.app.service.JoinRequestService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.utils.FileUploader;

import javax.validation.Valid;
import java.util.Set;
import java.util.regex.Pattern;
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
        if (!model.containsAttribute("bandRegister")) {
            model.addAttribute("bandRegister", new BandRegisterDTO());
        }
        return "band-register";
    }

    @PostMapping("/register")
    public String registerBand(@Valid @RequestBody BandRegisterDTO bandRegisterDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam(name = "file", required = false) MultipartFile file,
                               Authentication authentication) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bandRegister", bandRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bandRegister", bindingResult);
            return "redirect:register";
        } else {
            bandRegisterDTO.setFounder(authentication.getName());
            Band band = this.bandService.registerBand(bandRegisterDTO);
            return "redirect:/bands";
        }
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
            model.addAttribute("joinBandProducer", new JoinRequestProducerBindingDTO());
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

        //TODO list band events with links

        model.addAttribute("myBand", this.bandService.getMyBandDetails(id));
        if (!model.containsAttribute("removeMember")) {
            model.addAttribute("removeMember", new BandRemoveMemberBindingDTO());
        }
        if (!model.containsAttribute("removeProducer")) {
            model.addAttribute("removeProducer", new BandRemoveProducerBindingDTO());
        }
        return "my-band-details";
    }

    @PostMapping("/myBands/remove")
    public String removeMember(@ModelAttribute("removeMember") BandRemoveMemberBindingDTO bandRemoveMemberBindingDTO) {
        this.bandService.removeMember(bandRemoveMemberBindingDTO);
        return "redirect:/bands/myBands/" + bandRemoveMemberBindingDTO.getBandId();
    }

    @PostMapping("/myBands/remove/producer")
    public String removeProducer(@ModelAttribute("removeProducer") BandRemoveProducerBindingDTO bandRemoveProducerBindingDTO) {
        this.bandService.removeProducer(bandRemoveProducerBindingDTO);
        return "redirect:/bands/myBands/" + bandRemoveProducerBindingDTO.getBandId();
    }

    @PostMapping("/join")
    public String joinBand(@Valid @ModelAttribute(name = "joinBand") JoinRequestBindingDTO joinRequestBindingDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @ModelAttribute(name = "joinBandProducer") JoinRequestProducerBindingDTO joinRequestProducerBindingDTO,
                           @RequestParam("becomeProducer") boolean becomeProducer) {
        if (becomeProducer) {
            this.joinRequestService.submitJoinRequestProducer(joinRequestProducerBindingDTO);
        } else {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("redirectErr", true);
                redirectAttributes.addFlashAttribute("joinBand", joinRequestBindingDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.joinBand", bindingResult);
                return "redirect:/bands/details/" + joinRequestBindingDTO.getBandId();
            } else {
                try {
                    this.joinRequestService.submitJoinRequest(joinRequestBindingDTO);
                    return "redirect:/bands";
                } catch (NotRequiredSkillsException notRequiredSkillsException) {
                    FieldError err = new FieldError("joinBand", "instrument", "You don't have the required skills to join the band!");
                    bindingResult.addError(err);
                    redirectAttributes.addFlashAttribute("redirectErr", true);
                    redirectAttributes.addFlashAttribute("joinBand", joinRequestBindingDTO);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.joinBand", bindingResult);
                    return "redirect:/bands/details/" + joinRequestBindingDTO.getBandId();
                }
            }
        }
        return "redirect:/bands";
    }

    @PostMapping("/add/{request}")
    public String addMember(@PathVariable(name = "request") String requestId, @RequestParam("addMember") boolean addMember) {
        if(addMember) {
        this.joinRequestService.approveRequest(requestId);
        } else {
            this.joinRequestService.rejectRequest(requestId);
        }
        return "redirect:/bands/myBands";
    }

    @PostMapping("/myBands/addPhoto/{id}")
    public ModelAndView addPhoto(@PathVariable("id") String bandId,
                                 @RequestParam(name = "file", required = false) MultipartFile file,
                                 ModelAndView modelAndView) {
        if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0) {
            if (Pattern.matches(".+\\.(jpg|png)", file.getOriginalFilename())) {
                FileUploader.handleMultipartFile(file);
                this.bandService.addPhoto(bandId, "/" + FileUploader.UPLOAD_DIR + "/" + file.getOriginalFilename());
            } else {
                modelAndView.addObject("fileError", "Submit picture [.jpg, .png]");
            }
        }
        modelAndView.setViewName("redirect:/bands/myBands/" + bandId);
        return modelAndView;
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
