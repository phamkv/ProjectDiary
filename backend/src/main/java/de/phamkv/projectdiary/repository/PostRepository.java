package de.phamkv.projectdiary.repository;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByProfile(Profile user);
    List<Post> findByDayAndProfile(Integer day, Profile profile);
}


