package Enum.Application.Enum.App.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LikeResponse {
    private Long id;
    private Long postId;
    private Long learnerId;
}