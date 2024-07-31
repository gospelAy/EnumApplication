package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Like;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.LikeRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Like addLikeToPost(Long postId, Like like) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.getLikes().add(like);
            postRepository.save(post);
            return like;
        }
        return null;
    }

    @Override
    public int getLikesCountByPostId(Long postId) {
        return postRepository.findById(postId)
                .map(post -> post.getLikes().size())
                .orElse(0);
    }
}
