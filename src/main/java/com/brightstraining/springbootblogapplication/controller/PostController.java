package com.brightstraining.springbootblogapplication.controller;

import com.brightstraining.springbootblogapplication.model.Post;
import com.brightstraining.springbootblogapplication.model.UserAccount;
import com.brightstraining.springbootblogapplication.service.PostService;
import com.brightstraining.springbootblogapplication.service.UserAccountService;
import com.brightstraining.springbootblogapplication.service.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserAccountService userAccountService;
    //private UserAccountServiceImpl userAccountServiceImpl;

    //looking at noe post
    @GetMapping("/posts/{id}")   //dynamic url to see single post
    public String getPost(@PathVariable Long id, Model model) {
        //search post by id
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        //if post exists
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            return "post";  //return the post to the front
        }
        return "notfound";  //case if post does not exist
    }

    //connecting posts to accounts
    //hardcoding one userAccount to connect with posts - changed to authUsername
    @GetMapping("/posts/newpost")
    public String createNewPost(Model model, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<UserAccount> optionalUserAccount = userAccountService.findOneByEmail(authUsername);
        if (optionalUserAccount.isPresent()) {
            Post post = new Post();
            post.setUserAccount(optionalUserAccount.get());
            model.addAttribute("post", post);
            return "newPost";
        } else {

            return "notfound";
        }
    }



    //saving new post
    @PostMapping("/posts/newpost")
    public String saveNewPost(@Valid @ModelAttribute Post post,
                              BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "newPost";
        }
        postService.savePost(post);
        return "redirect:/posts/" + post.getId();   //after saving go to that post url
    }


    //retrieve post for update
    @GetMapping("/posts/{id}/updatePost")
    @PreAuthorize("isAuthenticated()")
    public String updatePost (@PathVariable(value="id") Long id, Model model) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "updatePost";
        }
        else {
            return "notfound";
        }
    }


    //updating post
    @PostMapping("/posts/{id}/updatePost")
    @Secured({"ROLE_ADMIN"})
    //@PreAuthorize("isAuthenticated()")
    public String updatePost (@PathVariable(value="id") long id, Post post,
                              BindingResult bindingResult, Model model) {

        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());


            postService.savePost(existingPost);
        }
        return "redirect:/posts/" + post.getId();
    }


    //retrieve post for deletion
    @GetMapping("/posts/{id}/deletePost")

    @Secured({"ROLE_ADMIN"})
    public String deletePost (@PathVariable Long id) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            postService.deletePostById(id);
            return "redirect:/posts";
        } else {
            return "notfound";
        }

    }



}