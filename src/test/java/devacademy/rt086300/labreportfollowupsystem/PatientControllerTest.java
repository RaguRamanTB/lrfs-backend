package devacademy.rt086300.labreportfollowupsystem;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

import devacademy.rt086300.labreportfollowupsystem.controller.PatientController;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Patient;
import devacademy.rt086300.labreportfollowupsystem.service.PatientServiceImpl;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PatientServiceImpl patientService;

	private Patient patient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve",
			"+919898765611", "MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);

	@Test
	void getAllPatientsSuccess() throws Exception {
		Patient p1 = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919898765611", "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		Patient p2 = new Patient("Marren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964", "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@gmail.com", "DS123115", 170, 81);
		List<Patient> patients = new ArrayList<Patient>();
		patients.add(p1);
		patients.add(p2);
		when(patientService.listAllPatients()).thenReturn(patients);
		this.mockMvc.perform(get("/api/patients")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(patients)));
	}

	@Test
	void getAllPatientsFailure() throws Exception {
		when(patientService.listAllPatients()).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/patients")).andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getPatientByIdSuccess() throws Exception {
		when(patientService.findPatientById(2)).thenReturn(this.patient);
		this.mockMvc.perform(get("/api/patients/2").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(this.patient)));
	}

	@Test
	void getPatientByIdFailure() throws Exception {
		when(patientService.findPatientById(23)).thenThrow(new ResourceNotFoundException("Patient not found"));
		this.mockMvc.perform(get("/api/patients/23").contentType("application/json")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Patient not found", result.getResolvedException().getMessage()));
	}

	@Test
	void registerPatientSuccess() throws Exception {
		when(patientService.createPatient(this.patient)).thenReturn(this.patient);
		this.mockMvc.perform(post("/api/patients").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(this.patient))).andExpect(status().isCreated());
	}

	@Test
	void registerPatientWithNoFirstName() throws Exception {
		Patient badPatient = new Patient(null, "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's firstname should not be null")));
	}

	@Test
	void registerPatientWithEmptyFirstName() throws Exception {
		Patient badPatient = new Patient("", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's firstname should not be empty")));
	}

	@Test
	void registerPatientWithAlphanumericFirstName() throws Exception {
		Patient badPatient = new Patient("Ragu1234Raman", "Sammy", 48, "812 Payton Square River Lane", "O+ve",
				"+919443822964", "MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170,
				81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Patient's firstname cannot have alphanumeric characters or any special characters.")));
	}

	@Test
	void registerPatientWithNoLastName() throws Exception {
		Patient badPatient = new Patient("Darren", null, 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's lastname should not be null")));
	}

	@Test
	void registerPatientWithEmptyLastName() throws Exception {
		Patient badPatient = new Patient("Darren", "", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's lastname should not be empty")));
	}

	@Test
	void registerPatientWithAlphanumericLastName() throws Exception {
		Patient badPatient = new Patient("Ragu", "Sam1234", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Patient's lastname cannot have alphanumeric characters or any special characters.")));
	}

	@Test
	void registerPatientWithNegativeAge() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", -10, "812 Payton Square River Lane", "O+ve",
				"+919443822964", "MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170,
				81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's age should be greater or equal to 0")));
	}

	@Test
	void registerPatientWithNoAddress() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, null, "O+ve", "+919443822964", "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's address should not be null")));
	}

	@Test
	void registerPatientWithEmptyAddress() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "", "O+ve", "+919443822964", "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's address should not be empty")));
	}

	@Test
	void registerPatientWithNoBloodgroup() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", null, "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's bloodgroup should not be null")));
	}

	@Test
	void registerPatientWithEmptyBloodgroup() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's bloodgroup should not be empty")));
	}

	@Test
	void registerPatientWithNoContact() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", null, "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's contact information should not be null")));
	}

	@Test
	void registerPatientWithEmptyContact() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "", "MALE",
				LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's contact information should not be empty")));
	}

	@Test
	void registerPatientWithLongContactNumberLength() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve",
				"+91999991111100000", "MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115",
				170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.")));
	}

	@Test
	void registerPatientWithShortContactNumberLength() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+9111100000",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.")));
	}

	@Test
	void registerPatientWithInvalidContactNumber() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+911111100000",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.")));
	}

	@Test
	void registerPatientWithInvalidPrefixContactNumber() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+119443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString(
						"Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.")));
	}

	@Test
	void registerPatientWithNoGender() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				null, LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's gender should not be null")));
	}

	@Test
	void registerPatientWithEmptyGender() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"", LocalDate.parse("1973-09-11"), "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's gender should not be empty")));
	}

	@Test
	void registerPatientWithNoDOB() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", null, "unolfsdotattrb@hickle.com", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Patient's Date of birth should not be null")));
	}

	@Test
	void registerPatientWithInvalidEmail() throws Exception {
		Patient badPatient = new Patient("Darren", "Sammy", 48, "812 Payton Square River Lane", "O+ve", "+919443822964",
				"MALE", LocalDate.parse("1973-09-11"), "unolfsdotattrb@", "DS123115", 170, 81);
		this.mockMvc
				.perform(post("/api/patients")
						.contentType("application/json").content(this.objectMapper.writeValueAsString(badPatient)))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().string(containsString("Valid email address must be given")));
	}
}
