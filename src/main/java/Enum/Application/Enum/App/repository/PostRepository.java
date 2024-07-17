package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
