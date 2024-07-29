package Enum.Application.Enum.App.dto.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleResponse {
    private Long id;
    private String message;
}
