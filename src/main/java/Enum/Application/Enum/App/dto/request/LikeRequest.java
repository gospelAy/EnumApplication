package Enum.Application.Enum.App.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequest {
    private Long postId;
    private Long learnerId;
}