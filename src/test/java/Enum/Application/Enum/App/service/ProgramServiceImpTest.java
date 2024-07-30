package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetProgramResponse;
import Enum.Application.Enum.App.dto.response.ProgramRegistrationResponse;
import Enum.Application.Enum.App.exceptions.ProgramNotFoundException;
import Enum.Application.Enum.App.model.Program;
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
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramServiceImpTest {
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ProgramServiceImp programServiceImp;
    private ProgramRegistrationRequest programRegistrationRequest;
    private Program program;
    private ProgramRegistrationResponse programRegistrationResponse;

    @BeforeEach
    void setUp() {
        programRegistrationRequest = new ProgramRegistrationRequest();
        programRegistrationRequest.setId(1L);
        programRegistrationRequest.setName("Test Program");
        programRegistrationRequest.setAbout("About Test Program");
        programRegistrationRequest.setGoals("Goals of Test Program");
        programRegistrationRequest.setBenefits("Benefits of Test Program");

        program = new Program();
        program.setId(1L);
        program.setName("Test Program");
        program.setAbout("About Test Program");
        program.setGoals("Goals of Test Program");
        program.setBenefits("Benefits of Test Program");

        programRegistrationResponse = ProgramRegistrationResponse.builder()
                .id(1L)
                .message("program successfully registered.")
                .build();
    }
    @Test
    void registerProgramSuccessfully() {
        when(modelMapper.map(programRegistrationRequest, Program.class)).thenReturn(program);
        when(programRepository.save(any(Program.class))).thenReturn(program);
        when(modelMapper.map(program, ProgramRegistrationResponse.class)).thenReturn(programRegistrationResponse);
        ProgramRegistrationResponse response = programServiceImp.register(programRegistrationRequest);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("program successfully registered.", response.getMessage());
        verify(programRepository, times(1)).save(any(Program.class));
    }

    @Test
    void getProgramSuccessfully() {
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(modelMapper.map(program, GetProgramResponse.class)).thenReturn(new GetProgramResponse());
        GetProgramResponse response = programServiceImp.getProgram(1L);
        assertNotNull(response);
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void getProgramNotFound() {
        when(programRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProgramNotFoundException.class, () -> programServiceImp.getProgram(1L));
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void updateProgramSuccessfully() {
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(programRepository.save(any(Program.class))).thenReturn(program);
        String response = programServiceImp.updateProgram(1L, programRegistrationRequest);
        assertEquals("Program Updated Successfully", response);
        verify(programRepository, times(1)).findById(1L);
        verify(programRepository, times(1)).save(any(Program.class));
    }

    @Test
    void deleteProgramSuccessfully() {
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        String response = programServiceImp.deleteProgram(1L);
        assertEquals("Program deleted successfully.", response);
        verify(programRepository, times(1)).findById(1L);
        verify(programRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllPrograms() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Program> programPage = new PageImpl<>(Collections.singletonList(program));
        when(programRepository.findAll(pageable)).thenReturn(programPage);
        when(modelMapper.map(program, GetProgramResponse.class)).thenReturn(new GetProgramResponse());
        Page<GetProgramResponse> response = programServiceImp.getAllProgram(pageable);
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(programRepository, times(1)).findAll(pageable);
    }
}
