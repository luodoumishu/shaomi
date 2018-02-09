/**
 * slgaweb
 * FjsqUtil.java
 * @Copyright: 2013 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2015年6月10日 下午8:59:17 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.base.util;

import java.util.ArrayList;
import java.util.List;

import com.xjj._extensions.roleUserPer.dao.IZorganizeDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj.cms.menu.dao.ICmsMenuOrgDao;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.framework.core.web.filter.WebContext;

/**
 * <p>
 * 分级授权工具类
 * 
 * @author yeyunfeng 2015年6月10日 下午8:59:17
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年6月10日
 * @modify by reason:{方法名}:{原因}
 */
public class FjsqUtil {

	// 私有的默认构造子
	private FjsqUtil() {
	}

	// 已经自行实例化
	private static final FjsqUtil fjsqUtil = new FjsqUtil();

	private static IZorganizeDao orgDao = SpringContextUtil.getInstance()
			.getBean("ZorganizeDao");

	// 静态工厂方法 这种单例模式是线程安全的
	public static FjsqUtil getInstance() {
		return fjsqUtil;
	}

	/**
	 * 递归查询当前用户对应所属部门下面的所有部门,返回所有部门id
	 * 
	 * @author yeyunfeng 2015年6月10日 下午9:24:56
	 * @param isReturnRootOrgId
	 *            是否返回起初的orgId
	 * @return
	 * @throws Exception
	 * 
	 */
	public String getChildOrgIds4CurOrg(boolean isReturnRootOrgId)
			throws Exception {
		String currOrdId = WebContext.getInstance().getHandle().getOrgID();
		return getChildOrgIds(currOrdId, isReturnRootOrgId);
	}

	/**
	 * 递归查询当前用户所属部门下面的所有部门,返回所有部门
	 * 
	 * @author yeyunfeng 2015年6月10日 下午9:26:44
	 * @param isReturnRootOrgId
	 *            是否返回起初的org
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<Zorganize> getChildOrg4CurOrg(boolean isReturnRootOrgId)
			throws Exception {
		String currOrdId = WebContext.getInstance().getHandle().getOrgID();
		return getChildOrg(currOrdId, isReturnRootOrgId);
	}

	/**
	 * 递归查询orgId下面的所有部门,返回所有部门id
	 * 
	 * @author yeyunfeng 2015年6月10日 下午9:10:15
	 * @param orgId
	 *            部门id
	 * @param isReturnRootOrgId
	 *            是否返回起初的orgId
	 * @return
	 * @throws Exception
	 * 
	 */
	public String getChildOrgIds(String orgId, boolean isReturnRootOrgId)
			throws Exception {

		StringBuffer strbuff = new StringBuffer();
		if (null != orgId) {
			if (isReturnRootOrgId) {
				strbuff.append("'" + orgId + "'");
			}
			List<Zorganize> orgList = new ArrayList<>();
			orgList = getChildOrg(orgId);
			for (Zorganize _org : orgList) {
				strbuff.append(",'" + _org.getId() + "'");
			}
			if (!isReturnRootOrgId && strbuff.length() > 0) {
				strbuff.substring(1);
			}
		}
		return strbuff.toString();
	}

	/**
	 * 递归查询orgId下面的所有部门,返回所有部门
	 * 
	 * @author yeyunfeng 2015年6月10日 下午9:17:57
	 * @param orgId
	 *            部门id
	 * @param isReturnRootOrgId
	 *            是否返回起初的org
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<Zorganize> getChildOrg(String orgId, boolean isReturnRootOrgId)
			throws Exception {

		List<Zorganize> orgList = new ArrayList<>();
		if (null != orgId) {
			if (isReturnRootOrgId) {
				Zorganize org = orgDao.get(orgId);
				orgList.add(org);
			}

			List<Zorganize> orgs = getChildOrg(orgId);
			if (null != orgs && orgs.size() > 0) {
				orgList.addAll(orgs);
			}
		}
		return orgList;
	}

	/**
	 * 递归查询子部门
	 * 
	 * @author yeyunfeng 2015年6月10日 下午9:09:49
	 * @param orgId
	 * @return
	 * @throws Exception
	 * 
	 */
	private List<Zorganize> getChildOrg(String orgId) throws Exception {

		List<Zorganize> orgList = new ArrayList<>();
		orgDao.getChildOrg(orgList, orgId);
		return orgList;
	}

	/**
	 * 根据orgId获取该部门在栏目分级授权中的顶级部门
	 * 
	 * @author yeyunfeng 2015年6月10日 下午11:07:04
	 * @param orgId
	 *            部门id
	 * @return
	 * @throws Exception
	 * 
	 */
	public String getOrg4MemuFjsq(String orgId) throws Exception {
		ICmsMenuOrgDao cmsMenuOrgDao = SpringContextUtil.getInstance().getBean(
				"cmsMenuOrgDao");
		return cmsMenuOrgDao.getByLoopOrgId(orgId);
	}

	/**
	 * 根据当前用户id所属的部门，获取该部门在栏目分级授权中的顶级部门
	 * 
	 * @author yeyunfeng 2015年6月10日 下午10:59:09
	 * @return
	 * 
	 */
	public String getOrg4MemuFjsqByCurrOrg() throws Exception {
		String orgId = WebContext.getInstance().getHandle().getOrgID();
		ICmsMenuOrgDao cmsMenuOrgDao = SpringContextUtil.getInstance().getBean(
				"cmsMenuOrgDao");
		return cmsMenuOrgDao.getByLoopOrgId(orgId);
	}

	
	public String getChildOrgIdsByPyName(String pyName)throws Exception{
		Zorganize org =  orgDao.getByPyName(pyName);
		String orgId = null;
		if (null != org) {
			orgId = org.getId();
		}
		return getChildOrgIds(orgId,true);
	}
}
