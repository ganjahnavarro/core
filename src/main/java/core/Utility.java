package core;

import java.text.Format;
import java.text.SimpleDateFormat;
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
	private static Format dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
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
		return dateFormat.format(date);
	}

}
