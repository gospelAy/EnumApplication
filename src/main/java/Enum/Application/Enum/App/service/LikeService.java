package Enum.Application.Enum.App.service;


import Enum.Application.Enum.App.dto.request.LikeRequest;
import Enum.Application.Enum.App.dto.response.LikeResponse;

public interface LikeService {
    LikeResponse addLike(LikeRequest likeRequest);
}
