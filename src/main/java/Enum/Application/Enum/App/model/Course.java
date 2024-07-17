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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    @OneToOne
    private CourseInformation courseInformation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CourseModule> modules;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Instructor> instructors;
}
