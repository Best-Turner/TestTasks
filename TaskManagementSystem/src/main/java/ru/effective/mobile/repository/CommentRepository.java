package ru.effective.mobile.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
