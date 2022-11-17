package com.brightstraining.springbootblogapplication.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity
@Table (name = "comments")
@SequenceGenerator(name = "comment_seq_gen",sequenceName = "comment_seq" , initialValue = 10, allocationSize = 1)

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_gen")
    @Column(name = "id")
    private Long id ;
    @Column(columnDefinition = "TEXT", nullable = false)
    @NotEmpty(message = "Comment content can not be empty!")
    private String content;

    //@Temporal(TemporalType.TIMESTAMP)
    //@CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;


    //@NotNull
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    //@NotNull
    @ManyToOne
    @JoinColumn(name ="user_id", referencedColumnName = "id",nullable = false)
    private UserAccount user;

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public Comment(Long id, String content, LocalDateTime creationDate, Post post, UserAccount user) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.post = post;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content ='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", post_id=" + post.getId() + // can't go for post object it self as it will make a recursion and overflow a stack
                ", username=" + user.getUserName() +
                '}';
    }


}
