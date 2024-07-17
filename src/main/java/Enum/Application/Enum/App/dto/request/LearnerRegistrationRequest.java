package Enum.Application.Enum.App.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearnerRegistrationRequest {
    private String name;
    private String email;

}
