package core.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import core.enums.UserType;
import core.model.User;

@Repository
@Transactional
public class UserRepository extends AbstractRepository<User> {
	
	public User findByUsername(String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName));
		return (User) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> find(UserType... types) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.in("type", types));
		criteria.addOrder(Order.asc("lastName"));
		return (List<User>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findFilteredItems(String filter, Integer pageSize, Integer pageOffset, String orderBy) {
		Criteria criteria = getPagedItemsCriteria(pageSize, pageOffset, orderBy);
		
		if (filter != null && !filter.isEmpty()) {
			criteria.add(Restrictions.ilike("lastName", filter, MatchMode.START));
		}
		
		return criteria.list();
	}
	
	public void activateUser(String token) {
		getSession().createQuery("update " + User.ENTITY_NAME + " o"
				+ " set active = true"
				+ " where o.confirmationToken = :token")
				.setParameter("token", token)
				.executeUpdate();
	}

	@Override
	protected String getEntityName() {
		return User.ENTITY_NAME;
	}

}
