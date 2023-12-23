package ru.effective.mobile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.CommentService;
import ru.effective.mobile.service.TaskService;
import ru.effective.mobile.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/{userId}/executors")
public class UserTaskController {

    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;



    public UserTaskController(UserService userService, TaskService taskService, CommentService commentService) {
        this.userService = userService;
        this.taskService = taskService;
        this.commentService = commentService;
    }
    // получить всех исполнителей моих задач
    @GetMapping
    public ResponseEntity<List<User>> getExecutorsMyTasks(@PathVariable Long userId) {
        List<User> myExecutors = taskService.getExecutorsMyTask(userId);
        if (myExecutors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(myExecutors);
    }


    // получить задачи у исполнителя
    @GetMapping("/{executorId}")
    public ResponseEntity<List<Task>> getTasksExecutor(@PathVariable Long executorId,
                                                       @RequestParam(name = "taskId", required = false) Optional<Long> taskId) {
        if (taskId.isPresent()) {
            Task oneExecutorTask = taskService.getOneExecutorTask(executorId, taskId.get());
            return (oneExecutorTask == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(Collections.singletonList(oneExecutorTask));
        }
        return ResponseEntity.ofNullable(taskService.getAllExecutorTasks(executorId));
    }
}
