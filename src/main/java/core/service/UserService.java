package core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.enums.UserType;
import core.model.User;
import core.repository.AbstractRepository;
import core.repository.UserRepository;

@Service
@Transactional
public class UserService extends AbstractService {

	@Autowired UserRepository repository;
	
	@Override
	public AbstractRepository<User> getRepository() {
		return repository;
	}
	
	public User findByUsername(String userName) {
		return repository.findByUsername(userName);
	}
	
	public void activateUser(String token) {
		repository.activateUser(token);
	}

	public List<User> find(UserType... types) {
		return repository.find(types);
	}
	
	public List<User> findFilteredItems(String filter, Integer pageSize, Integer pageOffset, String orderBy) {
		return repository.findFilteredItems(filter, pageSize, pageOffset, orderBy);
	}

}
