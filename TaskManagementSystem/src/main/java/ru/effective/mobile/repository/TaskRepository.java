package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByOwner(User user);

    Task findTaskByIdAndOwner(long taskId, User owner);

    List<Task> findTasksByExecutorId(long executorId);

    Task findTaskByExecutorIdAndId(long executorId, long taskId);

    @Query("select count(*)>0 from Task t where t.executor.id = :executorId")
    boolean existsExecutor(@Param(value = "executorId") long executorId);
}
