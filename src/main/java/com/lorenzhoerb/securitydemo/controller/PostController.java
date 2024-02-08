package com.lorenzhoerb.securitydemo.controller;


import com.lorenzhoerb.securitydemo.dto.CommentCreateRequest;
import com.lorenzhoerb.securitydemo.dto.CommentSimple;
import com.lorenzhoerb.securitydemo.dto.PostCreateRequest;
import com.lorenzhoerb.securitydemo.dto.PostDetails;
import com.lorenzhoerb.securitydemo.service.CommentService;
import com.lorenzhoerb.securitydemo.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public List<PostDetails> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDetails getPostById(@PathVariable Long postId) {
        return postService.findById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetails createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest);
    }

    @GetMapping("{postId}/comments")
    public List<CommentSimple> getComments(@PathVariable Long postId) {
        return commentService.getCommentOfPost(postId);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentSimple createPost(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        return commentService.createComment(postId, commentCreateRequest);
    }
}
