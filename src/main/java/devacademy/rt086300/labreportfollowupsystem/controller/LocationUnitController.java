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

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.BedRoomLocation;
import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;
import devacademy.rt086300.labreportfollowupsystem.service.LocationUnitService;

/**
 * This is a restful controller class that controls the data flow of the
 * LocationUnit model.
 * 
 * @author RT086300
 *
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LocationUnitController {
	private static final Logger log = LoggerFactory.getLogger(LocationUnitController.class);
	private LocationUnitService locationUnitService;

	/**
	 * A setter function to initialize the locationUnitService object. This setter
	 * is autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param locationUnitService This is the object that is autowired to the object
	 *                            of the LocationUnitController class
	 */
	@Autowired
	public void setLocationUnitService(LocationUnitService locationUnitService) {
		this.locationUnitService = locationUnitService;
	}

	/**
	 * This function is to fetch all the location units from the schema. It uses
	 * listAllLocationUnits() method of the locationUnitService object.
	 * 
	 * This method is a GET request to the route "/locationunits".
	 * 
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of location unit objects with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/locationunits")
	public ResponseEntity<List<LocationUnit>> fetchAllLocationUnits() {
		try {
			List<LocationUnit> locationUnitLists = locationUnitService.listAllLocationUnits();
			log.info("GET /locationunits 200 OK - Fetched location units successfully");
			return new ResponseEntity<>(locationUnitLists, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /locationunits 500 INTERNAL_SERVER_ERROR - Error in fetching location units");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch a location unit with ID. It uses
	 * findLocationUnitById() method of the locationUnitService object.
	 * 
	 * This method is a GET request to the route "/locationunits/{id}" where id is a
	 * path variable (i.e) LocationUnit ID.
	 * 
	 * @param id This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found location unit object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ResourceNotFoundException with a message
	 *         "Location unit not found".
	 */
	@GetMapping("/locationunits/{id}")
	public ResponseEntity<LocationUnit> getLocationUnit(@PathVariable("id") long id) {
		try {
			LocationUnit locationUnit = locationUnitService.findLocationUnitById(id);
			log.info("GET /locationunits/" + Long.toString(id) + " 200 OK - Fetched location unit by ID successfully");
			return new ResponseEntity<LocationUnit>(locationUnit, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /locationunits/" + Long.toString(id)
					+ " 404 NOT_FOUND - Error in fetching location unit by ID");
			throw new ResourceNotFoundException("Location unit not found");
		}
	}

	/**
	 * This function is to fetch location units with respect to a Building IDs. It
	 * uses listLocationUnitsByBuildIds() method of the locationUnitService object.
	 * 
	 * This method is a GET request to the route "/locationunits/ids" with a
	 * parameter of the list of building IDs.
	 * 
	 * @param ids This is the list of building IDs for which location units are to
	 *            be fetched.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of location unit objects with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/locationunits/ids")
	public ResponseEntity<List<LocationUnit>> fetchLocationUnitsByBuildIds(@RequestParam List<Long> ids) {
		try {
			List<LocationUnit> locationUnitLists = locationUnitService.listLocationUnitsByBuildIds(ids);
			log.info("GET /locationunits/ids 200 OK - Fetched location units by Multiple IDs successfully");
			return new ResponseEntity<>(locationUnitLists, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"GET /locationunits/ids 500 INTERNAL_SERVER_ERROR - Error in fetching location units by Multiple IDs");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch a location unit, bed, room, and summary details of a
	 * Patient with Patient ID. It uses findByBedId() method of the
	 * locationUnitService object.
	 * 
	 * This method is a GET request to the route "/locationunits/bed/{patId}" where
	 * patId is a path variable (i.e) Patient ID.
	 * 
	 * @param patId This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the BedRoomLocation object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ResourceNotFoundException with a message
	 *         "Location unit not found".
	 */
	@GetMapping("/locationunits/bed/{patId}")
	public ResponseEntity<BedRoomLocation> fetchLocationUnitByBedId(@PathVariable("patId") long patId) {
		try {
			BedRoomLocation _bedRoomLocation = locationUnitService.findByBedId(patId);
			log.info("GET /locationunits/bed/" + Long.toString(patId)
					+ " 200 OK - Fetched location unit by Bed ID successfully");
			return new ResponseEntity<BedRoomLocation>(_bedRoomLocation, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /locationunits/bed/" + Long.toString(patId)
					+ " 404 NOT_FOUND - Error in fetching location unit by Bed ID");
			throw new ResourceNotFoundException("Location unit not found");
		}
	}
}
