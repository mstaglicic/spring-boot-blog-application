package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Comment;
import com.brightstraining.springbootblogapplication.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {

        comment.setCreationDate(LocalDateTime.now());   //give it update time if exists
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteCommentById(long id) {
        boolean exists = this.commentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Comment with id " + id + " was not found.");
        }
        this.commentRepository.deleteById(id);

    }

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        Comment comment = null;

        if (optional.isPresent()) {
            comment = optional.get();
        }
//        else {
//            throw new RuntimeException("Post not found with that id: " + id);
//        }
        return comment;
    }
}