package ru.effective.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Status;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.repository.TaskRepository;
import ru.effective.mobile.repository.UserRepository;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;

        this.userService = userService;
    }

    @Override
    public void createTask(String title, String description, Priority priority, User owner) {
        taskRepository.save(new Task(title, description, Status.PENDING, priority, owner));
    }

    @Override
    public List<Task> getOwnerTasks(long userId) {
        User userById = userService.findOne(userId);
        if (userById == null) {
            return null;
        }
        return userById.getTasks();
    }

    @Override
    public Task findOne(long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public void changeTask(long taskId, Task updatedTask) {
        updatedTask.setId(taskId);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
}
