package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;

public interface LocationUnitRepository extends JpaRepository<LocationUnit, Long> {
	List<LocationUnit> findByBuildIdIn(List<Long> buildIdList);
}
