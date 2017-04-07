package core.repository;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import core.Utility;
import core.model.IRecord;

@Transactional
public abstract class AbstractRepository<T> {

	@Autowired private SessionFactory sessionFactory;
	
	private final Class<T> persistentClass;
	
	protected abstract String getEntityName();

	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer key) {
		return (T) getSession().get(persistentClass, key);
	}

	public IRecord persist(IRecord record) {
		preProcess(record);
		getSession().persist(record);
		return record;
	}

	public IRecord merge(IRecord record) {
		preProcess(record);
		getSession().merge(record);
		return record;
	}
	
	private void preProcess(IRecord record) {
		record.setModifiedBy(Utility.getUser());
		record.setModifiedDate(new Date());
	}

	public void delete(IRecord record) {
		try {
			getSession().delete(record);
		} catch (ConstraintViolationException e) {
			record.setDeleted(true);
			preProcess(record);
			merge(record);
		}
	}
	
	public void deleteRecordById(Long id) {
		Query query = getSession().createSQLQuery("delete from " + getEntityName() + " where id = :id");
		query.setLong("id", id);
		query.executeUpdate();
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll(){
		return findAll(null);
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll(String orderBy){
		return getOrderedCriteria(orderBy).list();
	}
	
	@SuppressWarnings("rawtypes")
	public List findPagedItems(String orderBy, Integer pageSize, Integer pageOffset){
		return getPagedItemsCriteria(orderBy, pageSize, pageOffset).list();
	}
	
	protected Criteria getOrderedCriteria(String orderBy){
		Criteria criteria = createEntityCriteria();
		if (orderBy != null) {
			criteria.addOrder(Order.asc(orderBy));
		}
		
		criteria.add(Restrictions.eqOrIsNull("deleted", false));
		return criteria;
	}
	
	protected Criteria getPagedItemsCriteria(String orderBy, Integer pageSize, Integer pageOffset){
		Criteria criteria = getOrderedCriteria(orderBy);
		
		if (pageSize != null) {
			criteria.setMaxResults(pageSize);
		}
		
		if (pageOffset != null) {
			int itemOffset = (pageSize * pageOffset);
			criteria.setFirstResult(itemOffset);
		}
		return criteria;
	}
	
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}
	
	

}