package rockwithme.app.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final InstrumentService instrumentService;
    private final PlayerSkillsService playerSkillsService;

    @Autowired
    public DataInitializer(UserService userService, InstrumentService instrumentService, PlayerSkillsService playerSkillsService) {
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.playerSkillsService = playerSkillsService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.userService.registerUser(new UserRegisterDTO("James", "Hatfield", "papaHat", "123", "123",  Role.ADMIN.name(), Town.SOFIA.name()));
//        User user = this.userService.getUserByUsername("papaHat");
//        this.userService.registerUser(new UserRegisterDTO("Kirk", "Hammet", "kirk", "123","123", Role.PLAYER.name(), Town.VARNA.name()));
//        this.userService.registerUser(new UserRegisterDTO("Bob", "Rock", "bobRock", "123","123", Role.PRODUCER.name(), Town.PLOVDIV.name()));
//
//        Arrays.stream(InstrumentEnum.values())
//                .forEach(this.instrumentService::saveInstrument);
//
//        Instrument instrument = this.instrumentService.getInstrument(InstrumentEnum.GUITAR);
//        Instrument instrument2 = this.instrumentService.getInstrument(InstrumentEnum.DRUMS);
//        PlayerSkillsAddDTO playerSkills = new PlayerSkillsAddDTO();
//
//        playerSkills.setUsername(user.getUsername());
//        playerSkills.setInstrument(instrument.getInstrument());
//        playerSkills.setLevel(Level.MASTER);
//        playerSkills.setYearsPlaying(45);
//        playerSkills.setBandPlayed(3);
//        this.playerSkillsService.registerPlayerSkills(playerSkills);
//        playerSkills.setUsername(user.getUsername());
//        playerSkills.setInstrument(instrument2.getInstrument());
//        playerSkills.setLevel(Level.INTERMEDIATE);
//        playerSkills.setYearsPlaying(5);
//        playerSkills.setBandPlayed(0);
//        this.playerSkillsService.registerPlayerSkills(playerSkills);
//        User userTest = this.userService.getUserByUsername("papaHat");
//        List<PlayerSkillsServiceDTO> skills = this.playerSkillsService.getByPlayerId(userTest.getId());
//        skills.forEach(s -> System.out.println(String.format("%s, %s %s %d %d",
//                userTest.getUsername(),
//                s.getInstrument(),
//                s.getLevel().name(), s.getYearsPlaying(), s.getBandPlayed())));
    }
}
