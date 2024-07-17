package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.SessionRegistrationRequest;
import Enum.Application.Enum.App.dto.response.SessionRegistrationResponse;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.repository.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public SessionRegistrationResponse createSession(SessionRegistrationRequest request) {
        String SESSION_MESSAGE = "SESSION SUCCESSFULLY CREATED ==============";
        Session session = modelMapper.map(request, Session.class);
        Session savedSession = sessionRepository.save(session);
        return SessionRegistrationResponse.builder()
                .id(savedSession.getId())
                .name(savedSession.getName())
                .description(savedSession.getDescription())
                .videoReference(savedSession.getVideoReference())
                .fileReference(savedSession.getFileReference())
                .message(SESSION_MESSAGE)
                .build();
    }

    @Override
    public Session getSessionById(Long id) {
        String SESSION_NOT_FOUND = "SESSION NOT FOUND WITH ID ";
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(SESSION_NOT_FOUND + id));
    }

    @Transactional
    @Override
    public String updateSession(Long id, SessionRegistrationRequest request) {
        String UPDATE_SESSION_NOT_FOUND = "SESSION NOT FOUND WITH ID ";
        String SUCCESS_MESSAGE = "SESSION SUCCESSFULLY UPDATED ";
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(UPDATE_SESSION_NOT_FOUND + id));
        modelMapper.map(request, session);
        sessionRepository.save(session);
        return SUCCESS_MESSAGE;
    }

    @Override
    public Page<SessionRegistrationResponse> getAllSessions(Pageable pageable) {
        return sessionRepository.findAll(pageable)
                .map(session -> modelMapper.map(session, SessionRegistrationResponse.class));
    }

    @Override
    public String deleteSessionById(Long id) {
        String SESSION_NOT_FOUND_WITH_ID ="SESSION NOT FOUND WITH ID: ";
        String SESSION_DELETED_SUCCESSFULLY = "SESSION SUCCESSFULLY DELETED";
        if (!sessionRepository.existsById(id)) {
            throw new RuntimeException(SESSION_NOT_FOUND_WITH_ID + id);
        }
        sessionRepository.deleteById(id);
        return SESSION_DELETED_SUCCESSFULLY;
    }
}
