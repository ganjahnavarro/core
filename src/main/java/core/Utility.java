package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import core.enums.UserType;

@Component
public final class Utility implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static ApplicationContext getApplicationContext() {
        return context;
    }
	
	public static void parseErrors(BindingResult result, ModelMap model){
		String errorMessage = "";
		
    	for(FieldError error : result.getFieldErrors()){
    		errorMessage += error.getDefaultMessage() + " ";
    	}
    	
    	model.addAttribute("errorMessage", errorMessage);
	}
	
	public static String getURLWithContextPath(HttpServletRequest request) {
	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public static String getUser() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static int getCurrentUserAccess() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				Iterator<GrantedAuthority> iterator = (Iterator<GrantedAuthority>) ((UserDetails) principal).getAuthorities().iterator();
				
				List<String> roles = new ArrayList<String>();
				while(iterator.hasNext()){
					roles.add(iterator.next().toString());
				}

				List<UserType> types = Arrays.asList(UserType.values());
				Collections.reverse(types);
				
				for(UserType type : types){
					for(String role : roles){
						if(role.equalsIgnoreCase("ROLE_" + type.toString())){
							return type.ordinal();
						}
					}
				}
			}
		}
		return 0;
	}
	
	public static boolean isLoggedUserAdmin() {
		return getCurrentUserAccess() >= UserType.ADMIN.ordinal();
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		Utility.context = context;
	}
	
	public static boolean isLoggedIn() {
		return Utility.getUser() != null;
	}
	
	public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
	        String search) {
	    for (T each : enumeration.getEnumConstants()) {
	        if (each.name().compareToIgnoreCase(search) == 0) {
	            return each;
	        }
	    }
	    return null;
	}
	
}
