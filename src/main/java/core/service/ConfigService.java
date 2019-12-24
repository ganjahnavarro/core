package core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.model.Config;
import core.repository.AbstractRepository;
import core.repository.ConfigRepository;

@Service
@Transactional
public class ConfigService extends AbstractService {

	@Autowired private ConfigRepository repository;
	
	@Override
	public AbstractRepository<Config> getRepository() {
		return repository;
	}
	
	public List<Config> findFilteredItems(String filter, Integer pageSize, Integer pageOffset, String orderBy) {
		return repository.findFilteredItems(filter, pageSize, pageOffset, orderBy);
	}
	
	public Config findByName(String name) {
		return repository.findByName(name);
	}

}
