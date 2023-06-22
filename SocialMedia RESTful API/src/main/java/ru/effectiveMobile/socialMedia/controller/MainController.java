package ru.effectiveMobile.socialMedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.services.RegistrationService;

@Controller
@RequestMapping("/")
public class MainController {

    private final RegistrationService registrationService;

    public MainController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("/hello")
    public String helloPAge() {
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("newUser") User user) {
        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("newUser") User user) {
        registrationService.register(user);
        return "redirect:/";
    }

}
