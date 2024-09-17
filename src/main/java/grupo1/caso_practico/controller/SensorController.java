package grupo1.caso_practico.controller;

import grupo1.caso_practico.model.SensorDTO;
import grupo1.caso_practico.model.SensorStatus;
import grupo1.caso_practico.model.SensorType;
import grupo1.caso_practico.service.SensorService;
import grupo1.caso_practico.util.ReferencedWarning;
import grupo1.caso_practico.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(final SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeValues", SensorType.values());
        model.addAttribute("statusValues", SensorStatus.values());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sensors", sensorService.findAll());
        return "sensor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sensor") final SensorDTO sensorDTO) {
        return "sensor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sensor") @Valid final SensorDTO sensorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensor/add";
        }
        sensorService.create(sensorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensor.create.success"));
        return "redirect:/sensors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("sensor", sensorService.get(id));
        return "sensor/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("sensor") @Valid final SensorDTO sensorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sensor/edit";
        }
        sensorService.update(id, sensorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sensor.update.success"));
        return "redirect:/sensors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = sensorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            sensorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sensor.delete.success"));
        }
        return "redirect:/sensors";
    }

}
