package rockwithme.app.serviceLayerTests.playerSkills;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import rockwithme.app.model.binding.PlayerSkillsAddDTO;
import rockwithme.app.model.binding.PlayerSkillsSearchBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.PlayerSkillsSearchDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.repository.PlayerSkillsRepository;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.service.impl.PlayerSkillsServiceImpl;
import rockwithme.app.specification.PlayerSkillsSpecificationBuilder;

import java.util.List;

public class PlayerSkillsTest {
    private PlayerSkillsService playerSkillsService;
    private PlayerSkills testPlayerSkills;
    private User testUser;
    private PlayerSkillsRepository mockedPlayerSkillsRepository;
    private UserService mockedUserService;
    private InstrumentService mockedInstrumentService;
    private ModelMapper mockedModelMapper;

    @Before
    public void init() {
        this.mockedPlayerSkillsRepository = Mockito.mock(PlayerSkillsRepository.class);
        this.mockedInstrumentService = Mockito.mock(InstrumentService.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.playerSkillsService = new PlayerSkillsServiceImpl(this.mockedPlayerSkillsRepository, this.mockedUserService, this.mockedInstrumentService, this.mockedModelMapper);

        this.testUser = new User() {{
            setUsername("papaHat");
        }};
        this.testPlayerSkills = new PlayerSkills(){{
            setLevel(Level.MASTER);
            setInstrument(new Instrument(InstrumentEnum.GUITAR));
            setPlayer(testUser);
            setBandPlayed(3);
            setYearsPlaying(5);
        }};
    }

    @Test
    public void getByPlayerId_ShouldReturn_Correct() {
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(this.testUser);
        Mockito.when(this.mockedPlayerSkillsRepository.findAllByPlayer("TEST_ID"))
                .thenReturn(List.of(testPlayerSkills));
        Mockito.when(this.mockedModelMapper.map(testPlayerSkills, PlayerSkillsServiceDTO.class))
        .thenReturn(new PlayerSkillsServiceDTO(){{
            setBandPlayed(3);
            setYearsPlaying(5);
            setInstrument(InstrumentEnum.GUITAR);
            setLevel(Level.MASTER);
        }});

        List<PlayerSkillsServiceDTO> actual = this.playerSkillsService.getByPlayerId("TEST_ID");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(InstrumentEnum.GUITAR, actual.get(0).getInstrument());
        Assert.assertEquals(5, actual.get(0).getYearsPlaying());
        Assert.assertEquals(3, actual.get(0).getBandPlayed());
        Assert.assertEquals(Level.MASTER, actual.get(0).getLevel());
    }

    @Test
    public void getByPlayerIdAndInstrumentId_ShouldReturnCorrect() {
        Mockito.when(this.mockedPlayerSkillsRepository.findByPlayerIdAndInstrId("TEST_ID", "TEST_INSTR"))
                .thenReturn(this.testPlayerSkills);

        PlayerSkills actual = this.playerSkillsService.getByPlayerIdAndInstrumentId("TEST_ID", "TEST_INSTR");
        Assert.assertEquals(3, actual.getBandPlayed());
        Assert.assertEquals(5, actual.getYearsPlaying());
        Assert.assertEquals(Level.MASTER, actual.getLevel());
        Assert.assertEquals(testUser, actual.getPlayer());
    }

    @Test
    public void getByCompositeId_ShouldReturnCorrect() {
        Mockito.when(this.mockedPlayerSkillsRepository.findById(new PlayerSkillsPK("TEST", "TEST")))
                .thenReturn(java.util.Optional.ofNullable(this.testPlayerSkills));

        PlayerSkills actual = this.playerSkillsService.getByCompositeId("TEST", "TEST");
        Assert.assertEquals(testPlayerSkills, actual);
    }
}
