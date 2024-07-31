package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;
    private Post post;
    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setPostDetails("This is a test post.");
    }

    @Test
    void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        Post createdPost = postService.createPost(post);
        assertNotNull(createdPost);
        assertEquals(post.getId(), createdPost.getId());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = Collections.singletonList(post);
        when(postRepository.findAll()).thenReturn(posts);
        List<Post> result = postService.getAllPosts();
        assertEquals(1, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testGetPostById() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Optional<Post> result = postService.getPostById(post.getId());
        assertTrue(result.isPresent());
        assertEquals(post.getId(), result.get().getId());
        verify(postRepository, times(1)).findById(post.getId());
    }

    @Test
    void testDeletePost() {
        doNothing().when(postRepository).deleteById(post.getId());
        postService.deletePost(post.getId());
        verify(postRepository, times(1)).deleteById(post.getId());
    }
}
