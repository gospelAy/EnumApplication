package Enum.Application.Enum.App.service;


import Enum.Application.Enum.App.dto.request.PostRequest;
import Enum.Application.Enum.App.dto.response.PostResponse;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .postDetails(postRequest.getPostDetails())
                .build();
        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToPostResponse(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .postDetails(post.getPostDetails())
                .commentCount(post.getComments() != null ? post.getComments().size() : 0)
                .likeCount(post.getLikes() != null ? post.getLikes().size() : 0)
                .build();
    }

}
