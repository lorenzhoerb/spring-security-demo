package com.lorenzhoerb.securitydemo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    private LocalDateTime dateCreated;


    public Comment() {
    }

    public Comment(String text, User author, Post post, LocalDateTime dateCreated) {
        this.text = text;
        this.author = author;
        this.post = post;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Comment setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public Comment setPost(Post post) {
        this.post = post;
        return this;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Comment setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
}
