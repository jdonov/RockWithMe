package rockwithme.app.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rockwithme.app.interceptor.AdminInterceptor;
import rockwithme.app.interceptor.BandOfTheWeekInterceptor;
import rockwithme.app.interceptor.LikesInterceptor;
import rockwithme.app.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LikesInterceptor likesInterceptor;
    private final BandOfTheWeekInterceptor bandOfTheWeekInterceptor;
    private final AdminInterceptor adminInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor, LikesInterceptor likesInterceptor, BandOfTheWeekInterceptor bandOfTheWeekInterceptor, AdminInterceptor adminInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.likesInterceptor = likesInterceptor;
        this.bandOfTheWeekInterceptor = bandOfTheWeekInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bandOfTheWeekInterceptor).addPathPatterns("/", "/home");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/home", "/users/**", "/skills/**", "/bands/**", "/search", "/about");
        registry.addInterceptor(likesInterceptor).addPathPatterns("/bands/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/users/admin");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }



//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/offers");
//    }

}
