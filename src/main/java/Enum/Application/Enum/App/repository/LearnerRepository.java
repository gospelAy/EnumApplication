package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Cohort;
import Enum.Application.Enum.App.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Optional<Learner> findByEmail(String email);
}
