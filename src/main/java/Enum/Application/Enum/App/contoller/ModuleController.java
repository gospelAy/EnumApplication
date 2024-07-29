package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.ModuleRegistrationRequest;
import Enum.Application.Enum.App.dto.request.ModuleUpdateRequest;
import Enum.Application.Enum.App.dto.response.GetModuleResponse;
import Enum.Application.Enum.App.dto.response.ModuleResponse;
import Enum.Application.Enum.App.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
@AllArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/Create")
    public ModuleResponse createModule(@RequestBody ModuleRegistrationRequest request) {
        return moduleService.createModule(request);
    }

    @GetMapping("/getModule/{id}")
    public GetModuleResponse getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PutMapping("/update/{id}")
    public String updateModule(@PathVariable Long id, @RequestBody ModuleUpdateRequest request) {
        return moduleService.updateModule(id, request);
    }

    @GetMapping("/getAll")
    public Page<GetModuleResponse> getAllModules(Pageable pageable) {
        return moduleService.getAllModules(pageable);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteModulesById(@PathVariable Long id) {
        return moduleService.deleteModulesById(id);
    }

    @PostMapping("/{moduleId}/sessions/{sessionId}")
    public ResponseEntity<String> addSessionToModule(@PathVariable Long moduleId, @PathVariable Long sessionId) {
        moduleService.addSessionToModule(moduleId, sessionId);
        return ResponseEntity.ok("Session added to module successfully");
    }
}
