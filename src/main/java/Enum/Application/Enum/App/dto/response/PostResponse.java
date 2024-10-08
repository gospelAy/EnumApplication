package Enum.Application.Enum.App.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PostResponse {
    private Long id;
    private String title;
    private String postDetails;
    private int commentCount;
    private int likeCount;
}