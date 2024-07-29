package Enum.Application.Enum.App.contoller;

import Enum.Application.Enum.App.dto.request.SessionRegistrationRequest;
import Enum.Application.Enum.App.dto.response.SessionRegistrationResponse;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@AllArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/create")
    public SessionRegistrationResponse createSession(@RequestBody SessionRegistrationRequest request) {
        return sessionService.createSession(request);
    }

    @GetMapping("getSessionById/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @PutMapping("updateSessionById/{id}")
    public String updateSession(@PathVariable Long id, @RequestBody SessionRegistrationRequest request) {
        return sessionService.updateSession(id, request);
    }

    @GetMapping("/getAllSession")
    public Page<SessionRegistrationResponse> getAllSessions(Pageable pageable) {
        return sessionService.getAllSessions(pageable);
    }

    @DeleteMapping("deleteSession/{id}")
    public String deleteSessionById(@PathVariable Long id) {
        return sessionService.deleteSessionById(id);
    }
}
