package com.userManagement.authapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.userManagement.authapi.entities.User;
import com.userManagement.authapi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	
    private final UserRepository userRepository;
    
    @Autowired
    JwtService jwtService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    
    public String emulateUser(String adminEmailId, String userEmailToEmulate) {
        // Check if the requesting user has admin privileges
        User admin = userRepository.findByEmail(adminEmailId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (!admin.getRole().equals("ADMIN")) {
            throw new RuntimeException("Only admins can emulate users");
        }

        // Load the user to be emulated
        User userToEmulate = userRepository.findByEmail(userEmailToEmulate)
                .orElseThrow(() -> new RuntimeException("User to emulate not found"));

        // Generate a token for the emulated user
        return jwtService.generateToken(userToEmulate);
    }
    
}
