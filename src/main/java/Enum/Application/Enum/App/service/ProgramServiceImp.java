package Enum.Application.Enum.App.service;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;
import Enum.Application.Enum.App.dto.response.GetProgramResponse;
import Enum.Application.Enum.App.dto.response.ProgramRegistrationResponse;
import Enum.Application.Enum.App.exceptions.ProgramNotFoundException;
import Enum.Application.Enum.App.model.Program;
import Enum.Application.Enum.App.repository.ProgramRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ProgramServiceImp implements ProgramService {
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ProgramRegistrationResponse register(ProgramRegistrationRequest request) {
        Program program = modelMapper.map(request, Program.class);
        program = programRepository.save(program);
        modelMapper.map(program, ProgramRegistrationResponse.class);
        return ProgramRegistrationResponse.builder()
                .id(program.getId())
                .message("program successfully registered.")
                .build();
    }

    @Override
    public GetProgramResponse getProgram(Long id) {
        Program program = programRepository.findById(id).orElseThrow(() -> new ProgramNotFoundException("Program not found with ID: " + id));
        return modelMapper.map(program, GetProgramResponse.class);
    }

    @Override
    public String updateProgram(Long id, ProgramRegistrationRequest request) {
        Program existingProgram = programRepository.findById(id).orElseThrow(() -> new ProgramNotFoundException("Program not found with ID: " + id));
        existingProgram.setName(request.getName());
        existingProgram.setAbout(request.getAbout());
        existingProgram.setGoals(request.getGoals());
        existingProgram.setBenefits(request.getBenefits());
        existingProgram = programRepository.save(existingProgram);
        modelMapper.map(existingProgram, ProgramRegistrationResponse.class);
        return "Program Updated Successfully";
    }

    @Override
    public String deleteProgram(Long id) {
        programRepository.findById(id).orElseThrow(() -> new ProgramNotFoundException("Program not found with ID: " + id));
        programRepository.deleteById(id);
        return "Program deleted successfully.";
    }

    @Override
    public Page<GetProgramResponse> getAllProgram(Pageable pageable) {
        Page<Program> programs = programRepository.findAll(pageable);
        return programs.map(program -> modelMapper.map(program, GetProgramResponse.class));
    }
}
