package de.phamkv.projectdiary.repository;

import de.phamkv.projectdiary.domain.Post;
import de.phamkv.projectdiary.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByProfile(Profile user);
}


