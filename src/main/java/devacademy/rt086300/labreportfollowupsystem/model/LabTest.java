package devacademy.rt086300.labreportfollowupsystem.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lab_test")
public class LabTest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "T_ID")
	private long id;

	@Column(name = "PAT_ID", insertable = false, updatable = false)
	@NotNull(message = "Patient ID should not be null")
	private long patId;

	@Column(name = "T_NAME")
	@NotNull(message = "Name of the test should not be null")
	@NotEmpty(message = "Name of the test should not be empty")
	private String T_NAME;

	@Column(name = "T_RESULT")
	@NotNull(message = "Result of the test should not be null")
	@NotEmpty(message = "Result of the test should not be empty")
	private String T_RESULT;

	@Column(name = "T_STATUS")
	private String T_STATUS;

	@Column(name = "T_COMMENT")
	private String T_COMMENT;

	@Column(name = "T_MOD_AT")
	private Timestamp T_MOD_AT;

	@Column(name = "T_CATEGORY")
	@NotNull(message = "Category of the test should not be null")
	@NotEmpty(message = "Category of the test should not be empty")
	private String T_CATEGORY;

	@Column(name = "DOC_ID")
	@NotNull(message = "Doctor ID who recommended the test should not be null")
	private long DOC_ID;

	@Column(name = "T_DATE")
	@NotNull(message = "Date when the test was taken should not be null")
	private LocalDate T_DATE;

	@Column(name = "NORMAL_RANGE")
	@NotNull(message = "Normal range of the test should not be null")
	@NotEmpty(message = "Normal range of the test should not be empty")
	private String NORMAL_RANGE;

	@Column(name = "T_UNITS")
	@NotNull(message = "Measurement unit of the test should not be null")
	@NotEmpty(message = "Measurement unit of the test should not be empty")
	private String T_UNITS;

	@Column(name = "IS_NOTIFIED")
	@NotNull(message = "Notification status of the test should not be null")
	private int isNotified;

	@ManyToOne
	@JoinColumn(name = "PAT_ID", referencedColumnName = "PAT_ID")
	private Patient patient;

	public LabTest() {
	}

	public LabTest(long id, @NotNull(message = "Patient ID should not be null") long patId,
			@NotNull(message = "Name of the test should not be null") @NotEmpty(message = "Name of the test should not be empty") String t_NAME,
			@NotNull(message = "Result of the test should not be null") @NotEmpty(message = "Result of the test should not be empty") String t_RESULT,
			String t_STATUS, String t_COMMENT, Timestamp t_MOD_AT,
			@NotNull(message = "Category of the test should not be null") @NotEmpty(message = "Category of the test should not be empty") String t_CATEGORY,
			@NotNull(message = "Doctor ID who recommended the test should not be null") long dOC_ID,
			@NotNull(message = "Date when the test was taken should not be null") LocalDate t_DATE,
			@NotNull(message = "Normal range of the test should not be null") @NotEmpty(message = "Normal range of the test should not be empty") String nORMAL_RANGE,
			@NotNull(message = "Measurement unit of the test should not be null") @NotEmpty(message = "Measurement unit of the test should not be empty") String t_UNITS,
			@NotNull(message = "Notification status of the test should not be null") int isNotified) {
		this.id = id;
		this.patId = patId;
		T_NAME = t_NAME;
		T_RESULT = t_RESULT;
		T_STATUS = t_STATUS;
		T_COMMENT = t_COMMENT;
		T_MOD_AT = t_MOD_AT;
		T_CATEGORY = t_CATEGORY;
		DOC_ID = dOC_ID;
		T_DATE = t_DATE;
		NORMAL_RANGE = nORMAL_RANGE;
		T_UNITS = t_UNITS;
		this.isNotified = isNotified;
	}

	public long getT_ID() {
		return id;
	}

	public long getPAT_ID() {
		return patId;
	}

	public Patient getPatient() {
		return patient;
	}

	public String getT_NAME() {
		return T_NAME;
	}

	public String getT_RESULT() {
		return T_RESULT;
	}

	public String getT_STATUS() {
		return T_STATUS;
	}

	public String getT_COMMENT() {
		return T_COMMENT;
	}

	public Timestamp getT_MOD_AT() {
		return T_MOD_AT;
	}

	public String getT_CATEGORY() {
		return T_CATEGORY;
	}

	public long getDOC_ID() {
		return DOC_ID;
	}

	public LocalDate getT_DATE() {
		return T_DATE;
	}

	public String getNORMAL_RANGE() {
		return NORMAL_RANGE;
	}

	public String getT_UNITS() {
		return T_UNITS;
	}

	public int getIS_NOTIFIED() {
		return isNotified;
	}

	public void setIsNotified(int isNotified) {
		this.isNotified = isNotified;
	}

}
