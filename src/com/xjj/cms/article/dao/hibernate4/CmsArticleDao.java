package com.xjj.cms.article.dao.hibernate4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.article.dao.ICmsArticleDao;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

/**
 * 文章dao实现
 * <p>
 * 
 * @author yeyunfeng 2014-9-2 下午3:24:21
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-2
 * @modify by reason:{方法名}:{原因}
 */
@Repository("cmsArticleDao")
public class CmsArticleDao extends BaseHibernateDao<CmsArticle, String>
		implements ICmsArticleDao {

	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;

	@Override
	public List<CmsArticle> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		Object o[] = queryMap.values().toArray();

		if (o[0] instanceof Map) {
			System.out.println("1");
		}
		if (o[0] instanceof Collection) {
			System.out.println("1");
		}
		if (o[0] instanceof Date) {
			System.out.println("1");
		}
		// if (o[0] instanceof Map) {
		// for (Map.Entry e : map.entrySet()) {
		// System.out.println(e.getValue());
		// }
		// }
		// 调用框架提供的方法进行分页查询
		List<CmsArticle> list = listPage(hql.toString(), start, pageSize,
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

	/**
	 * 
	 * @author yeyunfeng 2014-9-2 下午3:13:28
	 * @param queryMap
	 * @param hql
	 * 
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and id = :id");
				paramMap.put("id", queryMap.get("id"));
			}
			if (queryMap.get("title") != null && queryMap.get("title") != "") {
				hql.append(" and title like :title");
				paramMap.put("title", "%" + queryMap.get("title") + "%");
			}
			if (queryMap.get("menuId") != null && queryMap.get("menuId") != "") {
				hql.append(" and menuId = :menuId");
				paramMap.put("menuId", "'" + queryMap.get("menuId") + "'");
			}
			if (queryMap.get("isDelete") != null
					&& queryMap.get("isDelete") != "") {
				hql.append(" and isDelete = :isDelete");
				paramMap.put("isDelete", queryMap.get("isDelete"));
			}
			// 多个栏目
			if (queryMap.get("menuIds") != null
					&& queryMap.get("menuIds") != "") {
				hql.append(" and menuId in :menuIds");
				paramMap.put("menuIds", queryMap.get("menuIds"));
			}
			// 和上面的区别,防止修改错误
			if (queryMap.get("listMenuId") != null) {
				hql.append(" and menuId in (:listMenuId)");
				paramMap.put("listMenuId", queryMap.get("listMenuId"));
			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	public List<CmsArticle> query(int start, int pageSize, String subhql,
			Map<String, Object> queryMap) throws Exception {
		String hql = hql_condition(queryMap, subhql);
		List<CmsArticle> list = listPage(hql.toString(), start, pageSize,
				new Object[0]);
		return list;
	}

	private String hql_condition(Map<String, Object> queryMap, String subhql)
			throws Exception {
		StringBuffer hql = new StringBuffer("from CmsArticle ca where 1=1 ");
		if (queryMap != null) {
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and ca.id = '" + queryMap.get("id") + "'");
			}
			if (queryMap.get("title") != null && queryMap.get("title") != "") {
				hql.append(" and ca.title = '" + queryMap.get("title") + "'");
			}
			if (queryMap.get("creatUserId") != null && queryMap.get("creatUserId") != "") {
				hql.append(" and ca.creatUserId = '" + queryMap.get("creatUserId") + "'");
			}
			if (queryMap.get("searchValue") != null && queryMap.get("searchValue") != "") {
				hql.append(" and ca.title like '%" + queryMap.get("searchValue") + "%'");
			}
			if (queryMap.get("articleName") != null
					&& queryMap.get("articleName") != "") {
				hql.append(" and ca.title like '%"
						+ queryMap.get("articleName") + "%'");
			}
			if (queryMap.get("ifComment") != null
					&& queryMap.get("ifComment") != "") {
				if (!queryMap.get("ifComment").equals("0")) {
					hql.append(" and ca.ifComment = '"
							+ queryMap.get("ifComment") + "'");
				}
			}
			if (queryMap.get("menuId") != null && queryMap.get("menuId") != "") {
				hql.append(" and ca.menuId = '" + queryMap.get("menuId") + "'");
			}
			if (queryMap.get("isDelete") != null
					&& queryMap.get("isDelete") != "") {
				hql.append(" and ca.isDelete = '" + queryMap.get("isDelete")
						+ "'");
			}
			// 多个栏目
			if (null != queryMap.get("menuIds")
					&& !queryMap.get("menuIds").toString().isEmpty()) {
				hql.append(" and ca.menuId in (" + queryMap.get("menuIds")
						+ ")");
			}
			if (queryMap.get("hasImage") != null
					&& queryMap.get("hasImage") != "") {
				hql.append(" and ca.behalf_imageUrl is not null ");
			}
			//栏目分级授权
			if (null != queryMap.get("fjsq_orgCode")
					&& !queryMap.get("fjsq_orgCode").toString().isEmpty()) {
				String pyName = queryMap.get("fjsq_orgCode").toString();
				hql.append(" and ca.menuId in (select mo.menu.id from CmsMenuOrg mo where mo.org.pyCode = '"
						+ pyName + "')");
				String orgIds = getChildOrgIdsByPyName(pyName);
				 hql.append(" and ca.orgId in ("+orgIds+")");
			}
			//审核
			if(null != queryMap.get("check") && !queryMap.get("check").toString().isEmpty()){
				hql.append(" and ca.check_log in ("+queryMap.get("check")+") ");
			}
			if(null != queryMap.get("check_log") && !queryMap.get("check_log").toString().isEmpty()){
				hql.append(" and ca.check_log = "+queryMap.get("check_log"));
			}
			if (null != queryMap.get("orgId") && !queryMap.get("orgId").toString().isEmpty()) {
				if(!queryMap.get("orgName").equals("维护部门")){
					hql.append(" and ca.orgId = '"+queryMap.get("orgId")+"'");
				}
			}
		}
		if (subhql != null) {
			hql.append(subhql);
		}
		return hql.toString();
	}

	public int hql_total(Map<String, Object> queryMap) throws Exception {
		int total = 0;
		String hql = hql_condition(queryMap, null);
		List list = list("select count(*) " + hql);
		if (null != list) {
			total = ((Long) list.get(0)).intValue();
		}
		return total;
	}

	@Override
	public void saveVisitNumAddOne(CmsArticle ca) throws Exception {
		ca.setReadCount((Integer.parseInt(ca.getReadCount()) + 1) + "");
		this.saveOrUpdate(ca);
		this.flush();
	}

	
	private String getChildOrgIdsByPyName(String pyName)throws Exception{
		
		Zorganize org =  orgDao.getByPyName(pyName);
		StringBuffer strbuff = new StringBuffer();
		if (null != org) {
			String orgId = org.getId();
			if (null != orgId) {
				strbuff.append("'"+orgId+"'");
				List<Zorganize> orgList = new ArrayList<>();
				orgList = getChildOrg(orgId);
				for (Zorganize _org : orgList) {
					strbuff.append(",'"+_org.getId()+"'");
				}
			}
		}
		return strbuff.toString();
	}
	
	private List<Zorganize> getChildOrg(String orgId) throws Exception {

		List<Zorganize> orgList = new ArrayList<>();
		orgDao.getChildOrg(orgList, orgId);
		return orgList;
	}
}
