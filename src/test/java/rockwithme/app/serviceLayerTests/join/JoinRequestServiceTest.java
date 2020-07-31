package rockwithme.app.serviceLayerTests.join;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.JoinRequestServiceDTO;
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
}
