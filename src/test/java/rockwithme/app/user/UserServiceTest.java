package rockwithme.app.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import rockwithme.app.exeption.UserAlreadyExistsException;
import rockwithme.app.model.binding.UserRegisterDTO;
import rockwithme.app.model.binding.UserUpdateDTO;
import rockwithme.app.model.entity.Role;
import rockwithme.app.model.entity.Town;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.UserAdminServiceDTO;
import rockwithme.app.model.service.UserMyDetailsServiceDTO;
import rockwithme.app.model.service.UserPublicDetailsServiceDTO;
import rockwithme.app.model.service.UserServiceDTO;
import rockwithme.app.repository.UserRepository;
import rockwithme.app.service.UserService;
import rockwithme.app.service.impl.UserServiceImpl;
import java.util.Set;

public class UserServiceTest {
    private User testUser;
    private UserRepository mockedUserRepository;
    private UserService userService;
    private ModelMapper mockedModelMapper;
    private PasswordEncoder mockedPasswordEncoder;


    @Before
    public void init() {
        this.testUser = new User() {{
            setId("TEST_ID");
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHat");
            setAge(25);
            setAuthorities(Set.of(Role.PLAYER));
            setTown(Town.SOFIA);
        }};
        this.mockedUserRepository = Mockito.mock(UserRepository.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.mockedPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        this.
        userService = new UserServiceImpl(this.mockedUserRepository, this.mockedModelMapper, this.mockedPasswordEncoder);
    }


    @Test
    public void userService_registerUser_ShouldReturnCorrect() {
        User testUser = new User() {{
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHat2");
            setAge(25);
            setAuthorities(Set.of(Role.PLAYER));
            setTown(Town.SOFIA);
        }};
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("James", "Hatfield", "papaHat2", "123", "123", Role.PLAYER.name(), Town.SOFIA.name());
        UserServiceDTO userServiceDTO = new UserServiceDTO() {{
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHat2");
            setAge(25);
            setTown(Town.SOFIA);
            setAuthorities(Set.of(Role.PLAYER));
        }};
        Mockito.when(this.mockedUserRepository
                .saveAndFlush(testUser))
                .thenReturn(testUser);
        Mockito.when(this.mockedModelMapper.map(userRegisterDTO, User.class)).thenReturn(testUser);
        Mockito.when(this.mockedModelMapper.map(testUser, UserServiceDTO.class))
                .thenReturn(userServiceDTO);
        Mockito.when(this.mockedPasswordEncoder.encode("123"))
                .thenReturn("123");

        UserServiceDTO actual = userService.registerUser(userRegisterDTO);
        Assert.assertEquals(testUser.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Wrong last name", testUser.getLastName(), actual.getLastName());
        Assert.assertEquals("Wrong username", testUser.getUsername(), actual.getUsername());
        Assert.assertEquals("Wrong age", testUser.getAge(), actual.getAge());
        Assert.assertEquals("Wrong town", testUser.getTown(), actual.getTown());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userService_registerUser_ShouldThrowException() {
        Mockito.when(this.mockedUserRepository
                .findByUsername("papaHat"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        User expected = this.testUser;
        UserServiceDTO actual = userService.registerUser(new UserRegisterDTO("James", "Hatfield", "papaHat", "123", "123", Role.PLAYER.name(), Town.SOFIA.name()));
        Assert.assertEquals("Not working...", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Not working...", expected.getLastName(), actual.getLastName());
        Assert.assertEquals("Not working...", expected.getUsername(), actual.getUsername());
    }

    @Test
    public void userService_getUserByUsername_ShouldReturn_Correct() {
        Mockito.when(this.mockedUserRepository
                .findByUsername("papaHat"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        User expected = this.testUser;
        User actual = userService.getUserByUsername("papaHat");
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void userService_getUserDetailsByUsername_ShouldReturn_Correct() {
        Mockito.when(this.mockedUserRepository
                .findByUsername("papaHat"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        Mockito.when(this.mockedModelMapper.map(testUser, UserMyDetailsServiceDTO.class)).thenReturn(
                new UserMyDetailsServiceDTO(){{
                    setFirstName("James");
                    setLastName("Hatfield");
                    setUsername("papaHat");
                }}
        );
        User expected = this.testUser;
        UserMyDetailsServiceDTO actual = userService.getUserDetailsByUsername("papaHat");
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void userService_getUserPublicDetailsById_ShouldReturn_Correct() {
        Mockito.when(this.mockedUserRepository
                .findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        Mockito.when(this.mockedModelMapper.map(testUser, UserPublicDetailsServiceDTO.class)).thenReturn(
                new UserPublicDetailsServiceDTO(){{
                    setFirstName("James");
                    setLastName("Hatfield");
                    setUsername("papaHat");
                }}
        );
        User expected = this.testUser;
        UserPublicDetailsServiceDTO actual = userService.getUserPublicDetailsById("TEST_ID");
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void userService_getLastRegisteredUser_ShouldReturn_Correct() {
        Mockito.when(this.mockedUserRepository
                .findLastRegistered())
                .thenReturn(this.testUser);
        Mockito.when(this.mockedModelMapper.map(testUser, UserAdminServiceDTO.class)).thenReturn(
                new UserAdminServiceDTO(){{
                    setFirstName("James");
                    setLastName("Hatfield");
                    setUsername("papaHat");
                }}
        );
        User expected = this.testUser;
        UserAdminServiceDTO actual = userService.getLastRegisteredUser();
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    public void userService_changeUserPassword_ShouldReturn_True() {
        Mockito.when(this.mockedUserRepository
                .findByUsername("papaHat"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        Mockito.when(this.mockedUserRepository
                .saveAndFlush(testUser))
                .thenReturn(testUser);
        Mockito.when(this.mockedPasswordEncoder.encode("123")).thenReturn("123");
        boolean actual = userService.changeUserPassword("papaHat", "123");
        Assert.assertTrue(actual);
    }
    @Test
    public void userService_updatePlayer_ShouldReturn_True() {
        Mockito.when(this.mockedUserRepository
                .findByUsername("papaHat"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));
        Mockito.when(this.mockedUserRepository
                .saveAndFlush(testUser))
                .thenReturn(testUser);
        Mockito.when(this.mockedModelMapper.map(testUser, UserServiceDTO.class))
                .thenReturn(new UserServiceDTO(){{
                    setFirstName("Updated");
                    setLastName("Updated");
                    setUsername("papaHat");
                    setAge(25);
                    setTown(Town.PLEVEN);
                }});
        UserServiceDTO actual = userService.updatePlayer(new UserUpdateDTO(){{
            setUsername("papaHat");
            setFirstName("Updated");
            setLastName("Updated");
            setAge(25);
            setTown(Town.PLEVEN.name());
        }});
        Assert.assertEquals("papaHat", actual.getUsername());
        Assert.assertEquals("Updated", actual.getFirstName());
        Assert.assertEquals("Updated", actual.getLastName());
        Assert.assertEquals(25, actual.getAge());
    }
}

