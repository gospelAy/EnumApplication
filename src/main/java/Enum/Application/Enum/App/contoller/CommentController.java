package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.CommentRequest;
import Enum.Application.Enum.App.dto.response.CommentResponse;
import Enum.Application.Enum.App.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.addComment(commentRequest);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }
}


