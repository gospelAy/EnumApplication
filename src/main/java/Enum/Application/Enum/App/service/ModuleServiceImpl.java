package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ModuleRegistrationRequest;
import Enum.Application.Enum.App.dto.request.ModuleUpdateRequest;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.GetModuleResponse;
import Enum.Application.Enum.App.dto.response.ModuleResponse;
import Enum.Application.Enum.App.exceptions.CohortNotFoundException;
import Enum.Application.Enum.App.exceptions.CourseNotFoundException;
import Enum.Application.Enum.App.exceptions.ModuleNotFoundException;
import Enum.Application.Enum.App.exceptions.SessionNotFoundException;
import Enum.Application.Enum.App.model.Cohort;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.model.CourseModule;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.repository.ModulesRepository;
import Enum.Application.Enum.App.repository.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModulesRepository modulesRepository;
    private final ModelMapper modelMapper;
    private final SessionRepository sessionRepository;


    @Transactional
    @Override
    public ModuleResponse createModule(ModuleRegistrationRequest request) {
        String MODULE_ALREADY_EXIST = "MODULE ALREADY EXIST";
        if (modulesRepository.findByModules(request.getModules()).isPresent()) {
            throw new ModuleNotFoundException(MODULE_ALREADY_EXIST);
        }
        String MODULE_SUCCESSFULLY_CREATED_MESSAGE = "MODULE_SUCCESSFULLY CREATE";
        CourseModule modules = modelMapper.map(request, CourseModule.class);
        modulesRepository.save(modules);
        return ModuleResponse.builder()
                .message(MODULE_SUCCESSFULLY_CREATED_MESSAGE)
                .id(modules.getId())
                .build();
    }

    @Override
    public GetModuleResponse getModuleById(Long id) {
        String MODULE_NOT_FOUND_WITH_ID = " MODULE NOT FOUND WITH ID: ";
        CourseModule module = modulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MODULE_NOT_FOUND_WITH_ID + id));
        return modelMapper.map(module, GetModuleResponse.class);
    }

    @Transactional
    @Override
    public String updateModule(Long id, ModuleUpdateRequest request) {
        String UPDATE_MODULE_NOT_FOUND_WITH_ID = "MODULE NOT FOUND WITH ID: ";
        String UPDATE_SUCCESSFULLY = "MODULE SUCCESSFULLY UPDATED";
        CourseModule module = modulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(UPDATE_MODULE_NOT_FOUND_WITH_ID + id));
        modelMapper.map(request, module);
        modulesRepository.save(module);
        return UPDATE_SUCCESSFULLY;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetModuleResponse> getAllModules(Pageable pageable) {
        return modulesRepository.findAll(pageable)
                .map(courseModule -> modelMapper.map(courseModule, GetModuleResponse.class));
    }
//    @Override
//    @Transactional(readOnly = true)
//    public Page<GetCohortResponse> getAllCohorts(Pageable pageable) {
//        return cohortRepository.findAll(pageable)
//                .map(cohort -> modelMapper.map(cohort, GetCohortResponse.class));
//    }

    @Override
    public String deleteModulesById(Long id) {
        if (!modulesRepository.existsById(id)) {
            throw new RuntimeException("Module not found with ID: " + id);
        }
        modulesRepository.deleteById(id);
        return "Module successfully deleted";
    }
    @Transactional
    @Override
    public void addSessionToModule(Long moduleId, Long sessionId) {
        CourseModule module = modulesRepository.findById(moduleId)
                .orElseThrow(() -> new ModuleNotFoundException("Module not found with ID: " + moduleId));
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + sessionId));
        module.getSessions().add(session);
        modulesRepository.save(module);
    }
}
