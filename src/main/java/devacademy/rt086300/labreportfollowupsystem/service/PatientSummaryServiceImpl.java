package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.LabTest;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;
import devacademy.rt086300.labreportfollowupsystem.repository.LabTestRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.PatientSummaryRepository;

/**
 * This is a service implementation class of the PatientSummary service, that
 * uses PatientSummaryRepository, and LabTestRepository to execute database
 * queries in the PatientSummary, and LabTest Entities respectively.
 * 
 * @author RT086300
 *
 */
@Service
public class PatientSummaryServiceImpl implements PatientSummaryService {

	/**
	 * PatientSummaryRepository Interface that extends JPARepository with
	 * PatientSummary entity. This interface is autowired with @Autowired annotation
	 * to inject the object dependency implicitly.
	 */
	@Autowired
	PatientSummaryRepository patientSummaryRepository;

	/**
	 * LabTestRepository Interface that extends JPARepository with LabTest entity.
	 * This interface is autowired with @Autowired annotation to inject the object
	 * dependency implicitly.
	 */
	@Autowired
	LabTestRepository labTestRepository;

	/**
	 * This method is used to return the List of all PatientSummary in the schema
	 * using findAll() method of the PatientSummaryRepository Interface.
	 * 
	 * @return List<PatientSummary> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<PatientSummary> listAllPatientSummaries() {
		try {
			return (List<PatientSummary>) patientSummaryRepository.findAll();
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the List of PatientSummary objects by Patient
	 * ID using findByPatId() method of the PatientSummaryRepository Interface.
	 * 
	 * @param id Patient ID whose PatientSummary objects are to be fetched.
	 * @return List<PatientSummary> If the JPA repository method is successful, else
	 *         throws ResourceNotFoundException with a message "Patient Summary Not
	 *         Found".
	 */
	@Override
	public List<PatientSummary> listAllPatientSummariesByPatId(long id) {
		try {
			return (List<PatientSummary>) patientSummaryRepository.findByPatId(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Patient Summary Not Found");
		}
	}

	/**
	 * This method is used to return the List of Patient IDs by discharge status and
	 * multiple Bed IDs using findBybIdInAndIsDischarged() method of the
	 * PatientSummaryRepository Interface.
	 * 
	 * The repository method retrieves the patient objects. It is then iterated to
	 * fetch the ID and then add it to a List.
	 * 
	 * @param bedIdList    List of Bed IDs whose Patient IDs are to be fetched.
	 * @param isDischarged Discharge status of the patient (1 - Discharged, 0 - Not
	 *                     Discharged)
	 * @return List<Long> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<Long> listByBedIdAndDischarged(List<Long> bedIdList, int isDischarged) {
		try {
			List<PatientSummary> dischargedPatients = (List<PatientSummary>) patientSummaryRepository
					.findBybIdInAndIsDischarged(bedIdList, isDischarged);
			List<Long> patientIds = new ArrayList<Long>();
			for (PatientSummary pat : dischargedPatients) {
				patientIds.add(pat.getPAT_ID());
			}
			return patientIds;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the List of Patient IDs mapped with their tests
	 * by discharge status, test notified and multiple Bed IDs using
	 * findByPatIdInAndIsNotified() method of the LabTestRepository Interface. The
	 * Bed ID list is fetched with the help of listByBedIdAndDischarged() service.
	 * 
	 * The repository method retrieves the list of LabTest objects. It is then
	 * iterated to be mapped with the Patient ID.
	 * 
	 * @param bedIdList    List of Bed IDs whose Patient IDs are to be fetched.
	 * @param isNotified   Notification status of the Test (1 - Notified, 0 - Not
	 *                     Notified)
	 * @param isDischarged Discharge status of the patient (1 - Discharged, 0 - Not
	 *                     Discharged)
	 * @return HashMap<Long, List<LabTest>> If the JPA repository method is
	 *         successful, else throws ServerException with a message "Internal
	 *         Server Error".
	 */
	@Override
	public HashMap<Long, List<LabTest>> listByBedIdAndNotifiedAndDischarged(List<Long> bedIdList, int isNotified,
			int isDischarged) {
		try {
			List<Long> patientIds = listByBedIdAndDischarged(bedIdList, isDischarged);
			List<LabTest> tests = labTestRepository.findByPatIdInAndIsNotified(patientIds, isNotified);
			HashMap<Long, List<LabTest>> patientTestMap = new HashMap<Long, List<LabTest>>();
			for (LabTest test : tests) {
				patientTestMap.putIfAbsent(test.getPAT_ID(), new ArrayList<LabTest>());
				patientTestMap.get(test.getPAT_ID()).add(test);
			}
			return patientTestMap;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the List of LabTest objects of a Patient by
	 * Patient ID using findByPatId() method of the LabTestRepository Interface.
	 * 
	 * @param patId Patient ID whose LabTests are to be fetched.
	 * @return List<LabTest> If the JPA repository method is successful, else throws
	 *         ResourceNotFoundException with a message "Lab tests for patient not
	 *         found".
	 */
	@Override
	public List<LabTest> listTestsByPatId(long patId) {
		try {
			return (List<LabTest>) labTestRepository.findByPatId(patId);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Lab tests for patient not found");
		}
	}

	/**
	 * This method is used to return the LabTest by its ID using findById() method
	 * of the LabTestRepository Interface.
	 * 
	 * @param id ID whose LabTest is to be fetched.
	 * @return LabTest If the JPA repository method is successful, else throws
	 *         ResourceNotFoundException with a message "Test Not Found".
	 */
	@Override
	public LabTest findLabTestById(long id) {
		Optional<LabTest> optionalLabTest = labTestRepository.findById(id);
		if (optionalLabTest.isPresent()) {
			return optionalLabTest.get();
		} else {
			throw new ResourceNotFoundException("Test Not Found");
		}
	}

	/**
	 * This method is used to update the LabTest objects of a Patient by Patient ID
	 * to set their notification status to NOTIFIED using save() method of the
	 * LabTestRepository Interface.
	 * 
	 * The List of LabTest objects are first fetched by listTestsByPatId() service
	 * and then iterated, updates the isNotified integer to 1 of that object and
	 * saves that object to the table.
	 * 
	 * @param patId Patient ID whose LabTests are to be updated.
	 * @return List<LabTest> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<LabTest> updateLabTest(long patId) {
		try {
			List<LabTest> labTests = listTestsByPatId(patId);
			for (LabTest labTest : labTests) {
				labTest.setIsNotified(1);
				labTestRepository.save(labTest);
			}
			return labTests;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to update the LabTest objects of a Patient by Patient ID
	 * to set their notification status to NOT NOTIFIED using save() method of the
	 * LabTestRepository Interface.
	 * 
	 * The List of LabTest objects are first fetched by listTestsByPatId() service
	 * and then iterated, updates the isNotified integer to 0 of that object and
	 * saves that object to the table.
	 * 
	 * @param patId Patient ID whose LabTests are to be updated.
	 * @return List<LabTest> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<LabTest> revertLabTest(long patId) {
		try {
			List<LabTest> labTests = listTestsByPatId(patId);
			for (LabTest labTest : labTests) {
				labTest.setIsNotified(0);
				labTestRepository.save(labTest);
			}
			return labTests;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}
}
