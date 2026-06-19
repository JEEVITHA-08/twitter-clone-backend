package com.jeevitha.twitter_clone.service;

import com.jeevitha.twitter_clone.dto.LoginRequest;
import com.jeevitha.twitter_clone.entity.User;
import com.jeevitha.twitter_clone.jwt.JwtUtil;
import com.jeevitha.twitter_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }
    public String uploadProfileImage(
            MultipartFile file) {

        try {

            Authentication authentication =
                    SecurityContextHolder.getContext()
                            .getAuthentication();

            String email = authentication.getName();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            String originalName =
                    file.getOriginalFilename();

            String cleanFileName =
                    originalName
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "_");

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + cleanFileName;
            
            Path directoryPath =
                    Paths.get("uploads/profile");

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path uploadPath =
                    directoryPath.resolve(fileName);

            Files.write(
                    uploadPath,
                    file.getBytes()
            );

            user.setProfileImage(
                    "/uploads/profile/" + fileName
            );

            userRepository.save(user);

            return "Profile image uploaded successfully";

        } catch (IOException e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to upload image: " + e.getMessage()
            );
        }
    }

    public String loginUser(LoginRequest loginRequest) {

        Optional<User> optionalUser =
                userRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found!";
        }

        User user = optionalUser.get();

        boolean passwordMatches =
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            return "Invalid password!";
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public User getMyProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String followUser(Long userId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getId().equals(userToFollow.getId())) {
            throw new RuntimeException("You cannot follow yourself!");
        }

        currentUser.getFollowing().add(userToFollow);

        userRepository.save(currentUser);

        return "User followed successfully!";
    }

    public String unfollowUser(Long userId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        currentUser.getFollowing().remove(userToUnfollow);

        userRepository.save(currentUser);

        return "User unfollowed successfully!";
    }

    public int getFollowersCount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowers().size();
    }

    public int getFollowingCount(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowing().size();
    }

    public Set<User> getFollowers(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowers();
    }

    public Set<User> getFollowing(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFollowing();
    }

    public List<User> searchUsers(String email) {

        return userRepository.findByEmailContainingIgnoreCase(email);
    }
}