package com.xjj.cms.file.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.base.dao.ICmsAffixDao;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.file.dao.ICmsFileDao;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsFileDao")
public class CmsFileDao extends BaseHibernateDao<CmsFile, String> implements ICmsFileDao {
	
	@Autowired
	@Qualifier("cmsAffixDao")
	private ICmsAffixDao cmsAffixDao;
	
	public List<CmsFile> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsFile> list = listPage(hql.toString(), start, pageSize, queryMap);
		//文件下有多少个附件信息；
		int siz = list.size();
		for (int i = 0; i <siz; i++) {
			CmsFile file =  list.get(i);
			String id = file.getId();
			//查询他下面的附件
			List<CmsAffix> affixList =  cmsAffixDao.findAffixByModelId(id);
			file.setCmsAffixs(affixList);
			list.get(i).update();
		}
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
	 * @Description: 解析查询条件，拼接查询sql
	 * @param queryMap
	 *            查询
	 * @author longdp 2013-12-11下午12:14:20
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			/*if (queryMap.get("channeName") != null && queryMap.get("channeName") != "") {
				hql.append(" and channeName like :channeName");
				paramMap.put("channeName", "%"+queryMap.get("channeName")+"%");
			}*/
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}
	public List<CmsFile> query(int start, int pageSize,String subhql,Map<String, Object> queryMap)throws Exception {
		String hql = hql_condition(queryMap,subhql);
		List<CmsFile> list  = listPage(hql.toString(), start, pageSize, new Object[0]);
		return list;
	}
	
	private String hql_condition(Map<String, Object> queryMap,String subhql) throws Exception{
		StringBuffer hql = new StringBuffer("from CmsFile ca where 1=1 ");
		if (queryMap != null) {
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and ca.id = '"+queryMap.get("id")+"'");
			}
			if (queryMap.get("title") != null
					&& queryMap.get("title") != "") {
				hql.append(" and ca.title = '"+queryMap.get("title")+"'");
			}
			if (queryMap.get("menuId") != null && queryMap.get("menuId") != "") {
				hql.append(" and ca.menuId = '"+queryMap.get("menuId")+"'");
			}
			if (queryMap.get("isDelete") != null && queryMap.get("isDelete") != "") {
				hql.append(" and ca.isDelete = '"+queryMap.get("isDelete")+"'");
			}
			//多个栏目
			if (null !=  queryMap.get("menuIds") && !queryMap.get("menuIds").toString().isEmpty()) {
				hql.append(" and ca.menuId in ("+queryMap.get("menuIds")+")");
			}
			if (queryMap.get("hasImage") != null && queryMap.get("hasImage") != "") {
				hql.append(" and ca.behalf_imageUrl is not null ");
			}
			
		}
		if (subhql != null) {
			hql.append(subhql);
		}
		return hql.toString();
	}

	@Override
	public List<CmsAffix> affixQuery(int start, int pageSize, String subHql,
			Map<String, Object> queryMap) {
		String condition = (String) queryMap.get("menuIds");
		String newHql = "";
		StringBuilder hql = new StringBuilder("from CmsFile where menuId in (" + condition + ")");
		List<CmsAffix> listAffix = null;
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsFile> list = listPage(hql.toString(), start, pageSize, queryMap);
		if(!list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				String temp = "'" + list.get(i).getId() + "'";
				newHql += temp + ",";
			}
			newHql = newHql.substring(0,newHql.toString().length()-1);
			System.out.println(newHql);
			hql = new StringBuilder("from CmsAffix where modeId in (" + newHql + ")");
			builtCondition(queryMap, hql);
			if (subHql != null) {
				hql.append(subHql);
			}
			listAffix = listPage(hql.toString(), start, pageSize, queryMap);
		}
		return listAffix;
	}
}
