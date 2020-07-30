package rockwithme.app.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import rockwithme.app.model.binding.BandSearchBindingDTO;
import rockwithme.app.model.service.BandSearchServiceDTO;
import rockwithme.app.service.BandService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bands")
public class BandRestController {

    private final BandService bandService;

    public BandRestController(BandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/search")
    public Page<BandSearchServiceDTO> getBands(@RequestBody BandSearchBindingDTO bandSearchBindingDTO,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) {

        int currentPage = page == null ? 1 : page;
        int pageSize = size == null ? 5 : size;

        return this.bandService.searchBands(bandSearchBindingDTO, PageRequest.of(currentPage-1, pageSize));
    }

//    @PostMapping
//    @JsonView(Views.BandRegisterDTO.class)
//    public ResponseEntity<Band> registerBand(@RequestBody BandRegisterDTO bandRegisterDTO, Authentication authentication) {
//        bandRegisterDTO.setFounder(authentication.getName());
//        Band band = this.bandService.registerBand(bandRegisterDTO);
//        return ResponseEntity.ok(band);
//    }
}
