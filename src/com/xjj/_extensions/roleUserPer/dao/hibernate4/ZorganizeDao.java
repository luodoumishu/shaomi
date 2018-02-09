package com.xjj._extensions.roleUserPer.dao.hibernate4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZorganizeDao")
public class ZorganizeDao extends BaseHibernateDao<Zorganize, String> implements IZorganizeDao {
	
	public List<Zorganize> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<Zorganize> list = listPage(hql.toString(), start, pageSize,
				queryMap);
		return list;
	}

	@Override
	public int total(Map<String, Object> queryMap) {
		StringBuilder hql = new StringBuilder(HQL_COUNT_ALL);
		builtCondition(queryMap, hql);
		Long total = unique(hql.toString(), queryMap);
		return total.intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Zorganize> listTempByParentId(String parentId){
		String sql = "from Zorganize t where t.parentId=?";
		String[]paramList = {parentId}; 
		Query query = getSession().createQuery(sql);
		setParameters(query, paramList);
		return query.list();
	}
	
	/**
	 * @Description: 解析查询条件，拼接查询sql
	 * @param queryMap
	 *            查询
	 * @author longdp 2013-12-11下午12:14:20
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("name") != null && queryMap.get("name") != "") {
				hql.append(" and name = :name");
				paramMap.put("name", queryMap.get("name"));
			}
			if (queryMap.get("parentId") != null && queryMap.get("parentId") != "") {
				hql.append(" and parentId = :parentId");
				paramMap.put("parentId", queryMap.get("parentId"));
			}
			if (queryMap.get("notInId") != null && queryMap.get("notInId") != "") {
				hql.append(" and id not in ("+queryMap.get("notInId")+")");
			}
//			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
//			List<String> ids = getListByOrgId(orgRootId);
//			if(ids!=null && ids.size()>0){
//				String orgIds = "";
//				for(String id:ids){
//					if(!"".equals(orgIds)){
//						orgIds += ",";
//					}
//					orgIds += "'"+id+"'";
//				}
//				hql.append(" and id in ("+orgIds+")");
//			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<Zorganize> listAllByOrgRootId(String orgRootId) {
		List<String> ids = getListByOrgId(orgRootId);
		if(ids!=null && ids.size()>0){
			String orgIds = "";
			for(String id:ids){
				if(!"".equals(orgIds)){
					orgIds += ",";
				}
				orgIds += "'"+id+"'";
			}
			String hsql = "from Zorganize t where t.id in ("+orgIds+") order by pri";
			Query query = getSession().createQuery(hsql);
			setParameters(query, null);
			return query.list();
		}
		return null;
	}
	
	public List<String> getListByOrgId(String orgId){
		String sql = "select id from z_organize_t";
		//String sql = "select id from z_organize_t s start with s.id='"+orgId+"' connect by prior s.id=s.parentid";
		List<String> ids = getSession().createSQLQuery(sql).addScalar("id",StringType.INSTANCE).list();
		return ids;
	}
	public boolean checkExistChildOrg(String orgId){
		String sql = "";
		List<Zorganize> list = null;
		sql = " select * from z_organize_t where parentid ='"+orgId+"'";
		list = getSession().createSQLQuery(sql).addScalar("id",StringType.INSTANCE).list();	
		if(!list.isEmpty()){
			return true;
		}else{
			return false;
		}	
    }
	
	public List<Zorganize> getBypId(String pid) throws Exception {
		List<Zorganize> orgList = new ArrayList<>();
		String hql = "from Zorganize t where t.parentId = '" + pid
				+ "' order by t.pri";
		orgList = list(hql);
		return orgList;
	}
	
	public List<Zorganize> getChildOrg(List<Zorganize> orgList, String orgId)
			throws Exception {
		List<Zorganize> childList = new ArrayList<>();
		childList = getBypId(orgId);
		if (null != childList && childList.size() > 0) {
			for (Zorganize org : childList) {
				orgId = org.getId();
				orgList.add(org);
				getChildOrg(orgList, orgId);
			}
		}
		return orgList;
	}
	
	public Zorganize getByPyName(String pyName) throws Exception {
		Zorganize org = new Zorganize();
		List<Zorganize> orgList = new ArrayList<>();
		String hql = "from Zorganize t where t.pyCode = '" + pyName + "'";
		orgList = list(hql);
		if (null != orgList && orgList.size() > 0) {
			org = orgList.get(0);
		}
		return org;
	}

	@Override
	public List<Zorganize> checkIsAlreadyExisPyCode(String pyName) {
		String hql = "from Zorganize t where t.pyCode like '" + pyName + "%'";
		List<Zorganize> orgList = list(hql);
		if (null != orgList && orgList.size() > 0) {
			return orgList;
		}
		return null;
	}

	@Override
	public boolean checkIsExisPyCode(String pyName) {
		String hql = "from Zorganize t where t.pyCode = '" + pyName + "'";
		List<Zorganize> orgList = list(hql);
		if (null != orgList && orgList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIsChangeName(String id,String name) {
		String hql = "from Zorganize t where t.id = '"+id+"' and t.name = '" + name + "'";
		List<Zorganize> orgList = list(hql);
		if (null != orgList && orgList.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Zorganize> getAllOrg() {
		List<Zorganize> listOrg = new ArrayList<Zorganize>();
		String hql = "from Zorganize order by pri";
		listOrg = list(hql);
		return listOrg;
	}
}
