package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.Bed;

public interface BedRepository extends JpaRepository<Bed, Long> {
	List<Bed> findByRmIdIn(List<Long> roomIdList);
}
