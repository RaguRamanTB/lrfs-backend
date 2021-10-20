package devacademy.rt086300.labreportfollowupsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Bed;
import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;
import devacademy.rt086300.labreportfollowupsystem.model.Room;
import devacademy.rt086300.labreportfollowupsystem.repository.BedRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.LocationUnitRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.RoomRepository;
import devacademy.rt086300.labreportfollowupsystem.service.LocationUnitServiceImpl;

@ExtendWith(MockitoExtension.class)
class LocationUnitServiceTest {

	@Mock
	LocationUnitRepository locationUnitRepository;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private BedRepository bedRepository;

	@InjectMocks
	LocationUnitServiceImpl locationUnitService;

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
		when(locationUnitRepository.findAll()).thenReturn(locUnits);
		List<LocationUnit> fetchedLocUnits = locationUnitService.listAllLocationUnits();
		assertEquals(fetchedLocUnits, locUnits);
		verify(locationUnitRepository, times(1)).findAll();
	}

	@Test
	void getAllLocationUnitsFailure() throws Exception {
		when(locationUnitRepository.findAll()).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class, () -> locationUnitService.listAllLocationUnits());
		assertEquals("Internal Server Error", se.getMessage());
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
		when(locationUnitRepository.findById((long) 1)).thenReturn(Optional.of(lu1));
		Optional<LocationUnit> expectedLU = Optional.of(locationUnitService.findLocationUnitById((long) 1));
		assertEquals(lu1, expectedLU.get());
	}

	@Test
	void getLocationUnitByIdFailure() throws Exception {
		when(locationUnitRepository.findById((long) 12345))
				.thenThrow(new ResourceNotFoundException("Location unit Not Found"));
		ResourceNotFoundException re = assertThrows(ResourceNotFoundException.class,
				() -> locationUnitService.findLocationUnitById((long) 12345));
		assertEquals("Location unit Not Found", re.getMessage());
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
		when(locationUnitRepository.findByBuildIdIn(ids)).thenReturn(locUnits);
		List<LocationUnit> fetchedLocUnits = locationUnitService.listLocationUnitsByBuildIds(ids);
		assertEquals(fetchedLocUnits, locUnits);
		verify(locationUnitRepository, times(1)).findByBuildIdIn(ids);
	}

	@Test
	void getLocationUnitsByBuildIdsFailure() throws Exception {
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		when(locationUnitRepository.findByBuildIdIn(ids)).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class,
				() -> locationUnitService.listLocationUnitsByBuildIds(ids));
		assertEquals("Internal Server Error", se.getMessage());
	}

	@Test
	void getRoomIdsByLocIdsSuccess() throws Exception {
		Bed b = new Bed(1, 1, "A", 1);
		List<Bed> beds = new ArrayList<Bed>();
		beds.add(b);
		Room rm = new Room(1, 1, "PSY01", "GEN", 448800, 1, beds);
		List<Room> rooms = new ArrayList<Room>();
		rooms.add(rm);
		List<Long> locIds = new ArrayList<>();
		locIds.add((long) 1);
		locIds.add((long) 2);
		List<Long> roomIds = new ArrayList<>();
		roomIds.add((long) 1);
		when(roomRepository.findByLocIdIn(locIds)).thenReturn(rooms);
		List<Long> roomIdList = locationUnitService.listRoomIdsByLocIds(locIds);
		assertEquals(roomIdList, roomIds);
	}

	@Test
	void getRoomIdsByLocIdsFailure() throws Exception {
		List<Long> locIds = new ArrayList<>();
		locIds.add((long) 1);
		locIds.add((long) 2);
		when(roomRepository.findByLocIdIn(locIds)).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class, () -> locationUnitService.listRoomIdsByLocIds(locIds));
		assertEquals("Internal Server Error", se.getMessage());
	}
}
