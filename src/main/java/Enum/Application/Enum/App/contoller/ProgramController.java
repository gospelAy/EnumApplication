package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetProgramResponse;
import Enum.Application.Enum.App.dto.response.ProgramRegistrationResponse;
import Enum.Application.Enum.App.service.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping("/register")
    public ResponseEntity<ProgramRegistrationResponse> registerProgram(@RequestBody ProgramRegistrationRequest request) {
        return ResponseEntity.ok(programService.register(request));
    }

    @GetMapping("/getProgram/{id}")
    public ResponseEntity<GetProgramResponse> getProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgram(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProgram(@PathVariable Long id, @RequestBody ProgramRegistrationRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.deleteProgram(id));
    }

    @GetMapping("/getAllPrograms")
    public ResponseEntity<Page<GetProgramResponse>> getAllPrograms(Pageable pageable) {
        return ResponseEntity.ok(programService.getAllProgram(pageable));
    }
}
