/**
 * lgdjweb
 * CmsMenuController.java
 * @Copyright: 2014 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2014-8-22 下午3:21:29 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.menu.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.article.model.CmsArticleHistory;
import com.xjj.cms.article.service.ICmsArticleService;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.cms.base.util.Random;
import com.xjj.cms.base.util.ztree.TreeConfig;
import com.xjj.cms.base.util.ztree.TreeNode;
import com.xjj.cms.base.util.ztree.TreeUtil;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.ICommonService;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.filter.WebContext;

/**
 * <p>
 * 栏目Controller
 * 
 * @author yeyunfeng 2014-8-22 下午3:21:29
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-22
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = "/cms/menu")
public class CmsMenuController extends CMSBaseController<CmsMenu> {

	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;
	
	@Autowired
	@Qualifier("cmsArticleService")
	private ICmsArticleService cmsArticleService;
	
	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonService;

	@Override
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}

	/**
	 * 根据条件查询
	 * 
	 * @author yeyunfeng 2014-8-22 下午3:31:15
	 * @param filters
	 *            过滤条件
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters) throws Exception {
		// json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
		// account=}}
		if (null == filters ||filters.isEmpty()) {
			filters = "{\"skip\":-1,\"pageSize\":-1,\"queryObj\":{\"parentMenuId\":\"0\",\"menuName\":null}}"; 
		}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
				Map.class);
		// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<CmsMenu> page = cmsMenuService.query(filtersMap);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", page);
		return jr;
	}
	/**
	 * 获得根节点下所有的节点
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/alltreeList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult alltreeList() throws Exception{
		List<CmsMenu> list = new ArrayList<CmsMenu>();
		list = cmsMenuService.listAllByMenuRootId();
		JsonResult jr = new JsonResult(0, "", list);
        return jr;
	}
	
	@RequestMapping(value = "/fjsqTreeList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult fjsqTreeList() throws Exception{
		List<CmsMenu> list = new ArrayList<CmsMenu>();
		if (CmsCC.ENABLED_MENU_FJSQ) {//如果系统配置成栏目分级授权，执行
			String orgId = WebContext.getInstance().getHandle().getOrgID();
			list = cmsMenuService.getMenuByOrgId(orgId);
		}else {
			list = cmsMenuService.listAllByMenuRootId();
		}
		JsonResult jr = new JsonResult(0, "", list);
        return jr;
	}
	
	/**
	 * 获取当前用户拥有的栏目树
	 * @author yeyunfeng 2014-9-4  下午6:34:57
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/userMenuTree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult userMenuTreeList(HttpServletRequest request) throws Exception{
		String userId = WebContext.getInstance().getHandle().getUserId();
		String showMode = request.getParameter("showMode");
		String[] showModes = null;
		if(showMode != null){
			showModes = showMode.split(",");
		}
		List<CmsMenu> list = cmsMenuService.userMenuTreeList(userId,showModes);
		JsonResult jr = new JsonResult(0, "", list);
        return jr;
	}
	
	@RequestMapping(value = "/add",method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult add(String models) throws IOException, Exception {
		if (models != null && models.length() > 1) {
			CmsMenu model = JsonUtil.fromJson(models, CmsMenu.class);
			if(null == model.getParentMenuId() || model.getParentMenuId().length()<=0 ){
				model.setParentMenuId("0");
				model.setParentMenuName("");
			}
			this.setCmsBaseModel(model, BaseConstant.ADD);
			cmsMenuService.save(model);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/update")
	public JsonResult update(String models) throws Exception {
		if (models != null && models.length() > 1) {
			CmsMenu model = JsonUtil.fromJson(models, CmsMenu.class);
			this.setCmsBaseModel(model, BaseConstant.UPDATE);
			if(null == model.getParentMenuId() || model.getParentMenuId().length()<=0 ){
				model.setParentMenuId("0");
				model.setParentMenuName("");
			}
			List<CmsMenu> cmsMenus = cmsMenuService.getByParentId(model.getId());
			for(CmsMenu cmsMenu:cmsMenus){
				cmsMenu.setParentMenuName(model.getMenuName());
				cmsMenu.update();
			}
			//同步修改CmsChannelItem中的栏目名称
			List<CmsChannelItem> list = cmsMenuService.findCmsChannelItemByMenuId(model.getId());
			if(list!=null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					CmsChannelItem item = list.get(i);
					item.setMenuName(model.getMenuName());
					item.update();
				}
			}
			model.update();
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", null);
		return jr;
	}

	@RequestMapping(value = "/delete")
	public JsonResult delete(String models) throws Exception {
		JsonResult jr = new JsonResult(0, "", null);
		CmsMenu model = JsonUtil.fromJson(models, CmsMenu.class);
		if (cmsMenuService.checkExistChildMenu(model.getId())) {
			jr.setResultMsg("存在子栏目,不能删除,请先删除子栏目!");
		} else {
			//同步删除CmsChannelItem中的该栏目下所有的频道项
			List<CmsChannelItem> list = cmsMenuService.findCmsChannelItemByMenuId(model.getId());
			if(list!=null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					CmsChannelItem item = list.get(i);
					item.delete();
				}
			}
			cmsMenuService.delete(model);
		}
		// 构造返回对象
		return jr;
	}

	/**
	 * 上传栏目配图文件
	 * @author yeyunfeng 2014-9-13  上午10:16:38
	 * @param imgFile
	 * @return
	 * @throws IOException
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/upMenuImg",method = {RequestMethod.POST})
	public void upMenuImg(@RequestParam("imgFile") List<MultipartFile> imgFile,HttpServletResponse response) throws IOException, Exception {
		int resultCode = 0;
		String resultMsg = "";
		String showPath = "";
		try {
			
			for(MultipartFile mFile:imgFile){
				//得到文件名    
	            String filename =mFile.getOriginalFilename();
	            String ext = filename.substring(filename.lastIndexOf("."));
	            //新文件名
	            String newFileName=System.currentTimeMillis()+Random.getStrRandom(3)+ext;
	            long imageFileSize  = mFile.getSize();
	            //栏目图片保存路径
	            String menuImg_path = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.MENU_IMAGE_PATH);
	            String imageFormat = PropertiesUtil.getProperty(CmsCC.FILE_FORMAT, CmsCC.IMAGES_FORMAT);
	            String limitImgSize_str = PropertiesUtil.getProperty(CmsCC.FILE_FORMAT, CmsCC.MENU_IMAGES_SIZE);
	            String ui = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, CmsCC.ARTICLE_AFFIX_UI);
	            long limitImgSize = Long.valueOf(limitImgSize_str)*1l*1024*1024; 
	            if(!com.xjj.cms.base.util.FileTools.checkFormat(filename, imageFormat)){
	            	resultCode = 1;
	            	resultMsg = "上传文件格式不对，只支持"+imageFormat+"格式，请检查！";
				}else if(imageFileSize>limitImgSize){
					resultCode = 2;
					resultMsg = "上传文件不能超过"+limitImgSize+"M，请检查！";
				}else {
					File file = new File(menuImg_path);
					if (!file.exists()) {
						file.mkdir();
					}
					com.xjj.cms.base.util.FileTools.uploadFile(mFile.getInputStream(), menuImg_path+File.separator+newFileName);
					//找到目录保存的最后目录名称
					int menuStrIndex = menuImg_path.lastIndexOf(File.separator);
					if (menuStrIndex>0) {
						showPath =  ui+menuImg_path.substring(menuStrIndex)+File.separator+newFileName;
					}else {
						resultCode = 2;
						resultMsg = "文件栏目图片保存路径还未配置，请联系系统管理员！";
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult jr = new JsonResult(resultCode, resultMsg, showPath);
		response.getWriter().write(JSONObject.fromObject(jr).toString());
	}
	
	@RequestMapping(value = "/findMenuByParentId",method = {RequestMethod.POST,RequestMethod.GET})
	public JsonResult findMenuByParentId(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String parentMenuId = request.getParameter("menuId");
		List<CmsMenu> menus = null;
		if(cmsMenuService.checkExistChildMenu(parentMenuId)){
			menus = cmsMenuService.getByParentId(parentMenuId);
		}
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", menus);
		return jr;
	}
	
	@RequestMapping(value = "/getMenuArcticle",method = {RequestMethod.POST,RequestMethod.GET})
	public JsonResult getMenuArcticle(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String menuId = request.getParameter("menuId");
		List<CmsArticle> articles = null;
		articles = cmsArticleService.getALLArticlesByMenuId(menuId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", articles);
		return jr;
	}
	
	@RequestMapping(value = "/getAllmenu",method = {RequestMethod.POST,RequestMethod.GET})
	public List<TreeNode> getAllmenu(String orgId )throws Exception{
		List<TreeNode> menuTree = new ArrayList<TreeNode>();
		List<CmsMenu> menuList = new ArrayList<CmsMenu>();
		menuList = cmsMenuService.getAllmenu2ZTree(orgId);
		menuTree = TreeUtil.getTreeNodeList(menuList, treeConfig);
		return menuTree;
	}
	
	private TreeConfig<CmsMenu> treeConfig = new TreeConfig<CmsMenu>() {
		
		public TreeNode getTreeNode(CmsMenu obj) {
			// 额外节点的信息
			TreeNode treeNode = new TreeNode();
			String id = obj.getId();
			treeNode.setId(id);
			treeNode.setName(obj.getMenuName());
			Boolean nocheck = obj.getNocheck();
			if (null != nocheck) {
				treeNode.setChecked(!nocheck);
			}
			try {
				if (cmsMenuService.checkExistChildMenu(id)) {
					treeNode.setIsParent(true);
					if(null != nocheck){
						treeNode.setOpen(!nocheck);
					}
				} else {
					treeNode.setIsParent(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return treeNode;
		}
		
		public Object getPkid(CmsMenu obj) {
			return obj.getId();
		}
		
		public Object getParentId(CmsMenu obj) {
			return null == obj.getParentMenuId()?null:obj.getParentMenuId();
		}
	};
	
	@RequestMapping(value = "/get8ArticleByMenuId",method = {RequestMethod.POST,RequestMethod.GET})
	public JsonResult get8ArticleByMenuId(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String menuId = request.getParameter("menuId");
		List<CmsArticleHistory> articles = null;
		articles = cmsArticleService.get8ArticleByMenuId(menuId);
		// 构造返回对象
		JsonResult jr = new JsonResult(0, "", articles);
		return jr;
	}
	
	//获取视频栏目
	@RequestMapping(value = "/getVideoMenu")
    public List<Map> getType() throws IOException{
		List<Map> data=commonService.mapListByNative("select * from cms_menu where article_content_type = '1' order by sortno");
		return data;
    }
	
	@RequestMapping(value = "/getMenuTreeByOrgs",method = {RequestMethod.POST,RequestMethod.GET})
	public List<TreeNode> getMenuTreeByOrgs(HttpServletResponse response,HttpServletRequest request)throws Exception{
		List<TreeNode> menuTree = new ArrayList<TreeNode>();
		List<CmsMenu> menuList = new ArrayList<CmsMenu>();
		String orgids = request.getParameter("orgids");
		String checkMenus = request.getParameter("checkMenus");
		String[] orgIds = orgids.split(",");
		if(orgIds.length > 0){
			for (int i = 0; i < orgIds.length; i++) {
				String orgid = orgIds[i];
//				TreeNode treeNode = new TreeNode();
//				treeNode.setId("0");
//				treeNode.setName(iZorganizeService.get(orgid).getName());
//				treeNode.setIsParent(true);
//				treeNode.setOpen(true);
//				List<CmsMenu> menus = cmsMenuService.getMenusByOrgId4Tree(orgid,checkMenus);
//				if(null != menus && menus.size()>0){
//					List<TreeNode> childTreeNodes = new ArrayList<TreeNode>();
//					childTreeNodes = TreeUtil.getTreeNodeList(menus, treeConfig);
//					if(childTreeNodes.size() == 0){
//						for (int j = 0; j < menus.size(); j++) {
//							TreeNode menuTreeNode = new TreeNode();
//							menuTreeNode.setId(menus.get(j).getId());
//							menuTreeNode.setName(menus.get(j).getMenuName());
//							Boolean nocheck = menus.get(j).getNocheck();
//							if (null != nocheck) {
//								menuTreeNode.setChecked(!nocheck);
//							}
//							childTreeNodes.add(menuTreeNode);
//						}
//					}
//					treeNode.setNodes(childTreeNodes);
//				}
//				menuTree.add(treeNode);
				List<CmsMenu> menu_list = new ArrayList<CmsMenu>();
				menu_list = cmsMenuService.getAllmenu2ZTree2(orgid,checkMenus);
				if(null != menu_list && menu_list.size() > 0){
					for (CmsMenu cm : menu_list) {
						if(!menuList.contains(cm)){
							menuList.add(cm);
						}
					}
				}
			}
			menuTree = TreeUtil.getTreeNodeList(menuList, treeConfig);
		}
		return menuTree;
	}
	
}


