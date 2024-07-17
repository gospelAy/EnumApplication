package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.LearnerRegistrationRequest;
import Enum.Application.Enum.App.dto.response.LearnerResponse;
import Enum.Application.Enum.App.exceptions.LearnerNotFoundException;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.repository.LearnerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LearnerServiceImpl implements LearnerService {

    private final LearnerRepository learnerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public LearnerResponse createLearner(LearnerRegistrationRequest request) {
        String LEARNER_EXIST_MESSAGE = "LEARNER ALREADY EXIST:";
        String SUCCESS_MESSAGE_FOR_LEARNER = "LEARNER SUCCESSFULLY CREATED ================";
        if (learnerRepository.findByEmail(request.getEmail()).isPresent()){
            throw new LearnerNotFoundException(LEARNER_EXIST_MESSAGE);
        }
        Learner learner = modelMapper.map(request, Learner.class);
        Learner savedLearner = learnerRepository.save(learner);
        return LearnerResponse.builder()
                .id(savedLearner.getId())
                .message(SUCCESS_MESSAGE_FOR_LEARNER)
                .build();
    }

    @Override
    public Learner getLearnerById(Long id) {
        String NOT_FOUND = "LEARNER NOT FOUND WITH ID: ";
        return learnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND + id));
    }

    @Transactional
    @Override
    public String updateLearner(Long id, LearnerRegistrationRequest request) {
        String ID_NOT_FOUND = "LEARNER WITH ID NOT FOUND: ===========";
        String UPDATE_SUCCESS_MESSAGE = "LEARNER SUCCESSFULLY CREATED: ";
        Learner learner = learnerRepository.findById(id)
                .orElseThrow(() -> new LearnerNotFoundException(ID_NOT_FOUND+ id));
        modelMapper.map(request, learner);
        learnerRepository.save(learner);
        return UPDATE_SUCCESS_MESSAGE;
    }

    @Override
    public Page<LearnerResponse> getAllLearner(Pageable pageable) {
        return learnerRepository.findAll(pageable)
                .map(learner -> modelMapper.map(learner, LearnerResponse.class));
    }

    @Override
    public String deleteLearnerById(Long id) {
        String SUCCESSFULLY_DELETED_MESSAGE = "LEARNER SUCCESSFULLY DELETED";
        String LEARNER_NOT_FOUND = "LEARNER NOT FOUND WITH ID:";
        if (!learnerRepository.existsById(id)) {
            throw new RuntimeException(LEARNER_NOT_FOUND + id);
        }
        learnerRepository.deleteById(id);
        return SUCCESSFULLY_DELETED_MESSAGE;
    }
}
