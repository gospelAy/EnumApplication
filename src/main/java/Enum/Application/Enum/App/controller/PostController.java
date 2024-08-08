package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.PostRequest;
import Enum.Application.Enum.App.dto.response.PostResponse;
import Enum.Application.Enum.App.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/createPost")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getPostById/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.getPostById(id);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/getAllPost")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/deletePost/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
