package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.SessionRegistrationRequest;
import Enum.Application.Enum.App.dto.response.SessionRegistrationResponse;
import Enum.Application.Enum.App.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessionService {
    SessionRegistrationResponse createSession(SessionRegistrationRequest request);
    Session getSessionById(Long id);
    String updateSession(Long id, SessionRegistrationRequest request);
    Page<SessionRegistrationResponse> getAllSessions(Pageable pageable);
    String deleteSessionById(Long id);
}
