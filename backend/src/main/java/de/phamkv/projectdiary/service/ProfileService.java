package de.phamkv.projectdiary.service;

import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUsername(username).orElse(null);
    }

    public Profile addProfile(Profile profile) {
        profile.getRoles().add("ROLE_USER");
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile) {
        Profile existingProfile = profileRepository.findById(profile.getId()).orElse(null);
        if (existingProfile != null) {
            existingProfile.setUsername(profile.getUsername());
            existingProfile.setPassword(profile.getPassword());
            return profileRepository.save(existingProfile);
        }
        return null;
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
