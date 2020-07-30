package rockwithme.app.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import rockwithme.app.exeption.NoRegisteredSkills;
import rockwithme.app.exeption.NotRequiredSkillsException;
import rockwithme.app.exeption.UserAlreadyExistsException;
import rockwithme.app.exeption.UserRoleException;
import rockwithme.app.model.entity.InstrumentEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public RedirectView userExists(UserAlreadyExistsException e,
                                   HttpServletRequest request) {
        RedirectView rw = new RedirectView("/users/register");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("userExists", e.getMessage());
        return rw;
    }

    @ExceptionHandler(UserRoleException.class)
    public RedirectView userWithoutRoles(UserRoleException e, HttpServletRequest request) {
        RedirectView rw = new RedirectView("/users/admin");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("errRole", e.getMessage());
        return rw;
    }

    @ExceptionHandler(NotRequiredSkillsException.class)
    public RedirectView notRequiredSkills(NotRequiredSkillsException e, HttpServletRequest request) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (e.getInstrumentEnums().isEmpty()) {
            outputFlashMap.put("notRequiredSkills", e.getMessage());
        } else {
            String errMessage = String.format("%s Your skills are: %s", e.getMessage(), e.getInstrumentEnums().stream()
                    .map(InstrumentEnum::name).collect(Collectors.joining(", ")));
            outputFlashMap.put("notRequiredSkills", errMessage);
            outputFlashMap.put("redirectErr", true);
        }
        return new RedirectView("/bands/details/" + e.getBandId());
    }

    @ExceptionHandler(NoRegisteredSkills.class)
    public RedirectView noSkills(NoRegisteredSkills noRegisteredSkills, HttpServletRequest request) {
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("noSkillsRegistered", noRegisteredSkills.getMessage());
        return new RedirectView("/skills");
    }
}

