package com.xjj._extensions.roleUserPer.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.cons.AppConstant;
import com.xjj._extensions.roleUserPer.model.Menu;
import com.xjj._extensions.roleUserPer.model.MenuTree;
import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZRoleUserService;
import com.xjj._extensions.roleUserPer.service.IZroleService;
import com.xjj._extensions.roleUserPer.util.MenuUtil;
import com.xjj._extensions.roleUserPer.util.PermissionUtil;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.listener.InitCache;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.filter.WebContext;

@Controller
@RequestMapping(value = "/zrole")
public class ZroleController extends BaseController<Zrole>{

	@Autowired
    @Qualifier("ZroleService")
    private IZroleService zroleService;
	
	@Autowired
    @Qualifier("ZRoleUserService")
    private IZRoleUserService zroleUserService;
	
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters)throws Exception {
		//json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=, account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters, Map.class);
		
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		
		queryMap.put("notCode", AppConstant.ROLE_ADMIN);
		filtersMap.put("queryObj",queryMap); 
		
		//进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<Zrole> page = zroleService.query(filtersMap);
		//构造返回对象
		JsonResult jr = new JsonResult(0,"",page);
		return jr;
	}
	
	@RequestMapping(value = "/add")
    public JsonResult add(String models) throws IOException{
		Zrole model = JsonUtil.fromJson(models,Zrole.class);
		model.setCreateTime(new Date());
		zroleService.save(model);
		if(models!=null && models.length()>1){
	        JSONObject jsonObj = JSONObject.fromObject(models);
	        if (jsonObj.has("userIds")) {
	        	String userIds = jsonObj.getString("userIds");
	            String[] array =  userIds.split(",");
	            if(array!=null && array.length>0){
	            	for (int i = 0; i < array.length; i++) {
		            	ZRoleUser zRoleUser = new ZRoleUser();
		        		zRoleUser.setUserId(array[i]);
		        		zRoleUser.setRoleId(model.getId());
		        		zroleUserService.save(zRoleUser);
		            }
	            }
	        }
		}
		//构造返回对象
        JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@RequestMapping(value = "/update")
    public JsonResult update(String models) throws IOException{
		Zrole model = JsonUtil.fromJson(models,Zrole.class);
		model.setUsers(null);
		zroleService.update(model);
		if(models!=null && models.length()>1){
	        JSONObject jsonObj = JSONObject.fromObject(models);
	        if (jsonObj.has("userIds")) {
	            String userIds = jsonObj.getString("userIds");
	            String[] array =  userIds.split(",");
	            if(array!=null && array.length>0){
	            	for (int i = 0; i < array.length; i++) {
		            	ZRoleUser zRoleUser = new ZRoleUser();
		        		zRoleUser.setUserId(array[i]);
		        		zRoleUser.setRoleId(model.getId());
		        		zroleUserService.save(zRoleUser);
		            }
	            }
	        }
		}
		//构造返回对象
        JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@RequestMapping(value = "/delete")
    public JsonResult delete(String models) throws IOException{
		Zrole model = JsonUtil.fromJson(models,Zrole.class);
		model.setUsers(null);
		zroleService.delete(model);
    	//构造返回对象
        JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@RequestMapping(value = "/deleteuser")
    public JsonResult deleteuser(@RequestParam String userId,@RequestParam String roleId) throws IOException{
		ZRoleUser zRoleUser = new ZRoleUser();
		if(userId!=null && roleId!=null && userId.length()>0 && roleId.length()>0){
			zRoleUser.setUserId(userId);
			zRoleUser.setRoleId(roleId);
			zroleUserService.deleteByModel(zRoleUser);
	    	//构造返回对象
	        JsonResult jr =  new JsonResult(0,"",null);
	        return jr;
		}
		JsonResult jr =  new JsonResult(1,"",null);
		return jr;
    }
	
	@RequestMapping(value = "/validateCode")
	public JsonResult validateCode(@RequestParam String id ,@RequestParam String code){
		JsonResult jr =  new JsonResult(1,"",null);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		queryMap.put("code", code);
		List<Zrole> list = zroleService.query(queryMap,"");
		if(list==null || list.size()<=0){
			jr.setResultCode(0);
		}
		return jr;
	}
	@RequestMapping(value = "/readPermissions")
	public List<HashMap<String, String>> readPermissions(String module) throws Exception{
		//String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
		if(module!=null && !module.equals("")){
			return InitCache.getInst().getPermissionAppMap(module);
		}
		return new ArrayList<HashMap<String,String>>();
	}
	@RequestMapping(value = "/readModule")
	public List<Map<String, String>> readModule() throws Exception{
		return PermissionUtil.getInst().getModuleList();
	}
	
	@RequestMapping(value = "/jsonMenuTree")
	public ModelAndView jsonMenuTree(String codeStr) {
		if(SecurityUtils.getSubject().isPermitted("p1")){ }
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		Zuser zuser = new Zuser();
		//String userId = WebContext.getInstance().getHandle().getUserId();
		String userId = (String) session.getAttribute("userId");
		zuser = zuser.get(userId);
		codeStr = "";
		if(zuser!=null && zuser.getRoles()!=null && zuser.getRoles().size()>0){
			for(Zrole zrole: zuser.getRoles()){
				if(zrole.getIsValid()!=null&&zrole.getIsValid()==1){
					if(!"".equals(codeStr)){
						codeStr += ","; 
					}
					codeStr =codeStr + zrole.getPermissions();
				}
			}
		}
		MenuUtil menuUtil = MenuUtil.getInst();
		List<Menu> list= menuUtil.getMenuList();
		String[] arr = codeStr!=null?codeStr.split(","):null;
		List<MenuTree> tree= menuUtil.getMenuTree(list,arr);
		return new ModelAndView().addObject(tree);
    }
}
