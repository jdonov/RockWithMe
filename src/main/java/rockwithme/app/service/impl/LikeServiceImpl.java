package rockwithme.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rockwithme.app.model.binding.LikeBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Like;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.LikeServiceDTO;
import rockwithme.app.repository.LikeRepository;
import rockwithme.app.service.BandService;
import rockwithme.app.service.LikeService;
import rockwithme.app.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final BandService bandService;
    private final ModelMapper modelMapper;

    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, BandService bandService, ModelMapper modelMapper) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.bandService = bandService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void likeBand(LikeBindingDTO likeBindingDTO) {
        User user = this.userService.getUserByUsername(likeBindingDTO.getUsername());
        Band band = this.bandService.getBandById(likeBindingDTO.getBandId());
        Like like = new Like(user, band);
        this.likeRepository.saveAndFlush(like);
    }

    @Override
    public List<String> likedBands(String username) {
        //TODO try to implement modelMapper map with PropertyMap
        Set<Like> likes = this.likeRepository.findByUser_Username(username);
        return likes.stream().map(like -> like.getBand().getId()).collect(Collectors.toList());
//        return likes.stream().map(like -> {
//            LikeServiceDTO likeServiceDTO = this.modelMapper.map(like, LikeServiceDTO.class);
//            likeServiceDTO.setBandId(like.getBand().getId());
//            return likeServiceDTO;
//        }).collect(Collectors.toList());
    }
}
