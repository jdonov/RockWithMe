package rockwithme.app.webLayerTests;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.User;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.repository.UserRepository;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BandRepository bandRepository;

    @Before
    public void init() {

        User testUser = new User(){{
            setUsername("test");
            setId("123");
            setAuthorities(Set.of(Role.PLAYER));
            setFirstName("Test");
            setLastName("Test");
        }};
        userRepository.saveAndFlush(testUser);
    }

    @Test
    public void testBandRegister() throws Exception {
        mockMvc.perform(get("/bands/register"))
                .andExpect(status().is3xxRedirection());
    }
}
