package grupo1.caso_practico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Bienvenido a la página de inicio.");
        return "home/index";  // Redirigirá a home/index.html
    }
}
