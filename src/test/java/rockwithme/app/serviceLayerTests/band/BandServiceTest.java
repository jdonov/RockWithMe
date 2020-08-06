package rockwithme.app.serviceLayerTests.band;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.model.binding.BandRemoveMemberBindingDTO;
import rockwithme.app.model.binding.BandRemoveProducerBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;
import rockwithme.app.repository.BandRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.InstrumentService;
import rockwithme.app.service.PlayerSkillsService;
import rockwithme.app.service.UserService;
import rockwithme.app.service.impl.BandServiceImpl;

import java.time.LocalDateTime;
import java.util.HashSet;
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
        JoinRequest joinRequest = new JoinRequest(){{
            setUser(testUser);
            setInstrument(InstrumentEnum.GUITAR);
            setBand(new Band(){{
                setName("Metallica");
            }});
        }};
        testBand.setRequests(Set.of(joinRequest));

        Mockito.when(this.mockedBandRepository.findById("TEST_ID"))
                .thenReturn(java.util.Optional.ofNullable(this.testBand));
        Mockito.when(this.mockedModelMapper.map(testBand, BandMyBandDetailsDTO.class))
                .thenReturn(new BandMyBandDetailsDTO(){{
                    setName("Metallica");
                    setInstruments(List.of(InstrumentEnum.GUITAR, InstrumentEnum.BASS, InstrumentEnum.DRUMS));
                    setGoals(Set.of(Goal.PLAYING_FOR_FUN));
                    setStyles(Set.of(Style.TRASH_METAL));
                }});
        Mockito.when(this.mockedModelMapper.map(joinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setBandName("Metallica");
                    setUsername("papaHat");
                    setInstrument(InstrumentEnum.GUITAR);
                }});
        BandMyBandDetailsDTO actual = this.bandService.getMyBandDetails("TEST_ID");
        Assert.assertEquals("Metallica", actual.getName());
        Assert.assertEquals(1, actual.getGoals().size());
        Assert.assertTrue(actual.getStyles().contains(Style.TRASH_METAL));
        Assert.assertTrue(actual.getGoals().contains(Goal.PLAYING_FOR_FUN));
        Assert.assertEquals(1, actual.getGoals().size());
        Assert.assertEquals(1, actual.getRequests().size());
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

    @Test
    public void getBandByMember_ShouldReturnCorrect() {
        testUser.setBands(Set.of(testBand));
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat"))
                .thenReturn(testUser);
        Mockito.when(this.mockedModelMapper.map(testBand, BandMyAllBandsDTO.class))
                .thenReturn(new BandMyAllBandsDTO(){{
                    setName("Metallica");
                }});
        List<BandMyAllBandsDTO> actual = this.bandService.getBandByMember("papaHat");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals("Metallica", actual.get(0).getName());
    }

    @Test
    public void addRequest_ShouldReturnTrue() {
        JoinRequest joinRequest = new JoinRequest(){{
            setBand(testBand);
            setInstrument(InstrumentEnum.GUITAR);
            setUser(testUser);
        }};

        Assert.assertTrue(this.bandService.addRequest(testBand, joinRequest));
    }

    @Test
    public void addMember_ShouldReturnTrue() {
        testBand.setMembers(new HashSet<>());
        PlayerSkills playerSkills = new PlayerSkills(){{
            setPlayer(testUser);
            setInstrument(new Instrument(){{
                setInstrument(InstrumentEnum.GUITAR);
            }});
            setYearsPlaying(3);
            setBandPlayed(0);
            setLevel(Level.MASTER);
        }};
        Mockito.when(this.mockedBandRepository.saveAndFlush(testBand)).thenReturn(testBand);
        Assert.assertTrue(this.bandService.addMember(testBand,playerSkills));
    }

    @Test
    public void removeMember_ReturnTrue() {
        PlayerSkills playerSkills = new PlayerSkills(){{
            setLevel(Level.MASTER);
            setBandPlayed(3);
            setYearsPlaying(3);
            setInstrument(new Instrument(){{setInstrument(InstrumentEnum.GUITAR);}});
            setPlayer(testUser);
        }};
        testBand.setMembers(new HashSet<>(Set.of(playerSkills)));
        Mockito.when(this.mockedBandRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testBand));
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedInstrumentService.getInstrument(InstrumentEnum.GUITAR)).thenReturn(new Instrument(){{setInstrument(InstrumentEnum.GUITAR);}});
        Mockito.when(this.mockedPlayerSkillsService.getByCompositeId("TEST_ID", "TEST_ID"))
                .thenReturn(playerSkills);
        Assert.assertTrue(this.bandService.removeMember(new BandRemoveMemberBindingDTO(){{
            setBandId("TEST_ID");
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }}));
    }

    @Test
    public void addEvent_ReturnTrue() {
        Event event = new Event(){{
            setEventCategory(EventCategory.LIVE);
            setEventType(EventType.PUBLIC);
            setEventDate(LocalDateTime.now().plusHours(6));
        }};
        Mockito.when(this.mockedBandRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testBand));
        testBand.setEvents(new HashSet<>());
        Assert.assertTrue(this.bandService.addEvent(event, "TEST_ID"));
        Assert.assertTrue(testBand.getEvents().contains(event));

    }

    @Test
    public void addLike_ReturnTrue() {
        Like like = new Like(){{
           setBand(testBand);
           setUser(testUser);
        }};
        Mockito.when(this.mockedBandRepository.saveAndFlush(testBand)).thenReturn(testBand);
        testBand.setLikes(new HashSet<>());
        Assert.assertTrue(this.bandService.addLike(like, testBand));
        Assert.assertTrue(testBand.getLikes().contains(like));

    }

    @Test
    public void addPhoto_ReturnTrue() {
        Mockito.when(this.mockedBandRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testBand));
        Assert.assertTrue(this.bandService.addPhoto("TEST_ID", "img"));
    }

    @Test
    public void addPhoto_ReturnFalse() {
        testBand.setImgUrl("test");
        Mockito.when(this.mockedBandRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testBand));
        Assert.assertFalse(this.bandService.addPhoto("TEST_ID", null));
    }
}
