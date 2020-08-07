package rockwithme.app.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void test_search_OK() throws Exception {
        mockMvc.perform(get("/search")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void test_home_OK() throws Exception {
        mockMvc.perform(get("/home")).andExpect(status().isOk());
    }

    @Test

    public void test_about_OK() throws Exception {
        mockMvc.perform(get("/about")).andExpect(status().isOk());
    }

    @Test
    public void test_search_Redirect() throws Exception {
        mockMvc.perform(get("/search")).andExpect(status().is3xxRedirection());
    }
}
