package com.restapi.service;

import com.restapi.dto.UserDTO;
import com.restapi.model.Post;
import com.restapi.model.User;
import com.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Post> posts = user.getPosts();
            for (Post post : posts) {
                post.setUser(null);
                post.setComments(null);
            }
        }
        return users;
    }


    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<Post> posts = user.getPosts();
        for (Post post : posts) {
            post.setUser(null);
        }
        return user;
    }
}
