package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.CohortRegistrationRequest;
import Enum.Application.Enum.App.dto.request.CohortUpdateRequest;
import Enum.Application.Enum.App.dto.response.CohortRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CohortUpdateResponse;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.InviteLearnerResponse;
import Enum.Application.Enum.App.exceptions.CohortNotFoundException;
import Enum.Application.Enum.App.model.Cohort;
import Enum.Application.Enum.App.service.CohortService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cohorts")
@AllArgsConstructor
public class CohortController {

    private final CohortService cohortService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<CohortRegistrationResponse> registerCohort(@RequestBody CohortRegistrationRequest request) throws CohortNotFoundException {
        return ResponseEntity.ok(cohortService.register(request));
    }

    @GetMapping("/getCohort/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<Cohort> getCohort(@PathVariable Long id) {
        return ResponseEntity.ok(cohortService.getCohort(id));
    }

    @PutMapping("/updateCohort/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<CohortUpdateResponse> updateCohort(@PathVariable Long id, @RequestBody CohortUpdateRequest request) {
        return ResponseEntity.ok(cohortService.updateCohort(id, request));
    }

    @DeleteMapping("/deleteCohort/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteCohort(@PathVariable Long id) {
        return ResponseEntity.ok(cohortService.deleteCohort(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<Page<GetCohortResponse>> getAllCohorts(Pageable pageable) {
        return ResponseEntity.ok(cohortService.getAllCohorts(pageable));
    }

    @PostMapping("/addCourse/{cohortId}/{courseId}")
    @PreAuthorize("hasRole('client_admin')")
    public void addCourseToCohort(@PathVariable Long cohortId, @PathVariable Long courseId) {
        cohortService.addCourseToCohort(cohortId, courseId);
    }

    @PostMapping("/{cohortId}/invite-learner/{learnerId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<InviteLearnerResponse> inviteLearnerToCohort(@PathVariable Long cohortId, @PathVariable Long learnerId) {
        try {
            InviteLearnerResponse response = cohortService.inviteLearnerToCohort(cohortId, learnerId);
            return ResponseEntity.ok(response);
        } catch (MessagingException | javax.mail.MessagingException e) {
            return ResponseEntity.status(500).body(InviteLearnerResponse.builder()
                    .message("Failed to send invitation email.")
                    .build());
        }
    }
}
