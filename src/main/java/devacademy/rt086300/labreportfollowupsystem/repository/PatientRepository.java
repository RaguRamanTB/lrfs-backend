package devacademy.rt086300.labreportfollowupsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devacademy.rt086300.labreportfollowupsystem.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	Optional<Patient> findByPatContact(String patContact);
}
