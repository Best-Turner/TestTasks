package ru.effective.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.dto.TaskDTO;
import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.exception.TaskNotCreatedException;
import ru.effective.mobile.exception.TaskNotFoundException;
import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.CommentService;
import ru.effective.mobile.service.TaskService;
import ru.effective.mobile.service.UserService;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/{userId}/tasks")

public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable("userId") User user,
                                                  @RequestParam(required = false) boolean comment) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Task> ownerTasks = taskService.getOwnerTasks(user);
        List<TaskDTO> collect = ownerTasks.stream()
                .map(task -> objectMapper.convertValue(task, TaskDTO.class))
                .collect(Collectors.toList());

        if (!comment) {
            collect.forEach(taskDTO -> taskDTO.setComments(null));
        }
        return ResponseEntity.ok(collect);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getOneTask(@PathVariable("taskId") long taskId,
                                           @PathVariable("userId") User user) {
        Task task = taskService.getTaskByIdAndAuthorId(taskId, user);

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
    public ResponseEntity<String> updateTask(@PathVariable("taskId") Long taskId,
                                             @PathVariable("userId") User author,
                                             @RequestBody Map<String, Object> request) {
        if (taskId != null) {
            Task taskByIdAndAuthorId = taskService.getTaskByIdAndAuthorId(taskId, author);
            taskService.updateTask(taskByIdAndAuthorId, request);
            return ResponseEntity.ok("Successful!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data! \n" +
                "Please enter the id of the task you want to change");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    // добавить новый комментарий
    @PostMapping("/{taskById}/comment")
    public ResponseEntity<String> addCommentToTask(@PathVariable Optional<Task> taskById,
                                                   @PathVariable User userId,
                                                   @RequestBody @Valid Comment text,
                                                   BindingResult bindingResult) {
        Task task = taskById.orElseThrow(() -> new TaskNotFoundException("This task not found"));
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        commentService.save(text, userId, task);
        return ResponseEntity
                .created(URI.create("api/" + userId.getId().toString() + "/tasks/" + task.getId() + "?comment=true"))
                .body("Successful!!");
    }

    //получение всех моих задач на выполнение
    @GetMapping("/perform")
    public ResponseEntity<List<Task>> getAllMyTasksToComplete(@PathVariable Long userId) {
        List<Task> allExecutorTasks = taskService.getAllExecutorTasks(userId);
        if (allExecutorTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allExecutorTasks);

    }

    // получить конкретную задачу (из списка - на выполнение)
    @GetMapping("/perform/{taskId}")
    public ResponseEntity<Task> getOneMyTaskToComplete(@PathVariable Long userId,
                                                       @PathVariable Long taskId,
                                                       @RequestParam(required = false) Boolean comment) {
        Task oneExecutorTask = taskService.getOneExecutorTask(userId, taskId);
        if (oneExecutorTask == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(oneExecutorTask);
    }

    //изменить статус задачи (только для исполнителей)
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

    //назначить исполнителя для данной задачи
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
