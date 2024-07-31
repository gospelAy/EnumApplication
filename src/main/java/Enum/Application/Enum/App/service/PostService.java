package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Post;
import java.util.List;
import java.util.Optional;


public interface PostService {
    List<Post> getAllPosts();
    Post createPost(Post post);
    Optional<Post> getPostById(Long id);
    void deletePost(Long id);
}