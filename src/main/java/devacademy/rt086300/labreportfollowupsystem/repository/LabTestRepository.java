package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.LabTest;

public interface LabTestRepository extends JpaRepository<LabTest, Long> {
	List<LabTest> findByPatIdInAndIsNotified(List<Long> patIdList, int isNotified);
	List<LabTest> findByPatId(Long patId);
}
