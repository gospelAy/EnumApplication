package Enum.Application.Enum.App.service;


import Enum.Application.Enum.App.dto.request.LikeRequest;
import Enum.Application.Enum.App.dto.response.LikeResponse;
import Enum.Application.Enum.App.exceptions.LikeException;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Like;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.LikeRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final LearnerRepository learnerRepository;

    @Override
    public LikeResponse addLike(LikeRequest likeRequest) {
        Post post = postRepository.findById(likeRequest.getPostId())
                .orElseThrow(() -> new LikeException("Post not found"));
        Learner learner = learnerRepository.findById(likeRequest.getLearnerId())
                .orElseThrow(() -> new LikeException("Learner not found"));
        Like like = Like.builder()
                .post(post)
                .learner(learner)
                .build();
        Like savedLike = likeRepository.save(like);
        return mapToLikeResponse(savedLike);
    }
    private LikeResponse mapToLikeResponse(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .postId(like.getPost().getId())
                .learnerId(like.getLearner().getId())
                .build();
    }
}

