package Enum.Application.Enum.App.repository;

import Enum.Application.Enum.App.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
