package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Like;
import java.util.List;

public interface LikeService {
    Like addLikeToPost(Long postId, Like like);
    int getLikesCountByPostId(Long postId);
}