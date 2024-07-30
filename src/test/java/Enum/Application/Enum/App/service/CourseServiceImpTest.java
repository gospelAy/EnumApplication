package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CourseRegistrationRequest;
import Enum.Application.Enum.App.dto.response.CourseRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CourseUpdateResponse;
import Enum.Application.Enum.App.exceptions.CourseNotFoundException;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.model.CourseInformation;
import Enum.Application.Enum.App.model.CourseModule;
import Enum.Application.Enum.App.model.Instructor;
import Enum.Application.Enum.App.repository.CourseInformationRepository;
import Enum.Application.Enum.App.repository.CourseRepository;
import Enum.Application.Enum.App.repository.InstructorRepository;
import Enum.Application.Enum.App.repository.ModulesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceImpTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ModulesRepository modulesRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @InjectMocks
    private CourseServiceImp courseService;
    @Mock
    private CourseInformationRepository courseInformationRepository;

    private CourseRegistrationRequest courseRegistrationRequest;
    private Course course;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setCourseName("New Course");
        CourseInformation courseInformation = new CourseInformation();
        courseInformation.setId(1L); // Add an ID to mock saving operation
        courseInformation.setCourseOverview("Overview");
        courseInformation.setLearningOutCome("Learning Outcome");
        courseRegistrationRequest = CourseRegistrationRequest.builder()
                .courseName("New Course")
                .courseInformation(courseInformation)
                .build();
    }

    @Test
    void testRegisterCourseSuccessfully() {
        when(courseRepository.findByCourseName(courseRegistrationRequest.getCourseName()))
                .thenReturn(Optional.empty());
        when(courseInformationRepository.save(any(CourseInformation.class)))
                .thenReturn(courseRegistrationRequest.getCourseInformation());
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        CourseRegistrationResponse response = courseService.register(courseRegistrationRequest);
        assertNotNull(response);
        assertEquals(course.getId(), response.getId());
        assertEquals("COURSE SUCCESSFULLY REGISTERED", response.getMessage());
    }


    @Test
    void testRegisterCourse_ThrowsException_WhenCourseExists() {
        CourseRegistrationRequest request = new CourseRegistrationRequest();
        request.setCourseName("Existing Course");
        when(courseRepository.findByCourseName("Existing Course")).thenReturn(Optional.of(new Course()));
        assertThrows(CourseNotFoundException.class, () -> courseService.register(request));
    }


    @Test
    void testGetCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Test Course");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Course result = courseService.getCourse(1L);
        assertNotNull(result);
        assertEquals("Test Course", result.getCourseName());
    }


    @Test
    void testGetCourse_ThrowsException_WhenNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourse(1L));
    }

    @Test
    void testUpdateCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Old Name");
        CourseInformation courseInformation = new CourseInformation();
        courseInformation.setCourseOverview("Old Overview");
        course.setCourseInformation(courseInformation);
        CourseRegistrationRequest request = new CourseRegistrationRequest();
        request.setCourseName("New Name");
        CourseInformation newInfo = new CourseInformation();
        newInfo.setCourseOverview("New Overview");
        newInfo.setLearningOutCome("New Outcome");
        request.setCourseInformation(newInfo);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        CourseUpdateResponse response = courseService.updateCourse(1L, request);
        assertEquals("UPDATE SUCCESS", response.getMessage());
        assertEquals("New Name", course.getCourseName());
        assertEquals("New Overview", course.getCourseInformation().getCourseOverview());
    }


    @Test
    void testUpdateCourse_ThrowsException_WhenNotFound() {
        CourseRegistrationRequest request = new CourseRegistrationRequest();
        request.setCourseName("New Name");
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(1L, request));
    }


    @Test
    void testGetAllCourses() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Test Course");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> coursePage = new PageImpl<>(Collections.singletonList(course));
        when(courseRepository.findAll(pageable)).thenReturn(coursePage);
        Page<Course> result = courseService.getAllCourses(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Course", result.getContent().get(0).getCourseName());
    }

    @Test
    void testDeleteCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        String result = courseService.deleteCourse(1L);
        assertEquals("DELETED SUCCESSFULLY", result);
        verify(courseRepository, times(1)).deleteById(1L);
    }


    @Test
    void testDeleteCourse_ThrowsException_WhenNotFound() {
        when(courseRepository.existsById(1L)).thenReturn(false);
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(1L));
    }


    @Test
    void testAddModuleToCourse() {
        Course course = new Course();
        course.setId(1L);
        CourseModule module = new CourseModule();
        module.setId(2L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(modulesRepository.findById(2L)).thenReturn(Optional.of(module));
        courseService.addModuleToCourse(1L, 2L);
        assertTrue(course.getModules().contains(module));
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testAssignInstructorToCourse() {
        Course course = new Course();
        course.setId(1L);
        Instructor instructor = new Instructor();
        instructor.setId(2L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.findById(2L)).thenReturn(Optional.of(instructor));
        courseService.assignInstructorToCourse(1L, 2L);
        assertEquals(course, instructor.getCourse());
        verify(instructorRepository, times(1)).save(instructor);
    }
}
