package de.phamkv.projectdiary.tests;

import de.phamkv.projectdiary.model.Post;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.repository.PostRepository;
import de.phamkv.projectdiary.repository.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ProfileRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostRepository postRepository;

    private Profile profile;
    private Post post1;
    private Post post2;

    @BeforeEach
    public void setUp() {
        // create a new profile and two posts
        profile = new Profile("testuser", "testpass");
        post1 = new Post("Test Post 1", "This is a test post 1", profile);
        post2 = new Post("Test Post 2", "This is a test post 2", profile);

        // persist the profile and its posts to the database

        // add posts to the profile's posts list
        profile.setPosts(Arrays.asList(post1, post2));

        // persist the profile and its posts to the database
        entityManager.persist(profile);

        entityManager.flush();
    }

    @AfterEach
    public void test() {
    }

    @Test
    public void getPostsFromProfile() {
        List<Post> foundPosts = postRepository.findByProfile(profile);
        List<Post> posts = new ArrayList<Post>(profile.getPosts());
        Assertions.assertEquals(posts, foundPosts);
        Assertions.assertIterableEquals(profile.getPosts(), foundPosts);
    }

    @Test
    public void deletePosts(){
        long id = post1.getId();
        Optional<Post> post = postRepository.findById(id);
        System.out.println(post.get());
        Assertions.assertEquals(post1, post.get());

        postRepository.deleteById(id);

        Optional<Post> deletedPost = postRepository.findById(id);
        Assertions.assertNull(deletedPost.orElse(null));

        postRepository.deleteById(post2.getId());
        Optional<Post> deletedPost2 = postRepository.findById(id);
        Assertions.assertNull(deletedPost2.orElse(null));

        //List<Post> posts = postRepository.findAll();
        //System.out.println(posts);
    }

    @Test
    public void testFindByUsername() {
        // find the profile by its username
        Profile foundProfile = profileRepository.findByUsername("testuser").orElse(null);

        // assert that the profile was found and has the correct properties
        Assertions.assertNotNull(foundProfile);
        Assertions.assertEquals(profile.getId(), foundProfile.getId());
        Assertions.assertEquals(profile.getUsername(), foundProfile.getUsername());
        Assertions.assertEquals(profile.getPassword(), foundProfile.getPassword());

        System.out.println(foundProfile);

        // assert that the profile's posts were also retrieved
        List<Post> foundPosts = foundProfile.getPosts();
        System.out.println(foundPosts);
        Assertions.assertEquals(2, foundPosts.size());
        Assertions.assertEquals(post1.getId(), foundPosts.get(0).getId());
        Assertions.assertEquals(post1.getTitle(), foundPosts.get(0).getTitle());
        Assertions.assertEquals(post1.getContent(), foundPosts.get(0).getContent());
        Assertions.assertEquals(post2.getId(), foundPosts.get(1).getId());
        Assertions.assertEquals(post2.getTitle(), foundPosts.get(1).getTitle());
        Assertions.assertEquals(post2.getContent(), foundPosts.get(1).getContent());


    }
}

