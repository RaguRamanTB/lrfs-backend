package devacademy.rt086300.labreportfollowupsystem.model;

import java.time.LocalDate;
//import java.util.List;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAT_ID")
	private long id;

	@Column(name = "PAT_FIRSTNAME")
	@NotNull(message = "Patient's firstname should not be null")
	@NotEmpty(message = "Patient's firstname should not be empty")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Patient's firstname cannot have alphanumeric characters or any special characters.")
	private String patFirstName;

	@Column(name = "PAT_LASTNAME")
	@NotNull(message = "Patient's lastname should not be null")
	@NotEmpty(message = "Patient's lastname should not be empty")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Patient's lastname cannot have alphanumeric characters or any special characters.")
	private String patLastName;

	@Column(name = "PAT_AGE")
	@NotNull(message = "Patient's age should be greater or equal to 0")
	@PositiveOrZero(message = "Patient's age should be greater or equal to 0")
	private int PAT_AGE;

	@Column(name = "PAT_ADDRESS")
	@NotNull(message = "Patient's address should not be null")
	@NotEmpty(message = "Patient's address should not be empty")
	private String PAT_ADDRESS;

	@Column(name = "PAT_BLOODGROUP")
	@NotNull(message = "Patient's bloodgroup should not be null")
	@NotEmpty(message = "Patient's bloodgroup should not be empty")
	private String PAT_BLOODGROUP;

	@Column(name = "PAT_CONTACT")
	@NotNull(message = "Patient's contact information should not be null")
	@NotEmpty(message = "Patient's contact information should not be empty")
	@Pattern(regexp = "^\\+91[6-9]\\d{9}$", message = "Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.")
	private String patContact;

	@Column(name = "PAT_GENDER")
	@NotNull(message = "Patient's gender should not be null")
	@NotEmpty(message = "Patient's gender should not be empty")
	private String PAT_GENDER;

	@Column(name = "PAT_DOB")
	@NotNull(message = "Patient's Date of birth should not be null")
	private LocalDate PAT_DOB;

	@Column(name = "PAT_EMAIL")
	@Email(message = "Valid email address must be given")
	private String PAT_EMAIL;

	@Column(name = "PAT_INSURANCE_ID")
	private String PAT_INSURANCE_ID;

	@Column(name = "PAT_HEIGHT")
	private int PAT_HEIGHT;

	@Column(name = "PAT_WEIGHT")
	private int PAT_WEIGHT;

	@OneToMany
	@JoinColumn(name = "PAT_ID", referencedColumnName = "PAT_ID")
	List<Comments> comments;

	public Patient() {
	}

	public String getPAT_FIRSTNAME() {
		return patFirstName;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public Patient(
			@NotNull(message = "Patient's firstname should not be null") @NotEmpty(message = "Patient's firstname should not be empty") @Pattern(regexp = "^[A-Za-z]+$", message = "Patient's firstname cannot have alphanumeric characters or any special characters.") String pAT_FIRSTNAME,
			@NotNull(message = "Patient's lastname should not be null") @NotEmpty(message = "Patient's lastname should not be empty") @Pattern(regexp = "^[A-Za-z]+$", message = "Patient's lastname cannot have alphanumeric characters or any special characters.") String pAT_LASTNAME,
			@NotNull(message = "Patient's age should be greater or equal to 1") @Positive(message = "Patient's age should be greater or equal to 1") int pAT_AGE,
			@NotNull(message = "Patient's address should not be null") @NotEmpty(message = "Patient's address should not be empty") String pAT_ADDRESS,
			@NotNull(message = "Patient's bloodgroup should not be null") @NotEmpty(message = "Patient's bloodgroup should not be empty") String pAT_BLOODGROUP,
			@NotNull(message = "Patient's contact information should not be null") @NotEmpty(message = "Patient's contact information should not be empty") @Pattern(regexp = "^\\+91[6-9]\\d{9}$", message = "Contact number must be 10 digits, with a prefix +91, starting with 6,7,8, or 9.") String pAT_CONTACT,
			@NotNull(message = "Patient's gender should not be null") @NotEmpty(message = "Patient's gender should not be empty") String pAT_GENDER,
			@NotNull(message = "Patient's Date of birth should not be null") LocalDate pAT_DOB,
			@Email(message = "Valid email address must be given") String pAT_EMAIL, String pAT_INSURANCE_ID,
			@Positive(message = "Patient's height must be greater than 0 or null") int pAT_HEIGHT,
			@Positive(message = "Patient's weight must be greater than 0 or null") int pAT_WEIGHT) {
		patFirstName = pAT_FIRSTNAME;
		patLastName = pAT_LASTNAME;
		PAT_AGE = pAT_AGE;
		PAT_ADDRESS = pAT_ADDRESS;
		PAT_BLOODGROUP = pAT_BLOODGROUP;
		patContact = pAT_CONTACT;
		PAT_GENDER = pAT_GENDER;
		PAT_DOB = pAT_DOB;
		PAT_EMAIL = pAT_EMAIL;
		PAT_INSURANCE_ID = pAT_INSURANCE_ID;
		PAT_HEIGHT = pAT_HEIGHT;
		PAT_WEIGHT = pAT_WEIGHT;
	}

	public void setPAT_FIRSTNAME(String PAT_FIRSTNAME) {
		this.patFirstName = PAT_FIRSTNAME;
	}

	public String getPAT_LASTNAME() {
		return patLastName;
	}

	public void setPAT_LASTNAME(String PAT_LASTNAME) {
		this.patLastName = PAT_LASTNAME;
	}

	public int getPAT_AGE() {
		return PAT_AGE;
	}

	public void setPAT_AGE(int PAT_AGE) {
		this.PAT_AGE = PAT_AGE;
	}

	public String getPAT_ADDRESS() {
		return PAT_ADDRESS;
	}

	public void setPAT_ADDRESS(String PAT_ADDRESS) {
		this.PAT_ADDRESS = PAT_ADDRESS;
	}

	public String getPAT_BLOODGROUP() {
		return PAT_BLOODGROUP;
	}

	public void setPAT_BLOODGROUP(String PAT_BLOODGROUP) {
		this.PAT_BLOODGROUP = PAT_BLOODGROUP;
	}

	public String getPAT_CONTACT() {
		return patContact;
	}

	public void setPAT_CONTACT(String PAT_CONTACT) {
		this.patContact = PAT_CONTACT;
	}

	public String getPAT_GENDER() {
		return PAT_GENDER;
	}

	public void setPAT_GENDER(String PAT_GENDER) {
		this.PAT_GENDER = PAT_GENDER;
	}

	public LocalDate getPAT_DOB() {
		return PAT_DOB;
	}

	public void setPAT_DOB(LocalDate PAT_DOB) {
		this.PAT_DOB = PAT_DOB;
	}

	public String getPAT_EMAIL() {
		return PAT_EMAIL;
	}

	public void setPAT_EMAIL(String PAT_EMAIL) {
		this.PAT_EMAIL = PAT_EMAIL;
	}

	public String getPAT_INSURANCE_ID() {
		return PAT_INSURANCE_ID;
	}

	public void setPAT_INSURANCE_ID(String PAT_INSURANCE_ID) {
		this.PAT_INSURANCE_ID = PAT_INSURANCE_ID;
	}

	public int getPAT_HEIGHT() {
		return PAT_HEIGHT;
	}

	public void setPAT_HEIGHT(int PAT_HEIGHT) {
		this.PAT_HEIGHT = PAT_HEIGHT;
	}

	public int getPAT_WEIGHT() {
		return PAT_WEIGHT;
	}

	public void setPAT_WEIGHT(int PAT_WEIGHT) {
		this.PAT_WEIGHT = PAT_WEIGHT;
	}

	public long getPAT_ID() {
		return id;
	}
}
