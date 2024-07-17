package Enum.Application.Enum.App.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleRegistrationRequest {
    private Long id;
    private String modules;
}
