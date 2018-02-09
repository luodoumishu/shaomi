package com.xjj._extensions.roleUserPer.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.PinyinUtil;
import com.xjj.cms.base.util.ztree.TreeConfig;
import com.xjj.cms.base.util.ztree.TreeNode;
import com.xjj.cms.base.util.ztree.TreeUtil;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.filter.WebContext;
import com.xjj.jdk17.utils.StringUtil;

@Controller
@RequestMapping(value = "/Zorganize")
public class ZorganizeController extends BaseController<Zorganize>{
	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService iZorganizeService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws IOException {
		// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<Zorganize> page = iZorganizeService.query(filtersMap);
		
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}

	@RequestMapping(value = "/save")
	public JsonResult save(String models) throws IOException {
		Zorganize model = JsonUtil.fromJson(models,
				Zorganize.class);
		if(model.getParentId()==null || model.getParentId().length()<=0 ){
			model.setParentId("0");
			model.setParentName("单位列表");
		}
		
		String orgName = model.getName();
		if (!StringUtil.isEmpty(orgName)) {
			orgName = orgName.replaceAll("保亭县", "");
			orgName = orgName.replaceAll("保亭县", "");
		}
		if(model.getParentId().equals("0")){
			model.setParentName("单位列表");
		}
		String pyName = PinyinUtil.getInstance().string2Initial(orgName);
		if(iZorganizeService.checkIsExisPyCode(pyName)){   //去重
			List<Zorganize> tempZ = iZorganizeService.checkIsAlreadyExisPyCode(pyName);
			if(tempZ!=null && tempZ.size()>0){
				int num = tempZ.size();
				while (iZorganizeService.checkIsExisPyCode(pyName+num)) {
					num++;
				}
				pyName = pyName+num;
				model.setPyCode(pyName);
			}
		}else{
			model.setPyCode(pyName);
		}
		model.setCreateTime(new Date());
		model.save();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws IOException {
		Zorganize model = JsonUtil.fromJson(models,
				Zorganize.class);
		if(model.getParentId()==null || model.getParentId().length()<=0 ){
			model.setParentId("0");
			model.setParentName("");
		}
		
		List<Zorganize> zorganizes=iZorganizeService.listTempByParentId(model.getId());
		for(Zorganize zorganize:zorganizes){
			zorganize.setParentName(model.getName());
			zorganize.update();
		}
		
		String orgName = model.getName();
		if (!StringUtil.isEmpty(orgName)) {
			orgName = orgName.replaceAll("保亭县", "");
			orgName = orgName.replaceAll("保亭县", "");
		}
		String pyName = PinyinUtil.getInstance().string2Initial(orgName);
//		model.setPyCode(pyName);
		if(iZorganizeService.checkIsChangeName(model.getId(),model.getName())){  //判断是否修改单位名称
			if(iZorganizeService.checkIsExisPyCode(pyName)){   //去重
				List<Zorganize> tempZ = iZorganizeService.checkIsAlreadyExisPyCode(pyName);
				if(tempZ!=null && tempZ.size()>0){
					int num = tempZ.size();
					while (iZorganizeService.checkIsExisPyCode(pyName+num)) {
						num++;
					}
					pyName = pyName+num;
					model.setPyCode(pyName);
				}
			}else{
				model.setPyCode(pyName);
			}	
		}
		
		if(model.getParentId().equals("0")){
			model.setParentName("单位列表");
		}
		iZorganizeService.merge(model);
		//model.merge();
		//	model.update();
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws IOException {
		JsonResult jr = new JsonResult(0, "", null);
		Zorganize model = JsonUtil.fromJson(models,
				Zorganize.class);
		if(iZorganizeService.checkExistChildOrg(model.getId())){
			jr.setResultMsg("存在子单位,不能删除,请先删除子单位!");
		}else{
			model.delete();
		}
		// 构造返回对象
		
		return jr;

	}
	
	/**
	 * 获得单位信息
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("listOrgs")
	public JsonResult listOrgs() throws IOException{
		String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
		List<Zorganize> list = iZorganizeService.listAllByOrgRootId(orgRootId);
		JsonResult jr = new JsonResult(0, "", list);
        return jr;
	}

	@RequestMapping(value = "/read")
    public ModelAndView read() throws Exception{
		String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
		List<Zorganize> list = iZorganizeService.listAllByOrgRootId(orgRootId);
	   return new ModelAndView().addObject(list);
    }
	
	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {

	}
	
	
	/**
	 * 根据级别展示部门
	 * @author yeyunfeng 2015年5月13日  下午7:06:21
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getOrgByLevel", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<TreeNode> getOrgByLevel() throws Exception {
		int level = CmsCC.MENU_FJSQ_ORG_SHOWLEVEL;
		List<Zorganize>  orgList = iZorganizeService.getOrgByLevel("0", level);
		List<TreeNode> orgTreeList = new ArrayList<TreeNode>();
		// 构造返回对象
		orgTreeList = TreeUtil.getTreeNodeList(orgList, treeConfig);
		/*// 构造返回对象
		orgTreeList = toTreeNode(orgList);*/
		return orgTreeList;
		
	}
	
	/**
	 * 根据父级id和展示的级别，查询子部门
	 * @author yeyunfeng 2015年5月14日  上午10:18:46
	 * @param pId 父级id
	 * @param level 层级
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getByPid", method = { RequestMethod.GET,
			RequestMethod.POST })
	public List<TreeNode> getByPid(HttpServletRequest request) throws Exception {
		List<TreeNode> orgTreeList = new ArrayList<TreeNode>();
		String id =  request.getParameter("id");
		//节点层级
		int curLevel = 0;
		int allLevel = CmsCC.MENU_FJSQ_ORG_SHOWLEVEL;
		
		String level_str = request.getParameter("level");
		if(!StringUtil.isEmpty(level_str)){
			curLevel = Integer.parseInt(level_str);
		}
		
		List<Zorganize>  orgList = iZorganizeService.getBypId(id);
		// 构造返回对象
		orgTreeList = toTreeNode(orgList,allLevel-1,curLevel);
		return orgTreeList;
		
	}
	
	
	@RequestMapping(value = "/setPyName", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult setPyName(String name) throws Exception {
		String pyName = PinyinUtil.getInstance().string2Initial(name);
		JsonResult result = new JsonResult(1, "", pyName);
		return result;
		
	}
	
	/**
	 * 根据拼音代码查询
	 * @author yeyunfeng 2015年5月26日  上午10:59:42
	 * @param pyCode
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getByPyCode", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult getByPyCode(String pyCode) throws Exception {
		
		Zorganize org  = iZorganizeService.getByPyName(pyCode);
		JsonResult result = new JsonResult(1, "", org);
		// 构造返回对象
		return result;
		
	}
	
	/**
	 * 装换成树节点对象
	 * @author yeyunfeng 2015年5月13日  下午4:31:31
	 * @param org
	 * @return
	 * @throws Exception
	 *
	 */
	private List<TreeNode> toTreeNode(List<Zorganize> org,int allLevel,int level)throws Exception{
		List<TreeNode> menuTreeList = new ArrayList<TreeNode>();
		int size = org.size();
		for (int i = 0; i < size; i++) {
			TreeNode node  = getTreeNode(org.get(i),allLevel,level);
			menuTreeList.add(node);
		}
		return menuTreeList;
	}
	
	/**
	 * 单个部门装换成单个树节点
	 * @author yeyunfeng 2015年5月13日  下午4:31:17
	 * @param org
	 * @return
	 * @throws Exception
	 *
	 */
	private TreeNode getTreeNode(Zorganize org,int allLevel,int level) throws Exception{
		TreeNode treeNode = new TreeNode();
		String id = org.getId();
		treeNode.setId(id);
		treeNode.setName(org.getName());
		/*treeNode.setIsParent(org.isParent());*/
		if (allLevel == level) {
			treeNode.setIsParent(false);
		}else {
			if (iZorganizeService.checkExistChildOrg(id)) {
				treeNode.setIsParent(true);
			} else {
				treeNode.setIsParent(false);
			}
		}
		
		return treeNode;
	}
	
	private TreeConfig<Zorganize> treeConfig = new TreeConfig<Zorganize>() {
		
		public TreeNode getTreeNode(Zorganize obj) {
			// 额外节点的信息
			TreeNode treeNode = new TreeNode();
			treeNode.setId(obj.getId());
			treeNode.setName(obj.getName());
			/*if (iZorganizeService.checkExistChildOrg(obj.getId())) {
				treeNode.setIsParent(true);
			} else {
				treeNode.setIsParent(false);
			}*/
			treeNode.setIsParent(obj.isParent());
			return treeNode;
		}
		
		public Object getPkid(Zorganize obj) {
			return obj.getId();
		}
		
		public Object getParentId(Zorganize obj) {
			return null == obj.getParentId()?null:obj.getParentId();
		}
	};
	
	@RequestMapping(value = "/searchMaxSort")
	public JsonResult searchMaxSort(HttpServletRequest request) throws Exception {
		Integer  maxSort = iZorganizeService.searchMaxSort();
		if(null == maxSort){
			maxSort = 1;
		}else{
			maxSort ++;
		}
		JsonResult jr = new JsonResult(0, "", maxSort);
		// 构造返回对象
		return jr;
	}
	
	@RequestMapping(value = "/getAllOrg",method = {RequestMethod.POST,RequestMethod.GET})
	public List<TreeNode> getAllOrg(HttpServletRequest request)throws Exception{
		List<TreeNode> orgTree = new ArrayList<TreeNode>();
		List<Zorganize> orgList = new ArrayList<Zorganize>();
		String checkMenus = request.getParameter("checkMenus");
		orgList = iZorganizeService.getAllOrg2ZTree(checkMenus);
		orgTree = TreeUtil.getTreeNodeList(orgList, treeConfig);
		return orgTree;
	}
	
}
