package com.xjj.cms.link.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.ztree.TreeNode;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsChlLinkService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;


@Controller
@RequestMapping(value = "/cms/chlLink")
public class CmsChlLinkController extends BaseController<CmsChlLink>{
	
	@Autowired
	@Qualifier("cmsChlLinkService")
	private ICmsChlLinkService cmsChlLinkService;
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws IOException {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsChlLink> page = cmsChlLinkService.query(filtersMap);

		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	
	@RequestMapping(value = "/getLinkTree")
	public List<TreeNode> getLinkTree(String channelId) throws Exception {
		List<TreeNode> linkTreeList = new ArrayList<TreeNode>();
		List<CmsLink> linkList = new ArrayList<CmsLink>();
		linkList = cmsChlLinkService.getAllLinks();
		// 构造返回对象
		linkTreeList = toTreeNode(linkList,channelId);
		return linkTreeList;
	}
	
	private List<TreeNode> toTreeNode(List<CmsLink> links,String channelId)throws Exception{
		List<TreeNode> linkTreeList = new ArrayList<TreeNode>();
		int size = links.size();
		for (int i = 0; i < size; i++) {
			TreeNode node  = getTreeNode(links.get(i),channelId);
			linkTreeList.add(node);
		}
		return linkTreeList;
	}
	private TreeNode getTreeNode(CmsLink link,String channelId) throws Exception{
		TreeNode treeNode = new TreeNode();
		String id = link.getId();
		treeNode.setId(id);
		treeNode.setName(link.getLinkName());
		treeNode.setIsParent(false);
		//查询超链接是否在某一频道中
		boolean check = cmsChlLinkService.exitLinkInChannelLink(id,channelId);
		//设置是否勾选状态
		treeNode.setChecked(check);
		// 额外节点的信息
		return treeNode;
	}
	
	@RequestMapping(value = "/addChangeNodes")
	public JsonResult addChangeNodes(String linksId,String channelId) throws Exception {
		String model = linksId.replaceAll("\"", "");
		if(model.equals("")){
			List<CmsChlLink> list = cmsChlLinkService.findAllLinkByChannelId(channelId);
			for (int i = 0; i < list.size(); i++) {
				CmsChlLink item = list.get(i);
				item.delete();
			}
		}else{
			String[] linkIds = model.split(",");
			List<CmsChlLink> list = cmsChlLinkService.findAllLinkByChannelId(channelId);
				for (int i = 0; i < list.size(); i++) {
					CmsChlLink item = list.get(i);
					item.delete();
				}
			for(int i = 0;i<linkIds.length;i++){
				String linkId = linkIds[i];
				if(!linkId.equals("0")){
					CmsLink link = cmsChlLinkService.findByLinkId(linkId);
					cmsChlLinkService.addCmsChlLink(link, channelId);
				}
			}
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		if (models != null && models.length() > 1) {
			CmsChlLink model = JsonUtil.fromJson(models, CmsChlLink.class);
			model.update();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}
}
