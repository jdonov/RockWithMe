package rockwithme.app.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rockwithme.app.filter.LikesInterceptor;
import rockwithme.app.filter.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LikesInterceptor likesInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor, LikesInterceptor likesInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.likesInterceptor = likesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/skills", "/home");
        registry.addInterceptor(likesInterceptor).addPathPatterns("/bands/**");
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
