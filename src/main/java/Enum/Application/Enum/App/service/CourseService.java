package Enum.Application.Enum.App.service;


import Enum.Application.Enum.App.dto.request.CourseRegistrationRequest;
import Enum.Application.Enum.App.dto.response.CourseRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CourseUpdateResponse;
import Enum.Application.Enum.App.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseRegistrationResponse register(CourseRegistrationRequest request);
    Course getCourse(Long id);
    CourseUpdateResponse updateCourse(Long id, CourseRegistrationRequest request);
    Page<Course> getAllCourses(Pageable pageable);
    String deleteCourse(Long id);
    void addModuleToCourse(Long courseId, Long moduleId);
    void assignInstructorToCourse(Long courseId, Long instructorId);
}
