package core.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import core.model.Config;

@Repository
@Transactional
public class ConfigRepository extends AbstractRepository<Config> {

	@SuppressWarnings("unchecked")
	public List<Config> findFilteredItems(String filter, Integer pageSize, Integer pageOffset, String orderBy) {
		Criteria criteria = getPagedItemsCriteria(pageSize, pageOffset, orderBy);

		if (filter != null && !filter.isEmpty()) {
			criteria.add(Restrictions.ilike("name", filter, MatchMode.START));
		}
		return criteria.list();
	}
	
	public Config findByName(String name) {
		Criteria criteria = getDefaultCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Config) criteria.uniqueResult();
	}

	@Override
	protected String getEntityName() {
		return Config.ENTITY_NAME;
	}

}
