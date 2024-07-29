package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Like;
import java.util.List;

public interface LikeService {
    Like addLike(Long postId);
    void removeLike(Long id);
    List<Like> getAllLikesForPost(Long postId);
}
