package Enum.Application.Enum.App.dto.request;

import Enum.Application.Enum.App.model.CourseInformation;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseRegistrationRequest {
    private String courseName;
    private CourseInformation courseInformation;

}
