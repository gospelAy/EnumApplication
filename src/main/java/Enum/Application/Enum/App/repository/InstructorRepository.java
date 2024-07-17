package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
