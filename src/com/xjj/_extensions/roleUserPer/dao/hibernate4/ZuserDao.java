package com.xjj._extensions.roleUserPer.dao.hibernate4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.roleUserPer.dao.IZuserDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.util.Pinyin;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;
import com.xjj.framework.core.web.filter.WebContext;

@Repository("ZuserDao")
public class ZuserDao extends BaseHibernateDao<Zuser, String> implements IZuserDao {
	
	public List<Zuser> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql) {
		// 初始化HQL
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		// 解析查询条件，拼接查询sql
		builtCondition(queryMap, hql);
		if (subHql != null) {
			hql.append(subHql);
		}
		System.out.println(hql);
		// 调用框架提供的方法进行分页查询

		List<Zuser> list = listPage(hql.toString(), start, pageSize,
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
	 * @Description: 解析查询条件，拼接查询sql
	 * @param queryMap
	 *            查询
	 * @author longdp 2013-12-11下午12:14:20
	 */
	protected void builtCondition(Map<String, Object> queryMap,
			StringBuilder hql) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (queryMap != null) {
			if (queryMap.get("orgId") != null && queryMap.get("orgId") != "") {
				hql.append(" and id in (select z.userId from ZOrgUser z where z.orgId = :orgId)");
				paramMap.put("orgId", queryMap.get("orgId"));
			}
			if (queryMap.get("name") != null && queryMap.get("name") != "") {
				hql.append(" and name like :name");
				paramMap.put("name", "%"+queryMap.get("name")+"%");
			}
			if (queryMap.get("id") != null && queryMap.get("id") != "") {
				hql.append(" and id =:id");
				paramMap.put("id", queryMap.get("id"));
			}
			if (queryMap.get("uerIds") != null && queryMap.get("uerIds") != "") {
				System.out.println(queryMap.get("uerIds"));
				String uerIds_str =  (String) queryMap.get("uerIds");
				String uerIds_s[] = uerIds_str.split(",");
				String uerIds_str_s = "";
				int length = uerIds_s.length;
				for (int i = 0;i<length;i++){
					uerIds_str_s +="'"+uerIds_s[i]+"',";
				}
				if (uerIds_str_s.length()>0){
					uerIds_str_s = uerIds_str_s.substring(0,uerIds_str_s.length()-1);
					System.out.println(uerIds_str_s);
				}
				hql.append(" and id in ("+uerIds_str_s+")");
				//hql.append(" and uerIds in(:uerIds)");
				//paramMap.put("uerIds", queryMap.get("uerIds"));
			}
//			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
//			if (orgRootId != null && !"".equals(orgRootId)) {
//				hql.append(" and orgRootId = :orgRootId");
//				paramMap.put("orgRootId", orgRootId);
//			}
			// 解析后重新赋值
			queryMap.clear();
			queryMap.putAll(paramMap);
		}
	}

	@Override
	public List<Zuser> query(int start, int pageSize, Zuser zuser,
			String subHql) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);//初始化HQL
        Map<String, Object> paramMap = new HashMap<String, Object>();//采用map装载 通配符和参数值
        builtCondition(zuser, hql, paramMap);//用map拼装查询条件
        if (subHql != null)
            hql.append(subHql);
        return listPage(hql.toString(), start, pageSize, paramMap);
	}
	
	protected void builtCondition(Zuser zuser, StringBuilder hql, Map paramMap) {
        if (zuser != null) {
            if (zuser.getName() != null) {
            	hql.append(" and name =:name");
                paramMap.put("name", zuser.getName());
            }
            if (zuser.getAccount() != null) {
            	hql.append(" and account =:account");
                paramMap.put("account", zuser.getAccount());
            }
//            if (zuser.getOrgRootId() != null) {
//            	hql.append(" and orgRootId =:orgRootId");
//                paramMap.put("orgRootId", zuser.getOrgRootId());
//            }
            if(zuser.getOrgs() !=null && zuser.getOrgs().size()>0){
            	hql.append(" and id in (select userId from ZOrgUser t where t.orgId in (");
            	for(int i=0;zuser.getOrgs().size()>i;i++){
            		if(i!=0){
            			hql.append(",");
            		}
            		Zorganize org=zuser.getOrgs().get(i);
            		hql.append("'"+org.getId()+"'");
            	}
            	hql.append(")) ");
            }
        }
    }

	@Override
	public Zuser getObjectById(String id) {
		Zuser zuser = get(id);
		getSession().clear();
		return zuser;
	}

	@Override
	public List<Zuser> getListByOrgId(List<String> orgIds) {
		if(orgIds!=null && orgIds.size()>0){
			Zuser zuser = new Zuser();
			List<Zorganize> orgs = new ArrayList<Zorganize>();
			for(String orgId:orgIds){
				Zorganize zorganize = new Zorganize();
				zorganize.setId(orgId);
				orgs.add(zorganize);
			}
			zuser.setOrgs(orgs);
//			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
//			zuser.setOrgRootId(orgRootId);
			return this.query(-1, -1, zuser, " order by pri");
		}else{
			Zuser zuser = new Zuser();
//			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
//			zuser.setOrgRootId(orgRootId);
			return this.query(-1, -1, zuser, " order by pri");
		}
	}

	@Override
	public List<Zuser> getListByOrgidFilterstr(String orgid, String filterStr) {
		String sql = "select t.* from z_user_t t where 1=1 ";
		
		if(orgid !=null && !orgid.equals("")){
			sql += " and t.id in(select org_user.userid from z_org_user_t org_user where org_user.orgid in (select org.id from z_organize_t org start with org.id='"+orgid+"' connect by prior org.id=org.parentid))";
		}
		
    	if (filterStr != null && filterStr != "") {
    		String sb = Pinyin.getEngHeadChat(filterStr);
    		//String sb = null;
    		if(sb==null || sb.equals("")){
    			sql += " and (t.name like '%"+filterStr+"%' or t.id in (select org_user.userid from z_org_user_t org_user where org_user.orgid in (select org.id from z_organize_t org where org.name like '%"+filterStr+"%')))";
    		}else{
    			sql += " and t.HEADPY like '%"+sb+"%' ";
    		}
		}
        
		return getSession().createSQLQuery(sql).addEntity(Zuser. class).list();
	}

	@Override
	public void updateAccount(String id,String password) {
		this.execteNativeBulk("update z_user_t set password='"+password+"' where id='"+id+"'");
	}

	@Override
	public String getAccountById(String curUserId) {
		String hql = "from Zuser z where 1=1 and z.id='"+curUserId+"'";
		List<Zuser> list = list(hql);
		if(list!=null && list.size()>0){
			return list.get(0).getAccount();
		}
		return null;
	}
	
	@Override
	public Map getUserStatus(String account) {
		Map map = mapByNative("select (failuretimes + 1) failuretimes, status from z_user_t where account = ?", account);
		return map;
	}
}
