package com.brightstraining.springbootblogapplication.repository;

import com.brightstraining.springbootblogapplication.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
