package core;

import java.util.Date;

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
		if(modelAndView != null && modelAndView.getModelMap() != null){
			modelAndView.getModelMap().addAttribute("timestamp", new Date().getTime());
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
