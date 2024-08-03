package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CommentRequest;
import Enum.Application.Enum.App.dto.response.CommentResponse;

public interface CommentService {
    CommentResponse addComment(CommentRequest commentRequest);
}
