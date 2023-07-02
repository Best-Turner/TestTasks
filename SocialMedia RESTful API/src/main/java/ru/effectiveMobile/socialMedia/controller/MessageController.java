package ru.effectiveMobile.socialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.effectiveMobile.socialMedia.model.Message;
import ru.effectiveMobile.socialMedia.model.User;
import ru.effectiveMobile.socialMedia.repositories.MessageRepo;
import ru.effectiveMobile.socialMedia.services.UserServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final MessageRepo messageRepo;
    private final UserServiceImpl userRepo;
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MessageController(MessageRepo messageRepo, UserServiceImpl userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String messagePage(@ModelAttribute("newMessage") Message message,
                              Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User userByName = userRepo.getUserByName(name);
        model.addAttribute("messages", userByName.getMessages());
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
    public String saveNewMessage(@ModelAttribute("newMessage") Message message,
                                 @RequestParam("file")MultipartFile file) throws IOException {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User userByName = userRepo.getUserByName(name);

        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String resultFileName = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFileName(resultFileName);
        }
        message.setAuthor(userByName);
        messageRepo.save(message);
        return "redirect:/message";
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable("id") int id) {
        messageRepo.deleteById(id);
        return "redirect:/message";
    }


}
