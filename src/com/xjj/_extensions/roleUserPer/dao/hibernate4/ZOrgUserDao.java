package com.xjj._extensions.roleUserPer.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZOrgUserDao;
import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZOrgUserDao")
public class ZOrgUserDao extends BaseHibernateDao<ZOrgUser, String> implements
		IZOrgUserDao {

	public List<ZOrgUser> query(int start, int pageSize, ZOrgUser zOrgUser,
			String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);// 初始化HQL
		Map<String, Object> paramMap = new HashMap<String, Object>();// 采用map装载
																		// 通配符和参数值
		builtCondition(zOrgUser, hql, paramMap);// 用map拼装查询条件
		if (subHql != null)
			hql.append(subHql);
		return listPage(hql.toString(), start, pageSize, paramMap);
	}

	protected void builtCondition(ZOrgUser zOrgUser, StringBuilder hql,
			Map paramMap) {
		if (zOrgUser != null) {
			if (zOrgUser.getUserId() != null) {
				hql.append(" and userId =:userId");
				paramMap.put("userId", zOrgUser.getUserId());
			}
			if (zOrgUser.getOrgId() != null) {
				hql.append(" and orgId =:orgId");
				paramMap.put("orgId", zOrgUser.getOrgId());
			}
		}
	}
	
	public ZOrgUser checkExistZOrgUser(String orgId, String userId) {
		String hql = "";
		hql = "from ZOrgUser where orgId ='" + orgId +"' and userId='" + userId +"'";
		List<ZOrgUser> list = list(hql);
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public ZOrgUser checkExistZOrgUser(String userId) {
		String hql = "";
		hql = "from ZOrgUser where userId='" + userId +"'";
		List<ZOrgUser> list = list(hql);
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
