package devacademy.rt086300.labreportfollowupsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bed")
public class Bed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "B_ID")
	private long id;

	@Column(name = "RM_ID")
	@NotNull(message = "Room ID for which the bed is added should not be null")
	private long rmId;

	@Column(name = "B_CODE")
	@NotNull(message = "Bed code should not be null")
	@NotEmpty(message = "Bed code should not be empty")
	private String B_CODE;

	@Column(name = "IS_OCCUPIED")
	@NotNull(message = "Bed occupancy status cannot be null")
	@Min(value = 0, message = "Occupancy status can be only 0 or 1")
	@Max(value = 1, message = "Occupancy status can be only 0 or 1")
	private int IS_OCCUPIED;

	public Bed() {
	}

	public Bed(long id, @NotNull(message = "Room ID for which the bed is added should not be null") long rM_ID,
			@NotNull(message = "Bed code should not be null") @NotEmpty(message = "Bed code should not be empty") String b_CODE,
			@NotNull(message = "Bed occupancy status cannot be null") @Min(value = 0, message = "Occupancy status can be only 0 or 1") @Max(value = 1, message = "Occupancy status can be only 0 or 1") int iS_OCCUPIED) {
		this.id = id;
		rmId = rM_ID;
		B_CODE = b_CODE;
		IS_OCCUPIED = iS_OCCUPIED;
	}

	public long getRM_ID() {
		return rmId;
	}

	public String getB_CODE() {
		return B_CODE;
	}

	public int getIS_OCCUPIED() {
		return IS_OCCUPIED;
	}

	public long getB_ID() {
		return id;
	}

}
