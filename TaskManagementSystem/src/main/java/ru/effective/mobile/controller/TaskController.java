package ru.effective.mobile.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.exception.*;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.TaskService;
import ru.effective.mobile.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/{userId}/tasks")

public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@PathVariable("userId") User user) {
        return ResponseEntity.ofNullable(taskService.getOwnerTasks(user));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getOneTask(@PathVariable("taskId") long taskId,
                                           @PathVariable("userId") User user,
                                           @RequestParam(name = "userId", required = false) Long userId,
                                           @RequestParam(name = "executorId", required = false) Long executorId) {

        if (userId != null) {


        }
        if (executorId != null) {
            Task oneExecutorTask = taskService.getOneExecutorTask(executorId, taskId);
            return ResponseEntity.ok(oneExecutorTask);
        }
        Task task = taskService.findOne(taskId, user);
        if (task == null) {
            throw new TaskNotFoundException("This task not found");
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping()
    public ResponseEntity<String> saveTask(@PathVariable("userId") long userId,
                                           @RequestBody @Valid Task task,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError field : fieldErrors) {
                errorMessage.append(field.getField())
                        .append(" - ")
                        .append(field.getDefaultMessage());
            }
            throw new TaskNotCreatedException(errorMessage);
        }
        taskService.saveTask(task, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created!!");
    }

    @PutMapping("/{taskId}")
    public ResponseEntity updateTask(@PathVariable("taskId") Task taskFromDB,
                                     @PathVariable("userId") long ownerId,
                                     @RequestBody Map<String, Object> request) {

        if (taskFromDB == null || taskFromDB.getOwner().getId() != ownerId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        taskService.changeTask(taskFromDB, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable("taskId") Task task,
                                                   @PathVariable("userId") long executorId,
                                                   @RequestBody Map<String, String> requestParam) {

        try {
            taskService.changeStatus(task, executorId, requestParam);
            return ResponseEntity.ok("Status changed!");
        } catch (InvalidParameterException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<String> assignTaskExecutor(@PathVariable("taskId") long taskId,
                                                     @PathVariable("userId") long ownerId,
                                                     @RequestBody Map<String, Object> requestParam) {
        try {
            taskService.assignExecutor(taskId, ownerId, requestParam);
        } catch (AccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Executor is assign");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        return ResponseEntity.ofNullable(allTasks);
    }

}
