package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devacademy.rt086300.labreportfollowupsystem.exception.ResourceNotFoundException;
import devacademy.rt086300.labreportfollowupsystem.exception.ServerException;
import devacademy.rt086300.labreportfollowupsystem.model.Bed;
import devacademy.rt086300.labreportfollowupsystem.model.BedRoomLocation;
import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;
import devacademy.rt086300.labreportfollowupsystem.model.Room;
import devacademy.rt086300.labreportfollowupsystem.repository.BedRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.LocationUnitRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.PatientSummaryRepository;
import devacademy.rt086300.labreportfollowupsystem.repository.RoomRepository;

/**
 * This is a service implementation class of the LocationUnit service, that uses
 * LocationUnitRepository, RoomRepository, BedRepository, and
 * PatientSummaryRepository to execute database queries in the LocationUnit,
 * Room, Bed, and PatientSummary Entities respectively.
 * 
 * @author RT086300
 *
 */
@Service
public class LocationUnitServiceImpl implements LocationUnitService {

	/**
	 * LocationUnitRepository Interface that extends JPARepository with LocationUnit
	 * entity. This interface is autowired with @Autowired annotation to inject the
	 * object dependency implicitly.
	 */
	@Autowired
	private LocationUnitRepository locationUnitRepository;

	/**
	 * RoomRepository Interface that extends JPARepository with Room entity. This
	 * interface is autowired with @Autowired annotation to inject the object
	 * dependency implicitly.
	 */
	@Autowired
	private RoomRepository roomRepository;

	/**
	 * BedRepository Interface that extends JPARepository with Bed entity. This
	 * interface is autowired with @Autowired annotation to inject the object
	 * dependency implicitly.
	 */
	@Autowired
	private BedRepository bedRepository;

	/**
	 * PatientSummaryRepository Interface that extends JPARepository with
	 * PatientSummary entity. This interface is autowired with @Autowired annotation
	 * to inject the object dependency implicitly.
	 */
	@Autowired
	private PatientSummaryRepository patientSummaryRepository;

	/**
	 * This method is used to return the List of all LocationUnit in the schema
	 * using findAll() method of the LocationUnitRepository Interface.
	 * 
	 * @return List<LocationUnit> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<LocationUnit> listAllLocationUnits() {
		try {
			return (List<LocationUnit>) locationUnitRepository.findAll();
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the LocationUnit by its ID using findById()
	 * method of the LocationUnitRepository Interface.
	 * 
	 * @param id ID whose LocationUnit to be fetched.
	 * @return LocationUnit If the JPA repository method is successful, else throws
	 *         ResourceNotFoundException with a message "Location unit Not Found".
	 */
	@Override
	public LocationUnit findLocationUnitById(long id) {
		Optional<LocationUnit> optionalLocationUnit = locationUnitRepository.findById(id);
		if (optionalLocationUnit.isPresent()) {
			return optionalLocationUnit.get();
		} else {
			throw new ResourceNotFoundException("Location unit Not Found");
		}
	}

