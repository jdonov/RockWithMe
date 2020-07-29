package rockwithme.app.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import rockwithme.app.exeption.UserAlreadyExistsException;
import rockwithme.app.exeption.UserRoleException;

import javax.servlet.http.HttpServletRequest;

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
}

