package com.xjj._extensions.roleUserPer.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZRoleUserDao;
import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZRoleUserDao")
public class ZRoleUserDao extends BaseHibernateDao<ZRoleUser, String> implements IZRoleUserDao {
	
	public List<ZRoleUser> query(int start, int pageSize,
			ZRoleUser zRoleUser, String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);//初始化HQL
        Map<String, Object> paramMap = new HashMap<String, Object>();//采用map装载 通配符和参数值
        builtCondition(zRoleUser, hql, paramMap);//用map拼装查询条件
        if (subHql != null)
            hql.append(subHql);
        return listPage(hql.toString(), start, pageSize, paramMap);
	}

	protected void builtCondition(ZRoleUser zRoleUser, StringBuilder hql, Map paramMap) {
        if (zRoleUser != null) {
            if (zRoleUser.getUserId() != null) {
            	hql.append(" and userId =:userId");
                paramMap.put("userId", zRoleUser.getUserId());
            }
            if (zRoleUser.getRoleId() != null) {
            	hql.append(" and roleId =:roleId");
            	paramMap.put("roleId", zRoleUser.getRoleId());
            }
        }
    }
	
}
