package com.restapi.controller;


import com.restapi.dto.UserDTO;
import com.restapi.model.User;
import com.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public List<User> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return List.of(createdUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public List<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return List.of(user);
    }
}
