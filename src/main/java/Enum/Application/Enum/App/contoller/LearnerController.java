package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.LearnerRegistrationRequest;
import Enum.Application.Enum.App.dto.response.LearnerResponse;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.service.LearnerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learners")
@AllArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;

    @PostMapping("/create")
    public LearnerResponse createLearner(@RequestBody LearnerRegistrationRequest request) {
        return learnerService.createLearner(request);
    }

    @GetMapping("/getLearner/{id}")
    public Learner getLearnerById(@PathVariable Long id) {
        return learnerService.getLearnerById(id);
    }

    @PutMapping("/UpdateLearnerById/{id}")
    public String updateLearner(@PathVariable Long id, @RequestBody LearnerRegistrationRequest request) {
        return learnerService.updateLearner(id, request);
    }

    @GetMapping("/getAllLearner")
    public Page<LearnerResponse> getAllLearner(Pageable pageable) {
        return learnerService.getAllLearner(pageable);
    }

    @DeleteMapping("/deleteLearner/{id}")
    public String deleteLearnerById(@PathVariable Long id) {
        return learnerService.deleteLearnerById(id);
    }
}
