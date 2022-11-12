package com.brightstraining.springbootblogapplication.service;

import com.brightstraining.springbootblogapplication.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    void savePost(Post post);
    Post getPostById(long id);
    void deletePostById(long id);

}
