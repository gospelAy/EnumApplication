package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Comment;
import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, Long postId);
    Comment getCommentById(Long id);
    List<Comment> getAllCommentsForPost(Long postId);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);
}
