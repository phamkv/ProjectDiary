package de.phamkv.projectdiary.controller;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.service.PostService;
import de.phamkv.projectdiary.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Posts management API")
public class PostController {

    private final PostService postService;

    private final ProfileService profileService;

    public PostController(PostService postService, ProfileService profileService) {
        this.postService = postService;
        this.profileService = profileService;
    }

    @GetMapping("")
    @Operation(
            summary = "Retrieve every Post",
            description = "Get Post objects. The response is a List of Post objects with id, title, and text.",
            tags = { "posts", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/day")
    @Operation(
            summary = "Retrieve a Post",
            description = "Get Post objects by day. The response is a Post object with id, title, and text.",
            tags = { "posts", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public List<Post> getPostsByDay(Authentication authentication, @RequestParam("day") Integer day) {
        String username = authentication.getName();
        Profile profile = profileService.getProfileByUsername(username);
        return postService.getPostsByDayAndProfile(day, profile);
    }

    @PostMapping("")
    @Operation(
            summary = "Create a Post",
            description = "Create a Post object. The response is a Post object with id, title, and text.",
            tags = { "posts", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Post> addPost(Authentication authentication, @RequestBody Post post) {
        String username = authentication.getName();

        Profile profile = profileService.getProfileByUsername(username);
        System.out.println(username);

        post.setProfile(profile);

        Post newPost = postService.addPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a Post",
            description = "Delete a Post object by id.",
            tags = { "posts", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
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
    @Operation(
            summary = "Retrieve a Post",
            description = "Get a Post object by id. The response is a Post object with id, title, and text.",
            tags = { "posts", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
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

