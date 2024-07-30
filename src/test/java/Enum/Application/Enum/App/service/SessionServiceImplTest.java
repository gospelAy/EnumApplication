package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.SessionRegistrationRequest;
import Enum.Application.Enum.App.dto.response.SessionRegistrationResponse;
import Enum.Application.Enum.App.model.Session;
import Enum.Application.Enum.App.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SessionServiceImpl sessionServiceImpl;
    private SessionRegistrationRequest sessionRegistrationRequest;
    private Session session;
    private SessionRegistrationResponse sessionRegistrationResponse;
    @BeforeEach
    void setUp() {
        sessionRegistrationRequest = SessionRegistrationRequest.builder()
                .name("Test Session")
                .description("Test Description")
                .videoReference("Test Video Reference")
                .fileReference("Test File Reference")
                .build();

        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDescription("Test Description");
        session.setVideoReference("Test Video Reference");
        session.setFileReference("Test File Reference");

        sessionRegistrationResponse = SessionRegistrationResponse.builder()
                .id(1L)
                .name("Test Session")
                .description("Test Description")
                .videoReference("Test Video Reference")
                .fileReference("Test File Reference")
                .message("SESSION SUCCESSFULLY CREATED ==============")
                .build();
    }

    @Test
    void createSessionSuccessfully() {
        when(modelMapper.map(sessionRegistrationRequest, Session.class)).thenReturn(session);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        SessionRegistrationResponse response = sessionServiceImpl.createSession(sessionRegistrationRequest);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Session", response.getName());
        assertEquals("SESSION SUCCESSFULLY CREATED ==============", response.getMessage());
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void getSessionByIdSuccessfully() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Session foundSession = sessionServiceImpl.getSessionById(1L);
        assertNotNull(foundSession);
        assertEquals(1L, foundSession.getId());
        assertEquals("Test Session", foundSession.getName());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void getSessionByIdNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessionServiceImpl.getSessionById(1L));
        assertEquals("SESSION NOT FOUND WITH ID 1", exception.getMessage());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void updateSessionSuccessfully() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        String response = sessionServiceImpl.updateSession(1L, sessionRegistrationRequest);
        assertEquals("SESSION SUCCESSFULLY UPDATED ", response);
        verify(sessionRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void updateSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessionServiceImpl.updateSession(1L, sessionRegistrationRequest));
        assertEquals("SESSION NOT FOUND WITH ID 1", exception.getMessage());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void deleteSessionByIdSuccessfully() {
        when(sessionRepository.existsById(1L)).thenReturn(true);
        String response = sessionServiceImpl.deleteSessionById(1L);
        assertEquals("SESSION SUCCESSFULLY DELETED", response);
        verify(sessionRepository, times(1)).existsById(1L);
        verify(sessionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSessionByIdNotFound() {
        when(sessionRepository.existsById(1L)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessionServiceImpl.deleteSessionById(1L));
        assertEquals("SESSION NOT FOUND WITH ID: 1", exception.getMessage());
        verify(sessionRepository, times(1)).existsById(1L);
    }

    @Test
    void getAllSessionsSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Session> sessionPage = new PageImpl<>(Collections.singletonList(session));
        when(sessionRepository.findAll(pageable)).thenReturn(sessionPage);
        when(modelMapper.map(session, SessionRegistrationResponse.class)).thenReturn(sessionRegistrationResponse);
        Page<SessionRegistrationResponse> response = sessionServiceImpl.getAllSessions(pageable);
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(sessionRepository, times(1)).findAll(pageable);
    }
}
