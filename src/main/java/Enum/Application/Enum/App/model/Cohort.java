package Enum.Application.Enum.App.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cohort {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cohortName;
    private String description;
    @ManyToOne
    private Program program;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;
    private String cohortAvatar;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Learner> learners;
}
