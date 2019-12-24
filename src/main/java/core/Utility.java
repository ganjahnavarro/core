package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public final class Utility implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static void parseErrors(BindingResult result, ModelMap model) {
		String errorMessage = "";

		for (FieldError error : result.getFieldErrors()) {
			errorMessage += error.getDefaultMessage() + " ";
		}

		model.addAttribute("errorMessage", errorMessage);
	}

	public static String getURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}


	public void setApplicationContext(ApplicationContext context) throws BeansException {
		Utility.context = context;
	}

	public static <T extends Enum<?>> T searchEnum(Class<T> enumeration, String search) {
		for (T each : enumeration.getEnumConstants()) {
			if (each.name().compareToIgnoreCase(search) == 0) {
				return each;
			}
		}
		return null;
	}
	
	public static String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);
	}
	
	public static Date parseDate(String dateString) {
		try {
			if (dateString != null && !dateString.isEmpty()) {
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				return dateFormat.parse(dateString);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format.");
		}
		return null;
	}
	
	public static Date getCurrentDateWithoutTime() {
		Calendar calendar = Calendar.getInstance();
		removeCalendarTime(calendar);
		return calendar.getTime();
	}
	
	public static Date removeDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		removeCalendarTime(calendar);
		return calendar.getTime();
	}
	
	public static Date getTomorrowWithoutDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		removeCalendarTime(calendar);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static void removeCalendarTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

}
