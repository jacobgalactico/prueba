package grupo1.caso_practico.controller;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.User;
import grupo1.caso_practico.model.NotificationDTO;
import grupo1.caso_practico.model.NotificationStatus;
import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.UserRepository;
import grupo1.caso_practico.service.NotificationService;
import grupo1.caso_practico.util.CustomCollectors;
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
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public NotificationController(final NotificationService notificationService,
            final UserRepository userRepository, final EventRepository eventRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("notificationStatusValues", NotificationStatus.values());
        model.addAttribute("recipientValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getUsername)));
        model.addAttribute("eventValues", eventRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Event::getId, Event::getDetails)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("notifications", notificationService.findAll());
        return "notification/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("notification") final NotificationDTO notificationDTO) {
        return "notification/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("notification") @Valid final NotificationDTO notificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "notification/add";
        }
        notificationService.create(notificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("notification.create.success"));
        return "redirect:/notifications";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("notification", notificationService.get(id));
        return "notification/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("notification") @Valid final NotificationDTO notificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "notification/edit";
        }
        notificationService.update(id, notificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("notification.update.success"));
        return "redirect:/notifications";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        notificationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("notification.delete.success"));
        return "redirect:/notifications";
    }

}
