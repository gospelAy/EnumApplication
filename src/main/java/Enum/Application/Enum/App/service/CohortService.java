package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CohortRegistrationRequest;
import Enum.Application.Enum.App.dto.request.CohortUpdateRequest;
import Enum.Application.Enum.App.dto.response.CohortRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CohortUpdateResponse;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.InviteLearnerResponse;
import Enum.Application.Enum.App.exceptions.CohortNotFoundException;
import Enum.Application.Enum.App.model.Cohort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.mail.MessagingException;

public interface CohortService {
    CohortRegistrationResponse register(CohortRegistrationRequest cohortRegistrationRequest) throws CohortNotFoundException;
    Cohort getCohort(Long id);

    CohortUpdateResponse updateCohort(Long id, CohortUpdateRequest request);

    String deleteCohort(Long id);

    Page<GetCohortResponse> getAllCohorts(Pageable pageable);
    void addCourseToCohort(Long cohortId, Long courseId);

    InviteLearnerResponse inviteLearnerToCohort(Long cohortId, Long learnerId) throws MessagingException, jakarta.mail.MessagingException, javax.mail.MessagingException;
}
