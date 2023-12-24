package ru.effective.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.exception.TaskNotFoundException;
import ru.effective.mobile.exception.UserNotCreatedException;
import ru.effective.mobile.exception.UserNotFoundException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.TaskService;
import ru.effective.mobile.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService service, TaskService taskService) {
        this.userService = service;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ofNullable(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid User user,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage());
            }
            throw new UserNotCreatedException(errorMessage.toString());
        }
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.findOne(id);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") User userFromDB,
                                                 @RequestBody Map<String, Object> requestParam) {

        userService.changeUser(userFromDB, requestParam);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        boolean exists = userService.deleteUser(id);
        if (!exists) {
            throw new UserNotFoundException("This user not found");
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // получение всех задач данного пользователя
    @GetMapping("/{ownerId}/tasks")
    public ResponseEntity<List<Task>> getAllOwnerTasks(@PathVariable("ownerId") long userId) {
        User fromDb = userService.exists(userId);
        if (fromDb == null) {
            throw new UserNotFoundException("This user not found");
        }
        List<Task> ownerTasks = taskService.getOwnerTasks(fromDb);
        if (ownerTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ownerTasks);

    }

    //получение конкретной задачи данного пользователя
    @GetMapping("/{ownerId}/tasks/{taskId}")
    public ResponseEntity<Task> getOneOwnerTask(@PathVariable("ownerId") long userId,
                                                @PathVariable("taskId") long taskId) {
        User userFromDb = userService.exists(userId);
        if (userFromDb == null) {
            throw new UserNotFoundException("This user not found");
        }
        if (taskService.exists(taskId)) {
            ObjectMapper objectMapper = new ObjectMapper();
            Task task = taskService.getTaskByIdAndAuthorId(taskId, userFromDb);
            if (task == null) {
                throw new TaskNotFoundException("This user does not have this task");
            }
            return ResponseEntity.ok().body(task);
        }
        throw new TaskNotFoundException("Task with ID = " + taskId + " not found");
    }
}



