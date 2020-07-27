package rockwithme.app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.UserChangePasswordDTO;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.utils.FileUploader;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PlayerSkillsService playerSkillsService;

    public UserController(UserService userService, PlayerSkillsService playerSkillsService) {
        this.userService = userService;
        this.playerSkillsService = playerSkillsService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        if (!model.containsAttribute("registerUser")) {
            model.addAttribute("registerUser", new UserRegisterDTO());
        }
        return "user-register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("registerUser") UserRegisterDTO userRegisterDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUser", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUser", bindingResult);
            modelAndView.setViewName("redirect:/users/register");
        } else {
            this.userService.registerUser(userRegisterDTO);
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    @GetMapping("/details")
    public ModelAndView userDetails(@RequestParam("id") String userId, ModelAndView modelAndView) {
        UserPublicDetailsServiceDTO user = this.userService.getUserPublicDetailsById(userId);
        modelAndView.addObject("userPublicDetails", user);
        modelAndView.addObject("userSkills", this.playerSkillsService.getByPlayerId(userId));
        modelAndView.setViewName("user-public-details");
        return modelAndView;
    }

    @GetMapping("/update")
    public String updateUser(Model model) {
        if (!model.containsAttribute("userUpdateDTO")) {
            model.addAttribute("userUpdateDTO", new UserUpdateDTO());
        }
        model.addAttribute("userMyDetailsDTO", this.userService.getUserDetailsByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("changePassword", new UserChangePasswordDTO());
        return "user-update";
    }

    @PostMapping("/update")
    public ModelAndView updateUserConfirm(@Valid @ModelAttribute("userUpdateDTO") UserUpdateDTO userUpdateDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          ModelAndView modelAndView,
                                          Authentication authentication,
                                          @RequestParam(name = "file", required = false) MultipartFile file) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateDTO", userUpdateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateDTO", bindingResult);
            modelAndView.setViewName("redirect:update");
        } else {
            userUpdateDTO.setUsername(authentication.getName());
            if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0) {
                if (Pattern.matches(".+\\.(jpg|png)", file.getOriginalFilename())) {
                    FileUploader.handleMultipartFile(file);
                    userUpdateDTO.setImgUrl("/" + FileUploader.UPLOAD_DIR + "/" + file.getOriginalFilename());
                    modelAndView.setViewName("redirect:/home");
                } else {
                    modelAndView.addObject("fileError", "Submit picture [.jpg, .png]");
                    modelAndView.setViewName("redirect:update");
                }
            }
            this.userService.updatePlayer(userUpdateDTO);
            modelAndView.setViewName("redirect:update");
        }
        return modelAndView;
    }

    @PostMapping("/changePass")
    public ModelAndView changePassword(@Valid @ModelAttribute("changePassword") UserChangePasswordDTO userChangePasswordDTO,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userChangePasswordDTO.getNewPassword().equals(userChangePasswordDTO.getConfirmNewPassword())) {
            FieldError err = new FieldError("changePassword", "confirmNewPassword", "Passwords do not match!");
            bindingResult.addError(err);
        }
        //TODO passwordEncoder checks passwords too slowly!! Check passwordEncoder.matches() speed???
        if (!this.userService.checkIfValidOldPassword(username, userChangePasswordDTO.getOldPassword())) {
            FieldError err = new FieldError("changePassword", "oldPassword", "Wrong password!");
            bindingResult.addError(err);
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changePassword", userChangePasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangePasswordDTO", bindingResult);
            modelAndView.setViewName("redirect:update");
            return modelAndView;
        }

        this.userService.changeUserPassword(username, userChangePasswordDTO.getNewPassword());
        modelAndView.setViewName("redirect:update");
        return modelAndView;
    }


}
