package devacademy.rt086300.labreportfollowupsystem.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "PAT_ID")
	private long patId;

	@Column(name = "STATUS")
	@NotNull(message = "Status should not be null")
	@NotEmpty(message = "Status should not be empty")
	private String status;

	@Column(name = "COMMENT")
	@NotNull(message = "Comment should not be null")
	@NotEmpty(message = "Comment should not be empty")
	private String comment;

	@Column(name = "COMMENTED_AT")
	private Timestamp COMMENTED_AT;

	public Comments() {
	}

	public Comments(long patId,
			@NotNull(message = "Status should not be null") @NotEmpty(message = "Status should not be empty") String status,
			@NotNull(message = "Comment should not be null") @NotEmpty(message = "Comment should not be empty") String comment,
			Timestamp cOMMENTED_AT) {
		this.patId = patId;
		this.status = status;
		this.comment = comment;
		COMMENTED_AT = cOMMENTED_AT;
	}

	public Comments(long id, long patId,
			@NotNull(message = "Status should not be null") @NotEmpty(message = "Status should not be empty") String status,
			@NotNull(message = "Comment should not be null") @NotEmpty(message = "Comment should not be empty") String comment,
			Timestamp cOMMENTED_AT) {
		this.id = id;
		this.patId = patId;
		this.status = status;
		this.comment = comment;
		COMMENTED_AT = cOMMENTED_AT;
	}

	public long getId() {
		return id;
	}

	public long getPatId() {
		return patId;
	}

	public String getSTATUS() {
		return status;
	}

	public String getCOMMENT() {
		return comment;
	}

	public Timestamp getCOMMENTED_AT() {
		return COMMENTED_AT;
	}

}
