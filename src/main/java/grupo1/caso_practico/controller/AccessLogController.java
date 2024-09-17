package grupo1.caso_practico.controller;

import grupo1.caso_practico.domain.User;
import grupo1.caso_practico.model.AccessLogDTO;
import grupo1.caso_practico.repos.UserRepository;
import grupo1.caso_practico.service.AccessLogService;
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
@RequestMapping("/accessLogs")
public class AccessLogController {

    private final AccessLogService accessLogService;
    private final UserRepository userRepository;

    public AccessLogController(final AccessLogService accessLogService,
            final UserRepository userRepository) {
        this.accessLogService = accessLogService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getUsername)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("accessLogs", accessLogService.findAll());
        return "accessLog/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("accessLog") final AccessLogDTO accessLogDTO) {
        return "accessLog/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("accessLog") @Valid final AccessLogDTO accessLogDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "accessLog/add";
        }
        accessLogService.create(accessLogDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("accessLog.create.success"));
        return "redirect:/accessLogs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("accessLog", accessLogService.get(id));
        return "accessLog/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("accessLog") @Valid final AccessLogDTO accessLogDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "accessLog/edit";
        }
        accessLogService.update(id, accessLogDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("accessLog.update.success"));
        return "redirect:/accessLogs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        accessLogService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("accessLog.delete.success"));
        return "redirect:/accessLogs";
    }

}
