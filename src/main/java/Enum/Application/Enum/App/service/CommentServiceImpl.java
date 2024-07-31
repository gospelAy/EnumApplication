package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.model.Comment;
import Enum.Application.Enum.App.model.Post;
import Enum.Application.Enum.App.repository.CommentRepository;
import Enum.Application.Enum.App.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment addCommentToPost(Long postId, Comment comment) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.getComments().add(comment);
            postRepository.save(post);
            return comment;
        }
        return null;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return postRepository.findById(postId)
                .map(Post::getComments)
                .orElse(null);
    }
}
