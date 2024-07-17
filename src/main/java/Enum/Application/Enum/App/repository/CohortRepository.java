package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CohortRepository extends JpaRepository<Cohort, Long> {
    Optional<Cohort> findByCohortName(String cohortName);
}
