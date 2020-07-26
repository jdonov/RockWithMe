package rockwithme.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rockwithme.app.utils.ValidationUtil;
import rockwithme.app.utils.ValidationUtilImpl;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        PropertyMap<Event, EventUpdateBindingDTO> eventPM = new PropertyMap<Event, EventUpdateBindingDTO>() {
//            @Override
//            protected void configure() {
//                map().setBandId(source.getBand().getId());
//            }
//        };
//        modelMapper.addMappings(eventPM);
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

}
