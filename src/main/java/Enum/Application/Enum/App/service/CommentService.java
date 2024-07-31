package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Comment;
import java.util.List;

public interface CommentService {
    Comment addCommentToPost(Long postId, Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
}
