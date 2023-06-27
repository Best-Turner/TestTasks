package ru.effectiveMobile.socialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.services.RegistrationService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RegistrationService registrationService;

    @Autowired
    public UserController(RegistrationService registrationService) {
        this.registrationService = registrationService;
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
