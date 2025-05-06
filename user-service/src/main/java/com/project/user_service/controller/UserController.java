package com.project.user_service.controller;

import com.project.user_service.entity.User;
import com.project.user_service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public record UserDetails(int id, String email, String fullName, String picture, String role, String kycStatus, boolean flaggedAsRisk) {
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAuthenticatedUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        Optional<User> userOpt = userService.findUserByEmail(oidcUser.getEmail());
        User user = userOpt.get();
        return ResponseEntity.ok(new UserDetails(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPicture(),
                user.getRole(),
                user.getKycStatus(),
                user.isFlaggedAsRisk()
        ));

    }

    @PutMapping("/updateKycStatus/{userId}")
    public ResponseEntity<?> updateKycStatus(
            @PathVariable int userId,
            @RequestParam String status
    ) {
        boolean updated = userService.updateKycStatus(userId, status);
        if (updated) {
            return ResponseEntity.ok("KYC status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.logout();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }
    
    @GetMapping("/all")
    public List<User> getAllUsers(){return userService.findAllUsers();}

    @GetMapping("{id}")
    public User getUserById(@PathVariable int id){
        System.out.println("Fetching user with ID: " + id);
        return userService.findById(id);}

}
