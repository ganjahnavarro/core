package core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import core.service.UserService;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Autowired UserService userService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		initializeServices();
		
		if(modelAndView != null && modelAndView.getModelMap() != null){
			modelAndView.getModelMap().addAttribute("userName", Utility.getUser());
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	private void initializeServices() {
		if(userService == null){
			userService = Utility.getApplicationContext().getBean(UserService.class);
		}
	}
	
}
