package com.userManagement.authapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userManagement.authapi.entities.User;
import com.userManagement.authapi.services.UserService;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
	
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/emulateUser")
    public ResponseEntity<String> emulateUser(
            @RequestParam String adminId,
            @RequestParam String userIdToEmulate) {
        
        String token = userService.emulateUser(adminId, userIdToEmulate);
        return ResponseEntity.ok(token);
    }
    
}
