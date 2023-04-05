package de.phamkv.projectdiary.controller;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post newPost = postService.addPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping("")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        Post updatedPost = postService.updatePost(post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile/username/{username}")
    public List<Post> getPostsByUsername(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{profileId}")
    public List<Post> getPostsByProfile(@PathVariable Long profileId) {
        Profile profile = new Profile();
        profile.setId(profileId);
        return postService.getPostsByProfile(profile);
    }
}

