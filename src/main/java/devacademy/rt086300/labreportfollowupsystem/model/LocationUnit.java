package devacademy.rt086300.labreportfollowupsystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "location_unit")
public class LocationUnit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOC_ID")
	private long id;

	@Column(name = "BUILD_ID")
	@NotNull(message = "Building ID for which location unit is created should not be null.")
	private long buildId;

	@Column(name = "LOC_TYPE_ID")
	@NotNull(message = "Location Type ID should not be null")
	private long LOC_TYPE_ID;

	@Column(name = "LOC_NAME")
	@NotNull(message = "Name of the location unit should not be null")
	@NotEmpty(message = "Name of the location unit should not be empty")
	private String LOC_NAME;

	@Column(name = "LOC_INCHARGE")
	@NotNull(message = "Location Incharge ID should not be null")
	private long LOC_INCHARGE;

	@OneToMany
	@JoinColumn(name = "LOC_ID", referencedColumnName = "LOC_ID")
	List<Room> rooms;

	public LocationUnit() {
	}

	public LocationUnit(long id,
			@NotNull(message = "Building ID for which location unit is created should not be null.") long buildId,
			@NotNull(message = "Location Type ID should not be null") long lOC_TYPE_ID,
			@NotNull(message = "Name of the location unit should not be null") @NotEmpty(message = "Name of the location unit should not be empty") String lOC_NAME,
			@NotNull(message = "Location Incharge ID should not be null") long lOC_INCHARGE, List<Room> rooms) {
		this.id = id;
		this.buildId = buildId;
		LOC_TYPE_ID = lOC_TYPE_ID;
		LOC_NAME = lOC_NAME;
		LOC_INCHARGE = lOC_INCHARGE;
		this.rooms = rooms;
	}

	public long getBUILD_ID() {
		return buildId;
	}

	public long getLOC_TYPE_ID() {
		return LOC_TYPE_ID;
	}

	public String getLOC_NAME() {
		return LOC_NAME;
	}

	public long getLOC_INCHARGE() {
		return LOC_INCHARGE;
	}

	public long getLOC_ID() {
		return id;
	}
}
