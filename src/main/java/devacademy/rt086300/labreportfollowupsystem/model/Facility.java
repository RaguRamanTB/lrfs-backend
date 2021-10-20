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
@Table(name = "facility")
public class Facility {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FC_ID")
	private long id;

	@Column(name = "FC_NAME")
	@NotNull(message = "Name of the facility should not be null")
	@NotEmpty(message = "Name of the facility should not be empty")
	private String FC_NAME;

	@Column(name = "ORG_ID")
	@NotNull(message = "Organization ID for which facility is created should not be null.")
	private long ORG_ID;

	@OneToMany
	@JoinColumn(name = "FC_ID", referencedColumnName = "FC_ID")
	private List<Building> buildings;

	public Facility() {
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public Facility(long id,
			@NotNull(message = "Name of the facility should not be null") @NotEmpty(message = "Name of the facility should not be empty") String fC_NAME,
			@NotNull(message = "Organization ID for which facility is created should not be null.") long oRG_ID,
			List<Building> buildings) {
		this.id = id;
		FC_NAME = fC_NAME;
		ORG_ID = oRG_ID;
		this.buildings = buildings;
	}

	public String getFC_NAME() {
		return FC_NAME;
	}

	public long getORG_ID() {
		return ORG_ID;
	}

	public long getFC_ID() {
		return id;
	}

}
