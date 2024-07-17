package Enum.Application.Enum.App.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class SessionRegistrationResponse {
    private Long id;
    private String name;
    private String description;
    private String videoReference;
    private String fileReference;
    private String message;
}
