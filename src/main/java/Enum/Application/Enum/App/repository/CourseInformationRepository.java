package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.CourseInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInformationRepository extends JpaRepository<CourseInformation, Long> {
}
