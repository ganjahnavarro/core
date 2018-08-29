package core;

import java.util.ArrayList;
import java.util.List;

public class AggregateException extends RuntimeException {

	private static final long serialVersionUID = 8588891386455666210L;

	private List<Exception> basket = new ArrayList<>();

	public AggregateException(Runnable... runnables) {
		for (Runnable runnable : runnables) {
			addException(getException(runnable));
		}
	}
	
	public List<Exception> getExceptions() {
		return basket;
	}

	public void addExceptions(List<Exception> exceptions) {
		if (exceptions != null) {
			basket.addAll(exceptions);
		}
	}

	public void addException(Exception exception) {
		if (exception != null) {
			basket.add(exception);
		}
	}

	@Override
	public String getMessage() {
		if (!getExceptions().isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (Exception exception : basket) {
				builder.append(exception.getMessage());
				builder.append(" ");
			}
			return builder.toString().trim();
		}
		return null;
	}
	
	private Exception getException(Runnable function) {
		try {
			function.run();
		} catch (Exception exception) {
			return exception;
		}
		return null;
	}

}
