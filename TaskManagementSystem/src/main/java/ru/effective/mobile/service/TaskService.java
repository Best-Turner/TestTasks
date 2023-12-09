package ru.effective.mobile.service;

import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.List;

public interface TaskService {

    void saveTask(Task task, long userId);

    List<Task> getOwnerTasks(User user);
    List<Task> getAllTasks();

    Task findOne(long taskId, User user);

    void changeTask(Task updateTask, Task newTask);

    void deleteTask(long id);

}
