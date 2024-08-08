package Enum.Application.Enum.App.controller;

import Enum.Application.Enum.App.dto.request.SessionRegistrationRequest;
import Enum.Application.Enum.App.dto.response.SessionRegistrationResponse;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@AllArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<SessionRegistrationResponse> createSession(@RequestBody SessionRegistrationRequest request) {
        return ResponseEntity.ok(sessionService.createSession(request));
    }

    @GetMapping("/getSessionById/{id}")
    @PreAuthorize("hasRole('client_learner') or hasRole('client_instructor') or hasRole('client_admin')")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PutMapping("/updateSessionById/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> updateSession(@PathVariable Long id, @RequestBody SessionRegistrationRequest request) {
        return ResponseEntity.ok(sessionService.updateSession(id, request));
    }

    @GetMapping("/getAllSession")
    @PreAuthorize("hasRole('client_learner') or hasRole('client_instructor') or hasRole('client_admin')")
    public ResponseEntity<Page<SessionRegistrationResponse>> getAllSessions(Pageable pageable) {
        return ResponseEntity.ok(sessionService.getAllSessions(pageable));
    }

    @DeleteMapping("/deleteSession/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.deleteSessionById(id));
    }
}
