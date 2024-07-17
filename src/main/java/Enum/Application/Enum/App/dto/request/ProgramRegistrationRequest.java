package Enum.Application.Enum.App.dto.request;

import lombok.Data;

@Data
public class ProgramRegistrationRequest {
    private Long id;
    private String name;
    private String about;
    private String goals;
    private String benefits;
}

