package rockwithme.app.integration;


import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.repository.UserRepository;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BandRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BandRepository bandRepository;

    private User testUser;
    private Band testBand;

    @BeforeEach
    public void init() {

        testUser = new User() {{
            setUsername("test");
            setId("ID");
            setAuthorities(Set.of(Role.PLAYER));
            setFirstName("Test");
            setLastName("Test");
        }};

        testBand = new Band() {{
            setId("ID");
            setName("Metallica");
            setStyles(new HashSet<>(Set.of(Style.TRASH_METAL, Style.HARD_ROCK)));
            setGoals(new HashSet<>(Set.of(Goal.PUBS_PLAYING)));
            setInstruments(List.of(new Instrument(InstrumentEnum.GUITAR), new Instrument(InstrumentEnum.DRUMS)));
            setTown(Town.SOFIA);
        }};
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getBandRegister() throws Exception {
        mockMvc.perform(get("/bands/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("founderInstruments"))
                .andExpect(model().attributeExists("bandRegister"))
                .andExpect(view().name("band-register"));
    }

    @Test
    public void testBandRegister() throws Exception {
        mockMvc.perform(get("/bands/register"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postAddInstr() throws Exception {
        Map<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("bandInstruments", new ArrayList<InstrumentEnum>());

        mockMvc.perform(post("/bands/register/addInstr").sessionAttrs(sessionattr).param("instrument", "GUITAR"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postRemoveInstr() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandInstruments", new ArrayList<InstrumentEnum>());

        mockMvc.perform(post("/bands/register/removeInstr").sessionAttrs(sessionattr).param("instr", "GUITAR"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postAddStyle() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandStyles", new LinkedHashSet<Style>());

        mockMvc.perform(post("/bands/register/addStyle").sessionAttrs(sessionattr).param("style", "HARD_ROCK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postRemoveStyle() throws Exception {
        Map<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("bandStyles", new LinkedHashSet<Style>());

        mockMvc.perform(post("/bands/register/removeStyle").sessionAttrs(sessionattr).param("style", "HARD_ROCK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postAddGoal() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandGoals", new LinkedHashSet<Goal>());

        mockMvc.perform(post("/bands/register/addGoal").sessionAttrs(sessionattr).param("goal", "PUBS_PLAYING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void postRemoveGoal() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandGoals", new LinkedHashSet<Goal>());

        mockMvc.perform(post("/bands/register/removeGoal").sessionAttrs(sessionattr).param("goal", "PUBS_PLAYING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void registerBandConfirm() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandInstruments", new ArrayList<InstrumentEnum>());
        sessionattr.put("bandStyles", new LinkedHashSet<Style>());
        sessionattr.put("bandGoals", new LinkedHashSet<Goal>());
        mockMvc.perform(post("/bands/register").sessionAttrs(sessionattr).flashAttr("bandRegister", new BandRegisterDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void registerBandConfirm_AlreadyExists() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandInstruments", new ArrayList<>(List.of(InstrumentEnum.GUITAR)));
        sessionattr.put("bandStyles", new LinkedHashSet<>(Set.of(Style.TRASH_METAL)));
        sessionattr.put("bandGoals", new LinkedHashSet<>(Set.of(Goal.PLAYING_FOR_FUN)));
        BandRegisterDTO bandRegisterDTO = new BandRegisterDTO(){{
            setName("Metallica");
            setTown("SOFIA");
            setFounderInstrument("GUITAR");
            setHasStudio(true);
            setNeedsProducer(false);
        }};
        mockMvc.perform(post("/bands/register").sessionAttrs(sessionattr).flashAttr("bandRegister",bandRegisterDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("bandRegister"))
                .andExpect(view().name("redirect:/bands/register"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void registerBandConfirm_OK() throws Exception {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("bandInstruments", new ArrayList<>(List.of(InstrumentEnum.GUITAR)));
        sessionattr.put("bandStyles", new LinkedHashSet<>(Set.of(Style.TRASH_METAL)));
        sessionattr.put("bandGoals", new LinkedHashSet<>(Set.of(Goal.PLAYING_FOR_FUN)));
        BandRegisterDTO bandRegisterDTO = new BandRegisterDTO(){{
            setName("MetallicaTEST");
            setTown("SOFIA");
            setFounderInstrument("GUITAR");
            setHasStudio(true);
            setNeedsProducer(false);
        }};
        mockMvc.perform(post("/bands/register").sessionAttrs(sessionattr).flashAttr("bandRegister",bandRegisterDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }



}
