package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.InstructorRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetInstructorResponse;
import Enum.Application.Enum.App.dto.response.InstructorRegistrationResponse;
import Enum.Application.Enum.App.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/register")
    public ResponseEntity<InstructorRegistrationResponse> createInstructor(@RequestBody InstructorRegistrationRequest request) {
        return ResponseEntity.ok(instructorService.saveInstructor(request));
    }

    @GetMapping("/getInstructor/{id}")
    public ResponseEntity<GetInstructorResponse> getInstructorById(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getInstructorById(id));
    }

    @PutMapping("/updateInstructor/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id, @RequestBody InstructorRegistrationRequest request) {
        return ResponseEntity.ok(instructorService.updateInstructor(id, request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<GetInstructorResponse>> getAllInstructors(Pageable pageable) {
        return ResponseEntity.ok(instructorService.getAllInstructors(pageable));
    }

    @DeleteMapping("/deleteInstructor/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.deleteInstructor(id));
    }
}

