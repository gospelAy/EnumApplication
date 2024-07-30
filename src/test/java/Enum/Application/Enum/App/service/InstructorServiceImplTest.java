package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.InstructorRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetInstructorResponse;
import Enum.Application.Enum.App.dto.response.InstructorRegistrationResponse;
import Enum.Application.Enum.App.model.Instructor;
import Enum.Application.Enum.App.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class InstructorServiceImplTest {
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private InstructorServiceImpl instructorService;
    private InstructorRegistrationRequest request;
    private Instructor instructor;
    private GetInstructorResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new InstructorRegistrationRequest();
        request.setId(1L);
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setProfession("Professor");
        request.setAbout("About John Doe");

        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John Doe");
        instructor.setEmail("john.doe@example.com");
        instructor.setProfession("Professor");
        instructor.setAbout("About John Doe");
        instructor.setDateAdded(LocalDateTime.now());
        instructor.setLastActive(LocalDateTime.now());

        response = new GetInstructorResponse();
        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john.doe@example.com");
        response.setProfession("Professor");
        response.setAbout("About John Doe");
    }

    @Test
    void testSaveInstructor() throws MessagingException, jakarta.mail.MessagingException {
        when(modelMapper.map(request, Instructor.class)).thenReturn(instructor);
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        InstructorRegistrationResponse result = instructorService.saveInstructor(request);
        assertNotNull(result);
        assertEquals("INSTRUCTOR SAVED SUCCESSFULLY", result.getMessage());
    }

    @Test
    void testGetInstructorById() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(modelMapper.map(instructor, GetInstructorResponse.class)).thenReturn(response);
        GetInstructorResponse result = instructorService.getInstructorById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateInstructor() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        String result = instructorService.updateInstructor(1L, request);
        assertEquals("INSTRUCTOR UPDATED SUCCESSFULLY", result);
    }

    @Test
    void testGetAllInstructors() {
        Page<Instructor> page = new PageImpl<>(Collections.singletonList(instructor));
        when(instructorRepository.findAll(Pageable.unpaged())).thenReturn(page);
        when(modelMapper.map(instructor, GetInstructorResponse.class)).thenReturn(response);
        Page<GetInstructorResponse> result = instructorService.getAllInstructors(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testDeleteInstructor() {
        when(instructorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(instructorRepository).deleteById(1L);
        String result = instructorService.deleteInstructor(1L);
        assertEquals("INSTRUCTOR DELETED SUCCESSFULLY", result);
    }
}
