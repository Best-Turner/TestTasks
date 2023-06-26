package ru.effectiveMobile.socialMedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.effectiveMobile.socialMedia.model.Message;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.MessageRepo;
import ru.effectiveMobile.socialMedia.services.RegistrationService;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final RegistrationService registrationService;
    private final MessageRepo messageRepo;

    public MainController(RegistrationService registrationService, MessageRepo messageRepo) {
        this.registrationService = registrationService;
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("/hello")
    public String helloPAge(@ModelAttribute("newMessage") Message message,
                            Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "hello";
    }

    @PostMapping("/add")
    public String saveNewMessage(@ModelAttribute("newMessage") Message message) {
        messageRepo.save(message);
        return "redirect:/hello";
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

    @PostMapping("/filter")
    public String filterByTeg(@RequestParam("filter") String filter,
                              Model model,
                              @ModelAttribute("newMessage") Message message) {

        List<Message> byTag = messageRepo.findByTag(filter);

        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        return "/hello";
    }

}
