package com.xjj.idm.userInfo.dao.hibernate4;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;
import com.xjj.idm.userInfo.dao.UserHeadDao;
import com.xjj.idm.userInfo.model.UserHead;

@Repository("userHeadDao")
public class UserHeadDaoImpl extends BaseHibernateDao<UserHead, Long> implements UserHeadDao {
	/**
	 * 按属性查找对象集合
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from UserHead as model where model."
					+ propertyName + "= ? order by id";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserHead findByUserAccount(String account) {
		try {
			List<UserHead> list = (List<UserHead>) findByProperty("account", account);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (RuntimeException re) {
			throw re;
		}
		return null;
	}

	public void deleteByUserAccount(String account) {
		UserHead head = this.findByUserAccount(account);
		if (head != null) {
			delete(head);
		}
	}

	public void saveUserHead(UserHead head) {
		save(head);
	}

	public void updateUserHead(UserHead head) {
		update(head);
	}
}