package rockwithme.app.serviceLayerTests.band;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.model.binding.BandRemoveProducerBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.service.impl.BandServiceImpl;

import java.util.List;
import java.util.Set;

public class BandServiceTest {
    private User testUser;
    private Band testBand;
    private PlayerSkills testPlayerSkills;
    private BandService bandService;
    private ModelMapper mockedModelMapper;
    private BandRepository mockedBandRepository;
    private UserService mockedUserService;
    private InstrumentService mockedInstrumentService;
    private PlayerSkillsService mockedPlayerSkillsService;

    @Before
    public void init() {

        this.mockedBandRepository = Mockito.mock(BandRepository.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.mockedInstrumentService = Mockito.mock(InstrumentService.class);
        this.mockedPlayerSkillsService = Mockito.mock(PlayerSkillsService.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.bandService = new BandServiceImpl(this.mockedUserService, this.mockedInstrumentService, this.mockedBandRepository, this.mockedPlayerSkillsService, this.mockedModelMapper);
        this.testUser = new User() {{
            setId("TEST_ID");
            setFirstName("James");
            setLastName("Hatfield");
            setUsername("papaHat");
            setAge(25);
            setAuthorities(Set.of(Role.PLAYER));
            setTown(Town.SOFIA);
        }};
        this.testPlayerSkills = new PlayerSkills() {{
            setInstrument(new Instrument(InstrumentEnum.GUITAR));
            setPlayer(testUser);
            setLevel(Level.MASTER);
        }};
        this.testBand = new Band() {{
            setName("Metallica");
            setInstruments(List.of(new Instrument(InstrumentEnum.GUITAR), new Instrument(InstrumentEnum.BASS), new Instrument(InstrumentEnum.DRUMS)));
            setGoals(Set.of(Goal.PLAYING_FOR_FUN));
            setStyles(Set.of(Style.TRASH_METAL));
            setMembers(Set.of(testPlayerSkills));
            setTown(Town.SOFIA);
        }};
    }

    @Test
    public void bandService_getAllBands_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findAllByDeletedIsFalse())
                .thenReturn(List.of(this.testBand));
        Mockito.when(this.mockedModelMapper.map(testBand, BandServiceDTO.class))
                .thenReturn(new BandServiceDTO(){{
                    setName("Metallica");
                }});

        List<BandServiceDTO> bands = this.bandService.getAllBands();
        Assert.assertEquals(1, bands.size());
        Assert.assertEquals("Metallica", bands.get(0).getName());
    }

    @Test
    public void getBandDetails_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(this.testBand));
        Mockito.when(this.mockedModelMapper.map(testBand, BandDetailsDTO.class))
                .thenReturn(new BandDetailsDTO(){{
                    setName("Metallica");
                    setInstruments(List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS));
                    setGoals(Set.of(Goal.PLAYING_FOR_FUN));
                    setStyles(Set.of(Style.TRASH_METAL));
                }});
        BandDetailsDTO bandDetailsDTO = this.bandService.getBandDetails("TEST_ID");
        Assert.assertEquals("Metallica", bandDetailsDTO.getName());
        Assert.assertEquals(1, bandDetailsDTO.getGoals().size());
        Assert.assertTrue(bandDetailsDTO.getStyles().contains(Style.TRASH_METAL));
        Assert.assertTrue(bandDetailsDTO.getGoals().contains(Goal.PLAYING_FOR_FUN));
        Assert.assertEquals(1, bandDetailsDTO.getGoals().size());
    }

    @Test
    public void getMyBandDetails_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(this.testBand));
        Mockito.when(this.mockedModelMapper.map(testBand, BandMyBandDetailsDTO.class))
                .thenReturn(new BandMyBandDetailsDTO(){{
                    setName("Metallica");
                    setInstruments(List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS));
                    setGoals(Set.of(Goal.PLAYING_FOR_FUN));
                    setStyles(Set.of(Style.TRASH_METAL));
                }});
        BandMyBandDetailsDTO actual = this.bandService.getMyBandDetails("TEST_ID");
        Assert.assertEquals("Metallica", actual.getName());
        Assert.assertEquals(1, actual.getGoals().size());
        Assert.assertTrue(actual.getStyles().contains(Style.TRASH_METAL));
        Assert.assertTrue(actual.getGoals().contains(Goal.PLAYING_FOR_FUN));
        Assert.assertEquals(1, actual.getGoals().size());
    }

    @Test
    public void getBandById_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(this.testBand));

        Band actual = this.bandService.getBandById("TEST_ID");
        Assert.assertEquals("Metallica", actual.getName());
        Assert.assertEquals(1, actual.getGoals().size());
        Assert.assertTrue(actual.getStyles().contains(Style.TRASH_METAL));
        Assert.assertTrue(actual.getGoals().contains(Goal.PLAYING_FOR_FUN));
        Assert.assertEquals(1, actual.getGoals().size());
        Assert.assertEquals(Town.SOFIA, actual.getTown());
    }

    @Test
    public void findAllToDelete_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findAllToDelete())
                .thenReturn(List.of(testBand));

        List<Band> actual = this.bandService.findAllToDelete();
        Assert.assertEquals("Metallica", actual.get(0).getName());
        Assert.assertEquals(1, actual.size());
    }

    @Test
    public void getBandOfTheWeek_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findMostLikedBand())
                .thenReturn(testBand);

        BandOfTheWeekServiceDTO actual = this.bandService.getBandOfTheWeek();
        Assert.assertEquals("Metallica", actual.getName());
    }

    @Test
    public void getCountOfAllActiveBands_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findAllActive())
                .thenReturn(15);
        int actual = this.bandService.getCountOfAllActiveBands();
        Assert.assertEquals(15, actual);
    }

    @Test
    public void getCountOfAllDeletedBands_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findAllDeleted())
                .thenReturn(15);
        int actual = this.bandService.getCountOfAllDeletedBands();
        Assert.assertEquals(15, actual);
    }

    @Test
    public void getLastRegistered_ShouldReturn_Correct() {
        Mockito.when(this.mockedBandRepository.findLastRegistered())
                .thenReturn(testBand);
        Mockito.when(this.mockedModelMapper.map(testBand, BandAdminServiceDTO.class))
                .thenReturn(new BandAdminServiceDTO(){{
                    setName("Metallica");
                }});
        BandAdminServiceDTO actual = this.bandService.getLastRegistered();
        Assert.assertEquals("Metallica", actual.getName());
    }

    @Test
    public void addProducer_ShouldReturnCorrect() {
        Mockito.when(this.mockedBandRepository.saveAndFlush(testBand))
                .thenReturn(testBand);
        Mockito.when(this.mockedModelMapper.map(testBand, BandServiceDTO.class))
                .thenReturn(new BandServiceDTO(){{
                    setName("Metallica");
                }});
        BandServiceDTO actual = this.bandService.addProducer(this.testUser, this.testBand);
        Assert.assertEquals("Metallica", actual.getName());
        Assert.assertEquals("James", testBand.getProducer().getFirstName());
        Assert.assertEquals("Hatfield", testBand.getProducer().getLastName());
        Assert.assertEquals("papaHat", testBand.getProducer().getUsername());
    }

    @Test
    public void removeProducer_ShouldReturnCorrect() {
        Mockito.when(this.mockedBandRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(testBand));
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat"))
                .thenReturn(this.testUser);

        this.bandService.removeProducer(new BandRemoveProducerBindingDTO(){{
            setProducerUsername("papaHat");
            setBandId("TEST_ID");
        }});
        Assert.assertNull(testBand.getProducer());
    }
}
