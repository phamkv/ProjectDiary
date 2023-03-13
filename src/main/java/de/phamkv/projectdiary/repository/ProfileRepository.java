package de.phamkv.projectdiary.repository;

import de.phamkv.projectdiary.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);
}

