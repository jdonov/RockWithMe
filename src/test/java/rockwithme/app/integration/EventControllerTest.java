package rockwithme.app.integration;


import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.repository.EventRepository;
import rockwithme.app.repository.PlayerSkillsRepository;
import rockwithme.app.repository.UserRepository;
import rockwithme.app.service.EventService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    private User testUser;
    private PlayerSkills testPlayerSkills;
    private Band testBand;

    @BeforeEach
    public void init() {
        this.testUser = new User() {{
            setUsername("test");
            setId("ID");
            setAuthorities(Set.of(Role.PLAYER));
            setFirstName("Test");
            setLastName("Test");
        }};
        testPlayerSkills = new PlayerSkills();
        testPlayerSkills.setLevel(Level.MASTER);
        testPlayerSkills.setBandPlayed(3);
        testPlayerSkills.setYearsPlaying(1);
        testPlayerSkills.setInstrument(new Instrument(InstrumentEnum.GUITAR));
        testPlayerSkills.setPlayer(testUser);

        testBand = new Band();
        testBand.setId("ID");
        testBand.setTown(Town.SOFIA);
        testBand.setName("Test");
        testBand.setInstruments(List.of(new Instrument(InstrumentEnum.GUITAR)));
        testBand.setGoals(new HashSet<>(Set.of(Goal.PLAYING_FOR_FUN)));
        testBand.setStyles(new HashSet<>(Set.of(Style.TRASH_METAL)));
        testBand.setMembers(new HashSet<>(Set.of(testPlayerSkills)));
        testBand.setLikes(new HashSet<>());
        testBand.setEvents(new HashSet<>());

    }


    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getUpcommingEvents() throws Exception {
        mockMvc.perform(
                get("/bands/events")
                        .param("bandId", testBand.getId())
                        .param("upcoming", "true")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("upcomingEvents"))
                .andExpect(view().name("redirect:/bands/myBands/" + testBand.getId()));

    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getPastEvents() throws Exception {
        mockMvc.perform(
                get("/bands/events")
                        .param("bandId", testBand.getId())
                        .param("upcoming", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("pastEvents"))
                .andExpect(view().name("redirect:/bands/myBands/" + testBand.getId()));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getCreateEvent() throws Exception {
        mockMvc.perform(
                get("/bands/events/create").param("bandId", testBand.getId())
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("createEvent"))
                .andExpect(view().name("event-create"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postCreateEvent_BindingError() throws Exception {
        EventCreateBindingDTO eventCreateBindingDTO = new EventCreateBindingDTO(){{
            setBandId("ID");
        }};
        mockMvc.perform(
                post("/bands/events/create").flashAttr("createEvent", eventCreateBindingDTO)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("createEvent"))
                .andExpect(view().name("redirect:/bands/events/create?bandId=ID"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getEventUpdate_OK() throws Exception {
        Event event = this.eventRepository.findAll().stream().findFirst().orElse(null);
        mockMvc.perform(
                get("/bands/events/update").param("id", event.getId())
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("updateEvent"))
                .andExpect(view().name("event-update"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postEventUpdate_BindingErr() throws Exception {
        Event event = this.eventRepository.findAll().stream().findFirst().orElse(null);
        mockMvc.perform(
                post("/bands/events/update/"+event.getId())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("updateEvent"))
                .andExpect(view().name("redirect:/bands/events/update?id=" + event.getId()));
    }

}
