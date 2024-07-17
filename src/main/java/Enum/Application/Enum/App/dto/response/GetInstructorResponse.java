package Enum.Application.Enum.App.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class GetInstructorResponse {
        private Long id;
        private String name;
        private String Profession;
        private String about;
    }
