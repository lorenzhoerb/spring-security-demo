package com.lorenzhoerb.securitydemo.service;

import com.lorenzhoerb.securitydemo.dto.AuthorDetails;
import com.lorenzhoerb.securitydemo.dto.PostCreateRequest;
import com.lorenzhoerb.securitydemo.dto.PostDetails;
import com.lorenzhoerb.securitydemo.exception.NotFoundException;
import com.lorenzhoerb.securitydemo.model.Post;
import com.lorenzhoerb.securitydemo.model.User;
import com.lorenzhoerb.securitydemo.repository.PostRepository;
import com.lorenzhoerb.securitydemo.util.TextUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostDetails findById(Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id '" + id + "' not found."));
        return mapPostDetails(post);
    }

    public List<PostDetails> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapPostDetails)
                .toList();
    }

    public PostDetails createPost(PostCreateRequest postCreateRequest) {
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        Post post = new Post();
        var transformedTitle = TextUtils.toTitleCase(postCreateRequest.title());
        var transformedBody = postCreateRequest.body().trim();

        User author = userService.findUserByEmail(authenticatedUser.getName());


        post.setTitle(transformedTitle);
        post.setBody(transformedBody);
        post.setAuthor(author);

        post = postRepository.save(post);

        return mapPostDetails(post);
    }

    public PostDetails mapPostDetails(Post post) {
        var authorDetails = new AuthorDetails(
                post.getAuthor().getId(),
                post.getAuthor().getFirstName(),
                post.getAuthor().getLastName()
        );

        return new PostDetails(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                authorDetails,
                post.getDateCreated(),
                post.getDateUpdated()
        );
    }
}
