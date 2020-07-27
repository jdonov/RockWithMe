package rockwithme.app.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import rockwithme.app.service.LikeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class LikesInterceptor implements HandlerInterceptor {
    private final LikeService likeService;

    public LikesInterceptor(LikeService likeService) {
        this.likeService = likeService;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> likes = this.likeService.likedBands(username);

        modelAndView.addObject("userLikes", likes);
    }
}