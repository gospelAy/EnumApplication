package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CommentRequest;
import Enum.Application.Enum.App.dto.response.CommentResponse;
import Enum.Application.Enum.App.model.Comment;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.CommentRepository;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private LearnerRepository learnerRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        Post mockPost = new Post();
        mockPost.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        Learner mockLearner = new Learner();
        mockLearner.setId(1L);
        when(learnerRepository.findById(1L)).thenReturn(Optional.of(mockLearner));

        LocalDateTime now = LocalDateTime.now();
        Comment mockComment = Comment.builder()
                .id(1L)
                .comment("This is a comment")
                .post(mockPost)
                .learner(mockLearner)
                .createdAt(now)
                .build();
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .learnerId(1L)
                .comment("This is a comment")
                .build();
        CommentResponse expectedResponse = CommentResponse.builder()
                .id(1L)
                .postId(1L)
                .learnerId(1L)
                .comment("This is a comment")
                .createdAt(now)
                .build();

        CommentResponse actualResponse = commentService.addComment(commentRequest);
        assertEquals(expectedResponse, actualResponse);
    }

}

