package Enum.Application.Enum.App.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCohortResponse {
    private Long id;
    private String cohortName;
    private String description;
    private Long programId;
    private LocalDate startDate;
    private LocalDate endDate;
}
