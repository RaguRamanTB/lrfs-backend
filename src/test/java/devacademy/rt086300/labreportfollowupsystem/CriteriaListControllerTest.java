package devacademy.rt086300.labreportfollowupsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import devacademy.rt086300.labreportfollowupsystem.controller.CriteriaListController;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Building;
import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;
import devacademy.rt086300.labreportfollowupsystem.model.Facility;
import devacademy.rt086300.labreportfollowupsystem.service.CriteriaListServiceImpl;

@WebMvcTest(CriteriaListController.class)
class CriteriaListControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CriteriaListServiceImpl criteriaListService;

	private CriteriaList cl1;
	private CriteriaList cl2;

	@Test
	void getAllCriteriaListSuccess() throws Exception {
		Building building = new Building(1, "BW New", 1);
		List<Building> bList = new ArrayList<Building>();
		bList.add(building);
		Facility facility = new Facility(1, "Baseline West", 1, bList);
		Set<Facility> fList = new HashSet<Facility>();
		fList.add(facility);
		cl1 = new CriteriaList(1, "List A", fList);
		cl2 = new CriteriaList(2, "List B", fList);
		List<CriteriaList> criteriaLists = new ArrayList<CriteriaList>();
		criteriaLists.add(cl1);
		criteriaLists.add(cl2);
		when(criteriaListService.listAllCriteriaLists()).thenReturn(criteriaLists);
		this.mockMvc.perform(get("/api/criterialists")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(criteriaLists)));
	}

	@Test
	void getAllCriteriaListFailure() throws Exception {
		when(criteriaListService.listAllCriteriaLists()).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/criterialists")).andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getCriteriaListByIdSuccess() throws Exception {
		Building building = new Building(1, "BW New", 1);
		List<Building> bList = new ArrayList<Building>();
		bList.add(building);
		Facility facility = new Facility(1, "Baseline West", 1, bList);
		Set<Facility> fList = new HashSet<Facility>();
		fList.add(facility);
		CriteriaList cl1 = new CriteriaList(1, "List A", fList);
		when(criteriaListService.findCriteriaListById(1)).thenReturn(cl1);
		this.mockMvc.perform(get("/api/criterialists/1").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(cl1)));
	}

	@Test
	void getCriteriaListByIdFailure() throws Exception {
		when(criteriaListService.findCriteriaListById(111))
				.thenThrow(new ResourceNotFoundException("Criteria list not found"));
		this.mockMvc.perform(get("/api/criterialists/111").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(
						result -> assertEquals("Criteria list not found", result.getResolvedException().getMessage()));
	}

	@Test
	void getCriteriaListByMultipleIdsSuccess() throws Exception {
		Building building = new Building(1, "BW New", 1);
		List<Building> bList = new ArrayList<Building>();
		bList.add(building);
		Facility facility = new Facility(1, "Baseline West", 1, bList);
		Set<Facility> fList = new HashSet<Facility>();
		fList.add(facility);
		cl1 = new CriteriaList(1, "List A", fList);
		cl2 = new CriteriaList(2, "List B", fList);
		List<CriteriaList> criteriaLists = new ArrayList<CriteriaList>();
		criteriaLists.add(cl1);
		criteriaLists.add(cl2);
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		when(criteriaListService.listCriteriaListsByIds(ids)).thenReturn(criteriaLists);
		this.mockMvc.perform(get("/api/criterialists/ids").param("ids", "1,2")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(criteriaLists)));
	}

	@Test
	void getCriteriaListByMultipleIdsFailure() throws Exception {
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		ids.add((long) 3);
		when(criteriaListService.listCriteriaListsByIds(ids)).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/criterialists/ids").param("ids", "1,2,3"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}
}
