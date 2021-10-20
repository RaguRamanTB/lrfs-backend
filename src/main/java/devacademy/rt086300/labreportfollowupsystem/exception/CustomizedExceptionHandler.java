package devacademy.rt086300.labreportfollowupsystem.exception;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import devacademy.rt086300.labreportfollowupsystem.model.ErrorDetails;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		StringBuilder messageBuilder = new StringBuilder("Validation failed: ");
		ex.getConstraintViolations().stream().forEach(v -> messageBuilder
				.append("parameter: [" + v.getPropertyPath() + "], constraint: [" + v.getMessage() + "]"));
		ErrorDetails errorDetails = new ErrorDetails(new Date(), messageBuilder.toString());
		log.error("400 BAD_REQUEST ConstraintViolationException occurred: " + messageBuilder.toString());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and not '%s'", name, type, value);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), message);
		log.error("400 BAD_REQUEST MethodArgumentTypeMismatchException occurred: " + message);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
