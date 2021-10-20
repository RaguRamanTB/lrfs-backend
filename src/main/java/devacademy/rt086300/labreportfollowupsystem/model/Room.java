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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "room")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RM_ID")
	private long id;

	@Column(name = "LOC_ID")
	@NotNull(message = "Location Unit ID for which room is created should not be null.")
	private long locId;

	@Column(name = "R_CODE")
	@NotNull(message = "Room code should not be null")
	@NotEmpty(message = "Room code should not be empty")
	private String R_CODE;

	@Column(name = "R_TYPE")
	@NotNull(message = "Room type should not be null")
	@NotEmpty(message = "Room type should not be empty")
	private String R_TYPE;

	@Column(name = "R_ATTENDANT")
	@NotNull(message = "Room Attendant ID should not be null")
	private long R_ATTENDANT;

	@Column(name = "IS_FULL")
	@NotNull(message = "Room occupancy status cannot be null")
	@Min(value = 0, message = "Occupancy status can be only 0 or 1")
	@Max(value = 1, message = "Occupancy status can be only 0 or 1")
	private int IS_FULL;

	@OneToMany
	@JoinColumn(name = "RM_ID", referencedColumnName = "RM_ID")
	List<Bed> beds;

	public Room() {
	}

	public Room(long id,
			@NotNull(message = "Location Unit ID for which room is created should not be null.") long lOC_ID,
			@NotNull(message = "Room code should not be null") @NotEmpty(message = "Room code should not be empty") String r_CODE,
			@NotNull(message = "Room type should not be null") @NotEmpty(message = "Room type should not be empty") String r_TYPE,
			@NotNull(message = "Room Attendant ID should not be null") long r_ATTENDANT,
			@NotNull(message = "Room occupancy status cannot be null") @Min(value = 0, message = "Occupancy status can be only 0 or 1") @Max(value = 1, message = "Occupancy status can be only 0 or 1") int iS_FULL,
			List<Bed> beds) {
		this.id = id;
		locId = lOC_ID;
		R_CODE = r_CODE;
		R_TYPE = r_TYPE;
		R_ATTENDANT = r_ATTENDANT;
		IS_FULL = iS_FULL;
		this.beds = beds;
	}

	public long getLOC_ID() {
		return locId;
	}

	public String getR_CODE() {
		return R_CODE;
	}

	public String getR_TYPE() {
		return R_TYPE;
	}

	public long getR_ATTENDANT() {
		return R_ATTENDANT;
	}

	public int getIS_FULL() {
		return IS_FULL;
	}

	public long getRM_ID() {
		return id;
	}

}
