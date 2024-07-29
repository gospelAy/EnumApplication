package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post updatePost(Long id, Post post) {
        Post existingPost = getPostById(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setPostDetails(post.getPostDetails());
        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
