package com.xjj.cms.video.dao.hibernate4;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.video.dao.ICmsVideoDao;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("cmsVideoDao")
public class CmsVideoDao extends BaseHibernateDao<CmsVideo, String> implements ICmsVideoDao {

	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;

	@Override
	public List<CmsVideo> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql)throws  Exception{
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		Map<String, Object> paramMap = builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsVideo> list = listPage(hql.toString(), start, pageSize,
				paramMap);
		return list;
	}

	@Override
	public int total(Map<String, Object> queryMap)throws  Exception{
		StringBuilder hql = new StringBuilder(HQL_COUNT_ALL);
		Map<String, Object> paramMap = builtCondition(queryMap, hql);
		Long total = unique(hql.toString(), paramMap);
		return total.intValue();
	}
	
	/**
	 * @Description: 解析查询条件，拼接查询sql
	 * @param queryMap
	 *            查询
	 * @author luping 2013-12-11下午12:14:20
	 */
	protected Map<String, Object> builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) throws  Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("name") != null && queryMap.get("name") != "") {
				hql.append(" and name = :name");
				paramMap.put("name", queryMap.get("name"));
			}
			if (queryMap.get("menuIds") != null
					&& queryMap.get("menuIds") != "") {
				String menuIds = queryMap.get("menuIds").toString();
				hql.append(" and menuId in("+menuIds+")");
				//paramMap.put("menuIds", queryMap.get("menuIds"));
			}
			//栏目分级授权
//			if (null != queryMap.get("fjsq_orgCode")
//					&& !queryMap.get("fjsq_orgCode").toString().isEmpty()) {
//				String pyName = queryMap.get("fjsq_orgCode").toString();
//				hql.append(" and ca.menuId in (select mo.menu.id from CmsMenuOrg mo where mo.org.pyCode = '"
//						+ pyName + "')");
//				String orgIds = getChildOrgIdsByPyName(pyName);
//				hql.append(" and ca.orgId in ("+orgIds+")");
//			}
			if (queryMap.get("state") != null && queryMap.get("state") != "") {
				String state = queryMap.get("state").toString();
				hql.append(" and state ='"+state+"'");
				//paramMap.put("menuIds", queryMap.get("menuIds"));
			}
		}
		return paramMap;
	}

	@Override
	public CmsVideo findCmsVideoById(String id) {
		String hql = "from CmsVideo where 1=1 and id='"+id+"'";
		List<CmsVideo> list = list(hql);
		if(list !=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}

	}

//	private String getChildOrgIdsByPyName(String pyName)throws Exception{
//
//		Zorganize org =  orgDao.getByPyName(pyName);
//		StringBuffer strbuff = new StringBuffer();
//		if (null != org) {
//			String orgId = org.getId();
//			if (null != orgId) {
//				strbuff.append("'"+orgId+"'");
//				List<Zorganize> orgList = new ArrayList<>();
//				orgList = getChildOrg(orgId);
//				for (Zorganize _org : orgList) {
//					strbuff.append(",'"+_org.getId()+"'");
//				}
//			}
//		}
//		return strbuff.toString();
//	}

//	private List<Zorganize> getChildOrg(String orgId) throws Exception {
//
//		List<Zorganize> orgList = new ArrayList<>();
//		orgDao.getChildOrg(orgList, orgId);
//		return orgList;
//	}
}
