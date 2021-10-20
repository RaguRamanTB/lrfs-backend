package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
	List<Comments> findByPatId(Long patId);
}
