package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ModuleRegistrationRequest;
import Enum.Application.Enum.App.dto.request.ModuleUpdateRequest;
import Enum.Application.Enum.App.dto.response.GetModuleResponse;
import Enum.Application.Enum.App.dto.response.ModuleResponse;
import Enum.Application.Enum.App.exceptions.ModuleNotFoundException;
import Enum.Application.Enum.App.exceptions.SessionNotFoundException;
import Enum.Application.Enum.App.model.CourseModule;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.repository.ModulesRepository;
import Enum.Application.Enum.App.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ModuleServiceImplTest {
    @Mock
    private ModulesRepository modulesRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private ModuleServiceImpl moduleService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateModule() {
        ModuleRegistrationRequest request = new ModuleRegistrationRequest(null, "Module 1");
        CourseModule module = new CourseModule();
        module.setId(1L);
        when(modulesRepository.findByModules(any())).thenReturn(Optional.empty());
        when(modulesRepository.save(any())).thenReturn(module);
        when(modelMapper.map(any(), any())).thenReturn(module);
        ModuleResponse response = moduleService.createModule(request);
        assertEquals("MODULE_SUCCESSFULLY CREATE", response.getMessage());
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetModuleById() {
        CourseModule module = new CourseModule();
        module.setId(1L);
        when(modulesRepository.findById(anyLong())).thenReturn(Optional.of(module));
        when(modelMapper.map(any(), any())).thenReturn(new GetModuleResponse());
        GetModuleResponse response = moduleService.getModuleById(1L);
        assertEquals(GetModuleResponse.class, response.getClass());
    }

    @Test
    void testUpdateModule() {
        ModuleUpdateRequest request = new ModuleUpdateRequest("Updated Module");
        CourseModule module = new CourseModule();
        module.setId(1L);
        when(modulesRepository.findById(anyLong())).thenReturn(Optional.of(module));
        when(modulesRepository.save(any())).thenReturn(module);
        String result = moduleService.updateModule(1L, request);
        assertEquals("MODULE SUCCESSFULLY UPDATED", result);
    }

    @Test
    void testDeleteModulesById() {
        when(modulesRepository.existsById(anyLong())).thenReturn(true);
        String result = moduleService.deleteModulesById(1L);
        assertEquals("Module successfully deleted", result);
    }

    @Test
    void testGetAllModules() {
        Page<CourseModule> modules = new PageImpl<>(List.of(new CourseModule()));
        when(modulesRepository.findAll(any(Pageable.class))).thenReturn(modules);
        when(modelMapper.map(any(), any())).thenReturn(new GetModuleResponse());
        Page<GetModuleResponse> response = moduleService.getAllModules(Pageable.unpaged());
        assertEquals(1, response.getSize());
    }

    @Test
    void testCreateModule_ModuleAlreadyExists() {
        ModuleRegistrationRequest request = new ModuleRegistrationRequest(null, "Module 1");
        when(modulesRepository.findByModules(any())).thenReturn(Optional.of(new CourseModule()));
        assertThrows(ModuleNotFoundException.class, () -> moduleService.createModule(request));
    }

    @Test
    void testAddSessionToModule() {
        CourseModule module = new CourseModule();
        module.setId(1L);
        Session session = new Session();
        session.setId(2L);
        when(modulesRepository.findById(anyLong())).thenReturn(Optional.of(module));
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(modulesRepository.save(any())).thenReturn(module);
        moduleService.addSessionToModule(1L, 2L);
        assertEquals(1, module.getSessions().size());
    }

    @Test
    void testAddSessionToModule_ModuleNotFound() {
        when(modulesRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ModuleNotFoundException.class, () -> moduleService.addSessionToModule(1L, 2L));
    }

    @Test
    void testAddSessionToModule_SessionNotFound() {
        CourseModule module = new CourseModule();
        module.setId(1L);
        when(modulesRepository.findById(anyLong())).thenReturn(Optional.of(module));
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SessionNotFoundException.class, () -> moduleService.addSessionToModule(1L, 2L));
    }
}
