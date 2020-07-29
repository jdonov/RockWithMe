package rockwithme.app.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.binding.BandSearchBindingDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Views;
import rockwithme.app.model.service.BandSearchServiceDTO;
import rockwithme.app.service.BandService;

import java.util.List;

@RestController
@RequestMapping("/api/bands")
public class BandRestController {

    private final BandService bandService;

    public BandRestController(BandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<BandSearchServiceDTO>> getBands(@RequestBody BandSearchBindingDTO bandSearchBindingDTO) {
        List<BandSearchServiceDTO> bands = this.bandService.searchUsers(bandSearchBindingDTO);
        if (bands.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bands);
    }

//    @PostMapping
//    @JsonView(Views.BandRegisterDTO.class)
//    public ResponseEntity<Band> registerBand(@RequestBody BandRegisterDTO bandRegisterDTO, Authentication authentication) {
//        bandRegisterDTO.setFounder(authentication.getName());
//        Band band = this.bandService.registerBand(bandRegisterDTO);
//        return ResponseEntity.ok(band);
//    }
}
