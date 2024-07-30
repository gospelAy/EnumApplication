package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.LearnerRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetAllLearnersResponse;
import Enum.Application.Enum.App.dto.response.LearnerResponse;
import Enum.Application.Enum.App.model.Learner;
import Enum.Application.Enum.App.repository.LearnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LearnerServiceImplTest {
    @Mock
    private LearnerRepository learnerRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LearnerServiceImpl learnerService;
    private LearnerRegistrationRequest request;
    private Learner learner;
    private GetAllLearnersResponse getAllLearnersResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new LearnerRegistrationRequest("John Doe", "john.doe@example.com");
        learner = new Learner();
        learner.setId(1L);
        learner.setName("John Doe");
        learner.setEmail("john.doe@example.com");
        LearnerResponse learnerResponse = LearnerResponse.builder()
                .id(1L)
                .message("LEARNER SUCCESSFULLY CREATED ================")
                .build();
        getAllLearnersResponse = new GetAllLearnersResponse("1", "John Doe", "john.doe@example.com");
    }

    @Test
    void testCreateLearnerSuccessfully() {
        when(learnerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(request, Learner.class)).thenReturn(learner);
        when(learnerRepository.save(any(Learner.class))).thenReturn(learner);
        LearnerResponse response = learnerService.createLearner(request);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("LEARNER SUCCESSFULLY CREATED ================", response.getMessage());
    }

    @Test
    void testGetLearnerById() {
        when(learnerRepository.findById(anyLong())).thenReturn(Optional.of(learner));
        Learner result = learnerService.getLearnerById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    void testUpdateLearner() {
        when(learnerRepository.findById(anyLong())).thenReturn(Optional.of(learner));
        when(learnerRepository.save(any(Learner.class))).thenReturn(learner);
        String response = learnerService.updateLearner(1L, request);
        assertEquals("LEARNER SUCCESSFULLY CREATED: ", response);
        verify(learnerRepository, times(1)).findById(anyLong());
        verify(learnerRepository, times(1)).save(any(Learner.class));
    }

    @Test
    void testGetAllLearners() {
        Page<Learner> page = new PageImpl<>(Collections.singletonList(learner));
        when(learnerRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(learner, GetAllLearnersResponse.class)).thenReturn(getAllLearnersResponse);
        Page<GetAllLearnersResponse> responsePage = learnerService.getAllLearner(Pageable.unpaged());
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("John Doe", responsePage.getContent().get(0).getName());
    }

    @Test
    void testDeleteLearnerById() {
        when(learnerRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(learnerRepository).deleteById(anyLong());
        String response = learnerService.deleteLearnerById(1L);
        assertEquals("LEARNER SUCCESSFULLY DELETED", response);
    }

    @Test
    void testDeleteLearnerByIdNotFound() {
        when(learnerRepository.existsById(anyLong())).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            learnerService.deleteLearnerById(1L);
        });
        assertEquals("LEARNER NOT FOUND WITH ID:1", exception.getMessage());
    }
}
