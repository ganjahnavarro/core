package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.Constants;
import core.model.User;

public abstract class AbstractController {

	protected User getUser(HttpServletRequest request) {
		return (User) request.getAttribute(Constants.ATTRIBUTE_LOGGED_USER);
	}

}
