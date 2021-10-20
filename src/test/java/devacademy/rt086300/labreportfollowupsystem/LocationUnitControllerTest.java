package devacademy.rt086300.labreportfollowupsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import devacademy.rt086300.labreportfollowupsystem.controller.LocationUnitController;
import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Bed;
import devacademy.rt086300.labreportfollowupsystem.model.BedRoomLocation;
import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;
import devacademy.rt086300.labreportfollowupsystem.model.Room;
import devacademy.rt086300.labreportfollowupsystem.service.LocationUnitServiceImpl;

@WebMvcTest(LocationUnitController.class)
class LocationUnitControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LocationUnitServiceImpl locationUnitService;

	private LocationUnit lu1;
	private LocationUnit lu2;

	@Test
	void getAllLocationUnitsSuccess() throws Exception {
		Bed b = new Bed(1, 1, "A", 1);
		List<Bed> beds = new ArrayList<Bed>();
		beds.add(b);
		Room rm = new Room(1, 1, "PSY01", "GEN", 448800, 1, beds);
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(rm);
		lu1 = new LocationUnit(1, 1, 1, "BE PSY", 123098, rooms);
		lu1 = new LocationUnit(2, 1, 2, "BE ED 1", 456234, rooms);
		List<LocationUnit> locUnits = new ArrayList<LocationUnit>();
		locUnits.add(lu1);
		locUnits.add(lu2);
		when(locationUnitService.listAllLocationUnits()).thenReturn(locUnits);
		this.mockMvc.perform(get("/api/locationunits")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(locUnits)));
	}

	@Test
	void getAllLocationUnitsFailure() throws Exception {
		when(locationUnitService.listAllLocationUnits()).thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/locationunits")).andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getLocationUnitByIdSuccess() throws Exception {
		Bed b = new Bed(1, 1, "A", 1);
		List<Bed> beds = new ArrayList<Bed>();
		beds.add(b);
		Room rm = new Room(1, 1, "PSY01", "GEN", 448800, 1, beds);
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(rm);
		LocationUnit lu1 = new LocationUnit(1, 1, 1, "BE PSY", 123098, rooms);
		when(locationUnitService.findLocationUnitById(1)).thenReturn(lu1);
		this.mockMvc.perform(get("/api/locationunits/1").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(lu1)));
	}

	@Test
	void getLocationUnitByIdFailure() throws Exception {
		when(locationUnitService.findLocationUnitById(222))
				.thenThrow(new ResourceNotFoundException("Location unit not found"));
		this.mockMvc.perform(get("/api/locationunits/222").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(
						result -> assertEquals("Location unit not found", result.getResolvedException().getMessage()));
	}

	@Test
	void getLocationUnitsByBuildIdsSuccess() throws Exception {
		Bed b = new Bed(1, 1, "A", 1);
		List<Bed> beds = new ArrayList<Bed>();
		beds.add(b);
		Room rm = new Room(1, 1, "PSY01", "GEN", 448800, 1, beds);
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(rm);
		lu1 = new LocationUnit(1, 1, 1, "BE PSY", 123098, rooms);
		lu1 = new LocationUnit(2, 2, 2, "BE ED 1", 456234, rooms);
		List<LocationUnit> locUnits = new ArrayList<LocationUnit>();
		locUnits.add(lu1);
		locUnits.add(lu2);
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		when(locationUnitService.listLocationUnitsByBuildIds(ids)).thenReturn(locUnits);
		this.mockMvc.perform(get("/api/locationunits/ids").param("ids", "1,2")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(locUnits)));
	}

	@Test
	void getLocationUnitsByBuildIdsFailure() throws Exception {
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		ids.add((long) 3);
		when(locationUnitService.listLocationUnitsByBuildIds(ids))
				.thenThrow(new ServerException("Internal Server Error"));
		this.mockMvc.perform(get("/api/locationunits/ids").param("ids", "1,2,3"))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerException))
				.andExpect(result -> assertEquals("Internal Server Error", result.getResolvedException().getMessage()));
	}

	@Test
	void getBedRoomLocationByPatIdSuccess() throws Exception {
		Bed bed = new Bed(1, 1, "A", 1);
		List<Bed> bedList = new ArrayList<Bed>();
		bedList.add(bed);
		Room room = new Room(1, 1, "PSY-1", "GEN", (long) 123456, 1, bedList);
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(room);
		LocationUnit locationUnit = new LocationUnit(1, 1, 1, "BE PSY", 123098, rooms);
		PatientSummary patSum = new PatientSummary(1, 1, 1, LocalDate.parse("2021-02-14"), 1,
				LocalDate.parse("2021-02-18"));
		BedRoomLocation bedRoomLocation = new BedRoomLocation();
		bedRoomLocation.setBed(bed);
		bedRoomLocation.setRoom(room);
		bedRoomLocation.setLocationUnit(locationUnit);
		bedRoomLocation.setPatientSummary(patSum);
		when(locationUnitService.findByBedId(1)).thenReturn(bedRoomLocation);
		this.mockMvc.perform(get("/api/locationunits/bed/1")).andExpect(status().isOk())
				.andExpect(content().string(this.objectMapper.writeValueAsString(bedRoomLocation)));
	}

	@Test
	void getBedRoomLocationByPatIdFailure() throws Exception {
		when(locationUnitService.findByBedId(1121)).thenThrow(new ResourceNotFoundException("Location unit not found"));
		this.mockMvc.perform(get("/api/locationunits/bed/1121")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(
						result -> assertEquals("Location unit not found", result.getResolvedException().getMessage()));
	}
}
