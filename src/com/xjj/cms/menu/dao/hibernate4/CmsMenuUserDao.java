package com.xjj.cms.menu.dao.hibernate4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.cms.menu.dao.ICmsMenuUserDao;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.model.CmsOrgUser;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;
import com.xjj.framework.core.web.filter.WebContext;

@Repository("cmsMenuUserDao")
public class CmsMenuUserDao extends BaseHibernateDao<CmsMenuUser, String> implements ICmsMenuUserDao {
	
	
	@Autowired
	@Qualifier("cmsMenuOrgDao")
	private ICmsMenuOrgDao cmsMenuOrgDao;
	
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao orgDao;
	
	@Override
	public List<CmsMenuUser> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		// 调用框架提供的方法进行分页查询
		List<CmsMenuUser> list = listPage(hql.toString(), start, pageSize, queryMap);
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
	 * 解析查询条件，拼接查询sql
	 *  @param queryMap 查询条件
	 * @author yeyunfeng 2014-9-2  下午3:15:01
	 * @param queryMap
	 * @param hql
	 *
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("menuId") != null && queryMap.get("menuId") != "") {
				hql.append(" and menuId = :menuId");
				paramMap.put("menuId", queryMap.get("menuId"));
			}
			if (CmsCC.ENABLED_MENU_FJSQ) { //启用分级授权
				String cur_orgId  = WebContext.getInstance().getHandle().getOrgID(); //当前用户对应部门id
				String sx_orgId = null;
				try {
					sx_orgId = cmsMenuOrgDao.getByLoopOrgId(cur_orgId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer buffer = new StringBuffer();
				try {
					buffer = getchildOrgId(sx_orgId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				buffer.append("'"+sx_orgId+"'");
				hql.append(" and userId in( select ou.userId from ZOrgUser ou where ou.orgId in("+buffer.toString()+") )");
				}
			}
			
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
	}

	/**
	 * 获取子部门ids
	 * @author yeyunfeng 2015年6月1日  下午3:16:09
	 * @param sx_orgId
	 * @return
	 *
	 */
   private StringBuffer getchildOrgId(String sx_orgId) throws Exception{
	 StringBuffer buffer = new StringBuffer();
	 List<Zorganize> orgList = new ArrayList<Zorganize>();
	 orgDao.getChildOrg(orgList, sx_orgId);
	 int size = orgList.size();
	 for (int i = 0; i < size; i++) {
		 String orgId = orgList.get(i).getId();
		 buffer.append("'"+orgId+"',");
	 }
	 return buffer;
   }

	
	
	@Override
	public List<Zuser> getByAll() {
		String sql = "from Zuser t where 1 = 1 ";
		return list(sql);
	}



	@Override
	public boolean exitUserInMenuUser(String id, String menuId) {
		String hql = "from CmsMenuUser cm where cm.userId ='" + id +"' and cm.menuId = '"+menuId+"'";
		List<CmsMenuUser> itemList  = list(hql);
		if (null == itemList || itemList.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CmsOrgUser> getByParentId(String parentId) {
		List<CmsOrgUser> couList = new ArrayList<CmsOrgUser>();
		String sql = "from ZOrgUser t where t.orgId = ? order by t.id";
		String[] paramList = {parentId}; 
		Query query = getSession().createQuery(sql);
		setParameters(query, paramList);
		List<ZOrgUser> list = query.list();
		for(int i = 0;i<list.size();i++){
			ZOrgUser orgUser = list.get(i);
			CmsOrgUser cou = new CmsOrgUser();
			cou.setUserId(orgUser.getUserId());
			sql = "from Zuser where id='"+orgUser.getUserId()+"'";
			Zuser user = (Zuser) list(sql).get(0); 
			cou.setUserName(user.getName());
			couList.add(cou);
		}
		return couList;
	}



	@Override
	public boolean findChildOrg(String parentId) {
		String sql = "from Zorganize t where t.parentId = ? order by t.pri,t.createTime desc ";
		String[] paramList = {parentId}; 
		Query query = getSession().createQuery(sql);
		setParameters(query, paramList);
		if(query.list().size()>0){
			return true;
		}else{
			return false;
		}
	}



	@Override
	@SuppressWarnings("unchecked")
	public List<CmsOrgUser> getByParentOrg(String parentId) {
		List<CmsOrgUser> couList = new ArrayList<CmsOrgUser>();
		String sql = "from Zorganize t where t.parentId = ? order by t.pri,t.createTime desc ";
		String[] paramList = {parentId}; 
		Query query = getSession().createQuery(sql);
		setParameters(query, paramList);
		List<Zorganize> list = query.list();
		for(int i = 0;i<list.size();i++){
			CmsOrgUser cou = new CmsOrgUser();
			Zorganize org = list.get(i);
			cou.setOrgId(org.getId());
			cou.setOrgName(org.getName());
			couList.add(cou);
		}
		return couList;
	}



	@Override
	public boolean isAddUser(String userId, String menuId) {
		String hql = "from CmsMenuUser t where t.userId='"+userId+"' and menuId='"+menuId+"'";
		List<CmsMenuUser> list = list(hql);
		if(list.size() == 0){
			return false;
		}
		return true;
	}

	@Override
	public String getMenuNameByMenuId(String menuId) {
		String hql = "from CmsMenu t where t.id='"+menuId+"'";
		List<CmsMenu> menu = list(hql);
		if(menu.size()>0){
			return menu.get(0).getMenuName();
		}
		return null;
	}
	
	@Override
	public String getUserNameByUserId(String userId) {
		String hql = "from Zuser t where t.id='"+userId+"'";
		List<Zuser> menu = list(hql);
		if(menu.size()>0){
			return menu.get(0).getName();
		}
		return null;
	}



	@Override
	public CmsMenuUser getMenuUserById(String id) {
		String hql = "from CmsMenuUser t where t.id='"+id+"'";
		List<CmsMenuUser> cmu = list(hql);
		if(cmu.size()>0){
			return cmu.get(0);
		}
		return null;
	}
}
