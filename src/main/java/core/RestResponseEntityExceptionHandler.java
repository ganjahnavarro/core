package core;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {
		String message = exception.getMessage();
		
		if (exception instanceof ConstraintViolationException) {
			message = "";
			ConstraintViolationException validationException = (ConstraintViolationException) exception;
			
			for (ConstraintViolation<?> violation : validationException.getConstraintViolations()) {
				message += violation.getMessage() + ". ";
			}
		} else {
			message = exception.getMessage();
		}
		
		RestException body = new RestException(exception.getClass().getSimpleName(), message);
		return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
