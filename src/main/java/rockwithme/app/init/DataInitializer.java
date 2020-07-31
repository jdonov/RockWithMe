package rockwithme.app.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rockwithme.app.event.OnInitPublisher;
import rockwithme.app.service.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OnInitPublisher onInitPublisher;
    private final UserService userService;
    private final InstrumentService instrumentService;
    private final PlayerSkillsService playerSkillsService;
    private final BandService bandService;
    private final LikeService likeService;

    @Autowired
    public DataInitializer(OnInitPublisher onInitPublisher, UserService userService, InstrumentService instrumentService, PlayerSkillsService playerSkillsService, BandService bandService, LikeService likeService) {
        this.onInitPublisher = onInitPublisher;
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.playerSkillsService = playerSkillsService;
        this.bandService = bandService;
        this.likeService = likeService;
    }

    @Override
    public void run(String... args) {
        //TODO add pic to each band
//        Map<String, BandServiceDTO> bands = new HashMap<>();
//        List.of(
//                new UserRegisterDTO("Admin", "Admin", "admin", "123", "123", Role.ADMIN.name(), Town.SOFIA.name()),
//                new UserRegisterDTO("James", "Hatfield", "papaHat", "123", "123", Role.PLAYER.name(), Town.SOFIA.name()),
//                new UserRegisterDTO("Kirk", "Hammet", "kirk", "123", "123", Role.PLAYER.name(), Town.VARNA.name()),
//                new UserRegisterDTO("Bob", "Rock", "bobRock", "123", "123", Role.PRODUCER.name(), Town.PLOVDIV.name()),
//                new UserRegisterDTO("John", "Petrucci", "johnyP", "123", "123", Role.PLAYER.name(), Town.VARNA.name()),
//                new UserRegisterDTO("James", "Labrie", "jamesLab", "123", "123", Role.PLAYER.name(), Town.PLOVDIV.name()),
//                new UserRegisterDTO("Mitko", "Petrov", "mitko", "123", "123", Role.FAN.name(), Town.VRACA.name()),
//                new UserRegisterDTO("Zolthan", "Bathory", "zolthan", "123", "123", Role.PLAYER.name(), Town.BURGAS.name()),
//                new UserRegisterDTO("Ivan", "Moody", "ivanM", "123", "123", Role.PLAYER.name(), Town.SOFIA.name()),
//                new UserRegisterDTO("Mike", "Portnoy", "mikeP", "123", "123", Role.PLAYER.name(), Town.SOFIA.name()),
//                new UserRegisterDTO("Amy", "Lee", "amy", "123", "123", Role.PLAYER.name(), Town.SOFIA.name()),
//                new UserRegisterDTO("Tarja", "Turunen", "tarja", "123", "123", Role.PLAYER.name(), Town.SOFIA.name())
//        ).forEach(this.userService::registerUser);
//
//        Arrays.stream(InstrumentEnum.values())
//                .forEach(this.instrumentService::saveInstrument);
//
//        List.of(
//                new PlayerSkillsAddDTO("papaHat", InstrumentEnum.GUITAR, Level.MASTER, 15, 3),
//                new PlayerSkillsAddDTO("papaHat", InstrumentEnum.DRUMS, Level.INTERMEDIATE, 2, 0),
//                new PlayerSkillsAddDTO("kirk", InstrumentEnum.GUITAR, Level.MASTER, 7, 5),
//                new PlayerSkillsAddDTO("kirk", InstrumentEnum.BASS, Level.BEGINNER, 1, 1),
//                new PlayerSkillsAddDTO("johnyP", InstrumentEnum.GUITAR, Level.MASTER, 5, 1),
//                new PlayerSkillsAddDTO("zolthan", InstrumentEnum.GUITAR, Level.MASTER, 18, 3),
//                new PlayerSkillsAddDTO("jamesLab", InstrumentEnum.SINGER, Level.MASTER, 3, 3),
//                new PlayerSkillsAddDTO("amy", InstrumentEnum.SINGER, Level.MASTER, 13, 3),
//                new PlayerSkillsAddDTO("tarja", InstrumentEnum.SINGER, Level.MASTER, 17, 2)
//        ).forEach(this.playerSkillsService::registerPlayerSkills);
//
//        List.of(
//                new BandRegisterDTO("Metallica",
//                        "papaHat",
//                        "GUITAR",
//                        List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS),
//                        Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
//                        true, false, Set.of(Goal.RECORD_ALBUM), Town.SOFIA.name(), ""),
//                new BandRegisterDTO("Dream Theater",
//                        "johnyP",
//                        "GUITAR",
//                        List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.KEYBOARD, InstrumentEnum.DRUMS),
//                        Set.of(Style.PROGRESSIVE),
//                        true, false, Set.of(Goal.RECORD_ALBUM, Goal.PLAYING_FOR_FUN), Town.SOFIA.name(), ""),
//                new BandRegisterDTO("Five Finger Death Punch",
//                        "zolthan",
//                        "GUITAR",
//                        List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS),
//                        Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
//                        true, false, Set.of(Goal.PUBS_PLAYING), Town.VARNA.name(), ""),
//                new BandRegisterDTO("NightWish",
//                        "tarja",
//                        "SINGER",
//                        List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS, InstrumentEnum.KEYBOARD),
//                        Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
//                        false, true, Set.of(Goal.PUBS_PLAYING), Town.VARNA.name(), ""),
//                new BandRegisterDTO("Evanescence",
//                        "amy",
//                        "SINGER",
//                        List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS, InstrumentEnum.KEYBOARD),
//                        Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
//                        false, false, Set.of(Goal.PUBS_PLAYING), Town.VRACA.name(), ""),
//                new BandRegisterDTO("New Age Killer",
//                        "kirk",
//                        "GUITAR",
//                        List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS),
//                        Set.of(Style.ALTERNATIVE, Style.HARD_ROCK),
//                        true, false, Set.of(Goal.PUBS_PLAYING), Town.VRACA.name(), ""),
//                new BandRegisterDTO("Cruisers",
//                        "kirk",
//                        "BASS",
//                        List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS, InstrumentEnum.SAX),
//                        Set.of(Style.BLUES, Style.FUNK),
//                        true, true, Set.of(Goal.PUBS_PLAYING), Town.STARA_ZAGORA.name(), "")
//        ).forEach(b -> bands.put(b.getName(), this.bandService.registerBand(b)));
//
//        List.of(
//                new LikeBindingDTO("kirk", bands.get("Metallica").getId()),
//                new LikeBindingDTO("johnyP", bands.get("Metallica").getId()),
//                new LikeBindingDTO("amy", bands.get("Metallica").getId()),
//                new LikeBindingDTO("mitko", bands.get("NightWish").getId())
//        ).forEach(this.likeService::likeBand);
//
//        onInitPublisher.publishInit();
    }
}
