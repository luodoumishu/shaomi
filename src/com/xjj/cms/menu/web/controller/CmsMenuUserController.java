package com.xjj.cms.menu.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.ztree.TreeNode;
import com.xjj.cms.menu.model.CmsMenuUser;
import com.xjj.cms.menu.model.CmsOrgUser;
import com.xjj.cms.menu.service.ICmsMenuUserService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;

/**
 * <p>
 * 栏目权限Controller
 * 
 * @author wuqilin 2014-9-3 10:16:31
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-3
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/menuUser")
public class CmsMenuUserController extends BaseController<CmsMenuUser> {
	private String menuId = null;

	@Autowired
	@Qualifier("cmsMenuUserService")
	private ICmsMenuUserService icmsMenuUserService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		menuId = (String) queryMap.get("menuId");
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsMenuUser> page = icmsMenuUserService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/changeUser")
	public JsonResult changeUser(String models) throws Exception {
		String model = models.replaceAll("\"", "");
		String[] userIds = model.split(",");
		if(userIds.length>0){
			for(int i = 0;i<userIds.length;i++){
				String userId = userIds[i];
				if(!userId.equals("")){
					CmsMenuUser menuUser = icmsMenuUserService.getMenuUser(userId,menuId);
					if(menuUser != null){
						icmsMenuUserService.save(menuUser);
					}
				}
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		String model = models.replaceAll("\"", "");
		String[] ids = model.split(",");
		for(int i = 0;i<ids.length;i++){
			String id = ids[i];
			if(id != null && !id.equals("")){
				CmsMenuUser menuUser = icmsMenuUserService.getMenuUserById(id);
				icmsMenuUserService.delete(menuUser);
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	
	/**
	 * @author wuqilin 2014-9-3  下午2:27:27
	 * @param id 栏目id
	 * @param channelId 频道id
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getMenuTree")
	public List<TreeNode> getMenuTree(String id,String menuId) throws Exception {
		List<TreeNode> menuTreeList = new ArrayList<TreeNode>();
		List<CmsOrgUser> couList = new ArrayList<CmsOrgUser>();
		if (null == id || id.isEmpty()) {
			id = "0";
		}
		couList = icmsMenuUserService.getByParentId(id);
		// 构造返回对象
		menuTreeList = toTreeNode(couList,menuId);
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
	private List<TreeNode> toTreeNode(List<CmsOrgUser> users,String menuId)throws Exception{
		List<TreeNode> menuTreeList = new ArrayList<TreeNode>();
		int size = users.size();
		for (int i = 0; i < size; i++) {
			TreeNode node  = getTreeNode(users.get(i),menuId);
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
	private TreeNode getTreeNode(CmsOrgUser user,String menuId) throws Exception{
		TreeNode treeNode = new TreeNode();
		String id = null;
		boolean check = false;
		if(user.getOrgId() != null){
			//说明是返回的是部门
			 id = user.getOrgId();
			 treeNode.setId(id);
			 treeNode.setName(user.getOrgName());
			 treeNode.setIsParent(true);
		}else{
			 id = user.getUserId();
			 treeNode.setId(id);
			 treeNode.setName(user.getUserName());
			 treeNode.setIsParent(false);
			//查询用户是否在某一频道中
			check = icmsMenuUserService.exitUserInMenuUser(id,menuId);
		}
		//设置是否勾选状态
		treeNode.setChecked(check);
		// 额外节点的信息
		return treeNode;
	}
}
