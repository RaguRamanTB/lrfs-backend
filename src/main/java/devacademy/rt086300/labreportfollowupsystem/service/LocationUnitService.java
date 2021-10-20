package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.List;

import devacademy.rt086300.labreportfollowupsystem.model.BedRoomLocation;
import devacademy.rt086300.labreportfollowupsystem.model.LocationUnit;

/**
 * An interface LocationUnitService, to abstract the methods. The
 * implementations are in LocationUnitServiceImpl class.
 * 
 * @author RT086300
 *
 */
public interface LocationUnitService {
	List<LocationUnit> listAllLocationUnits();

	LocationUnit findLocationUnitById(long id);

	List<LocationUnit> listLocationUnitsByBuildIds(List<Long> buildIdList);

	List<Long> listRoomIdsByLocIds(List<Long> locIdList);

	List<Long> listBedIds(List<Long> locIdList);

	BedRoomLocation findByBedId(long patId);
}
