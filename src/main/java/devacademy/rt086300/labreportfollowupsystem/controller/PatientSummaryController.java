package devacademy.rt086300.labreportfollowupsystem.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.model.LabTest;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;
import devacademy.rt086300.labreportfollowupsystem.service.CommentsService;
import devacademy.rt086300.labreportfollowupsystem.service.LocationUnitService;
import devacademy.rt086300.labreportfollowupsystem.service.PatientSummaryService;

/**
 * This is a restful controller class that controls the data flow of the
 * PatientSummary and Comments model.
 * 
 * @author RT086300
 *
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Validated
@RequestMapping("/api")
public class PatientSummaryController {
	private static final Logger log = LoggerFactory.getLogger(PatientSummaryController.class);

	private PatientSummaryService patientSummaryService;
	private LocationUnitService locationUnitService;
	private CommentsService commentsService;

	/**
	 * A setter function to initialize the patientSummaryService object. This setter
	 * is autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param patientSummaryService This is the object that is autowired to the
	 *                              object of the PatientSummaryController class
	 */
	@Autowired
	public void setPatientSummaryService(PatientSummaryService patientSummaryService) {
		this.patientSummaryService = patientSummaryService;
	}

	/**
	 * A setter function to initialize the commentsService object. This setter is
	 * autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param commentsService This is the object that is autowired to the object of
	 *                        the PatientSummaryController class
	 */
	@Autowired
	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

	/**
	 * A setter function to initialize the locationUnitService object. This setter
	 * is autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param locationUnitService This is the object that is autowired to the object
	 *                            of the PatientSummaryController class
	 */
	@Autowired
	public void setLocationUnitService(LocationUnitService locationUnitService) {
		this.locationUnitService = locationUnitService;
	}

	/**
	 * This function is to fetch all the patient summaries from the schema. It uses
	 * listAllPatientSummaries() method of the patientSummaryService object.
	 * 
	 * This method is a GET request to the route "/patientsummary".
	 * 
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of patient summary objects with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/patientsummary")
	public ResponseEntity<List<PatientSummary>> fetchAllPatientSummaries() {
		try {
			List<PatientSummary> patientSummaries = patientSummaryService.listAllPatientSummaries();
			log.info("GET /patientsummary 200 OK - Fetched patient summaries successfully");
			return new ResponseEntity<List<PatientSummary>>(patientSummaries, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patientsummary 500 INTERNAL_SERVER_ERROR - Error in fetching patient summaries");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch patient summaries with Patient ID. It uses
	 * listAllPatientSummariesByPatId() method of the patientSummaryService object.
	 * 
	 * This method is a GET request to the route "/patientsummary/{patId}" where id
	 * is a path variable (i.e) Patient ID.
	 * 
	 * @param patId This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found list of patient summary object with HTTP Status 200
	 *         (OK). If the request failed, throws a ResourceNotFoundException with
	 *         a message "Patient Summary Not Found".
	 */
	@GetMapping("/patientsummary/{patId}")
	public ResponseEntity<List<PatientSummary>> fetchAllPatientSummariesByPatId(@PathVariable("patId") long patId) {
		try {
			List<PatientSummary> patientSummaries = patientSummaryService.listAllPatientSummariesByPatId(patId);
			log.info("GET /patientsummary/" + Long.toString(patId)
					+ " 200 OK - Fetched patient summaries by Patient ID successfully");
			return new ResponseEntity<List<PatientSummary>>(patientSummaries, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patientsummary/" + Long.toString(patId)
					+ " 404 NOT_FOUND - Error in fetching patient summaries by Patient ID");
			throw new ResourceNotFoundException("Patient Summary Not Found");
		}
	}

	/**
	 * This function is to fetch lab tests corresponding to the patients with
	 * respect to specific location units.
	 * 
	 * It uses listBedIds() method of the locationUnitService object to list the Bed
	 * IDs of the given Location IDs.
	 * 
	 * It uses listByBedIdAndNotifiedAndDischarged() method of the
	 * patientSummaryService object to map the lab tests with patients of
	 * corresponding Bed IDs, test notification status and discharge status.
	 * 
	 * This method is a GET request to the route "/patientsummary/status" with
	 * parameters of Location ID list, Test notification status and Discharge
	 * status.
	 * 
	 * @param locationIdList This is the list of Location IDs for which Bed IDs are
	 *                       to be fetched
	 * @param isNotified     This is the notification status of the test
	 * @param isDischarged   This is the discharge status of the patient
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of a HashMap with Patient ID, mapped with the list of their lab tests
	 *         with HTTP Status 200 (OK). If the request failed, throws a
	 *         ResourceNotFoundException with a message "Patient Summary Not Found".
	 */
	@GetMapping("/patientsummary/status")
	public ResponseEntity<HashMap<Long, List<LabTest>>> fetchByBedIdAndNotifiedAndDischarged(
			@RequestParam("locationIds") List<Long> locationIdList, @RequestParam("notified") int isNotified,
			@RequestParam("discharged") int isDischarged) {
		try {
			List<Long> bedIdList = locationUnitService.listBedIds(locationIdList);
			log.info("GET /patientsummary/status - Fetched Bed ID List of the given Location IDs successfully");
			HashMap<Long, List<LabTest>> patientSummaries = patientSummaryService
					.listByBedIdAndNotifiedAndDischarged(bedIdList, isNotified, isDischarged);
			log.info("GET /patientsummary/status 200 OK - Fetched Patients and their lab tests successfully");
			return new ResponseEntity<HashMap<Long, List<LabTest>>>(patientSummaries, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"GET /patientsummary/status 500 INTERNAL_SERVER_ERROR - Error in fetching Patients and their lab tests");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch lab tests corresponding to a specific patient. It
	 * uses listTestsByPatId() method of the patientSummaryService object to list
	 * the lab tests of a patient.
	 * 
	 * This method is a GET request to the route "/patientsummary/tests/{patId}"
	 * where id is a path variable (i.e) Patient ID.
	 * 
	 * @param patId This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found list of lab test object with HTTP Status 200 (OK). If
	 *         the request failed, throws a ResourceNotFoundException with a message
	 *         "Lab tests for patient not found".
	 */
	@GetMapping("/patientsummary/tests/{patId}")
	public ResponseEntity<List<LabTest>> fetchLabTestsByPatId(@PathVariable("patId") long patId) {
		try {
			List<LabTest> labTests = patientSummaryService.listTestsByPatId(patId);
			log.info("GET /patientsummary/tests/" + Long.toString(patId)
					+ " 200 OK - Fetched lab tests by Patient ID successfully");
			return new ResponseEntity<List<LabTest>>(labTests, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patientsummary/tests/" + Long.toString(patId)
					+ " 404 NOT_FOUND - Error in fetching lab tests by Patient ID");
			throw new ResourceNotFoundException("Lab tests for patient not found");
		}
	}

	/**
	 * This function is to update lab tests and add comments corresponding to a
	 * specific patient.
	 * 
	 * It uses updateLabTest() method of the patientSummaryService object to update
	 * the notification status of the lab test. The updateLabTest() method is called
	 * only when "complete" parameter is 1.
	 * 
	 * It uses createComment() method of the commentsService object to create a
	 * comment for a patient.
	 * 
	 * This method is a PUT request to the route "/patientsummary/update/{patId}"
	 * where id is a path variable (i.e) Patient ID, and with parameters of status,
	 * comment and completion of the patient records.
	 * 
	 * @param patId    This is the path variable from the route.
	 * @param status   This is the status to be added to the Comments object.
	 * @param comment  This is the comment to be added to the Comments object.
	 * @param complete This is the complete status which is either 1 or 0 to
	 *                 indicate the patient has been notified with their tests.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the list of comment object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with a message "Internal
	 *         Server Error".
	 */
	@PutMapping("/patientsummary/update/{patId}")
	public ResponseEntity<Object> updateTest(@PathVariable("patId") long patId,
			@RequestParam("status") @NotNull @NotEmpty String status,
			@RequestParam("comment") @NotNull @NotEmpty String comment,
			@RequestParam("complete") @NotNull int complete) {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		if (complete == 1) {
			patientSummaryService.updateLabTest(patId);
			Comments commentDetail = new Comments(patId, status, comment, commentMod);
			commentsService.createComment(commentDetail);
			List<Comments> comments = commentsService.listCommentsByPatId(patId);
			log.info("GET /patientsummary/update/" + Long.toString(patId)
					+ " 200 OK - Updated lab tests and created comments successfully");
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else if (complete == 0) {
			Comments commentDetail = new Comments(patId, status, comment, commentMod);
			commentsService.createComment(commentDetail);
			List<Comments> comments = commentsService.listCommentsByPatId(patId);
			log.info("GET /patientsummary/update/" + Long.toString(patId) + " 200 OK - Created comments successfully");
			return new ResponseEntity<>(comments, HttpStatus.OK);
		}
		log.error("GET /patientsummary/update/" + Long.toString(patId)
				+ " 500 INTERNAL_SERVER_ERROR - Error in updating lab test and creating comments");
		throw new ServerException("Internal Server Error");
	}

	/**
	 * This function is to revert lab tests notification statuses and add comments
	 * corresponding to a specific patient.
	 * 
	 * It uses revertLabTest() method of the patientSummaryService object to revert
	 * the notification status of the lab test.
	 * 
	 * It uses createComment() method of the commentsService object to create a
	 * comment for a patient.
	 * 
	 * This method is a PUT request to the route "/patientsummary/revert/{patId}"
	 * where id is a path variable (i.e) Patient ID, and with parameters of status,
	 * and comment.
	 * 
	 * @param patId   This is the path variable from the route.
	 * @param status  This is the status to be added to the Comments object.
	 * @param comment This is the comment to be added to the Comments object.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the list of comment object with HTTP Status 200 (OK). If the
	 *         request failed, throws a ServerException with a message "Internal
	 *         Server Error".
	 */
	@PutMapping("/patientsummary/revert/{patId}")
	public ResponseEntity<Object> revertTest(@PathVariable("patId") long patId,
			@RequestParam("status") @NotNull @NotEmpty String status,
			@RequestParam("comment") @NotNull @NotEmpty String comment) {
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		try {
			patientSummaryService.revertLabTest(patId);
			Comments commentDetail = new Comments(patId, status, comment, commentMod);
			commentsService.createComment(commentDetail);
			List<Comments> comments = commentsService.listCommentsByPatId(patId);
			log.info("GET /patientsummary/revert/" + Long.toString(patId)
					+ " 200 OK - Updated lab tests and created comments successfully");
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patientsummary/revert/" + Long.toString(patId)
					+ " 500 INTERNAL_SERVER_ERROR - Error in updating lab test and creating comments");
			throw new ServerException("Internal Server Error");
		}
	}
}
