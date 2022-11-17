package com.brightstraining.springbootblogapplication.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "posts")   //will create table in mysql
public class Post {

    @Id  //this will be primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE)   //will be generated in database automatically
    private Long id;
    @NotEmpty(message="Title cannot be empty")
    @NotNull
    private String title;    //title of the post
    @Column(columnDefinition = "TEXT")
    private String content;  // entire text of the post
    @Column(columnDefinition = "TEXT")
    private String summary;  //short text, intro to the post

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch= FetchType.EAGER)
    private Collection<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "userAccount_id", referencedColumnName="id")
    private UserAccount userAccount;

    public Post(Set<Comment> comments) {
        this.comments = comments;
    }

    public Post() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", title='" + title + "'" + ", content='" + content
                + "'" + ", createdAt='" + createdAt + "'" + ", updatedAt'" + updatedAt + "'" + "}";
    }
}
