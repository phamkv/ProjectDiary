package de.phamkv.projectdiary.repository;

import de.phamkv.projectdiary.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);

    Boolean existsByUsername(String username);
}

