package rockwithme.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Event;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.service.BandMyBandDetailsDTO;
import rockwithme.app.model.service.BandOfTheWeekServiceDTO;
import rockwithme.app.model.service.PlayerSkillsBandMemberDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.schedul.BandOfTheWeek;
import rockwithme.app.utils.ValidationUtil;
import rockwithme.app.utils.ValidationUtilImpl;

import java.util.stream.Collectors;


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
