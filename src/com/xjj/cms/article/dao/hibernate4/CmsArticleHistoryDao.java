package com.xjj.cms.article.dao.hibernate4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.dao.IZuserDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.article.dao.ICmsArticleHistoryDao;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;
import com.xjj.framework.core.web.filter.WebContext;

@Repository("cmsArticleHistoryDao")
public class CmsArticleHistoryDao extends BaseHibernateDao<CmsArticleHistory, String> implements ICmsArticleHistoryDao {

	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;
	
	@Autowired
	@Qualifier("ZuserDao")
	private IZuserDao iZuserDao;
	
	@Override
	public List<CmsArticleHistory> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) throws Exception {
		String hql = hql_condition(queryMap, subHql);
		List<CmsArticleHistory> list = new ArrayList<>();
		list = listPage(hql.toString(), 0, -1, new Object[0]);
		if(queryMap.get("menuId") != null && queryMap.get("menuId") != "" && queryMap.get("menuId").equals("0")){
			if(list.size() < pageSize*((start/pageSize)+1)){
				list = list.subList(start, list.size());
			}else{
				list = list.subList(start, pageSize*((start/pageSize)+1));
			}
			return list;
		}else{
			
			List<CmsArticleHistory> itemList = new ArrayList<>();
			//判断是否进行文章置顶处理
			if(queryMap.get("notTop") !=null && queryMap.get("notTop") != "" && queryMap.get("notTop").equals("true")){
				itemList = list;
			}else{
				//置顶处理
				if(list.size() > 0){
					for(int i = 0;i<list.size();i++){
						CmsArticleHistory article = list.get(i);
						if(null != article.getTop() && article.getTop() == 1){
							itemList.add(article);
							list.remove(i);
						}
					}
					Collections.sort(itemList, new Comparator<CmsArticleHistory>() {
			            public int compare(CmsArticleHistory arg0, CmsArticleHistory arg1) {
			                return arg1.getCreateTime().compareTo(arg0.getCreateTime());
			            }
			        });
					for(int i = 0;i<list.size();i++){
						CmsArticleHistory article = list.get(i);
						itemList.add(article);
					}
				}
			}
			
			if(itemList.size() < pageSize){
				itemList = itemList.subList(start, itemList.size());
			}else{
				int totalpage = 0;//总页数
				if(itemList.size()%pageSize==0){
					totalpage = itemList.size()/pageSize;
				}else{
					totalpage =  itemList.size()/pageSize+1;
				}
				int curPage = start/pageSize + 1;//当前页数
				if(curPage <totalpage){
					itemList = itemList.subList(start, curPage*pageSize);
				}else{
					itemList = itemList.subList(start, itemList.size());
				}
			}
			return itemList;
		}
	}

	@Override
	public int total(Map<String, Object> queryMap) {
		int total = 0;
		String hql = hql_condition(queryMap, null);
		List list = list("select count(*) " + hql);
		if (null != list) {
			total = ((Long) list.get(0)).intValue();
		}
		return total;
	}

	private String hql_condition(Map<String, Object> queryMap, String subhql) {
		StringBuffer hql = new StringBuffer("from CmsArticleHistory ca where 1=1 ");
		String curUserId = WebContext.getInstance().getHandle().getUserId();
		String curUserAccount = "";
		if(!"".equals(curUserId)){
			curUserAccount = iZuserDao.getAccountById(curUserId);
		}
		
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
				if(!queryMap.get("menuId").equals("0")){
					hql.append(" and ca.menuId = '" + queryMap.get("menuId") + "'");
				}
			}
			if (queryMap.get("isDelete") != null
					&& queryMap.get("isDelete") != "") {
				hql.append(" and ca.isDelete = '" + queryMap.get("isDelete")
						+ "'");
			}
			if (null != queryMap.get("orgId") && !queryMap.get("orgId").toString().isEmpty()) {
				if(!"admin".equals(curUserAccount)){
					if(!queryMap.get("orgName").equals("维护部门")){
						hql.append(" and ca.orgId = '"+queryMap.get("orgId")+"'");
					}
				}
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
			//审核
			if(null != queryMap.get("check") && !queryMap.get("check").toString().isEmpty()){
				hql.append(" and ca.check_log in ("+queryMap.get("check")+") ");
			}
			if(null != queryMap.get("check_log") && !queryMap.get("check_log").toString().isEmpty()){
				hql.append(" and ca.check_log = "+queryMap.get("check_log"));
			}
			
			//栏目分级授权
			if (null != queryMap.get("fjsq_orgCode")
					&& !queryMap.get("fjsq_orgCode").toString().isEmpty()) {
				String pyName = queryMap.get("fjsq_orgCode").toString();
				if(!"admin".equals(curUserAccount)){
					hql.append(" and ca.menuId in (select mo.menu.id from CmsMenuOrg mo where mo.org.pyCode = '"
							+ pyName + "')");
					String orgIds = "";
					try {
						orgIds = getChildOrgIdsByPyName(pyName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					 hql.append(" and ca.orgId in ("+orgIds+")");
				}
			}

			//文章推送
			if(null != queryMap.get("transmitMenuIds") && !queryMap.get("transmitMenuIds").toString().isEmpty()){
				hql.append(" or ca.menuId in ("+queryMap.get("transmitMenuIds")+")");
			}
		}
		if (subhql != null) {
			hql.append(subhql);
		}
		return hql.toString();
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
	
	@Override
	public void saveVisitNumAddOne(CmsArticleHistory cah) {
		cah.setReadCount((Integer.parseInt(cah.getReadCount()) + 1) + "");
		this.saveOrUpdate(cah);
		this.flush();
	}

	@Override
	public Integer getArticleNumByOrg(Zorganize org, String startDate, String endDate) {
		Integer total = 0;
		String hql = "from CmsArticleHistory where orgId = '" + org.getId() + "' and check_log = 2 and substr(createTime,0,10) >= to_date('"+startDate+"','yyyy-mm-dd') and substr(createTime,0,10) <= to_date('"+endDate+"','yyyy-mm-dd')";
		List list = list("select count(*) " + hql);
		if (null != list) {
			total = ((Long) list.get(0)).intValue();
		}
		return total;
	}

}
