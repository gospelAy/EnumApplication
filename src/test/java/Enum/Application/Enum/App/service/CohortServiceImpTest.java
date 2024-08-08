package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CohortRegistrationRequest;
import Enum.Application.Enum.App.dto.request.CohortUpdateRequest;
import Enum.Application.Enum.App.dto.response.CohortRegistrationResponse;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.InviteLearnerResponse;
import Enum.Application.Enum.App.exceptions.CohortNotFoundException;
import Enum.Application.Enum.App.model.Cohort;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Program;
import Enum.Application.Enum.App.repository.CohortRepository;
import Enum.Application.Enum.App.repository.CourseRepository;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.ProgramRepository;
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

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CohortServiceImpTest {
    @Mock
    private CohortRepository cohortRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LearnerRepository learnerRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CohortServiceImp cohortService;

    private Cohort cohort;
    private Program program;
    private CohortRegistrationRequest cohortRegistrationRequest;


    @BeforeEach
    void setUp() {
        cohort = new Cohort();
        cohort.setId(1L);
        cohort.setCohortName("Test Cohort");
        program = new Program();
        program.setId(1L);
        cohortRegistrationRequest = CohortRegistrationRequest.builder()
                .cohortName("Test Cohort")
                .description("Cohort Description")
                .programId(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .cohortAvatar("avatar.png")
                .build();

    }

    @Test
    void testRegisterCohortSuccessfully() {
        when(cohortRepository.findByCohortName(cohortRegistrationRequest.getCohortName())).thenReturn(Optional.empty());
        when(programRepository.findById(cohortRegistrationRequest.getProgramId())).thenReturn(Optional.of(program));
        when(modelMapper.map(cohortRegistrationRequest, Cohort.class)).thenReturn(cohort);
        when(cohortRepository.save(cohort)).thenReturn(cohort);
        CohortRegistrationResponse response = cohortService.register(cohortRegistrationRequest);
        assertNotNull(response);
        assertEquals(cohort.getId(), response.getId());
        assertEquals("COHORT SUCCESSFULLY REGISTERED", response.getMessage());
    }

    @Test
    void testRegisterCohortThrowsExceptionIfAlreadyExists() {
        when(cohortRepository.findByCohortName(cohortRegistrationRequest.getCohortName())).thenReturn(Optional.of(cohort));
        Exception exception = assertThrows(CohortNotFoundException.class, () ->
                cohortService.register(cohortRegistrationRequest));
        assertEquals("COHORT WITH THE SAME NAME ALREADY EXIST:", exception.getMessage());
    }

    @Test
    void testGetCohortSuccessfully() {
        when(cohortRepository.findById(cohort.getId())).thenReturn(Optional.of(cohort));
        Cohort result = cohortService.getCohort(cohort.getId());
        assertNotNull(result);
        assertEquals(cohort.getId(), result.getId());
    }

    @Test
    void testGetCohortThrowsExceptionIfNotFound() {
        when(cohortRepository.findById(cohort.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(CohortNotFoundException.class, () ->
                cohortService.getCohort(cohort.getId()));
        assertEquals("COHORT WITH ID" + cohort.getId() + "NOT FOUND", exception.getMessage());
    }

    @Test
    void testDeleteCohortSuccessfully() {
        when(cohortRepository.existsById(cohort.getId())).thenReturn(true);
        String response = cohortService.deleteCohort(cohort.getId());
        assertEquals("DELETED SUCCESSFULLY", response);
    }

    @Test
    void testDeleteCohortThrowsExceptionIfNotFound() {
        when(cohortRepository.existsById(cohort.getId())).thenReturn(false);
        Exception exception = assertThrows(CohortNotFoundException.class, () ->
                cohortService.deleteCohort(cohort.getId()));
        assertEquals("COHORT NOT FOUND WITH ID:" + cohort.getId(), exception.getMessage());
    }

    @Test
    void testGetAllCohortsSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cohort> cohortPage = new PageImpl<>(Collections.singletonList(cohort));
        when(cohortRepository.findAll(pageable)).thenReturn(cohortPage);
        when(modelMapper.map(cohort, GetCohortResponse.class)).thenReturn(new GetCohortResponse());
        Page<GetCohortResponse> responsePage = cohortService.getAllCohorts(pageable);
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
    }

    @Test
    void testAddCourseToCohortSuccessfully() {
        Course course = new Course();
        course.setId(1L);
        Cohort cohort = new Cohort();
        cohort.setId(1L);
        cohort.setCourses(new ArrayList<>());
        when(cohortRepository.findById(cohort.getId())).thenReturn(Optional.of(cohort));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        cohortService.addCourseToCohort(cohort.getId(), course.getId());
        verify(cohortRepository).save(cohort);
        assertTrue(cohort.getCourses().contains(course));
    }

    @Test
    void testInviteLearnerToCohortSuccessfully() throws MessagingException, jakarta.mail.MessagingException {
        Learner learner = new Learner();
        learner.setId(1L);
        learner.setName("John Doe");
        learner.setEmail("johndoe@example.com");
        Cohort cohort = new Cohort();
        cohort.setId(1L);
        cohort.setLearners(new ArrayList<>());
        when(cohortRepository.findById(cohort.getId())).thenReturn(Optional.of(cohort));
        when(learnerRepository.findById(learner.getId())).thenReturn(Optional.of(learner));
        InviteLearnerResponse response = cohortService.inviteLearnerToCohort(cohort.getId(), learner.getId());
        assertNotNull(response);
        assertEquals("Learner successfully invited to cohort.", response.getMessage());
        verify(emailService, times(1)).sendEmail(
                eq(learner.getEmail()),
                eq("Invitation to Join Cohort"),
                contains("Dear " + learner.getName()));
    }

}

