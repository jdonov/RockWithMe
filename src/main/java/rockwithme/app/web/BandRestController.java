package rockwithme.app.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Views;
import rockwithme.app.service.BandService;

@RestController
@RequestMapping("/api/bands")
public class BandRestController {

    private final BandService bandService;

    public BandRestController(BandService bandService) {
        this.bandService = bandService;
    }

//    @PostMapping
//    @JsonView(Views.BandRegisterDTO.class)
//    public ResponseEntity<Band> registerBand(@RequestBody BandRegisterDTO bandRegisterDTO, Authentication authentication) {
//        bandRegisterDTO.setFounder(authentication.getName());
//        Band band = this.bandService.registerBand(bandRegisterDTO);
//        return ResponseEntity.ok(band);
//    }
}
