package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.model.Comment;
import Enum.Application.Enum.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addCommentToPost")
    public ResponseEntity<Comment> addCommentToPost(@PathVariable Long postId, @RequestBody Comment comment) {
        Comment addedComment = commentService.addCommentToPost(postId, comment);
        if (addedComment != null) {
            return ResponseEntity.ok(addedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCommentsByPostId")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
