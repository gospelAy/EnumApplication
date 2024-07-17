package Enum.Application.Enum.App.dto.response;


import Enum.Application.Enum.App.model.CourseInformation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCourseResponse {
    private String courseName;
    private CourseInformation courseInformation;
}
