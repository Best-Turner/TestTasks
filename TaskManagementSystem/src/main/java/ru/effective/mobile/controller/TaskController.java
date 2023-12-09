package ru.effective.mobile.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Status;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/{userId}/tasks")

public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
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
                                     @RequestBody Map<String, Object> request) {
        if (taskFromDB == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.convertValue(request, Task.class);
        taskService.changeTask(taskFromDB, task);
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
}
