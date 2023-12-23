package ru.effective.mobile.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import ru.effective.mobile.exception.InvalidParameterException;
import ru.effective.mobile.exception.MissingFieldError;
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
        return taskRepository.findTasksByOwner(user);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    @Override
    public Task getTaskByIdAndAuthorId(long taskId, User user) {
        return taskRepository.findTaskByIdAndOwner(taskId, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public void updateTask(Task updateTask, Map<String, Object> request) {
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

    //изменить статус может только исполнитель
    @Override
    public void changeStatus(Task task, long executorId, Map<String, String> requestParam) throws InvalidParameterException {

        if (task.getExecutor().getId() != executorId) {
            throw new IllegalArgumentException("ExecutorId does not match the task's executorId");
        }
        String statusParam = "status";
        if (!requestParam.containsKey(statusParam)) {
            throw new InvalidParameterException(String.format("Passed parameter %s not found", statusParam));
        }

        String inputStatus = requestParam.get(statusParam);
        Status[] array = new Status[]{
                Status.IN_PROGRESS,
                Status.COMPLETED
        };
        Status updatedStatus = Arrays.stream(array).filter(status -> status.name().equalsIgnoreCase(inputStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No arguments found. Possible options: IN_PROGRESS, COMPLETED"));
        task.setStatus(updatedStatus);
        taskRepository.save(task);
    }

    @Override
    public void assignExecutor(long taskId, long ownerId, Map<String, Object> request) throws AccessException {
        String executorKey = "executor";
        Long executorId;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(request, JsonNode.class);

        // проверка - содержит ли входящий параметр необходимый ключ и значение
        if (jsonNode.has(executorKey) && (jsonNode.get(executorKey).isNumber() || jsonNode.get(executorKey).isNull())) {
            executorId = jsonNode.get(executorKey).isNull() ? null : jsonNode.get(executorKey).asLong();
        } else {
            throw new MissingFieldError("Raw field in request");
        }

        Optional<Task> taskById = taskRepository.findById(taskId);
        Task taskFromDB = taskById.orElseThrow(() -> new TaskNotFoundException("This task not found"));

        if (taskFromDB.getOwner().getId() != ownerId) {
            throw new AccessException("You cannot assign an executor");
        }

        if (executorId != null) {
            User executorFromDb = userService.findOne(executorId);
            if (executorFromDb == null) {
                throw new UserNotFoundException("This executor not found");
            }
            taskFromDB.setExecutor(executorFromDb);
        } else {
            taskFromDB.setExecutor(null);
        }
        taskRepository.save(taskFromDB);
    }

    @Override
    public boolean exists(long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public List<Task> getAllExecutorTasks(long executorId) {
        checkExecutorExists(executorId);
        return taskRepository.findTasksByExecutorId(executorId);
    }

    @Override
    public Task getOneExecutorTask(long executorId, long taskId) {
        checkExecutorExists(executorId);
        return taskRepository.findTaskByExecutorIdAndId(executorId, taskId);
    }

    @Override
    public List<User> getExecutorsMyTask(long authorId) {
        return taskRepository.allMyExecutors(authorId);
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

    private void checkExecutorExists(long executorId) {
        boolean exists = taskRepository.existsExecutor(executorId);
        if (!exists) {
            throw new UserNotFoundException("This user has no executable tasks");
        }
    }
}
