package core.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

import core.model.IRecord;
import core.repository.AbstractRepository;

@Transactional
public abstract class AbstractService {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractService.class);
	
	@SuppressWarnings("rawtypes")
	public abstract AbstractRepository getRepository();
	
	public IRecord findById(Long id) {
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
	public List findPagedItems(Integer pageSize, Integer pageOffset, String orderBy){
		return getRepository().findPagedItems(pageSize, pageOffset, orderBy);
	}
	
	public IRecord save(IRecord record) {
		try {
			return getRepository().persist(record);
		} catch(ConstraintViolationException e) {
			LOGGER.error("Error saving object", e);
			throw e;
		}
	}
	
	public Object savePlainObject(Object object) {
		try {
			return getRepository().persistPlainObject(object);
		} catch(ConstraintViolationException e) {
			LOGGER.error("Error saving object", e);
			throw e;
		}
	}
	
	public IRecord update(IRecord source) {
		return getRepository().merge(source);
	}
	
	public void delete(IRecord record) {
		getRepository().delete(record);
	}
	
	public void deleteRecordById(Long id) {
		getRepository().deleteRecordById(id);
	}
	
}
