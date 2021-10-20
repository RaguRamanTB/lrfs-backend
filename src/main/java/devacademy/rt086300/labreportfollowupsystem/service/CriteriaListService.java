package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;

import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;

/**
 * An interface CriteriaListService, to abstract the methods. The
 * implementations are in CriteriaListServiceImpl class.
 * 
 * @author RT086300
 *
 */
public interface CriteriaListService {
	List<CriteriaList> listAllCriteriaLists();

	CriteriaList findCriteriaListById(long id);
	
	List<CriteriaList> listCriteriaListsByIds(List<Long> criteriaIdList);
}
