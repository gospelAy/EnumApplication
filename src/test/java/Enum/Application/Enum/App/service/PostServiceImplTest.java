package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.PostRequest;
import Enum.Application.Enum.App.dto.response.PostResponse;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        PostRequest postRequest = PostRequest.builder()
                .title("Test Post")
                .postDetails("This is a test post.")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("Test Post")
                .postDetails("This is a test post.")
                .comments(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();

        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Test Post")
                .postDetails("This is a test post.")
                .commentCount(0)
                .likeCount(0)
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(post);
        PostResponse response = postService.createPost(postRequest);
        assertEquals(postResponse, response);
    }


    @Test
    void testGetPostById() {
        Post post = Post.builder()
                .id(1L)
                .title("Test Post")
                .postDetails("This is a test post.")
                .build();

        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .title("Test Post")
                .postDetails("This is a test post.")
                .commentCount(0)
                .likeCount(0)
                .build();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        PostResponse response = postService.getPostById(1L);
        assertEquals(postResponse, response);
    }

    @Test
    void testGetAllPosts() {
        Post post1 = Post.builder()
                .id(1L)
                .title("Post 1")
                .postDetails("Details of post 1")
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .title("Post 2")
                .postDetails("Details of post 2")
                .build();

        List<Post> posts = List.of(post1, post2);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostResponse> responses = postService.getAllPosts();
        assertEquals(2, responses.size());
        assertEquals("Post 1", responses.get(0).getTitle());
        assertEquals("Post 2", responses.get(1).getTitle());
    }
}


