package core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import core.model.IRecord;
import core.repository.AbstractRepository;

@Transactional
public abstract class AbstractService {
	
	@SuppressWarnings("rawtypes")
	public abstract AbstractRepository getRepository();
	
	public IRecord findById(Integer id) {
		return (IRecord) getRepository().findById(id);
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll() {
		return findAll(null);
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll(String orderBy) {
		return getRepository().findAll(orderBy);
	}
	
	@SuppressWarnings("rawtypes")
	public List findPagedItems(String orderBy, Integer pageSize, Integer pageOffset){
		return getRepository().findPagedItems(orderBy, pageSize, pageOffset);
	}
	
	public void save(IRecord record) {
		getRepository().persist(record);
	}
	
	public void delete(IRecord record) {
		getRepository().delete(record);
	}
	
	public void update(IRecord source) {
		getRepository().merge(source);
	}
	
}
