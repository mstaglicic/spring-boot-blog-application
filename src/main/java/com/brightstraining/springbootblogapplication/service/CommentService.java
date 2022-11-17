package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Comment;
import com.brightstraining.springbootblogapplication.model.Post;

public interface CommentService {

        Comment save(Comment comment);
        void delete(Comment comment);

        Comment getCommentById(Long id);

        void deleteCommentById(long id);
}
