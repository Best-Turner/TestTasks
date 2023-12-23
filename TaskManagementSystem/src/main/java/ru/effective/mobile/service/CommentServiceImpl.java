package ru.effective.mobile.service;

import org.springframework.stereotype.Service;
import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Comment comment, User author,  Task task) {
        comment.setOwner(author);
        comment.setTask(task);
        repository.save(comment);
    }

    @Override
    public Comment getCommentsForTask(long taskId) {
        return null;
    }
}
