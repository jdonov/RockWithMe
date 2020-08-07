package rockwithme.app.integration;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rockwithme.app.model.binding.UserChangePasswordDTO;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserSearchBindingDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.User;
import rockwithme.app.repository.UserRepository;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testGetUserRegister() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk())
                .andExpect(view().name("user-register"))
                .andExpect(model().attributeExists("registerUser"));
    }

    @Test
    public void testPostUserRegister_BindingError() throws Exception {

        mockMvc.perform(post("/users/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/register"))
                .andExpect(flash().attributeExists("registerUser"));
    }

    @Test
    public void testPostUserRegister_OK() throws Exception {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO() {{
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHatTest");
            setPassword("123");
            setConfirmPassword("123");
            setRole("PLAYER");
            setTown("SOFIA");
        }};

        mockMvc.perform(post("/users/register").flashAttr("registerUser",userRegisterDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    public void testPostUserRegister_PassNotMatch() throws Exception {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO() {{
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHatTest");
            setPassword("123");
            setConfirmPassword("321");
            setRole("PLAYER");
            setTown("SOFIA");
        }};

        mockMvc.perform(post("/users/register").flashAttr("registerUser",userRegisterDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/register"))
                .andExpect(flash().attributeExists("registerUser"));
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

    @Test
    @WithMockUser(username = "testAdmin", authorities = {"ADMIN"})
    public void testGetUserAdminOK() throws Exception {
        mockMvc.perform(get("/users/admin")).
                andExpect(status().isOk()).andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(username = "testAdmin", authorities = {"ADMIN"})
    public void testGetUserAdminSearchEmptyOK() throws Exception {
        mockMvc.perform(get("/users/admin/search")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testAdmin", authorities = {"ADMIN"})
    public void testGetUserAdminSearchWithModelAttrOK() throws Exception {
        UserSearchBindingDTO userSearchBindingDTO = new UserSearchBindingDTO(){{
            setUsername("papaHat");
            setFirstName("James");
            setLastName("Hatfield");
        }};
        mockMvc.perform(get("/users/admin/search")
                .param("username","papaHat")
                .param("firstName", "James")
                .param("lastName", "Hatfield")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "test", authorities = {"PLAYER"})
    public void addNewRole() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("role", null);
        mockMvc.perform(patch("/users/admin/addRole/ID").params(param))
                .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("errRole"))
        .andExpect(view().name("redirect:/users/admin"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void changePass() throws Exception {
        UserChangePasswordDTO test1 = new UserChangePasswordDTO(){{
            setOldPassword("24534");
            setNewPassword("123");
            setConfirmNewPassword("321");
        }};


        mockMvc.perform(patch("/users/changePass").flashAttr("changePassword",test1))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("changePassword"))
                .andExpect(view().name("redirect:update"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void changePassOK() throws Exception {
        UserChangePasswordDTO test1 = new UserChangePasswordDTO(){{
            setOldPassword("123");
            setNewPassword("123");
            setConfirmNewPassword("321");
        }};


        mockMvc.perform(patch("/users/changePass").flashAttr("changePassword",test1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:update"));
    }

    @Test
    @WithMockUser(username = "papaHat", authorities = {"PLAYER"})
    public void updateUser() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(){{
            setFirstName("TEST");
           setLastName("TEST");
           setTown("VARNA");
           setAge(15);
        }};

        mockMvc.perform(put("/users/update").flashAttr("userUpdateDTO", new UserUpdateDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("userUpdateDTO"))
                .andExpect(view().name("redirect:update"));
    }


}
