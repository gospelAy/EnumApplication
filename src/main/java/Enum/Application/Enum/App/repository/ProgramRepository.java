package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
