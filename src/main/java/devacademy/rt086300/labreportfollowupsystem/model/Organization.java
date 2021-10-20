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
@Table(name = "organization")
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORG_ID")
	private long ORG_ID;

	@Column(name = "ORG_NAME")
	@NotNull(message = "Name of the organization should not be null")
	@NotEmpty(message = "Name of the organization should not be empty")
	private String ORG_NAME;

	@OneToMany
	@JoinColumn(name = "ORG_ID", referencedColumnName = "ORG_ID")
	private List<Facility> facilities;

	public Organization() {
	}
}
