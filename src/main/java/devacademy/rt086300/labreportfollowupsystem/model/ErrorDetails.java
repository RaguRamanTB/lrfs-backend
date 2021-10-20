package devacademy.rt086300.labreportfollowupsystem.model;

import java.util.Date;
import java.util.List;

import org.springframework.validation.ObjectError;

public class ErrorDetails {
	private Date timestamp;
	private String message;
	private Patient duplicate;
	private List<ObjectError> details;

	public ErrorDetails(Date timestamp, String message, List<ObjectError> details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public ErrorDetails(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}
	
	public ErrorDetails(Date timestamp, String message, Patient duplicate) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.duplicate = duplicate;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public List<ObjectError> getDetails() {
		return details;
	}

	public Patient getDuplicate() {
		return duplicate;
	}
}
