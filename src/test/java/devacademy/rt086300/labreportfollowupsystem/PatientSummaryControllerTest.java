package devacademy.rt086300.labreportfollowupsystem;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import devacademy.rt086300.labreportfollowupsystem.controller.PatientSummaryController;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Comments;
import devacademy.rt086300.labreportfollowupsystem.model.LabTest;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;
import devacademy.rt086300.labreportfollowupsystem.service.CommentsServiceImpl;
import devacademy.rt086300.labreportfollowupsystem.service.LocationUnitServiceImpl;
import devacademy.rt086300.labreportfollowupsystem.service.PatientSummaryServiceImpl;

@WebMvcTest(PatientSummaryController.class)
class PatientSummaryControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PatientSummaryServiceImpl patientSummaryService;

	@MockBean
	private LocationUnitServiceImpl locationUnitService;

	@MockBean
	private CommentsServiceImpl commentsService;

	@Test
	void getAllPatientSummariesSuccess() throws Exception {
		PatientSummary patSum1 = new PatientSummary(1, 1, 1, LocalDate.parse("2021-02-14"), 1,
				LocalDate.parse("2021-02-18"));
		PatientSummary patSum2 = new PatientSummary(2, 2, 4, LocalDate.parse("2021-02-14"), 1,
				LocalDate.parse("2021-02-18"));
		List<PatientSummary> patSumList = new ArrayList<PatientSummary>();
		patSumList.add(patSum1);
		patSumList.add(patSum2);
		when(patientSummaryService.listAllPatientSummaries()).thenReturn(patSumList);
		this.mockMvc.perform(get("/api/patientsummary")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(patSumList)));
	}

	@Test
	void getAllPatientSummariesFailure() throws Exception {
		when(patientSummaryService.listAllPatientSummaries()).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/patientsummary")).andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getPatientSummaryByPatientIdSuccess() throws Exception {
		PatientSummary patSum1 = new PatientSummary(1, 1, 1, LocalDate.parse("2021-02-14"), 1,
				LocalDate.parse("2021-02-18"));
		List<PatientSummary> patSumList = new ArrayList<PatientSummary>();
		patSumList.add(patSum1);
		when(patientSummaryService.listAllPatientSummariesByPatId(1)).thenReturn(patSumList);
		this.mockMvc.perform(get("/api/patientsummary/1").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(patSumList)));
	}

	@Test
	void getPatientSummaryByPatientIdFailure() throws Exception {
		when(patientSummaryService.listAllPatientSummariesByPatId(12345))
				.thenThrow(new ResourceNotFoundException("Patient Summary Not Found"));
		this.mockMvc.perform(get("/api/patientsummary/12345").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Patient Summary Not Found",
						result.getResolvedException().getMessage()));
	}

	@Test
	void getPatientSummaryByStatusSuccess() throws Exception {
		List<Long> bedIds = new ArrayList<Long>();
		bedIds.add((long) 1);
		bedIds.add((long) 2);
		bedIds.add((long) 3);
		bedIds.add((long) 4);
		List<Long> locIds = new ArrayList<Long>();
		locIds.add((long) 1);
		locIds.add((long) 2);
		when(locationUnitService.listBedIds(locIds)).thenReturn(bedIds);
		List<LabTest> labTests = new ArrayList<LabTest>();
		labTests.add(new LabTest(1, 1, "Temperature Oral", "41", "No Status Found", "No Comment Found",
				Timestamp.valueOf("2021-02-17 12:58:07"), "GEN_MEDICINE", 713145, LocalDate.parse("2021-02-17"),
				"35.8-37.3", "degC", 1));
		HashMap<Long, List<LabTest>> patientSummaries = new HashMap<Long, List<LabTest>>();
		for (LabTest test : labTests) {
			patientSummaries.putIfAbsent(test.getPAT_ID(), new ArrayList<LabTest>());
			patientSummaries.get(test.getPAT_ID()).add(test);
		}
		when(patientSummaryService.listByBedIdAndNotifiedAndDischarged(bedIds, 1, 1)).thenReturn(patientSummaries);
		this.mockMvc
				.perform(get("/api/patientsummary/status").contentType("application/json").param("locationIds", "1,2")
						.param("notified", "1").param("discharged", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(patientSummaries)));
	}

	@Test
	void getPatientSummaryByStatusFailure() throws Exception {
		List<Long> bedIds = new ArrayList<Long>();
		when(patientSummaryService.listByBedIdAndNotifiedAndDischarged(bedIds, 1, 1))
				.thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc
				.perform(get("/api/patientsummary/status").contentType("application/json").param("locationIds", "1,2")
						.param("notified", "1").param("discharged", "1"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getTestsByPatientIdSuccess() throws Exception {
		List<LabTest> labTests = new ArrayList<LabTest>();
		labTests.add(new LabTest(1, 1, "Temperature Oral", "41", "No Status Found", "No Comment Found",
				Timestamp.valueOf("2021-02-17 12:58:07"), "GEN_MEDICINE", 713145, LocalDate.parse("2021-02-17"),
				"35.8-37.3", "degC", 1));
		when(patientSummaryService.listTestsByPatId((long) 1)).thenReturn(labTests);
		this.mockMvc.perform(get("/api/patientsummary/tests/1").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(this.objectMapper.writeValueAsString(labTests)));
	}

	@Test
	void getTestsByPatientIdFailure() throws Exception {
		when(patientSummaryService.listTestsByPatId((long) 112312))
				.thenThrow(new ResourceNotFoundException("Lab tests for patient not found"));
		this.mockMvc.perform(get("/api/patientsummary/tests/112312").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Lab tests for patient not found",
						result.getResolvedException().getMessage()));
	}

	@Test
	void updateTestSuccess() throws Exception {
		LabTest labTest = new LabTest(1, 1, "Temperature Oral", "41", "Updated", "Patient contacted",
				Timestamp.valueOf("2021-02-17 12:58:07"), "GEN_MEDICINE", 713145, LocalDate.parse("2021-02-17"),
				"35.8-37.3", "degC", 0);
		labTest.setIsNotified(1);
		List<LabTest> labTests = new ArrayList<LabTest>();
		labTests.add(labTest);
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments commentDetail = new Comments(1, "Sample", "Sample", commentMod);
		Comments _commentDetail = new Comments(1, 1, "Sample", "Sample", commentMod);
		List<Comments> commentList = new ArrayList<Comments>();
		commentList.add(_commentDetail);
		when(patientSummaryService.updateLabTest(1)).thenReturn(labTests);
		when(commentsService.createComment(commentDetail)).thenReturn(_commentDetail);
		when(commentsService.listCommentsByPatId((long) 1)).thenReturn(commentList);
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json").param("status", "Updated")
						.param("comment", "Patient contacted").param("complete", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(commentList)));
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json").param("status", "Updated")
						.param("comment", "Patient contacted").param("complete", "0"))
				.andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(commentList)));
	}

	@Test
	void updateLabTestMissingParamStatusFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1")
						.contentType("application/json").param("comment", "Some comment").param("complete", "1"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals("Required String parameter 'status' is not present",
						result.getResolvedException().getMessage()));
	}

	@Test
	void updateLabTestMissingParamCommentFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1")
						.contentType("application/json").param("status", "Some status").param("complete", "1"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals("Required String parameter 'comment' is not present",
						result.getResolvedException().getMessage()));
	}

	@Test
	void updateLabTestMissingParamCompleteFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json")
						.param("status", "Some status").param("comment", "Some comment"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals("Required int parameter 'complete' is not present",
						result.getResolvedException().getMessage()));
	}

	@Test
	void updateLabTestInvalidParamStatusFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json").param("status", "")
						.param("comment", "Has a comment").param("complete", "1"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
				.andExpect(content().string(containsString("parameter: [updateTest.status]")));
	}

	@Test
	void updateLabTestInvalidParamCommentFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json")
						.param("status", "Has a status").param("comment", "").param("complete", "1"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
				.andExpect(content().string(containsString("parameter: [updateTest.comment]")));
	}

	@Test
	void updateLabTestInvalidParamTypeCompleteFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json")
						.param("status", "Has a status").param("comment", "Has a comment").param("complete", "String"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
				.andExpect(content().string(containsString("'complete' should be a valid 'int' and not 'String'")));
	}

	@Test
	void updateLabTestInvalidParamCompleteFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/update/1").contentType("application/json")
						.param("status", "Has a status").param("comment", "Has a comment").param("complete", "3"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void revertTestSuccess() throws Exception {
		LabTest labTest = new LabTest(1, 1, "Temperature Oral", "41", "Updated", "Patient contacted",
				Timestamp.valueOf("2021-02-17 12:58:07"), "GEN_MEDICINE", 713145, LocalDate.parse("2021-02-17"),
				"35.8-37.3", "degC", 1);
		labTest.setIsNotified(0);
		List<LabTest> labTests = new ArrayList<LabTest>();
		labTests.add(labTest);
		Timestamp commentMod = new Timestamp(System.currentTimeMillis());
		Comments commentDetail = new Comments(1, "Sample", "Sample", commentMod);
		Comments _commentDetail = new Comments(1, 1, "Error", "Error in tests", commentMod);
		List<Comments> commentList = new ArrayList<Comments>();
		commentList.add(_commentDetail);
		when(patientSummaryService.updateLabTest(1)).thenReturn(labTests);
		when(commentsService.createComment(commentDetail)).thenReturn(_commentDetail);
		when(commentsService.listCommentsByPatId((long) 1)).thenReturn(commentList);
		this.mockMvc
				.perform(put("/api/patientsummary/revert/1").contentType("application/json").param("status", "Error")
						.param("comment", "Error in tests"))
				.andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(commentList)));
	}

	@Test
	void revertLabTestMissingParamStatusFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/revert/1")
						.contentType("application/json").param("comment", "Some comment"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals("Required String parameter 'status' is not present",
						result.getResolvedException().getMessage()));
	}

	@Test
	void revertLabTestMissingParamCommentFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/revert/1")
						.contentType("application/json").param("status", "Some status"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MissingServletRequestParameterException))
				.andExpect(result -> assertEquals("Required String parameter 'comment' is not present",
						result.getResolvedException().getMessage()));
	}

	@Test
	void revertLabTestInvalidParamStatusFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/revert/1").contentType("application/json").param("status", "")
						.param("comment", "Has a comment"))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
				.andExpect(content().string(containsString("parameter: [revertTest.status]")));
	}

	@Test
	void revertLabTestInvalidParamCommentFailure() throws Exception {
		this.mockMvc
				.perform(put("/api/patientsummary/revert/1").contentType("application/json")
						.param("status", "Has a status").param("comment", ""))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
				.andExpect(content().string(containsString("parameter: [revertTest.comment]")));
	}

	@Test
	void revertLabTestFailure() throws Exception {
		when(commentsService.listCommentsByPatId((long) 123)).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc
				.perform(put("/api/patientsummary/revert/123").contentType("application/json")
						.param("status", "Has a status").param("comment", "Has a comment"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}
}
