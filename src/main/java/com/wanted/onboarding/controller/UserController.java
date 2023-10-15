package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.entity.User;
import com.wanted.onboarding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(201).body(userRepository.save(user).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Check request body.");
        }
    }
}
