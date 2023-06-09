package de.phamkv.projectdiary.controller;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.service.PostService;
import de.phamkv.projectdiary.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    private final ProfileService profileService;

    public PostController(PostService postService, ProfileService profileService) {
        this.postService = postService;
        this.profileService = profileService;
    }

    @GetMapping("")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/day")
    public List<Post> getPostsByDay(Authentication authentication, @RequestParam("day") Integer day) {
        String username = authentication.getName();
        Profile profile = profileService.getProfileByUsername(username);
        return postService.getPostsByDayAndProfile(day, profile);
    }

    @PostMapping("")
    public ResponseEntity<Post> addPost(Authentication authentication, @RequestBody Post post) {
        String username = authentication.getName();

        Profile profile = profileService.getProfileByUsername(username);
        System.out.println(username);

        post.setProfile(profile);

        Post newPost = postService.addPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();

        Post post = postService.getPostById(id);

        if (!username.equals(post.getProfile().getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public List<Post> getPostsByProfile(Authentication authentication) {
        String username = authentication.getName();
        Profile profile = profileService.getProfileByUsername(username);

        return postService.getPostsByProfile(profile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        Post updatedPost = postService.updatePost(post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile/username/{username}")
    public List<Post> getPostsByUsername(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }
}

