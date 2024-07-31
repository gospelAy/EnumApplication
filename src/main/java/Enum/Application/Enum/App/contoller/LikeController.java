package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.model.Like;
import Enum.Application.Enum.App.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Like> addLikeToPost(@PathVariable Long postId, @RequestBody Like like) {
        Like addedLike = likeService.addLikeToPost(postId, like);
        if (addedLike != null) {
            return ResponseEntity.ok(addedLike);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getLikesCountByPostId(@PathVariable Long postId) {
        int likeCount = likeService.getLikesCountByPostId(postId);
        return ResponseEntity.ok(likeCount);
    }
}
