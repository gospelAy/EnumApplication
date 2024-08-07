package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.CourseRegistrationRequest;
import Enum.Application.Enum.App.dto.response.CourseRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CourseUpdateResponse;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<CourseRegistrationResponse> registerCourse(@RequestBody CourseRegistrationRequest request) {
        CourseRegistrationResponse response = courseService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getCourseById/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/getAllCourse")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<Page<Course>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @PutMapping("/updateCourseById/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<CourseUpdateResponse> updateCourse(@PathVariable Long id, @RequestBody CourseRegistrationRequest request) {
        CourseUpdateResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/DeleteCourseById/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        String response = courseService.deleteCourse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{courseId}/modules/{moduleId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> addModuleToCourse(@PathVariable Long courseId, @PathVariable Long moduleId) {
        courseService.addModuleToCourse(courseId, moduleId);
        return ResponseEntity.ok("Module added to course successfully");
    }

    @PostMapping("/{courseId}/assign-instructor/{instructorId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> assignInstructorToCourse(@PathVariable Long courseId, @PathVariable Long instructorId) {
        courseService.assignInstructorToCourse(courseId, instructorId);
        return ResponseEntity.ok("Instructor successfully assigned to course.");
    }
}
