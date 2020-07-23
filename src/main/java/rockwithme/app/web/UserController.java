package rockwithme.app.web;

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
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.service.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UserController {
    public static final String UPLOAD_DIR = "uploads";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ModelAndView userDetails(@RequestParam("id") String userId, ModelAndView modelAndView) {
        UserPublicDetailsServiceDTO user = this.userService.getUserPublicDetailsById(userId);
        modelAndView.addObject("userPublicDetails", user);
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
                    handleMultipartFile(file);
                    userUpdateDTO.setImgUrl("/" + UPLOAD_DIR + "/" + file.getOriginalFilename());
                    modelAndView.setViewName("redirect:/home");
                } else {
                    modelAndView.addObject("fileError", "Submit picture [.jpg, .png]");
                    modelAndView.setViewName("redirect:update");
                }
            }
            this.userService.updatePlayer(userUpdateDTO);
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

    private void handleMultipartFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        long size = file.getSize();
//        log.info("File: " + name + ", Size: " + size);
        try {
            File currentDir = new File(UPLOAD_DIR);
            if (!currentDir.exists()) {
                currentDir.mkdirs();
            }
            String path = currentDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            path = new File(path).getAbsolutePath();
//            log.info(path);
            File f = new File(path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
