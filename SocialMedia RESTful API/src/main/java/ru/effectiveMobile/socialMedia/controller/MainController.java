package ru.effectiveMobile.socialMedia.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.effectiveMobile.socialMedia.model.Message;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.MessageRepo;
import ru.effectiveMobile.socialMedia.services.RegistrationService;
import ru.effectiveMobile.socialMedia.services.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
