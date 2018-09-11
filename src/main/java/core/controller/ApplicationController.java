package core.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import core.Constants;
import core.DummySession;
import core.dto.CredentialsData;
import core.dto.UserData;
import core.dto.mapper.UserMapper;
import core.model.User;
import core.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ApplicationController extends AbstractController {

	@Autowired private UserService service;

	private UserMapper MAPPER = UserMapper.INSTANCE;

	@RequestMapping(value =  "login", method = RequestMethod.POST)
	public UserData login(@RequestBody CredentialsData credentialsData,
			HttpSession session) {
		User user = service.findByCredentials(credentialsData.getUserName(), credentialsData.getPassword());
		if (user != null && user.getActive()) {
			session.setAttribute(Constants.ATTRIBUTE_LOGGED_USER, user);
			DummySession.currentUser = user;
			return MAPPER.toData(user);
		}
		throw new IllegalArgumentException("Invalid username and/or password.");
	}
	
	@RequestMapping(value =  "logout", method = RequestMethod.POST)
	public void logout(HttpSession session) {
		User user = (User) session.getAttribute(Constants.ATTRIBUTE_LOGGED_USER);
		System.out.println(user.getUserName());
		
		session.removeAttribute(Constants.ATTRIBUTE_LOGGED_USER);
	}

}
