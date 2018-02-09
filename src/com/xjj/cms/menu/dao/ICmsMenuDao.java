package com.xjj.cms.menu.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsMenuDao extends IBaseDao<CmsMenu, String>
{

    List<CmsMenu> query(int start, int pageSize, Map<String, Object> queryMap,
            String subHql);

    int total(Map<String, Object> queryMap);
    
    /**
     * 根据跟节点查询
     * @author yeyunfeng 2014-8-25  下午4:41:30
     * @return
     * @throws Exception
     *
     */
	public List<CmsMenu> listAllByMenuRootId()throws Exception;
	
	/**
	 * 根据父节点查询
	 * @author yeyunfeng 2014-8-27  下午5:36:24
	 * @param parentId
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getByParentId(String parentId)throws Exception;
	
	/**
	 * 根据用户id,获取其拥有的栏目
	 * @author yeyunfeng 2014-9-4  下午6:38:01
	 * @param userId
	 * @param showModes 
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> userMenuTreeList(String userId, String[] showModes)throws Exception;

	List<CmsChannelItem> findCmsChannelItemByMenuId(String menuId);

	/**
	 * 查询所有栏目,同时根据部门设置栏目是都选中
	 * @author yeyunfeng 2015年5月14日  下午4:17:16
	 * @return
	 * @throws Exception
	 *
	 */
	public List<CmsMenu> getAllmenu()throws Exception;
}
