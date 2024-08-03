package Enum.Application.Enum.App.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CommentResponse {
    private Long id;
    private String comment;
    private Long postId;
    private Long learnerId;
    private LocalDateTime createdAt;
}