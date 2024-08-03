package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.LikeRequest;
import Enum.Application.Enum.App.dto.response.LikeResponse;
import Enum.Application.Enum.App.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/addLike")
    public ResponseEntity<LikeResponse> addLike(@RequestBody LikeRequest likeRequest) {
        LikeResponse likeResponse = likeService.addLike(likeRequest);
        return new ResponseEntity<>(likeResponse, HttpStatus.CREATED);
    }
}

