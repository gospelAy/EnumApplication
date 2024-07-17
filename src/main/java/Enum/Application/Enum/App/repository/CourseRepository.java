package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String CourseName);
}
