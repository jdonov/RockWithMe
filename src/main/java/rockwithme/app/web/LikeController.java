package rockwithme.app.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rockwithme.app.model.binding.LikeBindingDTO;
import rockwithme.app.service.LikeService;

@Controller
@RequestMapping("/bands/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/band/{id}")
    public String likeBand(@PathVariable("id") String bandId, Authentication authentication) {
        this.likeService.likeBand(new LikeBindingDTO(authentication.getName(), bandId));
        return "redirect:/bands/details/" + bandId;
    }

}
