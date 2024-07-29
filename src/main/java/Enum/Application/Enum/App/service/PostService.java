package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Post;
import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post getPostById(Long id);
    List<Post> getAllPosts();
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
}
