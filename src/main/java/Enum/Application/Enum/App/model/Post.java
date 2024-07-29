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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String postDetails;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Like> likes;
}
