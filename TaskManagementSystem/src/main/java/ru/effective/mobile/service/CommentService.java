package ru.effective.mobile.service;

import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;

import java.util.Optional;

public interface CommentService {

    Comment getCommentsForTask(long taskId);


    void save(Comment text, User user, Task task);
}
