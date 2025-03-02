package com.example.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public User createUser(@RequestBody User user) {
        System.out.println("udpamkmc");
        return userService.createUser(user);
    }

    @GetMapping("/get_users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PutMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> requestData) {
        return userService.updateUser(requestData);
    }
}
