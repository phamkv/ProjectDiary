package de.phamkv.projectdiary.controller;

import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.security.jwt.JwtUtils;
import de.phamkv.projectdiary.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final ProfileRepository profileRepository;
    private final JwtUtils jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Profile profile) {
        if (profileRepository.findByUsername(profile.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Profile newProfile = new Profile(profile.getUsername(), encoder.encode(profile.getPassword()));
        profileRepository.save(newProfile);

        return ResponseEntity.ok("Signed up successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Profile profile) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), profile.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateJwtToken(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            System.out.println(userDetails.getUsername());

            return ResponseEntity.ok(new JwtAuthenticationResponse(token, userDetails.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        // Get the authenticated user's username
        String username = authentication.getName();

        // Find the user's profile in the repository
        Optional<Profile> optionalProfile = profileRepository.findByUsername(username);

        if (optionalProfile.isEmpty()) {
            // Return a 404 Not Found response if the user's profile is not found
            return ResponseEntity.notFound().build();
        } else {
            // Delete the user's profile from the repository
            Profile profile = optionalProfile.get();
            profileRepository.delete(profile);

            // Return a 204 No Content response to indicate success
            return ResponseEntity.noContent().build();
        }
    }

    // Response to be sent after signing in
    private record JwtAuthenticationResponse(String token, String username) {
    }
}
