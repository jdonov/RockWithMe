package rockwithme.app.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rockwithme.app.event.OnInitPublisher;
import rockwithme.app.model.binding.*;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.BandServiceDTO;
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
                    new PlayerSkillsAddDTO("zoltan", InstrumentEnum.GUITAR, Level.MASTER, 18, 3),
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
                            true, false, Set.of(Goal.RECORD_ALBUM, Goal.PLAYING_FOR_FUN), Town.SOFIA.name(), "", "/uploads/sonsOfApollo.jpg"),
                    new BandRegisterDTO("Dream Theater",
                            "johnyP",
                            "GUITAR",
                            List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.KEYBOARD, InstrumentEnum.DRUMS),
                            Set.of(Style.PROGRESSIVE),
                            true, false, Set.of(Goal.RECORD_ALBUM, Goal.PLAYING_FOR_FUN), Town.SOFIA.name(), "", "/uploads/DreamTheater.jpg"),
                    new BandRegisterDTO("Five Finger Death Punch",
                            "zoltan",
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
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMonths(1).plusDays(2).plusHours(3), "Live in San Francisco", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.now().plusMonths(3).plusDays(5).plusHours(1), "TV Show", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMinutes(3), "Live in LA", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMinutes(3), "Live in NY", bands.get("Metallica").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMonths(0).plusDays(15).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("Metallica").getId()),

                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMonths(12).plusDays(15).plusHours(3).plusMinutes(15), "Live in San Francisco", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.RECORD, LocalDateTime.now().plusMonths(2).plusDays(7).plusHours(3).plusMinutes(15), "TV Show", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.now().plusMinutes(3), "Live in LA", bands.get("Dream Theater").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("Dream Theater").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMonths(4).plusDays(10).plusHours(5).plusMinutes(10), "Drinking beer", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMinutes(3), "TV Show", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMinutes(3), "Radio Show", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusMinutes(3), "Rehearsal", bands.get("Five Finger Death Punch").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("Five Finger Death Punch").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMonths(5).plusDays(15).plusHours(3).plusMinutes(20), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMonths(3).plusDays(5).plusHours(2).plusMinutes(15), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.now().plusMonths(6).plusDays(1).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("NightWish").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("NightWish").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMonths(1).plusDays(15).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMonths(0).plusDays(10).plusHours(6).plusMinutes(15), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.TV, LocalDateTime.now().plusMonths(0).plusDays(20).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("Evanescence").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("Evanescence").getId()),

                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.MEMBERS_MEETING, LocalDateTime.now().plusMonths(7).plusDays(1).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.LIVE, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMonths(2).plusDays(3).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("New Age Killer").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("New Age Killer").getId()),

                    new EventCreateBindingDTO(EventType.PUBLIC, EventCategory.RADIO, LocalDateTime.now().plusMonths(0).plusDays(7).plusHours(3).plusMinutes(15), "Drinking beer", bands.get("Cruisers").getId()),
                    new EventCreateBindingDTO(EventType.MEMBERS_ONLY, EventCategory.REHEARSAL, LocalDateTime.now().plusMinutes(3), "Drinking beer", bands.get("Cruisers").getId())
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