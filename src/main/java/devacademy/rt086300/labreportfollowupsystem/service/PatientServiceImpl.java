package devacademy.rt086300.labreportfollowupsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Patient;
import devacademy.rt086300.labreportfollowupsystem.repository.PatientRepository;

/**
 * This is a service implementation class of the Patient service, that uses
 * PatientRepository to execute database queries in the Patient Entity.
 * 
 * @author RT086300
 *
 */
@Service
public class PatientServiceImpl implements PatientService {
	/**
	 * PatientRepository Interface that extends JPARepository with Patient entity.
	 * This interface is autowired with @Autowired annotation to inject the object
	 * dependency implicitly.
	 */
	@Autowired
	private PatientRepository patientRepository;

	/**
	 * This method is used to return the List of all Patient objects in the schema
	 * using findAll() method of the PatientRepository Interface.
	 * 
	 * @return List<Patient> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<Patient> listAllPatients() {
		try {
			return (List<Patient>) patientRepository.findAll();
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the Patient by its ID using findById() method
	 * of the PatientRepository Interface.
	 * 
	 * @param id ID whose Patient to be fetched.
	 * @return Patient If the JPA repository method is successful, else throws
	 *         ResourceNotFoundException with a message "Patient not found".
	 */
	@Override
	public Patient findPatientById(long id) {
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			return optionalPatient.get();
		} else {
			throw new ResourceNotFoundException("Patient not found");
		}
	}

	@Override
	public Patient findPatientByPatContact(String patContact) {
		Optional<Patient> optionalPatient = patientRepository.findByPatContact(patContact);
		if (optionalPatient.isPresent()) {
			return optionalPatient.get();
		} else {
			return null;
		}
	}

	/**
	 * This method is used to create a patient using save() method of the
	 * PatientRepository Interface.
	 * 
	 * @param patient Patient object that has the details of the patient that are to
	 *                be inserted into the Patient Table.
	 * @return Patient If saved successfully, it returns the created Patient object.
	 *         If validation fails, it throws MethodArgumentNotValidException, since
	 *         validation is done in Request Body.
	 */
	@Override
	public Patient createPatient(Patient patient) {
		LocalDate date = LocalDate.parse(patient.getPAT_DOB().toString());
		return patientRepository.save(new Patient(patient.getPAT_FIRSTNAME(), patient.getPAT_LASTNAME(),
				patient.getPAT_AGE(), patient.getPAT_ADDRESS(), patient.getPAT_BLOODGROUP(), patient.getPAT_CONTACT(),
				patient.getPAT_GENDER(), date, patient.getPAT_EMAIL(), patient.getPAT_INSURANCE_ID(),
				patient.getPAT_HEIGHT(), patient.getPAT_WEIGHT()));
	}

}
