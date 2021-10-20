package devacademy.rt086300.labreportfollowupsystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "criteria_list")
public class CriteriaList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CL_ID")
	private long id;

	@Column(name = "CL_NAME")
	@NotNull(message = "Name of the criteria list should not be null")
	@NotEmpty(message = "Name of the criteria list should not be empty")
	private String CL_NAME;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cl_fac_bridge", joinColumns = { @JoinColumn(name = "CL_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "FC_ID") })
	private Set<Facility> facilities = new HashSet<>();

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public CriteriaList() {
	}

	public CriteriaList(long id,
			@NotNull(message = "Name of the criteria list should not be null") @NotEmpty(message = "Name of the criteria list should not be empty") String cL_NAME,
			Set<Facility> facilities) {
		this.id = id;
		CL_NAME = cL_NAME;
		this.facilities = facilities;
	}

	public long getCL_ID() {
		return id;
	}

	public String getCL_NAME() {
		return CL_NAME;
	}

}
