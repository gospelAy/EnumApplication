package Enum.Application.Enum.App.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLearnersResponse {
    private String id;
    private String name;
    private String email;
}
