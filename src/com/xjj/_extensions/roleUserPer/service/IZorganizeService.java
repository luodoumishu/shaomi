package com.xjj._extensions.roleUserPer.service;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.adrsbook.web.controller.AddressContactVo;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface IZorganizeService extends IBaseService<Zorganize, String>{

	public Page<Zorganize> query(Map<String, Object> filtersMap);

	public List<Zorganize> listTempByParentId(String parentId);
	
	public List<Zorganize> listAllByOrgRootId(String orgRootId);
	
	public List<String> getListByOrgId(String orgId);
	
	public boolean checkExistChildOrg(String orgId);
	
	public List<Zorganize> getOrgsByOrgType(String orgId,Integer type);
	
	/**
	 * 根据根部门id,和要显示的层级
	 * @author yeyunfeng 2015年5月12日  上午10:17:24
	 * @param startOrgId 开始级别id
	 * @param level 显示的级别 level=0是 将显示所有
	 * @return
	 * @throws Exception
	 *
	 */
	public List<Zorganize> getOrgByLevel(String startOrgId,int level)throws Exception;
	
	/**
	 * 根据父级部门，查询下面第一级部门
	 * @author yeyunfeng 2015年5月14日  上午10:09:33
	 * @param pid
	 * @return
	 * @throws Exception
	 *
	 */
	public List<Zorganize> getBypId(String pid) throws Exception;
	
	/**
	 * 根据拼音代码查询
	 * @author yeyunfeng 2015年5月26日  上午10:53:57
	 * @param pyName
	 * @return
	 * @throws Exception
	 *
	 */
	public Zorganize getByPyName(String pyName)throws Exception;
	
	/**
	 * 根据部门id，查询该部门下所的子部门
	 * @author yeyunfeng 2015年5月26日  下午5:18:44
	 * @param orgId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<Zorganize> getChildOrg(String orgId)
			throws Exception;

	public List<AddressContactVo> getChildOrgUserCount(String string);

	public AddressContactVo getCurrentOrgACO(String id);

	public List<Zorganize> queryList(Map<String, Object> filtersMap);

	public Integer searchMaxSort();

	public List<Zorganize> getFirstLevelByOrgName(String orgName) throws Exception;

	public List<Zorganize> checkIsAlreadyExisPyCode(String pyName);

	public boolean checkIsExisPyCode(String pyName);

	public boolean checkIsChangeName(String id,String name);

	public List<Zorganize> getAllOrg2ZTree(String checkMenus);
	
}
