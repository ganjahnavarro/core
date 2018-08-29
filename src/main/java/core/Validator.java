package core;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class Validator {
	
	private final Format dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public void validateEmailAddress(String emailAddress) {
		if (emailAddress != null && !EmailValidator.getInstance().isValid(emailAddress)) {
			throw new IllegalArgumentException("Invalid email address.");
		}
	}
	
	public void validateDate(String dateString) {
		validateDate(dateString, "Invalid date format.");
	}
	
	public void validateDate(String dateString, String invalidMessage) {
		if (dateString != null && !dateString.isEmpty()) {
			try {
				dateFormat.parseObject(dateString);
			} catch (ParseException e) {
				throw new IllegalArgumentException(invalidMessage);
			}
		}
	}
	
	public void aggregate(Runnable... runnables) {
		AggregateException aggregate = new AggregateException(runnables);
		if (!aggregate.getExceptions().isEmpty()) {
			throw aggregate;
		}
	}
	
}
