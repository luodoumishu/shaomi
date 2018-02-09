package com.xjj.cms.menu.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsMenuService extends IBaseService<CmsMenu, String>
{

	/**
	 * 根据查询条件
	 * @author yeyunfeng 2014-8-22  下午2:58:40
	 * @param filtersMap 查询条件
	 * @return
	 *
	 */
	public Page<CmsMenu> query(Map<String, Object> filtersMap)throws Exception;


	/**
	 * 检查该栏目是否有子栏目
	 * @author yeyunfeng 2014-8-22  下午3:52:55
	 * @param menuId 栏目id 
	 * @return
	 *
	 */
	public boolean checkExistChildMenu(String menuId) throws Exception;
	
	/**
	 * 根据父栏目节点查询
	 * @author yeyunfeng 2014-8-25  下午4:18:47
	 * @param menuRootId 父栏目节点
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> listAllByMenuRootId()throws Exception;
	
	/**
	 * 根据父节点查询
	 * @author yeyunfeng 2014-8-27  下午5:39:05
	 * @param parentId 父节点
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getByParentId(String parentId)throws Exception;
	
	
	/**
	 * 获取一个栏目下的所有的子节点栏目
	 * @author yeyunfeng 2014-9-3  下午3:31:18
	 * @param parentMentId 父栏目id
	 * @param childMenuList
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getAllChildMenus(String parentMentId,List<CmsMenu> childMenuList) throws Exception;
	
	/**
	 * 根据用户id,获取其拥有的栏目
	 * @author yeyunfeng 2014-9-4  下午6:37:05
	 * @param userId
	 * @param showModes 
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> userMenuTreeList(String userId, String[] showModes)throws Exception;

	public List<CmsChannelItem> findCmsChannelItemByMenuId(String id);
	
	
	/**
	 * 查询所有栏目,同时根据部门设置栏目是都选中
	 * @author yeyunfeng 2015年5月19日  下午5:06:46
	 * @param orgId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getAllmenu2ZTree(String orgId)throws Exception;
	
	
	/**
	 * 栏目分级授权后的 栏目权限分配，根据部门id,查出所有该部门在栏目分级授权中的所有栏目
	 * @author yeyunfeng 2015年5月21日  上午10:21:46
	 * @param orgId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getMenuByOrgId(String orgId)throws Exception;

	/**
	 * 根据栏目Id查询
	 * @param channelId
	 * @return
	 */
	public List<CmsMenu> findAllMenuByChannelId(String channelId);


	public List<CmsMenu> getAllmenu2ZTree2(String orgid, String checkMenus) throws Exception;


}
