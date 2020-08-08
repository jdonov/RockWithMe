package rockwithme.app.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.binding.JoinRequestBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.repository.BandRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BandRepository bandRepository;

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
    public void getMyBand() throws Exception {
        mockMvc.perform(get("/bands/myBands"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("myBands"))
                .andExpect(view().name("my-bands"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postJoinBand() throws Exception {
        mockMvc.perform(post("/bands/join")
                .flashAttr("joinBand", new JoinRequestBindingDTO(){{setBandId("ID");}}).param("becomeProducer", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("redirectErr", "joinBand"))
                .andExpect(view().name("redirect:/bands/details/ID"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getMyBandDetails() throws Exception {
        Band band = this.bandRepository.findLastRegistered();
        mockMvc.perform(get("/bands/myBands/" + band.getId()))
                .andExpect(model().attributeExists("myBand", "removeMember", "removeProducer"))
                .andExpect(view().name("my-band-details"));
    }
}
