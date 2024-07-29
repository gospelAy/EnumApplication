package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.CohortRegistrationRequest;
import Enum.Application.Enum.App.dto.request.CohortUpdateRequest;
import Enum.Application.Enum.App.dto.response.CohortRegistrationResponse;
import Enum.Application.Enum.App.dto.response.CohortUpdateResponse;
import Enum.Application.Enum.App.dto.response.GetCohortResponse;
import Enum.Application.Enum.App.dto.response.InviteLearnerResponse;
import Enum.Application.Enum.App.exceptions.CohortNotFoundException;
import Enum.Application.Enum.App.exceptions.CourseNotFoundException;
import Enum.Application.Enum.App.exceptions.InvalidException;
import Enum.Application.Enum.App.exceptions.LearnerNotFoundException;
import Enum.Application.Enum.App.model.Cohort;
import Enum.Application.Enum.App.model.Course;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.model.Program;
import Enum.Application.Enum.App.repository.CohortRepository;
import Enum.Application.Enum.App.repository.CourseRepository;
import Enum.Application.Enum.App.repository.LearnerRepository;
import Enum.Application.Enum.App.repository.ProgramRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@Slf4j
@AllArgsConstructor
public class CohortServiceImp implements CohortService {
    private final ModelMapper modelMapper;
    private final CohortRepository cohortRepository;
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final LearnerRepository learnerRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public CohortRegistrationResponse register(CohortRegistrationRequest request) throws CohortNotFoundException {
        String COHORT_EXIST_MESSAGE = "COHORT WITH THE SAME NAME ALREADY EXIST:";
        String COHORT_SUCCESSFULLY_REGISTERED = "COHORT SUCCESSFULLY REGISTERED";
        if (cohortRepository.findByCohortName(request.getCohortName()).isPresent()) {
            throw new CohortNotFoundException(COHORT_EXIST_MESSAGE);
        }
        Program program = programRepository.findById(request.getProgramId()).orElseThrow(() -> new InvalidException("Invalid Program ID"));
        Cohort cohort = modelMapper.map(request, Cohort.class);
        cohort.setProgram(program);
        Cohort savedCohort = cohortRepository.save(cohort);
        return CohortRegistrationResponse.builder()
                .id(savedCohort.getId())
                .message(COHORT_SUCCESSFULLY_REGISTERED)
                .build();
    }
    @Override
    public Cohort getCohort(Long id) {
        String GET_COHORT_API_NOT_FOUND  = "COHORT WITH ID";
        String NOT_FOUND = "NOT FOUND";
        return cohortRepository.findById(id)
                .orElseThrow(() -> new CohortNotFoundException(GET_COHORT_API_NOT_FOUND + id + NOT_FOUND));
    }

    @Transactional
    @Override
    public CohortUpdateResponse updateCohort(Long id, CohortUpdateRequest request) {
        String SUCCESS_MESSAGE ="UPDATE SUCCESS";
        String COHORT_NOT_FOUND = "COHORT Not FOUND WITH ID:";
        Cohort cohort = cohortRepository.findById(id)
                .orElseThrow(() -> new CohortNotFoundException(COHORT_NOT_FOUND + id));
        modelMapper.map(request, cohort);
        Cohort updatedCohort = cohortRepository.save(cohort);
        modelMapper.map(updatedCohort, CohortUpdateResponse.class);
        return CohortUpdateResponse.builder().message(SUCCESS_MESSAGE).build();
    }
    @Override
    public String deleteCohort(Long id) {
        String MESSAGE_SUCCESS = "DELETED SUCCESSFULLY";
        String DELETE_COHORT_NOT_FOUND = "COHORT NOT FOUND WITH ID:";
        if (!cohortRepository.existsById(id)) {
            throw new CohortNotFoundException(DELETE_COHORT_NOT_FOUND + id);
        }else if (cohortRepository.existsById(id)){
            cohortRepository.deleteById(id);
        }return MESSAGE_SUCCESS;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<GetCohortResponse> getAllCohorts(Pageable pageable) {
        return cohortRepository.findAll(pageable)
                .map(cohort -> modelMapper.map(cohort, GetCohortResponse.class));
    }

    @Override
    @Transactional
    public void addCourseToCohort(Long cohortId, Long courseId) {
        String COHORT_NOT_FOUND ="COHORT NOT FOUND WITH ID: ";
        String COURSE_NOT_FOUND ="COURSE NOT FOUND WITH ID: ";
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new CohortNotFoundException(COHORT_NOT_FOUND + cohortId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND + courseId));
        cohort.getCourses().add(course);
        cohortRepository.save(cohort);
    }

    @Transactional
    @Override
    public InviteLearnerResponse inviteLearnerToCohort(Long cohortId, Long learnerId) throws MessagingException, jakarta.mail.MessagingException, MessagingException {
        String COHORT_NOT_FOUND = "Cohort not found with ID: ";
        String LEARNER_NOT_FOUND = "Learner not found with ID: ";
        String INVITE_SUCCESS_MESSAGE = "Learner successfully invited to cohort.";
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new CohortNotFoundException(COHORT_NOT_FOUND + cohortId));
        Learner learner = learnerRepository.findById(learnerId)
                .orElseThrow(() -> new LearnerNotFoundException(LEARNER_NOT_FOUND + learnerId));
        cohort.getLearners().add(learner);
        cohortRepository.save(cohort);
        String subject = "Invitation to Join Cohort";
        String text = "Dear " + learner.getName() + ",<br><br>" +
                "You have been invited to join the cohort: " + cohort.getCohortName() + ".<br>" +
                "Welcome!<br" +
                "><br>" +
                "Best Regards,<br>Your Team";
        emailService.sendEmail(learner.getEmail(), subject, text);
        return InviteLearnerResponse.builder()
                .message(INVITE_SUCCESS_MESSAGE)
                .build();
    }
}

