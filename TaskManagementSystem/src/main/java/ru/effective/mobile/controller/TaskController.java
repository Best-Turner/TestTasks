package ru.effective.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.exception.TaskNotFoundException;
import ru.effective.mobile.exception.UserNotFoundException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.TaskService;
import ru.effective.mobile.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/tasks")

public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable("taskId") long taskId,
                                        @PathVariable("userId") User user) {
        Task task = taskService.findOne(taskId, user);
        return (task != null) ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> saveTask(@PathVariable("userId") long userId,
                                               @RequestBody Task task) {
        taskService.saveTask(task, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity updateTask(@PathVariable("taskId") Task taskFromDB,
                                     @PathVariable("userid") long ownerId,
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

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();
        return (!allTasks.isEmpty()) ? ResponseEntity.ok(allTasks) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{taskId}/{executorId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable("taskId") Task task,
                                                   @PathVariable("executorId") long executorId,
                                                   @RequestBody Map<String, String> requestParam) {
        if (task.getExecutor().getId() != executorId) {
            return ResponseEntity.notFound().build();
        }
        try {
            boolean isChanged = taskService.changeStatus(task, requestParam);
            if (isChanged) {
                return ResponseEntity.ok("Status changed!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (InvalidParameterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{taskId}/{executorId}")
    public ResponseEntity<String> assignTaskExecutor(@PathVariable("taskId") long  taskId,
                                                     @PathVariable("userId") long ownerId,
                                                     @PathVariable("executorId") long executorId) {
        try {
            taskService.assignExecutor(taskId, ownerId, executorId);
            return ResponseEntity.ok().body("Executor is assign");
        } catch (UserNotFoundException | TaskNotFoundException | InvalidParameterException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
