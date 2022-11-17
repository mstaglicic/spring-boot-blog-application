package com.brightstraining.springbootblogapplication.controller;

import com.brightstraining.springbootblogapplication.model.Comment;
import com.brightstraining.springbootblogapplication.model.Post;
import com.brightstraining.springbootblogapplication.model.UserAccount;
import com.brightstraining.springbootblogapplication.service.CommentService;
import com.brightstraining.springbootblogapplication.service.PostService;
import com.brightstraining.springbootblogapplication.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {

    private PostService postService ;
    private UserAccountService userAccountService ;
    private CommentService commentService ;

    @Autowired
    public CommentController(PostService postService, UserAccountService userAccountService, CommentService commentService) {
        this.postService = postService;
        this.userAccountService = userAccountService ;
        this.commentService = commentService ;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/posts/{id}/comment")
    public String showComment(@PathVariable(value="id") Long id, Model model, Principal principal ) {
        //get username from Principal
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        // get username of current logged in session user
        Optional<UserAccount> optionalUserAccount = this.userAccountService.findOneByEmail(authUsername);

        Optional<Post> postOptional = Optional.ofNullable(this.postService.getPostById(id));

        if(postOptional.isPresent() && optionalUserAccount.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setUser(optionalUserAccount.get());
            model.addAttribute("comment", comment);
            return "commentForm";
        } else {
            return "notfound";
        }
    }

    @PostMapping("/posts/{id}/comment")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    //@PreAuthorize("isAuthenticated()")
    public String saveNewComment (@Valid @ModelAttribute Comment comment,
                                  BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
//        if(bindingResult.hasErrors()) {
//            return "commentForm";
//        } else {
        this.commentService.save(comment);
        sessionStatus.setComplete();
        return "redirect:/posts/" + comment.getPost().getId();
//        }
    }

    @GetMapping("/posts/{id}/deleteComment")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String deleteComment (@PathVariable Long id, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Comment> optionalComment = Optional.ofNullable(commentService.getCommentById(id));
        Optional<UserAccount> optionalUserAccount = this.userAccountService.findOneByEmail(authUsername);

        if (optionalComment.isPresent() && optionalUserAccount.isPresent()) {
            Comment comment = optionalComment.get();
            commentService.deleteCommentById(id);
            return "redirect:/posts/" + comment.getPost().getId();
        } else {
            return "notfound";
        }
    }

//    @GetMapping("/posts/{id}/deleteComment")
//    public String deleteComment (@PathVariable Long id, Principal principal ) {
//        Optional<Comment> optionalComment = Optional.ofNullable(commentService.getCommentById(id));
//        String authUsername = "anonymousUser";
//        if ( principal != null ) {
//            authUsername = principal.getName();;
//        }
//        if (optionalComment.isPresent()) {
//            Comment comment = optionalComment.get();
//            if(authUsername.equals(comment.getUser().getUserName())) {
//                commentService.deleteCommentById(id);
//                return "redirect:/posts/" + comment.getPost().getId();
//            }
//            else {
//                return "notfound";
//            }
//        }
//        return "notfound";
//    }

}