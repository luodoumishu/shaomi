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
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj.cms.menu.dao.ICmsMenuTransmitDao;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("ZorganizeService")
public class ZorganizeService extends BaseService<Zorganize, String> implements
		IZorganizeService {

	@Autowired
	@Qualifier("cmsMenuTransmitDao")
	private ICmsMenuTransmitDao cmsMenuTransmitDao;
	
	@Autowired
	@Qualifier("ZorganizeDao")
	private IZorganizeDao iZorganizeDao;

	@Autowired
	@Qualifier("ZuserDao")
	private IZuserDao iZuserDao;

	@Override
	public Page<Zorganize> query(Map<String, Object> filtersMap) {
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
				.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20
				: (Integer) filtersMap.get("pageSize");
		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		// 进行条件查询
		List<Zorganize> items = iZorganizeDao.query(skip, pageSize, queryMap,
				" order by pri");
		// 获取总记录数
		int total = iZorganizeDao.total(queryMap);
		// 构造返回对象page
		Page<Zorganize> page = PageUtil.getPage(skip, pageSize, items, total);

		return page;
	}

	@Autowired
	@Qualifier("ZorganizeDao")
	@Override
	public void setBaseDao(IBaseDao<Zorganize, String> baseDao) {
		this.baseDao = iZorganizeDao;
	}

	@Override
	public List<Zorganize> listTempByParentId(String parentId) {
		return iZorganizeDao.listTempByParentId(parentId);
	}

	@Override
	public List<Zorganize> listAllByOrgRootId(String orgRootId) {
		return iZorganizeDao.listAllByOrgRootId(orgRootId);
	}

	public boolean checkExistChildOrg(String orgId) {
		return iZorganizeDao.checkExistChildOrg(orgId);
	}

	@Override
	public List<String> getListByOrgId(String orgId) {
		return iZorganizeDao.getListByOrgId(orgId);
	}

	public List<Zorganize> getOrgsByOrgType(String orgId, Integer type) {
		List<Zorganize> zorganizes = new ArrayList<Zorganize>();
		if (orgId != null && !orgId.equals("")) {
			if (type == 3) {
				Zorganize Zorganize = new Zorganize();
				Zorganize org = new Zorganize();
				org = org.get(orgId);
				String parentId = org.getParentId();
				if (parentId != null && !parentId.equals("0")
						&& !parentId.equals("")) {
					Zorganize = Zorganize.get(org.getParentId());
					zorganizes.add(Zorganize);
				}
			} else if (type == 5) {
				Zorganize org = new Zorganize();
				org = org.get(orgId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				String parentId = org.getParentId();
				if (parentId != null && !parentId.equals("0")
						&& !parentId.equals("")) {
					queryMap.put("parentId", org.getParentId());
					queryMap.put("notInId", "'" + orgId + "'");
					zorganizes = iZorganizeDao.query(-1, -1, queryMap, "");
				}
			} else if (type == 4) {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("parentId", orgId);
				zorganizes = iZorganizeDao.query(-1, -1, queryMap, "");
			}
		}
		return zorganizes;
	}

	@Override
	public List<Zorganize> getOrgByLevel(String startOrgId, int level)
			throws Exception {
		List<Zorganize> orgList = new ArrayList<Zorganize>();
		List<Zorganize> list = getBypId(startOrgId);
		if (null != list && list.size() > 0) {
			orgList.addAll(list);
			int size = list.size();
			for (int i = 0; i < size; i++) {
				Zorganize org = list.get(i);
				String orgId = org.getId();
				if (1 == level) {
					org.setParent(false);
				} else {
					if (checkExistChildOrg(orgId)) {
						org.setParent(true);
					}
				}
				getByStartOrg(orgList, orgId, level - 1);
			}
		}
		return orgList;
	}

	/**
	 * 递归在层级范围内的部门
	 * 
	 * @author yeyunfeng 2015年5月14日 上午9:31:13
	 * @param newOrgList
	 * @param orgId
	 * @param level
	 *            层级
	 * @throws Exception
	 * 
	 */
	private void getByStartOrg(List<Zorganize> newOrgList, String orgId,
			int level) throws Exception {
		List<Zorganize> list = getBypId(orgId);
		if (0 != level) {
			if (null != list && list.size() > 0) {
				newOrgList.addAll(list);
				level--;
				int size = list.size();
				for (int i = 0; i < size; i++) {
					Zorganize org = list.get(i);
					orgId = org.getId();
					if (0 == level) {
						org.setParent(false);
					} else {
						if (checkExistChildOrg(orgId)) {
							org.setParent(true);
						}
					}
					getByStartOrg(newOrgList, orgId, level);
				}
			}
		}
	}

	public List<Zorganize> getBypId(String pid) throws Exception {
		List<Zorganize> orgList = new ArrayList<>();
		orgList = iZorganizeDao.getBypId(pid);
		return orgList;
	}

	public Zorganize getByPyName(String pyName) throws Exception {
		Zorganize org = iZorganizeDao.getByPyName(pyName);
		return org;
	}

	public List<Zorganize> getChildOrg(String orgId)
			throws Exception {
		List<Zorganize> orgList = new ArrayList<>();
		orgList = iZorganizeDao.getChildOrg(orgList, orgId);
		return orgList;
	}

	@Override
	public List<AddressContactVo> getChildOrgUserCount(String orgId) {
		
		List<Zorganize> result = this.listTempByParentId(orgId);
		List<AddressContactVo> contactVos = null;
		if(result!=null&&result.size()>0)
		{
			contactVos =  new ArrayList<AddressContactVo>();
			
			for (Zorganize org : result) {
				AddressContactVo acvo = new AddressContactVo();
				/*if(org.getSjlb()==2){
					acvo.setEnableChoose(false);
				}*/
				acvo.setType("ORG");
				
				acvo.setId(org.getId());
				
				List<String> orgIds = new ArrayList<String>();
				
				orgIds.add(org.getId());
				
				List<Zuser> zusers = iZuserDao.getListByOrgId(orgIds);
				
				Long count = 0L;
				
				if(zusers!=null && zusers.size()>0){
					count = (long) zusers.size();
				}

				acvo.setUserCount(count);

				if(this.checkExistChildOrg(org.getId())){
					acvo.setHasChildren(true);
				}else{
					acvo.setHasChildren(count > 0L);
				}
				
//				acvo.setHasChildren(this.checkExistChildOrg(org.getId()));
				
				acvo.setText(org.getName());
				
				contactVos.add(acvo);
		   }

		}
		return contactVos;
	}

	@Override
	public AddressContactVo getCurrentOrgACO(String orgId) {
		Zorganize org = this.get(orgId);
		if(org!=null){
			AddressContactVo acvo = new AddressContactVo();
			/*if(org.getSjlb()==2){
				acvo.setEnableChoose(false);
			}*/
			acvo.setType("ORG");
			
			acvo.setId(org.getId());
			
			List<String> orgIds = new ArrayList<String>();
			
			orgIds.add(org.getId());
			
			List<Zuser> zusers = iZuserDao.getListByOrgId(orgIds);
			
			Long count = 0L;
			
			if(zusers!=null && zusers.size()>0){
				count = (long) zusers.size();
			}
	
			acvo.setUserCount(count);
			
			acvo.setHasChildren(count > 0L || this.checkExistChildOrg(org.getId()));
			
			acvo.setText(org.getName());

			return acvo;
			
		}else{
			
			return null;
			
		}
	}

	@Override
	public List<Zorganize> queryList(Map<String, Object> filtersMap) {
		List<Zorganize> resultList =  iZorganizeDao.query(-1,-1,filtersMap, " order by pri");
    	return resultList;
	}

	@Override
	public Integer searchMaxSort() {
		return iZorganizeDao.unique("select max(pri) from Zorganize", new Object[0]);
	}

	@Override
	public List<Zorganize> getFirstLevelByOrgName(String orgName) throws Exception {
		String hql = "from Zorganize where 1=1 and name like '%"+orgName+"%'";
		List<Zorganize> orgs = iZorganizeDao.list(hql);
		if(!orgs.isEmpty() && orgs.size() > 0){
			Zorganize org = orgs.get(0);
			List<Zorganize> datas = getOrgByLevel(org.getId(), 1);
			if(!datas.isEmpty() && datas.size() > 0){
				return datas;
			}
		}
		return null;
	}

	@Override
	public List<Zorganize> checkIsAlreadyExisPyCode(String pyName) {
		return iZorganizeDao.checkIsAlreadyExisPyCode(pyName);
	}

	@Override
	public boolean checkIsExisPyCode(String pyName) {
		return iZorganizeDao.checkIsExisPyCode(pyName);
	}

	@Override
	public boolean checkIsChangeName(String id,String name) {
		return iZorganizeDao.checkIsChangeName(id,name);
	}

	@Override
	public List<Zorganize> getAllOrg2ZTree(String checkMenus) {
		List<Zorganize> listOrg = new ArrayList<Zorganize>();
		listOrg = iZorganizeDao.getAllOrg();
		String sql = "";
		if(null != checkMenus){
//			String menus = "";
			String[] menids = checkMenus.split(",");
			for (int i = 0; i < menids.length; i++) {
				if(i == 0){
					sql += "select cmt.target_orgId from cms_menu_transmit cmt where cmt.source_menuId = '"+menids[i]+"'";
					//menus += "'"+menids[i]+"'";
				}else{
					sql += " intersect select cmt.target_orgId from cms_menu_transmit cmt where cmt.source_menuId = '"+menids[i]+"'";
//					menus += ",'"+menids[i]+"'";
				}
			}
			List cmtList = cmsMenuTransmitDao.listByNative(sql, null);
//			System.out.println(cmtList);
			if(null != listOrg && listOrg.size() > 0){
				for (Zorganize org : listOrg) {
					if(cmtList.contains(org.getId())){
						org.setNocheck(false);
					}
				}
			}
		}
		return listOrg;
	}
}
