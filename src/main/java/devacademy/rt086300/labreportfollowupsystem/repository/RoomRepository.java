package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	List<Room> findByLocIdIn(List<Long> locIdList);
}
