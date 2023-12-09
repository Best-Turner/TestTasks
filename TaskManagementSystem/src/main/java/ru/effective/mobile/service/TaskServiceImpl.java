package ru.effective.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Status;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.repository.TaskRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    public void saveTask(Task task, long userId) {
        User owner = userService.findOne(userId);
        task.setOwner(owner);
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }
        task.setStatus(Status.PENDING);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getOwnerTasks(User user) {
        //User userById = userService.findOne(userId);
//        if (userById == null) {
//            return null;
//        }
         return taskRepository.findTasksByOwner(user);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findOne(long taskId, User user) {

        return taskRepository.findTaskByIdAndOwner(taskId, user);
    }

    @Override
    public void changeTask(Task updateTask, Task newTask) {

        Long ownerId = updateTask.getOwner().getId();
        BeanUtils.copyProperties(newTask, updateTask, "id");
        saveTask(updateTask, ownerId);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }
}
