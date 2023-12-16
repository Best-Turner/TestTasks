package ru.effective.mobile.service;

import ru.effective.mobile.model.Comment;

public interface CommentService {

    void save(String text);
    Comment getCommentsForTask(long taskId);


}
