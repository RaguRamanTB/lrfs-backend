package devacademy.rt086300.labreportfollowupsystem.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pat_summary")
public class PatientSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "PAT_ID")
	@NotNull(message = "Patient ID cannot be null")
	private long patId;

	@Column(name = "B_ID")
	@NotNull(message = "Bed ID of the patient cannot be null")
	private long bId;

	@Column(name = "ADMIT_DATE")
	@NotNull(message = "Patient's admission date cannot be null")
	private LocalDate ADMIT_DATE;

	@Column(name = "IS_DISCHARGED")
	@NotNull(message = "Patient's discharge status cannot be null")
	@Min(value = 0, message = "Discharge status can be only 0 or 1")
	@Max(value = 1, message = "Discharge status can be only 0 or 1")
	private int isDischarged;

	@Column(name = "DIS_DATE")
	private LocalDate DIS_DATE;

	public PatientSummary() {
	}

	public PatientSummary(long id, @NotNull(message = "Patient ID cannot be null") long patId,
			@NotNull(message = "Bed ID of the patient cannot be null") long bId,
			@NotNull(message = "Patient's admission date cannot be null") LocalDate aDMIT_DATE,
			@NotNull(message = "Patient's discharge status cannot be null") @Min(value = 0, message = "Discharge status can be only 0 or 1") @Max(value = 1, message = "Discharge status can be only 0 or 1") int isDischarged,
			LocalDate dIS_DATE) {
		this.id = id;
		this.patId = patId;
		this.bId = bId;
		ADMIT_DATE = aDMIT_DATE;
		this.isDischarged = isDischarged;
		DIS_DATE = dIS_DATE;
	}

	public long getId() {
		return id;
	}

	public long getPAT_ID() {
		return patId;
	}

	public long getB_ID() {
		return bId;
	}

	public LocalDate getADMIT_DATE() {
		return ADMIT_DATE;
	}

	public int getIS_DISCHARGED() {
		return isDischarged;
	}

	public LocalDate getDIS_DATE() {
		return DIS_DATE;
	}

}
