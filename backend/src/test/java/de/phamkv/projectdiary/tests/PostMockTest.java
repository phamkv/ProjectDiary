package de.phamkv.projectdiary.tests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.phamkv.projectdiary.controller.PostController;
import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.service.PostService;
import de.phamkv.projectdiary.service.ProfileService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private ProfileService profileService;

    @Test
    public void getAllPostsFromService() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Title", "Hello World", new Profile("Jane", "20001")));
        posts.add(new Post("Bonjour", "Hola Hallo", new Profile("John", "30303")));
        when(postService.getAllPosts()).thenReturn(posts);
        this.mockMvc.perform(get("/api/posts")).andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"title\": \"Title\", \"content\": \"Hello World\", \"profile\": {\"username\": \"Jane\"}}," +
                        "{\"title\": \"Bonjour\", \"content\": \"Hola Hallo\", \"profile\": {\"username\": \"John\"}}" +
                        "]"));
    }

    @Test
    public void getPostByIdFromService() throws Exception {
        doReturn(new Post("Title", "Hello World", new Profile("Jane", "20001")))
                .when(postService).getPostById(1L);

        this.mockMvc.perform(get("/api/posts/1")).andExpect(status().isOk())
                .andExpect(content().json("{\"title\": \"Title\", \"content\": \"Hello World\", \"profile\": {\"username\": \"Jane\"}}"));
    }
    
    public void addPost() throws Exception {
        Profile profile = new Profile();
        profile.setUsername("Jane");

        Post post = new Post("Title", "Hello World", profile);
        doReturn(post).when(postService).addPost(post);


        // Create a mock Authentication object
        Authentication authentication = mock(Authentication.class);
        // Set the name of the mock authentication object
        when(authentication.getName()).thenReturn("Jane");

        String requestBody = new ObjectMapper().writeValueAsString(post);

        this.mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(content().json("{\"title\": \"Title\", \"content\": \"Hello World\", \"profile\": {\"username\": \"Jane\"}}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePostSuccessfully() throws Exception {
        Profile profile = new Profile();
        profile.setUsername("Jane");
        Post updatedPost = new Post("Heading", "Hello Everyone", profile);
        when(postService.updatePost(updatedPost)).thenReturn(updatedPost);

        String requestBody = new ObjectMapper().writeValueAsString(updatedPost);

        this.mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(content().json("{\"title\": \"Heading\", \"content\": \"Hello Everyone\", \"profile\": {\"username\": \"Jane\"}}"))
                .andExpect(status().isOk());

    }

    @Test
    public void updatePostNotFound() throws Exception {
        Profile profile = new Profile();
        profile.setUsername("Jane");
        Post updatedPost = new Post("Heading", "Hello Everyone", profile);
        when(postService.updatePost(updatedPost)).thenReturn(updatedPost);

        Post otherPost = new Post("Title", "Hello Everyone", profile);

        String requestBody = new ObjectMapper().writeValueAsString(otherPost);

        this.mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

    }

    public void deleteProfileByIdFromService() throws Exception {
        doNothing().when(postService).deletePost(1L);

        this.mockMvc.perform(delete("/api/posts/1")).andExpect(status().isNoContent());
    }
}
