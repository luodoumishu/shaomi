package com.xjj._extensions.roleUserPer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZorganizeDao extends IBaseDao<Zorganize, String> {
	
	public List<Zorganize> query(int start, int pageSize,
			Map<String, Object> queryMap, String subHql);

	public int total(Map<String, Object> queryMap);
	
	public List<Zorganize> listTempByParentId(String parentId);
	
	public List<Zorganize> listAllByOrgRootId(String orgRootId);
	
	public List<String> getListByOrgId(String orgId);

	public boolean checkExistChildOrg(String orgId);
	
	/**
	 * 根据父级部门，查询下面第一级部门
	 * @author yeyunfeng 2015年5月26日  下午5:30:42
	 * @param pid
	 * @return
	 * @throws Exception
	 *
	 */
	public List<Zorganize> getBypId(String pid) throws Exception;
	
	/**
	 * 根据部门id，查询该部门下所的子部门
	 * @author yeyunfeng 2015年5月26日  下午5:18:44
	 * @param orgList
	 * @param orgId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<Zorganize> getChildOrg(List<Zorganize> orgList, String orgId)
			throws Exception;
	
	/**
	 * 根据拼音代码查询
	 * @author yeyunfeng 2015年5月26日  下午5:48:15
	 * @param pyName
	 * @return
	 * @throws Exception
	 *
	 */
	public Zorganize getByPyName(String pyName) throws Exception;
	
	public List<Zorganize> checkIsAlreadyExisPyCode(String pyName);

	public boolean checkIsExisPyCode(String pyName);

	public boolean checkIsChangeName(String id,String name);

	public List<Zorganize> getAllOrg();
}
