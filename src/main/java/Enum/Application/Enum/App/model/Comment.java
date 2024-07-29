package Enum.Application.Enum.App.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String comment;
}
