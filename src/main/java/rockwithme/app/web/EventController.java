package rockwithme.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rockwithme.app.model.binding.EventCreateBindingDTO;
import rockwithme.app.model.binding.EventUpdateBindingDTO;
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
            redirectAttributes.addFlashAttribute("createEvent", eventCreateBindingDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createEvent", bindingResult);
            modelAndView.setViewName("redirect:/bands/events/create?bandId="+eventCreateBindingDTO.getBandId());
        } else {
            this.eventService.createEvent(eventCreateBindingDTO);
            modelAndView.setViewName("redirect:/bands/myBands/"+eventCreateBindingDTO.getBandId());
        }


        return modelAndView;
    }

    @GetMapping("/update")
    public String updateEvent(@RequestParam("id") String eventId, Model model) {
        if (!model.containsAttribute("updateEvent")) {
            model.addAttribute("updateEvent", this.eventService.getEventToUpdateById(eventId));
        }
        return "event-update";
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateEventConfirm(@PathVariable("id") String eventId,
                                     @Valid @ModelAttribute EventUpdateBindingDTO eventUpdateBindingDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateEvent", eventUpdateBindingDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateEvent", bindingResult);
            modelAndView.setViewName("redirect:/bands/events/update?id="+eventId);
        } else {
            this.eventService.updateEvent(eventId, eventUpdateBindingDTO);
            modelAndView.setViewName("redirect:/bands/myBands/"+eventUpdateBindingDTO.getBandId());
        }

        return modelAndView;
    }

    @PostMapping("/cancel/{id}")
    public String cancelEvent(@PathVariable("id") String eventId) {
        this.eventService.cancelEvent(eventId);
        return "redirect:/bands/myBands";
    }
}
