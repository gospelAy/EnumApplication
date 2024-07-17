package Enum.Application.Enum.App.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRegistrationRequest {
    private Long id;
    private String name;
    private String email;
    private String Profession;
    private String about;
    private LocalDateTime dateAdded;
    private LocalDateTime lastActive;
}
