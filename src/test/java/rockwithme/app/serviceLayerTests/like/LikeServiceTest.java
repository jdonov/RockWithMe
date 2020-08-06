package rockwithme.app.serviceLayerTests.like;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Like;
import rockwithme.app.model.entity.User;
import rockwithme.app.repository.LikeRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.LikeService;
import rockwithme.app.service.UserService;
import rockwithme.app.service.impl.LikeServiceImpl;

import java.util.List;
import java.util.Set;

public class LikeServiceTest {

    private LikeService likeService;
    private LikeRepository mockedLikeRepository;
    private BandService mockedBandService;
    private UserService mockedUserService;
    private ModelMapper mockedModelMapper;
    private Like testLike;

    @Before
    public void init() {
        this.mockedLikeRepository = Mockito.mock(LikeRepository.class);
        this.mockedBandService = Mockito.mock(BandService.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.mockedModelMapper = Mockito.mock(ModelMapper.class);
        this.likeService = new LikeServiceImpl(this.mockedLikeRepository,this.mockedUserService, this.mockedBandService, this.mockedModelMapper);
        this.testLike = new Like(){{
            setUser(new User(){{
                setUsername("test");
            }});
            setBand(new Band(){{
                setName("Metallica");
                setId("ID");
            }});
        }};
    }


    @Test
    public void likedBands_ReturnCorrect() {
        Mockito.when(this.mockedLikeRepository.findByUser_Username("test"))
                .thenReturn(Set.of(testLike));

        List<String> actual = this.likeService.likedBands("test");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals("ID", actual.get(0));
    }
}
