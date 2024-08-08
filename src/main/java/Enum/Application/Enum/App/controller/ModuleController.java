package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.ModuleRegistrationRequest;
import Enum.Application.Enum.App.dto.request.ModuleUpdateRequest;
import Enum.Application.Enum.App.dto.response.GetModuleResponse;
import Enum.Application.Enum.App.dto.response.ModuleResponse;
import Enum.Application.Enum.App.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
@AllArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/Create")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor')")
    public ModuleResponse createModule(@RequestBody ModuleRegistrationRequest request) {
        return moduleService.createModule(request);
    }

    @GetMapping("/getModule/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor') or hasRole('client_learner')")
    public GetModuleResponse getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor')")
    public String updateModule(@PathVariable Long id, @RequestBody ModuleUpdateRequest request) {
        return moduleService.updateModule(id, request);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor') or hasRole('client_learner')")
    public Page<GetModuleResponse> getAllModules(Pageable pageable) {
        return moduleService.getAllModules(pageable);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public String deleteModulesById(@PathVariable Long id) {
        return moduleService.deleteModulesById(id);
    }

    @PostMapping("/{moduleId}/sessions/{sessionId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor')")
    public ResponseEntity<String> addSessionToModule(@PathVariable Long moduleId, @PathVariable Long sessionId) {
        moduleService.addSessionToModule(moduleId, sessionId);
        return ResponseEntity.ok("Session added to module successfully");
    }
}
