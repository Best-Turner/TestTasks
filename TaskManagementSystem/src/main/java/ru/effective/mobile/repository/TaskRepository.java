package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
