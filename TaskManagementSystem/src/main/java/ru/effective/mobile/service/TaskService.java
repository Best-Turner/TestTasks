package ru.effective.mobile.service;

import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.exception.MissingFieldError;
import ru.effective.mobile.exception.TaskNotFoundException;
import ru.effective.mobile.exception.UserNotFoundException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.List;
import java.util.Map;

public interface TaskService {

    void saveTask(Task task, long userId);

    List<Task> getOwnerTasks(User user);
    List<Task> getAllTasks();

    Task findOne(long taskId, User user);

    void changeTask(Task updateTask, Map<String, Object> paramForNewTask);

    void deleteTask(long id);

    boolean changeStatus(Task task, long executorId, Map<String, String> requestParam) throws InvalidParameterException;

    void assignExecutor(long taskId, long ownerId, Map<String, Object> requestParam) throws UserNotFoundException, TaskNotFoundException, InvalidParameterException, MissingFieldError;
}
