package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
