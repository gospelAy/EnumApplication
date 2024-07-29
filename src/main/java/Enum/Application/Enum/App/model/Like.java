package Enum.Application.Enum.App.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
