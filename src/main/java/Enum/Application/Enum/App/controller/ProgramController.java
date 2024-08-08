package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetProgramResponse;
import Enum.Application.Enum.App.dto.response.ProgramRegistrationResponse;
import Enum.Application.Enum.App.service.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<ProgramRegistrationResponse> registerProgram(@RequestBody ProgramRegistrationRequest request) {
        return ResponseEntity.ok(programService.register(request));
    }

    @GetMapping("/getProgram/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_learner') or hasRole('client_instructor')")
    public ResponseEntity<GetProgramResponse> getProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgram(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> updateProgram(@PathVariable Long id, @RequestBody ProgramRegistrationRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.deleteProgram(id));
    }

    @GetMapping("/getAllPrograms")
    @PreAuthorize("hasRole('client_learner') or hasRole('client_instructor') or hasRole('client_admin')")
    public ResponseEntity<Page<GetProgramResponse>> getAllPrograms(Pageable pageable) {
        return ResponseEntity.ok(programService.getAllProgram(pageable));
    }
}
