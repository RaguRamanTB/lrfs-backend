package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;

import devacademy.rt086300.labreportfollowupsystem.model.Patient;

/**
 * An interface PatientService, to abstract the methods. The implementations are
 * in PatientServiceImpl class.
 * 
 * @author RT086300
 *
 */
public interface PatientService {
	List<Patient> listAllPatients();

	Patient findPatientById(long id);

	Patient createPatient(Patient patient);
	
	Patient findPatientByPatContact(String patContact);
}
