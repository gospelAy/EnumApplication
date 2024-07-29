package Enum.Application.Enum.App.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String profession;
    private String about;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Course course;;
    private LocalDateTime dateAdded;
    private LocalDateTime lastActive;
}
