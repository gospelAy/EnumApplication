package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.InstructorRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetInstructorResponse;
import Enum.Application.Enum.App.dto.response.InstructorRegistrationResponse;
import Enum.Application.Enum.App.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<InstructorRegistrationResponse> createInstructor(@RequestBody InstructorRegistrationRequest request) {
        return ResponseEntity.ok(instructorService.saveInstructor(request));
    }

    @GetMapping("/getInstructor/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor') or hasRole('client_learner')")
    public ResponseEntity<GetInstructorResponse> getInstructorById(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getInstructorById(id));
    }

    @PutMapping("/updateInstructor/{id}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor')")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id, @RequestBody InstructorRegistrationRequest request) {
        return ResponseEntity.ok(instructorService.updateInstructor(id, request));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_instructor') or hasRole('client_learner')")
    public ResponseEntity<Page<GetInstructorResponse>> getAllInstructors(Pageable pageable) {
        return ResponseEntity.ok(instructorService.getAllInstructors(pageable));
    }

    @DeleteMapping("/deleteInstructor/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.deleteInstructor(id));
    }
}
