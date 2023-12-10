package ru.effective.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.exception.TaskNotFoundException;
import ru.effective.mobile.exception.UserNotFoundException;
import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Status;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.repository.TaskRepository;

import java.beans.PropertyDescriptor;
import java.util.*;

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
        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }
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
    public void changeTask(Task updateTask, Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String notAssign = "executor";
        if (request.containsKey(notAssign)) {
            request.remove(notAssign);
        }
        Task newTask = objectMapper.convertValue(request, Task.class);
        long ownerId = updateTask.getOwner().getId();
        BeanUtils.copyProperties(newTask, updateTask, getNullPropertyNames(newTask));
        saveTask(updateTask, ownerId);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean changeStatus(Task task, Map<String, String> requestParam) throws InvalidParameterException {
        String statusParam = "status";

        if (!requestParam.containsKey(statusParam)) {
            throw new InvalidParameterException(String.format("Передаваемый параметр %s не найден", statusParam));
        }

        String requestStatus = requestParam.get(statusParam);
        if (requestStatus.equalsIgnoreCase(Status.COMPLETED.name())) {
            task.setStatus(Status.COMPLETED);
            taskRepository.save(task);
            return true;
        }
        return false;

    }

    @Override
    public void  assignExecutor(long taskId,long ownerId, long executorId) throws UserNotFoundException, TaskNotFoundException, InvalidParameterException {
        Optional<Task> taskById = taskRepository.findById(taskId);
        Task taskFromDB = taskById.orElseThrow(() -> new TaskNotFoundException("This task not found"));

        User executorFromDb = userService.findOne(executorId);
        if (executorFromDb == null) {
            throw new UserNotFoundException(String.format("Executor with ID = %s not found", executorId));
        }
        if (taskFromDB.getOwner().getId() != ownerId) {
            throw new InvalidParameterException("You cannot assign an executor");
        }
        taskFromDB.setExecutor(executorFromDb);
        taskRepository.save(taskFromDB);
    }


    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
