package Enum.Application.Enum.App.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
   @ManyToOne
   @JoinColumn(name = "post_id", nullable = false)
   private Post post;
   @ManyToOne
   @JoinColumn(name = "learner_id", nullable = false)
   private Learner learner;
   private LocalDateTime createdAt;

   @PrePersist
   public void prePersist() {
      createdAt = LocalDateTime.now();
   }

}
