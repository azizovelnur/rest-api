package com.restapi.controller;



import com.restapi.dto.PostDTO;
import com.restapi.model.Post;
import com.restapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public Post createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
