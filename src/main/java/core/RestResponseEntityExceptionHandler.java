package core;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = Logger.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
		String message = exception.getMessage();
		
		@SuppressWarnings("rawtypes")
		List<Class> validExceptionClasses = Arrays.asList(IllegalArgumentException.class, IllegalStateException.class);
		
		if (exception instanceof ConstraintViolationException) {
			message = "";
			ConstraintViolationException validationException = (ConstraintViolationException) exception;
			
			for (ConstraintViolation<?> violation : validationException.getConstraintViolations()) {
				message += violation.getMessage() + ". ";
			}
		} else if (validExceptionClasses.contains(exception.getClass())) {
			message = exception.getMessage();
		} else {
			String exceptionCode = exception.getClass().getSimpleName().replaceAll("[a-z]", "");
			message = "An unexpected error has occured (" + exceptionCode + ").";
			
			LOGGER.error("An unexpected error has occured: " + exception.getMessage(), exception);
		}
		
		RestException body = new RestException(exception.getClass().getSimpleName(), message);
		return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
