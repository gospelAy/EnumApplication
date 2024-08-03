package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.LikeRequest;
import Enum.Application.Enum.App.dto.response.LikeResponse;
import Enum.Application.Enum.App.model.Like;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.LikeRepository;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private LearnerRepository learnerRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLike() {
        LikeRequest likeRequest = LikeRequest.builder()
                .postId(1L)
                .learnerId(1L)
                .build();
        Post post = Post.builder().id(1L).build();
        Learner learner = Learner.builder().id(1L).build();

        Like like = Like.builder()
                .id(1L)
                .post(post)
                .learner(learner)
                .build();

        LikeResponse likeResponse = LikeResponse.builder()
                .id(1L)
                .postId(1L)
                .learnerId(1L)
                .build();

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(learnerRepository.findById(1L)).thenReturn(java.util.Optional.of(learner));
        when(likeRepository.save(any(Like.class))).thenReturn(like);
        LikeResponse response = likeService.addLike(likeRequest);
        assertEquals(likeResponse, response);
    }
}

