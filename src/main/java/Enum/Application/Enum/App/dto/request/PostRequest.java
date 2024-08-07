package Enum.Application.Enum.App.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    private String title;
    private String postDetails;
}