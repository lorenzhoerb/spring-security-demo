package com.lorenzhoerb.securitydemo.service;

import com.lorenzhoerb.securitydemo.dto.CommentCreateRequest;
import com.lorenzhoerb.securitydemo.dto.CommentSimple;
import com.lorenzhoerb.securitydemo.exception.NotFoundException;
import com.lorenzhoerb.securitydemo.model.Comment;
import com.lorenzhoerb.securitydemo.model.Post;
import com.lorenzhoerb.securitydemo.model.User;
import com.lorenzhoerb.securitydemo.repository.CommentRepository;
import com.lorenzhoerb.securitydemo.repository.PostRepository;
import com.lorenzhoerb.securitydemo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<CommentSimple> getCommentOfPost(Long postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(this::mapSimpleComment)
                .toList();
    }

    public CommentSimple createComment(Long postId, CommentCreateRequest commentCreateRequest) {
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        User author = userRepository.findByEmail(authenticatedUser.getName())
                .orElseThrow(() -> new RuntimeException("This should never happen"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with id '" + postId + "' not found."));

        Comment comment = new Comment();
        comment.setText(commentCreateRequest.text().trim());
        comment.setAuthor(author);
        comment.setPost(post);

        comment = commentRepository.save(comment);

        return mapSimpleComment(comment);
    }

    private CommentSimple mapSimpleComment(Comment comment) {
        return new CommentSimple(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getPost().getId(),
                comment.getText()
        );
    }
}
