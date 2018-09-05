package core.controller;

import javax.servlet.http.HttpServletRequest;

import core.Constants;
import core.model.IRecord;
import core.model.User;
import core.service.AbstractService;

public abstract class AbstractController {
	
	protected AbstractService getService() {
		return null;
	};

	protected User getUser(HttpServletRequest request) {
		return (User) request.getAttribute(Constants.ATTRIBUTE_LOGGED_USER);
	}
	
	protected void delete(Long id) {
		if (getService() == null) {
			throw new Error("Accessing super.delete() without overriding getService method.");
		}
		
		IRecord record = getService().findById(id);
		if (record != null) {
			try {
				getService().delete(record);
			} catch (Throwable e) {
				record.setDeleted(true);
				getService().update(record);
			}
		}
	}

}
