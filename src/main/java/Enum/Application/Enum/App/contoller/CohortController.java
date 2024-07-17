package Enum.Application.Enum.App.contoller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cohorts")
@AllArgsConstructor
public class CohortController {

    private final CohortService cohortService;

    @PostMapping("/register")
    public ResponseEntity<CohortRegistrationResponse> registerCohort(@RequestBody CohortRegistrationRequest request) throws CohortNotFoundException {
        return ResponseEntity.ok(cohortService.register(request));
    }

    @GetMapping("/getCohort/{id}")
    public ResponseEntity<Cohort> getCohort(@PathVariable Long id) {
        return ResponseEntity.ok(cohortService.getCohort(id));
    }

    @PutMapping("/updateCohort/{id}")
    public ResponseEntity<CohortUpdateResponse> updateCohort(@PathVariable Long id, @RequestBody CohortUpdateRequest request) {
        return ResponseEntity.ok(cohortService.updateCohort(id, request));
    }

    @DeleteMapping("/deleteCohort/{id}")
    public ResponseEntity<String> deleteCohort(@PathVariable Long id) {
        return ResponseEntity.ok(cohortService.deleteCohort(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<GetCohortResponse>> getAllCohorts(Pageable pageable) {
        return ResponseEntity.ok(cohortService.getAllCohorts(pageable));
    }

    @PostMapping("/addCourse/{cohortId}/{courseId}")
    public void addCourseToCohort(@PathVariable Long cohortId, @PathVariable Long courseId) {
        cohortService.addCourseToCohort(cohortId, courseId);
    }

    @PostMapping("/{cohortId}/invite-learner/{learnerId}")
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
