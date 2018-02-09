package com.xjj.cms.channel.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.ztree.TreeNode;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

@Controller
@RequestMapping(value = "/CmsChannelItem")
public class CmsChannelItemController extends CMSBaseController<CmsChannelItem> {
	private String channelId = null;
	private String channelName = null;
	int isadd = 0;

	@Autowired
	@Qualifier("CmsChannelItemService")
	private ICmsChannelItemService iCmsChannelItemService;

	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listMenu", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult listMenu(String filters) throws IOException {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		channelId = (String) queryMap.get("itemId");
		channelName = iCmsChannelItemService.findByChannelId(channelId).get(0).getChanneName();
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsChannelItem> page = iCmsChannelItemService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		CmsChannelItem model = JsonUtil.fromJson(models, CmsChannelItem.class);
		this.setCmsBaseModel(model, BaseConstant.UPDATE);
		model.update();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/checked")
	public JsonResult checked(String models) throws Exception {
		List<CmsChannelItem> list = iCmsChannelItemService.findAllItem(models);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", list);
		return jr;
	}
	@RequestMapping(value = "/addChangeNodes")
	public JsonResult addChangeNodes(String models) throws Exception {
		String model = models.replaceAll("\"", "");
		if(model.equals("")){
			List<CmsChannelItem> list = iCmsChannelItemService.findAllItem(channelId);
			for (int i = 0; i < list.size(); i++) {
				CmsChannelItem item = list.get(i);
				item.delete();
			}
		}else{
			String[] menuIds = model.split(",");
			List<CmsChannelItem> list = iCmsChannelItemService.findAllItem(channelId);
				for (int i = 0; i < list.size(); i++) {
					CmsChannelItem item = list.get(i);
					item.delete();
				}
			for(int i = 0;i<menuIds.length;i++){
				String menuId = menuIds[i];
				if(!menuId.equals("0")){
					CmsMenu menu = iCmsChannelItemService.findByMenuId(menuId).get(0);
					addCmsChannelItem(menu, menuId);
				}
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	private void addCmsChannelItem(CmsMenu menu, String menuId)
			throws Exception {
		CmsChannelItem cci = new CmsChannelItem();
		this.setCmsBaseModel(cci, BaseConstant.ADD);
		cci.setChanneItemName(menu.getMenuName());
		cci.setChannelId(channelId);	
		cci.setChanneName(channelName);
		cci.setMenuId(menuId);
		cci.setMenuName(menu.getMenuName());
		cci.setSortno(menu.getSortNo());
		cci.save();
	}

	/**
	 * @author yeyunfeng 2014-9-1  下午3:49:27
	 * @param id 栏目id
	 * @param channelId 频道id
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getMenuTree")
	public List<TreeNode> getMenuTree(String id,String channelId) throws Exception {
		List<TreeNode> menuTreeList = new ArrayList<TreeNode>();
		List<CmsMenu> memuList = new ArrayList<CmsMenu>();
		if (null == id || id.isEmpty()) {
			id = "0";
		}
		memuList = cmsMenuService.getByParentId(id);
		// 构造返回对象
		menuTreeList = toTreeNode(memuList,channelId);
		return menuTreeList;
	}

	/**
	 * 装换成树节点对象
	 * @author yeyunfeng 2014-9-1  下午3:50:20
	 * @param menus 栏目
	 * @param channelId 频道id 
	 * @return
	 * @throws Exception
	 *
	 */
	private List<TreeNode> toTreeNode(List<CmsMenu> menus,String channelId)throws Exception{
		List<TreeNode> menuTreeList = new ArrayList<TreeNode>();
		int size = menus.size();
		for (int i = 0; i < size; i++) {
			TreeNode node  = getTreeNode(menus.get(i),channelId);
			menuTreeList.add(node);
		}
		return menuTreeList;
	}
	/**
	 * 单个栏目装换成单个树节点
	 * @author yeyunfeng 2014-9-1  下午3:51:19
	 * @param menu 单个栏目
	 * @param channelId 频道
	 * @return
	 * @throws Exception
	 *
	 */
	private TreeNode getTreeNode(CmsMenu menu,String channelId) throws Exception{
		TreeNode treeNode = new TreeNode();
		String id = menu.getId();
		treeNode.setId(id);
		treeNode.setName(menu.getMenuName());
		String parentId = menu.getParentMenuId();
		if (null == parentId || parentId.isEmpty()) {
			treeNode.setIsParent(false);
		} else {
			treeNode.setIsParent(true);
		}
		//查询栏目是否在某一频道中
		boolean check = iCmsChannelItemService.exitMenuInChannelItem(id,channelId);
		//设置是否勾选状态
		treeNode.setChecked(check);
		// 额外节点的信息
		return treeNode;
	}
}
