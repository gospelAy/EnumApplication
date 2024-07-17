package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.InstructorRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetInstructorResponse;
import Enum.Application.Enum.App.dto.response.InstructorRegistrationResponse;
import Enum.Application.Enum.App.exceptions.InstructorException;
import Enum.Application.Enum.App.model.Instructor;
import Enum.Application.Enum.App.repository.InstructorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public InstructorRegistrationResponse saveInstructor(InstructorRegistrationRequest request) {
        String INSTRUCTOR_SAVED_SUCCESSFULLY = "INSTRUCTOR SAVED SUCCESSFULLY";
        Instructor instructor = modelMapper.map(request, Instructor.class);
        instructor.setDateAdded(LocalDateTime.now());
        instructor.setLastActive(LocalDateTime.now());
        instructorRepository.save(instructor);
        try {
            String subject = "Invitation to Join as Instructor";
            String text = "Dear " + instructor.getName() + ",<br>" +
                    "<br>You have been invited to join as an instructor." +
                    " Welcome!<br>" +
                    "<br>Best Regards,<br>Your Team";
            emailService.sendEmail(instructor.getEmail(), subject, text);
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            log.error("Failed to send email to instructor: " + instructor.getEmail(), e);
        }

        return InstructorRegistrationResponse.builder()
                .message(INSTRUCTOR_SAVED_SUCCESSFULLY).build();
    }

    @Override
    public GetInstructorResponse getInstructorById(Long id) {
        String INSTRUCTOR_NOT_FOUND = "INSTRUCTOR NOT FOUND";
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorException(INSTRUCTOR_NOT_FOUND));
        return modelMapper.map(instructor, GetInstructorResponse.class);
    }

    @Override
    public String updateInstructor(Long id, InstructorRegistrationRequest request) {
        String UPDATE_INSTRUCTOR_NOT_FOUND_MESSAGE = "INSTRUCTOR NOT FOUND";
        String SUCCESSFUL_UPDATE = "INSTRUCTOR UPDATED SUCCESSFULLY";
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorException(UPDATE_INSTRUCTOR_NOT_FOUND_MESSAGE));
        modelMapper.map(request, instructor);
        instructor.setLastActive(LocalDateTime.now());
        instructorRepository.save(instructor);
        return SUCCESSFUL_UPDATE;
    }

    @Override
    public Page<GetInstructorResponse> getAllInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable)
                .map(entity -> modelMapper.map(entity, GetInstructorResponse.class));
    }

    @Override
    public String deleteInstructor(Long id) {
        String DELETED_SUCCESS = "INSTRUCTOR DELETED SUCCESSFULLY";
        String NOT_FOUND = "INSTRUCTOR WITH: " + id + " NOT FOUND";
        if (!instructorRepository.existsById(id)) {
            throw new InstructorException(NOT_FOUND);
        }
        instructorRepository.deleteById(id);
        return DELETED_SUCCESS;
    }
}
