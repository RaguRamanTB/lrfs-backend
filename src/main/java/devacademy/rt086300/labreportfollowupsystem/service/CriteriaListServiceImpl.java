package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;
import devacademy.rt086300.labreportfollowupsystem.repository.CriteriaListRepository;

/**
 * This is a service implementation class of the CriteriaList service, that uses
 * CriteriaListRepository to execute database queries in the CriteriaList
 * Entity.
 * 
 * @author RT086300
 *
 */
@Service
public class CriteriaListServiceImpl implements CriteriaListService {

	/**
	 * CriteriaListRepository Interface that extends JPARepository with CriteriaList
	 * entity. This interface is autowired with @Autowired annotation to inject the
	 * object dependency implicitly.
	 */
	@Autowired
	private CriteriaListRepository criteriaListRepository;

	/**
	 * This method is used to return the List of all CriteriaList in the schema
	 * using findAll() method of the CriteriaListRepository Interface.
	 * 
	 * @return List<CriteriaList> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<CriteriaList> listAllCriteriaLists() {
		try {
			return (List<CriteriaList>) criteriaListRepository.findAll();
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the CriteriaList by its ID using findById()
	 * method of the CriteriaListRepository Interface.
	 * 
	 * @param id ID whose CriteriaList to be fetched.
	 * @return CriteriaList If the JPA repository method is successful, else throws
	 *         ResourceNotFoundException with a message "Criteria list Not Found".
	 */
	@Override
	public CriteriaList findCriteriaListById(long id) {
		Optional<CriteriaList> optionalCriteriaList = criteriaListRepository.findById(id);
		if (optionalCriteriaList.isPresent()) {
			return optionalCriteriaList.get();
		} else {
			throw new ResourceNotFoundException("Criteria list Not Found");
		}
	}

	/**
	 * This method is used to return the List of CriteriaList objects by multiple
	 * IDs using findByIdIn() method of the CriteriaListRepository Interface.
	 * 
	 * @param criteriaIdList List of IDs whose CriteriaList objects are to be
	 *                       fetched.
	 * @return List<CriteriaList> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<CriteriaList> listCriteriaListsByIds(List<Long> criteriaIdList) {
		try {
			return (List<CriteriaList>) criteriaListRepository.findByIdIn(criteriaIdList);
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}
}
