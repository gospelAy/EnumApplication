package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Comment;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.CommentRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CommentServiceImpl commentService;
    private Post post;
    private Comment comment;
    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setId(1L);
        comment.setComment("This is a test comment.");

        post = new Post();
        post.setId(1L);
        post.setComments(new ArrayList<>());
    }

    @Test
    void testAddCommentToPost() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Comment addedComment = commentService.addCommentToPost(post.getId(), comment);
        assertNotNull(addedComment);
        assertEquals(comment.getId(), addedComment.getId());
        verify(postRepository, times(1)).findById(post.getId());
        verify(postRepository, times(1)).save(argThat(updatedPost -> updatedPost.getComments().contains(comment)));
    }


    @Test
    void testGetCommentsByPostId() {
        post.getComments().add(comment);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        List<Comment> comments = commentService.getCommentsByPostId(post.getId());
        assertEquals(1, comments.size());
        assertEquals(comment.getId(), comments.get(0).getId());
        verify(postRepository, times(1)).findById(post.getId());
    }
}
