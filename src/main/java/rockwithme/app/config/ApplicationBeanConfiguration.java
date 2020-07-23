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
        ModelMapper modelMapper = new ModelMapper();
//        PropertyMap<PlayerSkills, PlayerSkillsServiceDto> pm = new PropertyMap<PlayerSkills, PlayerSkillsServiceDto>() {
//            @Override
//            protected void configure() {
////                map().setUsername(source.getPlayer().getUsername());
////                map().setInstrument(source.getInstrument().getInstrument().name());
//                skip().setUsername(null);
//                skip().setInstrument(null);
//            }
//        };
//        modelMapper.addMappings(pm);
        return modelMapper;
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

}
