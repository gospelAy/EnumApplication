package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModulesRepository extends JpaRepository<CourseModule, Long> {
    Optional<CourseModule> findByModules(String modules);
}
