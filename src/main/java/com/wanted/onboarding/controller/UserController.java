package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.User;
import com.wanted.onboarding.repository.UserRepository;
import com.wanted.onboarding.Utils.CustomResponseEntity;
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
        if (user.getId() != null)
            return ResponseEntity.badRequest().body("Don't send id value.");
        if (user.getName() == null)
            return ResponseEntity.status(204).body("");
        try {
            User savedUser = userRepository.save(user);

            Long id = savedUser.getId();
            String name = savedUser.getName();
            String json = String.format("{\"id\": %d, \"name\": \"%s\"}", id, name);
            return CustomResponseEntity.jsonResponse(201, json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something's going wrong on server :(");
        }
    }
}
