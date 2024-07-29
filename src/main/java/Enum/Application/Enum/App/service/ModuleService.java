package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ModuleRegistrationRequest;
import Enum.Application.Enum.App.dto.request.ModuleUpdateRequest;
import Enum.Application.Enum.App.dto.response.GetModuleResponse;
import Enum.Application.Enum.App.dto.response.ModuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {

    ModuleResponse createModule(ModuleRegistrationRequest request);

    GetModuleResponse getModuleById(Long id);
    String updateModule(Long id, ModuleUpdateRequest request);
    Page<GetModuleResponse> getAllModules(Pageable pageable);
    String deleteModulesById(Long id);

    void addSessionToModule(Long moduleId, Long sessionId);
}
