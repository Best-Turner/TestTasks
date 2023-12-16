package ru.effective.mobile.service;

import org.springframework.stereotype.Service;
import ru.effective.mobile.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {



    @Override
    public void save(String text) {

    }

    @Override
    public Comment getCommentsForTask(long taskId) {
        return null;
    }
}
