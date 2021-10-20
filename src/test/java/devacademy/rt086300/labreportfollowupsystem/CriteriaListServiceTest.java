package devacademy.rt086300.labreportfollowupsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Building;
import devacademy.rt086300.labreportfollowupsystem.model.CriteriaList;
import devacademy.rt086300.labreportfollowupsystem.model.Facility;
import devacademy.rt086300.labreportfollowupsystem.repository.CriteriaListRepository;
import devacademy.rt086300.labreportfollowupsystem.service.CriteriaListServiceImpl;

@ExtendWith(MockitoExtension.class)
class CriteriaListServiceTest {

	@Mock
	CriteriaListRepository criteriaListRepository;

	@InjectMocks
	CriteriaListServiceImpl criteriaListService;

	private CriteriaList cl1;
	private CriteriaList cl2;

	@Test
	void getAllCriteriaListsSuccess() throws Exception {
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
		when(criteriaListRepository.findAll()).thenReturn(criteriaLists);
		List<CriteriaList> fetchedClLists = criteriaListService.listAllCriteriaLists();
		assertEquals(fetchedClLists, criteriaLists);
		verify(criteriaListRepository, times(1)).findAll();
	}

	@Test
	void getAllCriteriaListsFailure() throws Exception {
		when(criteriaListRepository.findAll()).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class, () -> criteriaListService.listAllCriteriaLists());
		assertEquals("Internal Server Error", se.getMessage());
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
		when(criteriaListRepository.findById((long) 1)).thenReturn(Optional.of(cl1));
		Optional<CriteriaList> expectedCL = Optional.of(criteriaListService.findCriteriaListById(1));
		assertEquals(cl1, expectedCL.get());
	}

	@Test
	void getCriteriaListByIdFailure() throws Exception {
		when(criteriaListRepository.findById((long) 112))
				.thenThrow(new ResourceNotFoundException("Criteria list Not Found"));
		ResourceNotFoundException re = assertThrows(ResourceNotFoundException.class,
				() -> criteriaListRepository.findById((long) 112));
		assertEquals("Criteria list Not Found", re.getMessage());
	}

	@Test
	void getCriteriaListsByMultipleIdsSuccess() throws Exception {
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
		when(criteriaListRepository.findByIdIn(ids)).thenReturn(criteriaLists);
		List<CriteriaList> fetchedClLists = criteriaListService.listCriteriaListsByIds(ids);
		assertEquals(fetchedClLists, criteriaLists);
		verify(criteriaListRepository, times(1)).findByIdIn(ids);
	}

	@Test
	void getCriteriaListsByMultipleIdsFailure() throws Exception {
		List<Long> ids = new ArrayList<>();
		ids.add((long) 1);
		ids.add((long) 2);
		ids.add((long) 3);
		when(criteriaListRepository.findByIdIn(ids)).thenThrow(new ServerException("Internal Server Error"));
		ServerException se = assertThrows(ServerException.class, () -> criteriaListService.listCriteriaListsByIds(ids));
		assertEquals("Internal Server Error", se.getMessage());
	}
}
