package devacademy.rt086300.labreportfollowupsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devacademy.rt086300.labreportfollowupsystem.service.CriteriaListService;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;

/**
 * This is a restful controller class that controls the data flow of the
 * CriteriaList model.
 * 
 * @author RT086300
 *
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CriteriaListController {
	private static final Logger log = LoggerFactory.getLogger(CriteriaListController.class);
	private CriteriaListService criteriaListService;

	/**
	 * A setter function to initialize the criteriaListService object. This setter
	 * is autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param criteriaListService This is the object that is autowired to the object
	 *                            of the CriteriaListController class
	 */
	@Autowired
	public void setCriteriaListService(CriteriaListService criteriaListService) {
		this.criteriaListService = criteriaListService;
	}

	/**
	 * This function is to fetch all the criteria lists from the schema. It uses
	 * listAllCriteriaLists() method of the criteriaListService object.
	 * 
	 * This method is a GET request to the route "/criterialists".
	 * 
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of criteria list objects with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/criterialists")
	public ResponseEntity<List<CriteriaList>> fetchAllCriteriaLists() {
		try {
			List<CriteriaList> criteriaLists = criteriaListService.listAllCriteriaLists();
			log.info("GET /criterialists 200 OK - Fetched criteria lists successfully");
			return new ResponseEntity<>(criteriaLists, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /criterialists 500 INTERNAL_SERVER_ERROR - Error in fetching criteria lists");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch a criteria list with ID. It uses
	 * findCriteriaListById() method of the criteriaListService object.
	 * 
	 * This method is a GET request to the route "/criterialists/{id}" where id is a
	 * path variable (i.e) CriteriaList ID.
	 * 
	 * @param id This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found criteria list object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ResourceNotFoundException with a message
	 *         "Criteria list not found".
	 */
	@GetMapping("/criterialists/{id}")
	public ResponseEntity<CriteriaList> getCriteriaList(@PathVariable("id") long id) {
		try {
			log.info("GET /criterialists/" + Long.toString(id) + " 200 OK - Fetched criteria list by ID successfully");
			return new ResponseEntity<CriteriaList>(criteriaListService.findCriteriaListById(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /criterialists/" + Long.toString(id)
					+ " 404 NOT_FOUND - Error in fetching criteria list by ID");
			throw new ResourceNotFoundException("Criteria list not found");
		}
	}

	/**
	 * This function is to fetch criteria lists with multiple IDs. It uses
	 * listCriteriaListsByIds() method of the criteriaListService object.
	 * 
	 * This method is a GET request to the route "/criterialists/ids" with a
	 * parameter of the list of criteria list IDs.
	 * 
	 * @param ids This is the list of criteria list IDs for which criteria lists are
	 *            to be fetched.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of criteria list objects with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/criterialists/ids")
	public ResponseEntity<List<CriteriaList>> fetchCriteriaListsByIds(@RequestParam List<Long> ids) {
		try {
			List<CriteriaList> criteriaLists = criteriaListService.listCriteriaListsByIds(ids);
			log.info("GET /criterialists/ids 200 OK - Fetched criteria lists by Multiple IDs successfully");
			return new ResponseEntity<>(criteriaLists, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"GET /criterialists/ids 500 INTERNAL_SERVER_ERROR - Error in fetching criteria lists by Multiple IDs");
			throw new ServerException("Internal Server Error");
		}
	}
}
