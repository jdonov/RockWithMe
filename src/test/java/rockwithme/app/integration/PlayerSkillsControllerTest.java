package rockwithme.app.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.entity.InstrumentEnum;
import rockwithme.app.model.entity.Level;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerSkillsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void getSkills() throws Exception {
        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("currentPlayerSkills"))
                .andExpect(model().attributeExists("currentInstruments"))
                .andExpect(view().name("skills"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void addSkill() throws Exception {
        PlayerSkillsAddDTO playerSkillsAddDTO = new PlayerSkillsAddDTO(){{
            setYearsPlaying(3);
            setBandPlayed(5);
            setLevel(Level.MASTER);
            setUsername("papaHat");
            setInstrument(InstrumentEnum.KEYBOARD);
        }};
        mockMvc.perform(post("/skills").flashAttr("addPlayerSkill", playerSkillsAddDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/skills"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void addSkill_RedirectBindingResult() throws Exception {
        PlayerSkillsAddDTO playerSkillsAddDTO = new PlayerSkillsAddDTO(){{
            setYearsPlaying(3);
            setBandPlayed(5);
            setLevel(Level.MASTER);
            setUsername("papaHat");
            setInstrument(null);
        }};
        mockMvc.perform(post("/skills").flashAttr("addPlayerSkill", playerSkillsAddDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("addPlayerSkill"))
                .andExpect(view().name("redirect:/skills"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void updateSkill_RedirectBindingResult() throws Exception {
        PlayerSkillsAddDTO playerSkillsAddDTO = new PlayerSkillsAddDTO(){{
            setYearsPlaying(3);
            setBandPlayed(5);
            setLevel(Level.MASTER);
            setUsername("papaHat");
            setInstrument(null);
        }};
        mockMvc.perform(put("/skills/update").flashAttr("addPlayerSkill", playerSkillsAddDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("addPlayerSkill"))
                .andExpect(view().name("redirect:/skills"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void updateSkill_Redirect() throws Exception {
        PlayerSkillsAddDTO playerSkillsAddDTO = new PlayerSkillsAddDTO(){{
            setYearsPlaying(3);
            setBandPlayed(5);
            setLevel(Level.MASTER);
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }};
        mockMvc.perform(put("/skills/update").flashAttr("addPlayerSkill", playerSkillsAddDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/skills"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void updateSkill_OK() throws Exception {
        PlayerSkillsAddDTO playerSkillsAddDTO = new PlayerSkillsAddDTO(){{
            setYearsPlaying(3);
            setBandPlayed(5);
            setLevel(Level.MASTER);
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }};
        mockMvc.perform(put("/skills/update").flashAttr("addPlayerSkill", playerSkillsAddDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/skills"));
    }
}
