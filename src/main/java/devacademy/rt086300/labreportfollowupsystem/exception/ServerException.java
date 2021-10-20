package devacademy.rt086300.labreportfollowupsystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RuntimeException {
	private static final Logger log = LoggerFactory.getLogger(ServerException.class);
	
	private static final long serialVersionUID = 1L;

	public ServerException(String exception) {
		super(exception);
		log.error("500 INTERNAL_SERVER_ERROR ServerException occurred");
	}
}
