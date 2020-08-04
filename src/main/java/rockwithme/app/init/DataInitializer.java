package rockwithme.app.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rockwithme.app.event.OnInitPublisher;
import rockwithme.app.model.binding.*;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.BandServiceDTO;
import rockwithme.app.model.service.UserServiceDTO;
import rockwithme.app.service.*;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OnInitPublisher onInitPublisher;
    private final UserService userService;
    private final InstrumentService instrumentService;
    private final PlayerSkillsService playerSkillsService;
    private final BandService bandService;
    private final LikeService likeService;
    private final EventService eventService;

    @Autowired
    public DataInitializer(OnInitPublisher onInitPublisher, UserService userService, InstrumentService instrumentService, PlayerSkillsService playerSkillsService, BandService bandService, LikeService likeService, EventService eventService) {
        this.onInitPublisher = onInitPublisher;
        this.userService = userService;
        this.instrumentService = instrumentService;
        this.playerSkillsService = playerSkillsService;
        this.bandService = bandService;
        this.likeService = likeService;
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {
        if(this.userService.getCountOfAllUsers() == 0) {
            Map<String, BandServiceDTO> bands = new HashMap<>();
            List.of(
                    new UserRegisterDTO("Admin", "Admin", "admin", "123", "123", Role.ADMIN.name(), Town.SOFIA.name(), null),
                    new UserRegisterDTO("James", "Hatfield", "papaHat", "123", "123", Role.PLAYER.name(), Town.SOFIA.name(), "/uploads/JH.jpg"),
                    new UserRegisterDTO("Kirk", "Hammet", "kirk", "123", "123", Role.PLAYER.name(), Town.VARNA.name(), "/uploads/Kirk.jpg"),
                    new UserRegisterDTO("Bob", "Rock", "bobRock", "123", "123", Role.PRODUCER.name(), Town.PLOVDIV.name(), "/uploads/bobRock.jpg"),
                    new UserRegisterDTO("John", "Petrucci", "johnyP", "123", "123", Role.PLAYER.name(), Town.VARNA.name(), "/uploads/JohnyP.jpg"),
                    new UserRegisterDTO("James", "Labrie", "jamesLab", "123", "123", Role.PLAYER.name(), Town.PLOVDIV.name(), "/uploads/JamesLabrie.jpg"),
                    new UserRegisterDTO("Mitko", "Petrov", "mitko", "123", "123", Role.FAN.name(), Town.VRACA.name(), null),
                    new UserRegisterDTO("Zoltan", "Bathory", "zoltan", "123", "123", Role.PLAYER.name(), Town.BURGAS.name(), "/uploads/Zoltan.jpg"),
                    new UserRegisterDTO("Ivan", "Moody", "ivanM", "123", "123", Role.PLAYER.name(), Town.SOFIA.name(), "/uploads/IvanMoody.jpg"),
                    new UserRegisterDTO("Mike", "Portnoy", "mikeP", "123", "123", Role.PLAYER.name(), Town.SOFIA.name(), "/uploads/MikePortnoy.jpg"),
                    new UserRegisterDTO("Amy", "Lee", "amy", "123", "123", Role.PLAYER.name(), Town.SOFIA.name(), "/uploads/AmyLee.jpg"),
                    new UserRegisterDTO("Tarja", "Turunen", "tarja", "123", "123", Role.PLAYER.name(), Town.SOFIA.name(), "/uploads/Tarja.jpg")
            ).forEach(this.userService::registerUser);

            Arrays.stream(InstrumentEnum.values())
                    .forEach(this.instrumentService::saveInstrument);

            List.of(
                    new PlayerSkillsAddDTO("papaHat", InstrumentEnum.GUITAR, Level.MASTER, 15, 3),
                    new PlayerSkillsAddDTO("papaHat", InstrumentEnum.DRUMS, Level.INTERMEDIATE, 2, 0),
                    new PlayerSkillsAddDTO("kirk", InstrumentEnum.GUITAR, Level.MASTER, 7, 5),
                    new PlayerSkillsAddDTO("kirk", InstrumentEnum.BASS, Level.BEGINNER, 1, 1),
                    new PlayerSkillsAddDTO("johnyP", InstrumentEnum.GUITAR, Level.MASTER, 5, 1),
                    new PlayerSkillsAddDTO("jamesLab", InstrumentEnum.SINGER, Level.MASTER, 9, 5),
                    new PlayerSkillsAddDTO("jamesLab", InstrumentEnum.SAX, Level.BEGINNER, 9, 5),
                    new PlayerSkillsAddDTO("mikeP", InstrumentEnum.DRUMS, Level.MASTER, 16, 8),
                    new PlayerSkillsAddDTO("zolthan", InstrumentEnum.GUITAR, Level.MASTER, 18, 3),
                    new PlayerSkillsAddDTO("ivanM", InstrumentEnum.SINGER, Level.MASTER, 6, 7),
                    new PlayerSkillsAddDTO("amy", InstrumentEnum.SINGER, Level.MASTER, 13, 3),
                    new PlayerSkillsAddDTO("tarja", InstrumentEnum.SINGER, Level.MASTER, 17, 2)
            ).forEach(this.playerSkillsService::registerPlayerSkills);

            List.of(
                    new BandRegisterDTO("Metallica",
                            "papaHat",
                            "GUITAR",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS),
                            Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
                            true, false, Set.of(Goal.RECORD_ALBUM), Town.SOFIA.name(), "", "/uploads/metallica.jpg"),
                    new BandRegisterDTO("New Age Killer",
                            "kirk",
                            "GUITAR",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS),
                            Set.of(Style.ALTERNATIVE, Style.HARD_ROCK),
                            true, false, Set.of(Goal.PUBS_PLAYING), Town.VRACA.name(), "", null),
                    new BandRegisterDTO("Cruisers",
                            "kirk",
                            "BASS",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS, InstrumentEnum.SAX),
                            Set.of(Style.BLUES, Style.FUNK),
                            true, true, Set.of(Goal.PUBS_PLAYING, Goal.CRUISE_PLAYING), Town.STARA_ZAGORA.name(), "", null),
                    new BandRegisterDTO("Sons of Apollo",
                            "mikeP",
                            "DRUMS",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.KEYBOARD, InstrumentEnum.DRUMS),
                            Set.of(Style.PROGRESSIVE, Style.HARD_ROCK),
                            true, false, Set.of(Goal.RECORD_ALBUM, Goal.PLAYING_FOR_FUN), Town.SOFIA.name(), "", "/uploads/sonsOfapollo.jpg"),
                    new BandRegisterDTO("Dream Theater",
                            "johnyP",
                            "GUITAR",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.KEYBOARD, InstrumentEnum.DRUMS),
                            Set.of(Style.PROGRESSIVE),
                            true, false, Set.of(Goal.RECORD_ALBUM, Goal.PLAYING_FOR_FUN), Town.SOFIA.name(), "", "/uploads/DreamTheater.jpg"),
                    new BandRegisterDTO("Five Finger Death Punch",
                            "zolthan",
                            "GUITAR",
                            List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS),
                            Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
                            true, false, Set.of(Goal.PUBS_PLAYING), Town.VARNA.name(), "", "/uploads/FFDP.jpg"),
                    new BandRegisterDTO("NightWish",
                            "tarja",
                            "SINGER",
                            List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS, InstrumentEnum.KEYBOARD),
                            Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
                            false, true, Set.of(Goal.PUBS_PLAYING), Town.VARNA.name(), "", "/uploads/Nightwish.jpg"),
                    new BandRegisterDTO("Evanescence",
                            "amy",
                            "SINGER",
                            List.of(InstrumentEnum.SINGER, InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.GUITAR, InstrumentEnum.DRUMS, InstrumentEnum.KEYBOARD),
                            Set.of(Style.TRASH_METAL, Style.HARD_ROCK),
                            false, false, Set.of(Goal.PUBS_PLAYING), Town.VRACA.name(), "", "/uploads/evanescence.jpg")

            ).forEach(b -> bands.put(b.getName(), this.bandService.registerBand(b)));

            List.of(
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.of(2020, 12, 1, 20, 30), "Live in San Francisco", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.of(2020, 11, 3, 20, 30), "TV Show", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusSeconds(5), "Live in LA", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusSeconds(5), "Live in NY", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.of(2020, 8, 3, 21, 15), "Drinking beer", bands.get("Metallica").getId()),

                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.of(2020, 10, 3, 12, 30), "Live in San Francisco", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.RECORD, LocalDateTime.of(2020, 9, 3, 15, 45), "TV Show", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.now().plusSeconds(5), "Live in LA", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("Dream Theater").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.of(2020, 8, 19, 23, 0), "Drinking beer", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusSeconds(5), "TV Show", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusSeconds(5), "Radio Show", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusSeconds(5), "Rehearsal", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("Five Finger Death Punch").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.of(2021, 3, 3, 19, 15), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.of(2020, 10, 5, 16, 0), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.of(2020, 9, 15, 22, 30), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("NightWish").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.of(2021, 3, 3, 19, 15), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.of(2020, 10, 5, 16, 0), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.of(2020, 9, 15, 22, 30), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("Evanescence").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.of(2021, 3, 3, 19, 15), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.of(2020, 10, 5, 16, 0), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("New Age Killer").getId()),

                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.of(2020, 10, 5, 16, 0), "Drinking beer", bands.get("Cruisers").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusSeconds(5), "Drinking beer", bands.get("Cruisers").getId())
            ).forEach(this.eventService::createEvent);

            List.of(
                    new LikeBindingDTO("kirk", bands.get("Metallica").getId()),
                    new LikeBindingDTO("johnyP", bands.get("Metallica").getId()),
                    new LikeBindingDTO("amy", bands.get("Metallica").getId()),
                    new LikeBindingDTO("mitko", bands.get("NightWish").getId())
            ).forEach(this.likeService::likeBand);

            onInitPublisher.publishInit();
        }
    }
}