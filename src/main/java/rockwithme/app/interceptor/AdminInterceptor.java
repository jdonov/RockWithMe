package rockwithme.app.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import rockwithme.app.model.service.AdminDetailsServiceDTO;
import rockwithme.app.service.BandService;
import rockwithme.app.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final BandService bandService;

    public AdminInterceptor(UserService userService, BandService bandService) {
        this.userService = userService;
        this.bandService = bandService;
    }

        @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AdminDetailsServiceDTO adminDetailsServiceDTO = new AdminDetailsServiceDTO();
        adminDetailsServiceDTO.setRegisteredUsers(this.userService.getCountOfAllUsers());
        adminDetailsServiceDTO.setActiveBands(this.bandService.getCountOfAllActiveBands());
        adminDetailsServiceDTO.setDeletedBands(this.bandService.getCountOfAllDeletedBands());
        adminDetailsServiceDTO.setLastRegisteredUser(this.userService.getLastRegisteredUser());
        adminDetailsServiceDTO.setLastRegisteredBand(this.bandService.getLastRegistered());
        modelAndView.addObject("adminInfo", adminDetailsServiceDTO);
    }
}
