package ru.effective.mobile.service;

import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.List;

public interface TaskService {

    void createTask(String title, String description, Priority priority, User ownerId);

    List<Task> getOwnerTasks(long userId);

    Task findOne(long taskId);

    void changeTask(long taskId, Task updatedTask);

    void deleteTask(long id);

}
