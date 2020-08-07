package rockwithme.app.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/login").param("error", "true"))
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
    }
}
