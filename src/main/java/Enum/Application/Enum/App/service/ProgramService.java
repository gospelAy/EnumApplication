package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetProgramResponse;
import Enum.Application.Enum.App.dto.response.ProgramRegistrationResponse;
import Enum.Application.Enum.App.exceptions.ProgramNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProgramService {
    ProgramRegistrationResponse register(ProgramRegistrationRequest programRegistrationRequest) throws ProgramNotFoundException;
    GetProgramResponse getProgram(Long id);
    String updateProgram(Long id, ProgramRegistrationRequest programRegistrationRequest);
    String deleteProgram(Long id);
    Page<GetProgramResponse> getAllProgram(Pageable pageable);
}
