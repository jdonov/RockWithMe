package rockwithme.app.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rockwithme.app.service.BandService;


@Controller
@RequestMapping("/")
public class HomeController {
    private final BandService bandService;

    public HomeController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!model.containsAttribute("allBands") && !model.containsAttribute("bands")) {
            model.addAttribute("allBands", this.bandService.getAllBands());
        }
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/search")
    public String searchBand() {
        return "search";
    }
}
