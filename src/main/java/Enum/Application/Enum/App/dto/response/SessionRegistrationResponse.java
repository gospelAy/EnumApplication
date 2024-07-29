package Enum.Application.Enum.App.dto.response;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionRegistrationResponse {
    private Long id;
    private String name;
    private String description;
    private String videoReference;
    private String fileReference;
    private String message;
}
