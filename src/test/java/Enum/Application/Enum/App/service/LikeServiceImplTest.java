package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Like;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.LikeRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private LikeServiceImpl likeService;
    private Post post;
    private Like like;
    @BeforeEach
    void setUp() {
        like = new Like();
        like.setId(1L);

        post = new Post();
        post.setId(1L);
        post.setLikes(new ArrayList<>());
    }

    @Test
    void testAddLikeToPost() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Like addedLike = likeService.addLikeToPost(post.getId(), like);
        assertNotNull(addedLike);
        assertEquals(like.getId(), addedLike.getId());
        verify(postRepository, times(1)).findById(post.getId());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testGetLikesCountByPostId() {
        post.getLikes().add(like);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        int likeCount = likeService.getLikesCountByPostId(post.getId());
        assertEquals(1, likeCount);
        verify(postRepository, times(1)).findById(post.getId());
    }
}
