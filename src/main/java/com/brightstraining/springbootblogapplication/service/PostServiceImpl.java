package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Post;
import com.brightstraining.springbootblogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
    }

    @Override
    public void savePost(Post post) {
       if (post.getId() == null) {      //if post does not exist, have no id value
            post.setCreatedAt(LocalDateTime.now());   //give it now time
       }
        post.setUpdatedAt(LocalDateTime.now());   //give it update time if exists
        this.postRepository.save(post);
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;

        if (optional.isPresent()) {
            post = optional.get();
        }
        return post;
    }

    @Override
    public void deletePostById(long id) {
        boolean exists = this.postRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Post with id " + id + " was not found.");
        }
        this.postRepository.deleteById(id);

    }
}
