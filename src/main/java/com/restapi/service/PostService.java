package com.restapi.service;

import com.restapi.dto.PostDTO;
import com.restapi.model.Comment;
import com.restapi.model.Post;
import com.restapi.model.User;
import com.restapi.repository.PostRepository;
import com.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + postDTO.getUserId()));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());

        post.setUser(newUser);
        return postRepository.save(post);
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    Post newPost = new Post();
                    newPost.setId(post.getId());
                    newPost.setTitle(post.getTitle());
                    newPost.setContent(post.getContent());

                    User user = new User();
                    user.setId(post.getUser().getId());
                    user.setEmail(post.getUser().getEmail());

                    newPost.setUser(user);

                    return newPost;
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    Post newPost = new Post();
                    newPost.setId(post.getId());
                    newPost.setTitle(post.getTitle());
                    newPost.setContent(post.getContent());

                    User user = new User();
                    user.setId(post.getUser().getId());
                    user.setEmail(post.getUser().getEmail());

                    newPost.setUser(user);

                    newPost.setComments(post.getComments().stream()
                            .map(comment -> {
                                Comment newComment = new Comment();
                                newComment.setId(comment.getId());
                                newComment.setComment(comment.getComment());
                                return newComment;
                            })
                            .collect(Collectors.toList()));
                    return newPost;
                })
                .collect(Collectors.toList());
    }


}
