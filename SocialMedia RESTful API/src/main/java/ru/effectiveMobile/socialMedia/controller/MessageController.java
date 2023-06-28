package ru.effectiveMobile.socialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.effectiveMobile.socialMedia.model.Message;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.MessageRepo;
import ru.effectiveMobile.socialMedia.services.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final MessageRepo messageRepo;
    private final UserServiceImpl userRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo, UserServiceImpl userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String messagePage(@ModelAttribute("newMessage") Message message,
                              Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "message";
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
        return "/message";
    }

    @PostMapping()
    public String saveNewMessage(@ModelAttribute("newMessage") Message message) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User userByName = userRepo.getUserByName(name);
        message.setAuthor(userByName);
        messageRepo.save(message);
        return "redirect:/message";
    }
}
