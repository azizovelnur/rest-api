package com.restapi.service;


import com.restapi.dto.CommentDTO;
import com.restapi.model.Comment;
import com.restapi.model.Post;
import com.restapi.model.User;
import com.restapi.repository.CommentRepository;
import com.restapi.repository.PostRepository;
import com.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setComment(comment.getComment());
                    commentDTO.setUserId(comment.getUser().getId());
                    commentDTO.setPostId(comment.getPost().getId());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentDTO.getUserId()));

        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + commentDTO.getPostId()));

        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);
        return commentDTO;
    }

}
