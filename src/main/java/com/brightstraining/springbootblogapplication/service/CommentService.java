package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Comment;

public interface CommentService {
Comment save(Comment comment);
void delete(Comment comment);


}
