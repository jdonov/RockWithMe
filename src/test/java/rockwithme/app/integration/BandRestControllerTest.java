package rockwithme.app.integration;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.User;
import rockwithme.app.repository.UserRepository;

import javax.servlet.ServletContext;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        User testUser = new User(){{
            setUsername("test");
            setId("ID");
            setAuthorities(Set.of(Role.PLAYER));
            setFirstName("Test");
            setLastName("Test");
        }};
        User testAdmin = new User(){{
            setUsername("testAdmin");
            setId("123");
            setAuthorities(Set.of(Role.ADMIN));
            setFirstName("Test");
            setLastName("Test");
        }};
        userRepository.saveAndFlush(testUser);
        userRepository.saveAndFlush(testAdmin);
    }

    @Test
    @WithMockUser(username = "test", roles = {"PLAYER"})
    public void test_Search() throws Exception {
        String json = "{\"name\":\"Metallica\", \"style\":\"HARD_ROCK\", \"goal\":\"PUBS_PLAYING\", \"town\":\"SOFIA\", \"needMembers\":true, \"needsProducer\":true}";

        mockMvc.perform(post("/api/bands/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON).param("page", "1").param("size","2"))
                .andExpect(status().isOk());
    }
}
