package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
