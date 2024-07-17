package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CourseRegistrationRequest;
import Enum.Application.Enum.App.dto.response.CourseRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CourseUpdateResponse;
import Enum.Application.Enum.App.exceptions.CourseNotFoundException;
import Enum.Application.Enum.App.exceptions.InstructorException;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.model.CourseInformation;
import Enum.Application.Enum.App.model.CourseModule;
import Enum.Application.Enum.App.model.Instructor;
import Enum.Application.Enum.App.repository.CourseInformationRepository;
import Enum.Application.Enum.App.repository.CourseRepository;
import Enum.Application.Enum.App.repository.InstructorRepository;
import Enum.Application.Enum.App.repository.ModulesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class CourseServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseInformationRepository courseInformationRepository;
    private final ModulesRepository modulesRepository;
    private final InstructorRepository instructorRepository;

    @Transactional
    @Override
    public CourseRegistrationResponse register(CourseRegistrationRequest request) throws CourseNotFoundException {
        String COURSE_EXIST_MESSAGE = "COURSE WITH THE SAME NAME ALREADY EXISTS:";
        String COURSE_SUCCESSFULLY_REGISTERED = "COURSE SUCCESSFULLY REGISTERED";
        if (courseRepository.findByCourseName(request.getCourseName()).isPresent()) {
            throw new CourseNotFoundException(COURSE_EXIST_MESSAGE);
        }
        Course course = mapToCourse(request);
        Course savedCourse = courseRepository.save(course);
        return CourseRegistrationResponse.builder()
                .id(savedCourse.getId())
                .message(COURSE_SUCCESSFULLY_REGISTERED)
                .build();
    }

    @Override
    public Course getCourse(Long id) {
        String GET_COURSE_API_NOT_FOUND = "COURSE WITH ID";
        String NOT_FOUND = "NOT FOUND";
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(GET_COURSE_API_NOT_FOUND + id + NOT_FOUND));
    }

    @Transactional
    @Override
    public CourseUpdateResponse updateCourse(Long id, CourseRegistrationRequest request) {
        String SUCCESS_MESSAGE = "UPDATE SUCCESS";
        String COURSE_NOT_FOUND = "COURSE NOT FOUND WITH ID:";
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND + id));
        course.setCourseName(request.getCourseName());
        CourseInformation courseInformation = course.getCourseInformation();
        if (courseInformation != null) {
            courseInformation.setCourseOverview(request.getCourseInformation().getCourseOverview());
            courseInformation.setLearningOutCome(request.getCourseInformation().getLearningOutCome());
        }
        courseRepository.save(course);
        return CourseUpdateResponse.builder().message(SUCCESS_MESSAGE).build();
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public String deleteCourse(Long id) {
        String MESSAGE_SUCCESS = "DELETED SUCCESSFULLY";
        String DELETE_COURSE_NOT_FOUND = "COURSE NOT FOUND WITH ID:";

        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(DELETE_COURSE_NOT_FOUND + id);
        }
         courseRepository.deleteById(id);
        return MESSAGE_SUCCESS;
    }

    private Course mapToCourse(CourseRegistrationRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        CourseInformation courseInformation = request.getCourseInformation();
        if (courseInformation != null) {
            courseInformation = courseInformationRepository.save(courseInformation);
            course.setCourseInformation(courseInformation);
        }
        return course;
    }

    @Transactional
    @Override
    public void addModuleToCourse(Long courseId, Long moduleId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
        CourseModule module = modulesRepository.findById(moduleId)
                .orElseThrow(() -> new CourseNotFoundException("Module not found with ID: " + moduleId));
        course.getModules().add(module);
        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void assignInstructorToCourse(Long courseId, Long instructorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorException("Instructor not found with ID: " + instructorId));
        instructor.setCourse(course);
        instructorRepository.save(instructor);
    }
}
