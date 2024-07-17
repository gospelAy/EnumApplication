package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulesRepository extends JpaRepository<CourseModule, Long> {
}
