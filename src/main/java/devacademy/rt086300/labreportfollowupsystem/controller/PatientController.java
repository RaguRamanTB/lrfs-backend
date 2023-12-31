package devacademy.rt086300.labreportfollowupsystem.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.ErrorDetails;
import devacademy.rt086300.labreportfollowupsystem.model.Patient;
import devacademy.rt086300.labreportfollowupsystem.service.PatientService;

/**
 * This is a restful controller class that controls the data flow of the Patient
 * model.
 * 
 * @author RT086300
 *
 */
@CrossOrigin(origins = "https://lab-report-follow-up-system.vercel.app")
@RestController
@RequestMapping("/api")
public class PatientController {
	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	private PatientService patientService;

	/**
	 * A setter function to initialize the patientService object. This setter is
	 * autowired with @Autowired annotation to inject the object dependency
	 * implicitly.
	 * 
	 * @param patientService This is the object that is autowired to the object of
	 *                       the PatientController class
	 */
	@Autowired
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * This function is to fetch all the patients from the schema. It uses
	 * listAllPatients() method of the patientService object.
	 * 
	 * This method is a GET request to the route "/patients".
	 * 
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of list of patient objects with HTTP Status 200 (OK). If the request
	 *         failed, throws a ServerException with HTTP Status 500
	 *         (INTERNAL_SERVER_ERROR).
	 */
	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> fetchAllPatients() {
		try {
			List<Patient> patients = patientService.listAllPatients();
			log.info("GET /patients 200 OK - Fetched patients successfully");
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patients 500 INTERNAL_SERVER_ERROR - Error in fetching patients");
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This function is to fetch a patient with ID. It uses findPatientById() method
	 * of the patientService object.
	 * 
	 * This method is a GET request to the route "/patients/{id}" where id is a path
	 * variable (i.e) Patient ID.
	 * 
	 * @param id This is the path variable from the route.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the found patient object with HTTP Status 200 (OK). If the request
	 *         failed, throws a ResourceNotFoundException with a message "Patient
	 *         not found".
	 */
	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable("id") long id) {
		try {
			Patient _patient = patientService.findPatientById(id);
			log.info("GET /patients/" + Long.toString(id) + " 200 OK - Fetched patient by ID successfully");
			return new ResponseEntity<Patient>(_patient, HttpStatus.OK);
		} catch (Exception e) {
			log.error("GET /patients/" + Long.toString(id) + " 404 NOT_FOUND - Error in fetching patient by ID");
			throw new ResourceNotFoundException("Patient not found");
		}
	}

	/**
	 * This function is to register a patient into the database. It uses
	 * createPatient() method of the patientService object.
	 * 
	 * This method is a POST request to the route "/patients" with a request body of
	 * the patient details in JSON format.
	 * 
	 * @param patient This is the request body which contains the patient details as
	 *                a JSON. Spring boot internally converts the JSON into Object
	 *                using Jackson and Object Mapper.
	 * @return ResponseEntity If the request is successful, sends a response entity
	 *         of the patient object with HTTP Status 201 (CREATED). If validation
	 *         fails, it sends a BadArgumentsException with a response entity with
	 *         ErrorDetails object and response code of 400 (BAD_REQUEST).
	 */
	@PostMapping("/patients")
	public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient) {
		Patient duplicate = patientService.findPatientByPatContact(patient.getPAT_CONTACT());
		if (duplicate == null) {
			Patient _patient = patientService.createPatient(patient);
			log.info("POST /patients 201 CREATED - Created patient successfully");
			return new ResponseEntity<>(_patient, HttpStatus.CREATED);
		} else {
			ErrorDetails error = new ErrorDetails(new Date(),
					"Duplicate patient registration, a patient has been already registered with same mobile number. Patient's mobile number needs to be unique.",
					duplicate);
			log.info("POST /patients 400 BAD_REQUEST - Duplicate Patient Found");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
}
