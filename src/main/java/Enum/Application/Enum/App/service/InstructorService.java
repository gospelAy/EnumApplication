package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.InstructorRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.GetInstructorResponse;
import Enum.Application.Enum.App.dto.response.InstructorRegistrationResponse;
import Enum.Application.Enum.App.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstructorService {
        InstructorRegistrationResponse saveInstructor(InstructorRegistrationRequest request);
        GetInstructorResponse getInstructorById(Long id);
        String updateInstructor(Long id, InstructorRegistrationRequest request);
        Page<GetInstructorResponse> getAllInstructors(Pageable pageable);
        String deleteInstructor(Long id);
    }


