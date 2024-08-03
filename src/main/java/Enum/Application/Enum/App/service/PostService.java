package Enum.Application.Enum.App.service;


import Enum.Application.Enum.App.dto.request.PostRequest;
import Enum.Application.Enum.App.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);
    PostResponse getPostById(Long id);
    List<PostResponse> getAllPosts();
    void deletePost(Long id);
}
