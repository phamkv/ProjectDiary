package de.phamkv.projectdiary.controller;

import de.phamkv.projectdiary.domain.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.phamkv.projectdiary.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        Profile newProfile = profileService.addProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.updateProfile(profile);
        if (updatedProfile != null) {
            return ResponseEntity.ok(updatedProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}

