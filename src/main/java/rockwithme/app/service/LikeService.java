package rockwithme.app.service;

import rockwithme.app.model.binding.LikeBindingDTO;
import rockwithme.app.model.service.LikeServiceDTO;

import java.util.List;

public interface LikeService {
    void likeBand(LikeBindingDTO likeBindingDTO);

    List<String> likedBands(String username);
}
