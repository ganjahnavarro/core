package core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.Validator;
import core.dto.UserData;
import core.dto.mapper.UserMapper;
import core.model.User;
import core.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired private UserService service;
	@Autowired private Validator validator;

	private UserMapper MAPPER = UserMapper.INSTANCE;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public List<UserData> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "pageOffset", required = false) Integer pageOffset,
			@RequestParam(value = "orderedBy", required = false) String orderedBy) {
		return MAPPER.toData(service.findFilteredItems(filter, pageSize, pageOffset, orderedBy));
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public UserData create(@RequestBody UserData userData) throws Exception {
		validate(userData);
		
		User user = MAPPER.fromData(userData);
		return MAPPER.toData((User) service.save(user));
	}

	@RequestMapping(value = "/", method = RequestMethod.PATCH)
	public UserData update(@RequestBody UserData userData) throws Exception {
		validate(userData);
		
		User user = MAPPER.fromData(userData);
		return MAPPER.toData((User) service.update(user));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		service.deleteRecordById(id);
	}
	
	private void validate(UserData userData) throws Exception {
		Runnable validateEmail = () -> validator.validateEmailAddress(userData.getEmail());
		Runnable validatePassword = () -> validatePassword(userData);
		Runnable validateBirthDate = () -> validator.validateDate(userData.getBirthDate(), "Invalid birth date format.");

		validator.aggregate(validatePassword, validateEmail, validateBirthDate);
	}

	private void validatePassword(UserData userData) {
		if (userData.getPassword() != null && !userData.getPassword().equals(userData.getPasswordConfirmation())) {
			throw new IllegalArgumentException("Password does not match.");
		}
	}

}