	/**
	 * This method is used to return the List of LocationUnit objects by multiple
	 * Building IDs using findByBuildIdIn() method of the LocationUnitRepository
	 * Interface.
	 * 
	 * @param buildIdList List of Building IDs whose LocationUnit objects are to be
	 *                    fetched.
	 * @return List<LocationUnit> If the JPA repository method is successful, else
	 *         throws ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<LocationUnit> listLocationUnitsByBuildIds(List<Long> buildIdList) {
		try {
			return (List<LocationUnit>) locationUnitRepository.findByBuildIdIn(buildIdList);
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the List of Room IDs by multiple Location IDs
	 * using findByLocIdIn() method of the RoomRepository Interface.
	 * 
	 * The repository method retrieves the room objects. It is then iterated to
	 * fetch the ID and then add it to a List.
	 * 
	 * @param locIdList List of LocationUnit IDs whose Room IDs are to be fetched.
	 * @return List<Long> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<Long> listRoomIdsByLocIds(List<Long> locIdList) {
		try {
			List<Room> rooms = (List<Room>) roomRepository.findByLocIdIn(locIdList);
			List<Long> roomIds = new ArrayList<Long>();
			for (Room room : rooms) {
				roomIds.add(room.getRM_ID());
			}
			return roomIds;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the List of Bed IDs, first by multiple Location
	 * IDs to fetch the Room IDs with the help of listRoomIdsByLocIds() service
	 * method, then uses those Room IDs as parameter in findByRmIdIn of the
	 * BedRepository Interface.
	 * 
	 * The repository method retrieves the bed objects. It is then iterated to fetch
	 * the Bed ID and then add it to a List.
	 * 
	 * @param locIdList List of LocationUnit IDs whose Room IDs are to be fetched
	 *                  and then whose Bed IDs are to be fetched with Room IDs.
	 * @return List<Long> If the JPA repository method is successful, else throws
	 *         ServerException with a message "Internal Server Error".
	 */
	@Override
	public List<Long> listBedIds(List<Long> locIdList) {
		try {
			List<Long> roomIdList = this.listRoomIdsByLocIds(locIdList);
			List<Bed> beds = (List<Bed>) bedRepository.findByRmIdIn(roomIdList);
			List<Long> bedIds = new ArrayList<Long>();
			for (Bed bed : beds) {
				bedIds.add(bed.getB_ID());
			}
			return bedIds;
		} catch (Exception e) {
			throw new ServerException("Internal Server Error");
		}
	}

	/**
	 * This method is used to return the PatientSummary, Bed, Room and the
	 * LocationUnit of a Patient using the patient ID, in a combined POJO object
	 * BedRoomLocation.
	 * 
	 * The method first finds the PatientSummary using the findById() method of
	 * PatientSummaryRepository (that contains Bed ID) and with the bed ID, it
	 * fetches Bed with findById() method of BedRepository (that contains Room ID)
	 * and with the room ID, it fetches Room with findById() method of
	 * RoomRepository (that contains LocationUnit ID) and with the location unit ID,
	 * it fetches LocationUnit with findById() method of LocationUnitRepository.
	 * Finally, it adds every fetched object to the BedRoomLocation object and
	 * returns it.
	 * 
	 * @param patId Patient ID whose PatientSummary, Bed, Room and the LocationUnit
	 *              are to be fetched.
	 * @return BedRoomLocation If the JPA repository method is successful, else
	 *         throws ResourceNotFoundException at different conditions of the
	 *         method.
	 */
	@Override
	public BedRoomLocation findByBedId(long patId) {
		Optional<PatientSummary> optionalPatientSummary = patientSummaryRepository.findById(patId);
		if (optionalPatientSummary.isPresent()) {
			BedRoomLocation bedRoomLocation = new BedRoomLocation();
			bedRoomLocation.setPatientSummary(optionalPatientSummary.get());
			Optional<Bed> optionalBed = bedRepository.findById(optionalPatientSummary.get().getB_ID());
			if (optionalBed.isPresent()) {
				Bed bed = optionalBed.get();
				bedRoomLocation.setBed(bed);
				Optional<Room> optionalRoom = roomRepository.findById(bed.getRM_ID());
				if (optionalRoom.isPresent()) {
					Room room = optionalRoom.get();
					bedRoomLocation.setRoom(room);
					Optional<LocationUnit> optionalLocationUnit = locationUnitRepository.findById(room.getLOC_ID());
					if (optionalLocationUnit.isPresent()) {
						bedRoomLocation.setLocationUnit(optionalLocationUnit.get());
						return bedRoomLocation;
					} else {
						throw new ResourceNotFoundException("Location Unit Not Found");
					}
				} else {
					throw new ResourceNotFoundException("Room Not Found");
				}
			} else {
				throw new ResourceNotFoundException("Bed Not Found");
			}
		} else {
			throw new ResourceNotFoundException("Patient Summary Not Found");
		}

	}

}
