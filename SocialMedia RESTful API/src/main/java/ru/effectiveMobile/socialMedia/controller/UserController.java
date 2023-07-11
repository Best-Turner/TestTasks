package ru.effectiveMobile.socialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.services.RegistrationService;
import ru.effectiveMobile.socialMedia.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RegistrationService registrationService;
    private final UserService userservice;

    @Autowired
    public UserController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userservice = userService;
    }

    @GetMapping
    public String registrationPage(@ModelAttribute("newUser") User user) {
        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("newUser") User user) {
        registrationService.register(user);
        return "redirect:/";
    }

    @GetMapping("/userList")
    public String getUsersList(Model model) {
        model.addAttribute("users", userservice.getAllUsers());
        return "userList";
    }

    @GetMapping("{user}")
    public String editUser(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user) {
    userservice.updateUser(id, user);
    return "redirect:/userList";
    }

}
