package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CommentRequest;
import Enum.Application.Enum.App.dto.response.CommentResponse;
import Enum.Application.Enum.App.exceptions.CommentException;
import Enum.Application.Enum.App.model.Comment;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.CommentRepository;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LearnerRepository learnerRepository;

    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new CommentException("Post not found"));
        Learner learner = learnerRepository.findById(commentRequest.getLearnerId())
                .orElseThrow(() -> new CommentException("Learner not found"));
        Comment comment = Comment.builder()
                .comment(commentRequest.getComment())
                .post(post)
                .learner(learner)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentResponse(savedComment);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        if (comment == null) {
            throw new CommentException("Comment cannot be null");
        }
        Post post = comment.getPost();
        Learner learner = comment.getLearner();
        if (post == null || learner == null) {
            throw new CommentException("Post or Learner cannot be null");
        }
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .postId(post.getId())
                .learnerId(learner.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}


