package rockwithme.app.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import rockwithme.app.schedul.BandOfTheWeek;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BandOfTheWeekInterceptor implements HandlerInterceptor {
    private final BandOfTheWeek bandOfTheWeek;

    public BandOfTheWeekInterceptor(BandOfTheWeek bandOfTheWeek) {
        this.bandOfTheWeek = bandOfTheWeek;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        modelAndView.addObject("bandOfTheWeek", bandOfTheWeek.getBandOfTheWeekServiceDTO());
    }
}
