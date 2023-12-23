package ru.effective.mobile.service;

import org.springframework.expression.AccessException;
import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.List;
import java.util.Map;

public interface TaskService {

    void saveTask(Task task, long userId);

    List<Task> getOwnerTasks(User user);
    List<Task> getAllTasks();

    Task getTaskByIdAndAuthorId(long taskId, User user);

    void updateTask(Task updateTask, Map<String, Object> paramForNewTask);

    void deleteTask(long id);

    void changeStatus(Task task, long executorId, Map<String, String> requestParam) throws InvalidParameterException;

    void assignExecutor(long taskId, long ownerId, Map<String, Object> requestParam) throws AccessException;
    boolean exists(long taskId);
    List<Task> getAllExecutorTasks(long executorId);
    Task getOneExecutorTask(long executorId, long taskId);

    List<User> getExecutorsMyTask(long authorId);

}
