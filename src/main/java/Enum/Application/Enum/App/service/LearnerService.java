package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.LearnerRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetAllLearnersResponse;
import Enum.Application.Enum.App.dto.response.LearnerResponse;
import Enum.Application.Enum.App.model.Learner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LearnerService {
    LearnerResponse createLearner(LearnerRegistrationRequest request);
    Learner getLearnerById(Long id);
    String updateLearner(Long id, LearnerRegistrationRequest request);
    Page<GetAllLearnersResponse> getAllLearner(Pageable pageable);
    String deleteLearnerById(Long id);
}
