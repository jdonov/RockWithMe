package rockwithme.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.service.EventService;

import javax.validation.Valid;

@Controller
@RequestMapping("/bands/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/create")
    public String createEvent(@RequestParam("bandId") String bandId, Model model) {
        if (!model.containsAttribute("createEvent")) {
            EventCreateBindingDTO eventCreateBindingDTO = new EventCreateBindingDTO();
            eventCreateBindingDTO.setBandId(bandId);
            model.addAttribute("createEvent", eventCreateBindingDTO);
        }

        return "event-create";
    }

    @PostMapping("/create")
    public ModelAndView createEventConfirm(@Valid @ModelAttribute("createEvent") EventCreateBindingDTO eventCreateBindingDTO,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes,
                                           ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {

        }

        this.eventService.createEvent(eventCreateBindingDTO);
        modelAndView.setViewName("redirect:/bands/myBands");
        return modelAndView;
    }
}
