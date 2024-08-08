package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.LearnerRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetAllLearnersResponse;
import Enum.Application.Enum.App.dto.response.LearnerResponse;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.service.LearnerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learners")
@AllArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('client_admin')")
    public LearnerResponse createLearner(@RequestBody LearnerRegistrationRequest request) {
        return learnerService.createLearner(request);
    }

    @GetMapping("/getLearner/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public Learner getLearnerById(@PathVariable Long id) {
        return learnerService.getLearnerById(id);
    }

    @PutMapping("/updateLearnerById/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner')")
    public String updateLearner(@PathVariable Long id, @RequestBody LearnerRegistrationRequest request) {
        return learnerService.updateLearner(id, request);
    }

    @GetMapping("/getAllLearner")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor')")
    public Page<GetAllLearnersResponse> getAllLearner(Pageable pageable) {
        return learnerService.getAllLearner(pageable);
    }

    @DeleteMapping("/deleteLearner/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public String deleteLearnerById(@PathVariable Long id) {
        return learnerService.deleteLearnerById(id);
    }
}
