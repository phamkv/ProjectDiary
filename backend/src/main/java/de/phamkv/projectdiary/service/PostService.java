package de.phamkv.projectdiary.service;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.repository.PostRepository;
import de.phamkv.projectdiary.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final ProfileRepository profileRepository;

    public PostService(PostRepository postRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getPostsByProfile(Profile profile) {
        return postRepository.findByProfile(profile);
    }

    public List<Post> getPostsByUsername(String username) {
        Profile profile = profileRepository.findByUsername(username).orElse(null);
        return getPostsByProfile(profile);
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId()).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            return postRepository.save(existingPost);
        }
        return null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

