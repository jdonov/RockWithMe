package rockwithme.app.webLayerTests;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.User;
import rockwithme.app.repository.UserRepository;
import rockwithme.app.service.UserService;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
    @WithMockUser(username = "test", roles = {"PLAYER"})
    public void testGetUserRegister() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk())
                .andExpect(view().name("user-register"))
                .andExpect(model().attributeExists("registerUser"));
    }

    @Test
    @WithMockUser(username = "test", roles = {"PLAYER"})
    public void testGetUserAdmin() throws Exception {
        mockMvc.perform(get("/users/admin")).
                andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "test", roles = {"PLAYER"})
    public void testGetUserAdminSearch() throws Exception {
        mockMvc.perform(get("/users/admin/search")).
                andExpect(status().isForbidden());
    }

}
