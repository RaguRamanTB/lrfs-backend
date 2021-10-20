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
@Table(name = "building")
public class Building {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUILD_ID")
	private long id;

	@Column(name = "BUILD_NAME")
	@NotNull(message = "Name of the building should not be null")
	@NotEmpty(message = "Name of the building should not be empty")
	private String BUILD_NAME;

	@Column(name = "FC_ID")
	@NotNull(message = "Facility ID for which building is created should not be null.")
	private long FC_ID;

	@OneToMany
	@JoinColumn(name = "BUILD_ID", referencedColumnName = "BUILD_ID")
	List<LocationUnit> locationUnits;

	public Building() {
	}

	public String getBUILD_NAME() {
		return BUILD_NAME;
	}

	public Building(long id,
			@NotNull(message = "Name of the building should not be null") @NotEmpty(message = "Name of the building should not be empty") String bUILD_NAME,
			@NotNull(message = "Facility ID for which building is created should not be null.") long fC_ID) {
		this.id = id;
		BUILD_NAME = bUILD_NAME;
		FC_ID = fC_ID;
	}

	public long getFC_ID() {
		return FC_ID;
	}

	public long getBUILD_ID() {
		return id;
	}

}
