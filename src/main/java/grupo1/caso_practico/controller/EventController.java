package grupo1.caso_practico.controller;

import grupo1.caso_practico.domain.Sensor;
import grupo1.caso_practico.model.EventDTO;
import grupo1.caso_practico.model.EventType;
import grupo1.caso_practico.repos.SensorRepository;
import grupo1.caso_practico.service.EventService;
import grupo1.caso_practico.util.CustomCollectors;
import grupo1.caso_practico.util.ReferencedWarning;
import grupo1.caso_practico.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final SensorRepository sensorRepository;

    public EventController(final EventService eventService,
            final SensorRepository sensorRepository) {
        this.eventService = eventService;
        this.sensorRepository = sensorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("eventTypeValues", EventType.values());
        model.addAttribute("sensorValues", sensorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Sensor::getId, Sensor::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("events", eventService.findAll());
        return "event/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("event") final EventDTO eventDTO) {
        return "event/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") @Valid final EventDTO eventDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/add";
        }
        eventService.create(eventDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.create.success"));
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("event", eventService.get(id));
        return "event/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("event") @Valid final EventDTO eventDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/edit";
        }
        eventService.update(id, eventDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.update.success"));
        return "redirect:/events";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = eventService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            eventService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("event.delete.success"));
        }
        return "redirect:/events";
    }

}
