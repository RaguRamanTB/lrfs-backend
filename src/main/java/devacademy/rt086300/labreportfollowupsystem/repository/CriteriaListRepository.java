package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;

public interface CriteriaListRepository extends JpaRepository<CriteriaList, Long> {
	List<CriteriaList> findByIdIn(List<Long> criteriaIdList);
}
