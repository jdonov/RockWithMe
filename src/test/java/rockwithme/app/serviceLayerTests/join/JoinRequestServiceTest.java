package rockwithme.app.serviceLayerTests.join;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.exeption.NotRequiredSkillsException;
import rockwithme.app.model.binding.JoinRequestBindingDTO;
import rockwithme.app.model.binding.JoinRequestProducerBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.JoinRequestServiceDTO;
import rockwithme.app.model.service.PlayerSkillsServiceDTO;
import rockwithme.app.model.service.UserServiceDTO;
import rockwithme.app.repository.JoinRequestRepository;
import rockwithme.app.service.*;
import rockwithme.app.service.impl.JoinRequestServiceImpl;

import java.util.HashSet;
import java.util.List;

public class JoinRequestServiceTest {
    private JoinRequestService joinRequestService;
    private JoinRequestRepository mockedJoinRequestRepository;
    private UserService mockedUserService;
    private BandService mockedBandService;
    private PlayerSkillsService mockedPlayerSkillsService;
    private InstrumentService mockedInstrumentService;
    private ModelMapper mockedModelMapper;
    private JoinRequest testJoinRequest;
    private Band testBand;
    private User testUser;

    @Before
    public void init() {
        this.mockedJoinRequestRepository = Mockito.mock(JoinRequestRepository.class);
        this.mockedBandService = Mockito.mock(BandService.class);
        this.mockedInstrumentService = Mockito.mock(InstrumentService.class);
        this.mockedPlayerSkillsService = Mockito.mock(PlayerSkillsService.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.joinRequestService = new JoinRequestServiceImpl(this.mockedJoinRequestRepository, this.mockedUserService, this.mockedBandService, this.mockedPlayerSkillsService, this.mockedInstrumentService, this.mockedModelMapper);

        this.testBand = new Band() {{
            setName("Metallica");
            setRequests(new HashSet<>());
        }};
        this.testUser = new User() {{
            setUsername("papaHat");
            setRequests(new HashSet<>());
        }};
        this.testJoinRequest = new JoinRequest() {{
            setBand(testBand);
            setUser(testUser);
            setInstrument(InstrumentEnum.GUITAR);
        }};
    }

    @Test
    public void getRequestByBandId_ShouldReturnCorrect_Number() {
        Mockito.when(this.mockedJoinRequestRepository.findByBand_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByBandId("TEST");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals("Metallica", actual.get(0).getBandName());
    }
    @Test
    public void getRequestByBandId_ShouldReturnCorrect_Name() {
        Mockito.when(this.mockedJoinRequestRepository.findByBand_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByBandId("TEST");
        Assert.assertEquals("Metallica", actual.get(0).getBandName());
    }
    @Test
    public void getRequestByBandId_ShouldReturnCorrect_User() {
        Mockito.when(this.mockedJoinRequestRepository.findByBand_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByBandId("TEST");
        Assert.assertEquals("papaHat", actual.get(0).getUsername());
    }
    @Test
    public void getRequestByUserId_ShouldReturnCorrect_Number() {
        Mockito.when(this.mockedJoinRequestRepository.findByUser_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByUserId("TEST");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals("Metallica", actual.get(0).getBandName());
    }
    @Test
    public void getRequestByUserId_ShouldReturnCorrect_Name() {
        Mockito.when(this.mockedJoinRequestRepository.findByUser_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByUserId("TEST");
        Assert.assertEquals("Metallica", actual.get(0).getBandName());
    }
    @Test
    public void getRequestByUserId_ShouldReturnCorrect_User() {
        Mockito.when(this.mockedJoinRequestRepository.findByUser_Id("TEST"))
                .thenReturn(List.of(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        List<JoinRequestServiceDTO> actual = this.joinRequestService.getRequestByUserId("TEST");
        Assert.assertEquals("papaHat", actual.get(0).getUsername());
    }

    @Test(expected = NotRequiredSkillsException.class)
    public void submitJoinRequest_ThrowException() {
        JoinRequestBindingDTO joinRequestBindingDTO = new JoinRequestBindingDTO(){{
           setBandId("ID");
           setUsername("papaHat");
        }};
        Mockito.when(this.mockedBandService.getBandById("ID")).thenReturn(testBand);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        this.joinRequestService.submitJoinRequest(joinRequestBindingDTO);
    }

    @Test
    public void submitJoinRequest_SetCorrectRequest() {
        JoinRequestBindingDTO joinRequestBindingDTO = new JoinRequestBindingDTO(){{
            setBandId("ID");
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }};
        testUser.setId("TEST_ID");
        PlayerSkillsServiceDTO playerSkillsServiceDTO = new PlayerSkillsServiceDTO(){{
            setLevel(Level.MASTER);
            setInstrument(InstrumentEnum.GUITAR);
            setYearsPlaying(3);
            setBandPlayed(2);
        }};
        Mockito.when(this.mockedModelMapper.map(joinRequestBindingDTO, JoinRequest.class)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedBandService.getBandById("ID")).thenReturn(testBand);
        Mockito.when(this.mockedBandService.addRequest(testBand,testJoinRequest)).thenReturn(true);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedUserService.addRequest(testUser, testJoinRequest)).thenReturn(new UserServiceDTO());
        Mockito.when(this.mockedPlayerSkillsService.getByPlayerId("TEST_ID")).thenReturn(List.of(playerSkillsServiceDTO));
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        this.joinRequestService.submitJoinRequest(joinRequestBindingDTO);

        Assert.assertEquals(testJoinRequest.getBand(), testBand);
        Assert.assertEquals(testJoinRequest.getUser(), testUser);
        Assert.assertEquals(testJoinRequest.getInstrument(), InstrumentEnum.GUITAR);
    }

    @Test(expected = NotRequiredSkillsException.class)
    public void submitJoinRequest_ThrowExc() {
        JoinRequestBindingDTO joinRequestBindingDTO = new JoinRequestBindingDTO(){{
            setBandId("ID");
            setUsername("papaHat");
            setInstrument(InstrumentEnum.BASS);
        }};
        testUser.setId("TEST_ID");
        PlayerSkillsServiceDTO playerSkillsServiceDTO = new PlayerSkillsServiceDTO(){{
            setLevel(Level.MASTER);
            setInstrument(InstrumentEnum.GUITAR);
            setYearsPlaying(3);
            setBandPlayed(2);
        }};
        Mockito.when(this.mockedModelMapper.map(joinRequestBindingDTO, JoinRequest.class)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedBandService.getBandById("ID")).thenReturn(testBand);
        Mockito.when(this.mockedBandService.addRequest(testBand,testJoinRequest)).thenReturn(true);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedUserService.addRequest(testUser, testJoinRequest)).thenReturn(new UserServiceDTO());
        Mockito.when(this.mockedPlayerSkillsService.getByPlayerId("TEST_ID")).thenReturn(List.of(playerSkillsServiceDTO));
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        this.joinRequestService.submitJoinRequest(joinRequestBindingDTO);

        Assert.assertEquals(testJoinRequest.getBand(), testBand);
        Assert.assertEquals(testJoinRequest.getUser(), testUser);
        Assert.assertEquals(testJoinRequest.getInstrument(), InstrumentEnum.GUITAR);
    }

    @Test
    public void submitJoinRequestProducer_SetCorrectRequest() {
        JoinRequestProducerBindingDTO joinRequestProducerBindingDTO = new JoinRequestProducerBindingDTO(){{
            setBandId("ID");
            setUsername("papaHat");
        }};
        testUser.setId("TEST_ID");

        Mockito.when(this.mockedModelMapper.map(joinRequestProducerBindingDTO, JoinRequest.class)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedBandService.getBandById("ID")).thenReturn(testBand);
        Mockito.when(this.mockedBandService.addRequest(testBand,testJoinRequest)).thenReturn(true);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedUserService.addRequest(testUser, testJoinRequest)).thenReturn(new UserServiceDTO());
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        this.joinRequestService.submitJoinRequestProducer(joinRequestProducerBindingDTO);

        Assert.assertEquals(testJoinRequest.getBand(), testBand);
        Assert.assertEquals(testJoinRequest.getUser(), testUser);
        Assert.assertEquals(testJoinRequest.getInstrument(), InstrumentEnum.GUITAR);
    }

    @Test
    public void approveRequest_ReturnCorrect() {
        JoinRequestBindingDTO joinRequestBindingDTO = new JoinRequestBindingDTO(){{
            setBandId("ID");
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }};
        testUser.setId("TEST_ID");
        PlayerSkillsServiceDTO playerSkillsServiceDTO = new PlayerSkillsServiceDTO(){{
            setLevel(Level.MASTER);
            setInstrument(InstrumentEnum.GUITAR);
            setYearsPlaying(3);
            setBandPlayed(2);
        }};
        testBand.setId("TEST_ID");
        testJoinRequest.setBecomeProducer(false);

        Instrument testInstr = new Instrument(){{
            setInstrument(InstrumentEnum.GUITAR);
            setId("TEST_ID");
        }};
        PlayerSkills playerSkills = new PlayerSkills(){{
            setPlayer(testUser);
            setInstrument(testInstr);
            setYearsPlaying(3);
            setBandPlayed(4);
            setLevel(Level.MASTER);
        }};


        Mockito.when(this.mockedJoinRequestRepository.findById("ID")).thenReturn(java.util.Optional.ofNullable(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(joinRequestBindingDTO, JoinRequest.class)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setInstrument(InstrumentEnum.GUITAR);
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        Mockito.when(this.mockedJoinRequestRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testJoinRequest));
        Mockito.when(this.mockedBandService.getBandById("TEST_ID")).thenReturn(testBand);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedInstrumentService.getInstrument(testJoinRequest.getInstrument())).thenReturn(testInstr);
        Mockito.when(this.mockedPlayerSkillsService.getByPlayerIdAndInstrumentId("TEST_ID", "TEST_ID")).thenReturn(playerSkills);
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedBandService.addMember(testBand, playerSkills)).thenReturn(true);
        Mockito.when(this.mockedUserService.addBand(testUser, testBand)).thenReturn(new UserServiceDTO());

        JoinRequestServiceDTO joinRequestServiceDTO = this.joinRequestService.approveRequest("TEST_ID");
        Assert.assertTrue(testJoinRequest.isApproved());
    }

    @Test
    public void approveRequest_Producer_ReturnCorrect() {
        JoinRequestBindingDTO joinRequestBindingDTO = new JoinRequestBindingDTO(){{
            setBandId("ID");
            setUsername("papaHat");
            setInstrument(InstrumentEnum.GUITAR);
        }};
        testUser.setId("TEST_ID");
        PlayerSkillsServiceDTO playerSkillsServiceDTO = new PlayerSkillsServiceDTO(){{
            setLevel(Level.MASTER);
            setInstrument(InstrumentEnum.GUITAR);
            setYearsPlaying(3);
            setBandPlayed(2);
        }};
        testBand.setId("TEST_ID");
        testJoinRequest.setBecomeProducer(true);

        Instrument testInstr = new Instrument(){{
            setInstrument(InstrumentEnum.GUITAR);
            setId("TEST_ID");
        }};
        PlayerSkills playerSkills = new PlayerSkills(){{
            setPlayer(testUser);
            setInstrument(testInstr);
            setYearsPlaying(3);
            setBandPlayed(4);
            setLevel(Level.MASTER);
        }};


        Mockito.when(this.mockedJoinRequestRepository.findById("ID")).thenReturn(java.util.Optional.ofNullable(testJoinRequest));
        Mockito.when(this.mockedModelMapper.map(joinRequestBindingDTO, JoinRequest.class)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO(){{
                    setInstrument(InstrumentEnum.GUITAR);
                    setUsername("papaHat");
                    setBandName("Metallica");
                }});
        Mockito.when(this.mockedJoinRequestRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testJoinRequest));
        Mockito.when(this.mockedBandService.getBandById("TEST_ID")).thenReturn(testBand);
        Mockito.when(this.mockedUserService.getUserByUsername("papaHat")).thenReturn(testUser);
        Mockito.when(this.mockedInstrumentService.getInstrument(testJoinRequest.getInstrument())).thenReturn(testInstr);
        Mockito.when(this.mockedPlayerSkillsService.getByPlayerIdAndInstrumentId("TEST_ID", "TEST_ID")).thenReturn(playerSkills);
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedBandService.addMember(testBand, playerSkills)).thenReturn(true);
        Mockito.when(this.mockedUserService.addBand(testUser, testBand)).thenReturn(new UserServiceDTO());

        JoinRequestServiceDTO joinRequestServiceDTO = this.joinRequestService.approveRequest("TEST_ID");
        Assert.assertTrue(testJoinRequest.isApproved());
    }

    @Test
    public void rejectRequest_ReturnCorrect() {
        testJoinRequest.setBecomeProducer(true);

        Mockito.when(this.mockedJoinRequestRepository.findById("TEST_ID")).thenReturn(java.util.Optional.ofNullable(testJoinRequest));
        Mockito.when(this.mockedJoinRequestRepository.saveAndFlush(testJoinRequest)).thenReturn(testJoinRequest);
        Mockito.when(this.mockedModelMapper.map(testJoinRequest, JoinRequestServiceDTO.class))
                .thenReturn(new JoinRequestServiceDTO());

        JoinRequestServiceDTO joinRequestServiceDTO = this.joinRequestService.rejectRequest("TEST_ID");
        Assert.assertFalse(testJoinRequest.isApproved());
        Assert.assertTrue(testJoinRequest.isClosed());
    }
}
