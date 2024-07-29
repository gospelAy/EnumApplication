package Enum.Application.Enum.App.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String modules;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Session> sessions;
}