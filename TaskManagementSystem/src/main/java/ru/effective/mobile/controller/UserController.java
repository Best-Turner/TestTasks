package ru.effective.mobile.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return service.findOne(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") User userFromDB,
                                                 @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDB, "id");
        service.saveUser(userFromDB);
    return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody User user) {
        service.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
