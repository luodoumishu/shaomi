package com.xjj._extensions.roleUserPer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.adrsbook.web.controller.AddressContactVo;
import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.dao.IZuserDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj._extensions.util.Pinyin;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.jdk17.utils.StringUtil;

@Service("ZuserService")
public class ZuserService extends BaseService<Zuser, String> implements IZuserService{

	@Autowired
    @Qualifier("ZuserDao")
    private IZuserDao dao;
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao iZorganizeDao;

    @Autowired
    @Qualifier("ZuserDao")
    @Override
	public void setBaseDao(IBaseDao<Zuser, String> baseDao) {
		this.baseDao = dao;
	}
	
    public Zuser save(Zuser model) {
		return super.save(model);
	}

	public Page<Zuser> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<Zuser> items = dao.query(skip,pageSize,queryMap, " order by orgId,id");
		//获取总记录数
		int total =  dao.total(queryMap);
		//构造返回对象page
		Page<Zuser> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public List<Zuser> query(int start, int pageSize, Zuser zuser) {
		return dao.query(start, pageSize, zuser, " order by orgId,id");
	}

	@Override
	public Zuser getObjectById(String id) {
		return dao.getObjectById(id);
	}

	@Override
	public List<Zuser> getListByOrgId(String orgId,boolean includeSubOrg) {
		List<String> orgIds = new ArrayList<String>();
		if(includeSubOrg){
			orgIds = iZorganizeDao.getListByOrgId(orgId);
		}else{
			orgIds.add(orgId);
		}
		return dao.getListByOrgId(orgIds);
	}
	
	public Zuser getZuserByAccount(String account){
		Zuser zuser = new Zuser();
        zuser.setAccount(account);
        List<Zuser> zusers= this.query(-1 , -1, zuser);
        if(zusers!=null && zusers.size()>0){
        	return zusers.get(0);
        }else{
        	return null;
        }
	}

	@Override
	public List<Zuser> getListByuserIds(String userIds) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("uerIds",userIds);
		//进行条件查询
		List<Zuser> items = dao.query(-1,-1,queryMap, null);
		return  items;
	}

	@Override
	public List<AddressContactVo> getUserOrgname(String orgid,String filterStr) {
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		
		List<Zuser> zusers = dao.getListByOrgidFilterstr(orgid, filterStr);
		String highLightLetters = Pinyin.getEngHeadChat(filterStr);
		if(zusers!=null && zusers.size()>0){
			for(Zuser zuser:zusers)
			{
				AddressContactVo contactVo = new AddressContactVo();
				contactVo.setId(zuser.getId());
				String orgName = "";
				for(Zorganize zorganize: zuser.getOrgs()){
					orgName += zorganize.getName();
					orgName += ",";
				}
				if(orgName!=null && !orgName.equals("")){
					orgName = orgName.substring(0, orgName.length()-1);
					contactVo.setText(zuser.getName()+"("+orgName+")");
				}
				else {
					contactVo.setText(zuser.getName());
				}
				contactVo.setType("ORG_USER");
				
				contactVo.setHasChildren(false);
				Map<String, String> userInfo = new HashMap<String, String>();
				String highLightStr = filterStr;
				if(!StringUtil.isEmpty(highLightLetters))
					highLightStr = Pinyin.getHighLightCharsByLetter(zuser.getName(),highLightLetters);
				userInfo.put("highLightStr", highLightStr);
				userInfo.put("oaduty", zuser.getOaduty());
				userInfo.put("userName", zuser.getName());
				userInfo.put("userAccount", zuser.getAccount());
				userInfo.put("orgName", orgName);
				userInfo.put("orgId", zuser.getOrgId());
				contactVo.setUserInfo(userInfo);
				contactVos.add(contactVo);
			}
		}
		
		return contactVos;
	}

	@Override
	public List<Zuser> queryList(Map<String, Object> filtersMap) {
    	List<Zuser> resultList =  dao.query(-1,-1,filtersMap, " order by pri");
    	return resultList;
    }

	@Override
	public void updataAccount(String id,String password) {
		dao.updateAccount(id,password);
	}

	public List<Map> findBysql(String sql)throws  Exception {
		return dao.mapListByNative(sql, null);
	}
	
	@Override
	public void updateFailuretimes(String account, String status) {
		dao.execteNativeBulk("update z_user_t set failuretimes = failuretimes + 1, status = ? where account = ?", status, account);
	}
	
	@Override
	public void updateStatus(String id, String status) {
		dao.execteNativeBulk("update z_user_t set status = ?, failuretimes = 0 where id = ?", status, id);
	}
	
	@Override
	public Map getUserStatus(String account){
		Map map = dao.getUserStatus(account);
		return map;
	}
}