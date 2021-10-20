package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;

public interface PatientSummaryRepository extends JpaRepository<PatientSummary, Long> {
	List<PatientSummary> findByPatId(Long id);

	List<PatientSummary> findBybIdInAndIsDischarged(List<Long> bedIdList, int isDischarged);
}
