package devacademy.rt086300.labreportfollowupsystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final Logger log = LoggerFactory.getLogger(ResourceNotFoundException.class);

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String exception) {
		super(exception);
		log.error("404 NOT_FOUND ResourceNotFoundException occurred");
	}
}
