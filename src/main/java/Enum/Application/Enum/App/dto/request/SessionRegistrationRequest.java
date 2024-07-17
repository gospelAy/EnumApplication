package Enum.Application.Enum.App.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRegistrationRequest {
    private String name;
    private String description;
    private String videoReference;
    private String fileReference;
}
