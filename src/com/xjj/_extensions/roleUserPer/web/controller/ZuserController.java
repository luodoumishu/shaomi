package com.xjj._extensions.roleUserPer.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj._extensions.roleUserPer.model.ZRoleUser;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.model.ZuserQuery;
import com.xjj._extensions.roleUserPer.service.IZOrgUserService;
import com.xjj._extensions.roleUserPer.service.IZRoleUserService;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj._extensions.web.controller.BaseController;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.ICommonService;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.filter.WebContext;

@Controller
@RequestMapping(value = "/zuser")
public class ZuserController extends BaseController<Zuser>{

	@Autowired
    @Qualifier("ZuserService")
    private IZuserService zuserService;
	
	@Autowired
    @Qualifier("ZRoleUserService")
    private IZRoleUserService zroleUserService;
	
	@Autowired
	@Qualifier("ZOrgUserService")
	private IZOrgUserService zOrgUserService;
	
	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonService;
	
	protected void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy) {
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult list(String filters)throws Exception {
		//json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=, account=}}
		Map<String, Object> filtersMap = new ObjectMapper().readValue(filters, Map.class);
		//进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
		Page<Zuser> page = zuserService.query(filtersMap);
		//构造返回对象
		JsonResult jr = new JsonResult(0,"",page);
		return jr;
	}
	
	@RequestMapping(value = "/add")
    public JsonResult add(String models) throws IOException{
		JsonResult jr = new JsonResult(1,"保存出错，请联系管理员",null);
		if(models!=null && models.length()>1){
			JSONObject jsonObj = JSONObject.fromObject(models);
			Zuser model = JsonUtil.fromJson(models,Zuser.class);
			model.setCreateTime(new Date());
			model.setLastupdatedate(new Date());
			model.setStatus("0");
			model.setFailuretimes(0);
//			String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
//	        String passwordHash = new Sha512Hash(model.getPassword(), salt, 1024).toBase64();
//	        model.setPassword(passwordHash);
//	        model.setSalt(salt);
			if(zuserService.getZuserByAccount(model.getAccount()) != null){
				 jr.setResultCode(1);
				 jr.setResultMsg("不能添加相同的登录账号");
			}else{
				zuserService.save(model);
				if (jsonObj.has("orgIds")) {
		            JSONArray array = jsonObj.getJSONArray("orgIds");
		            for (int i = 0; i < array.size(); i++) {
		            	ZOrgUser zOrgUser = new ZOrgUser();
		            	zOrgUser.setUserId(model.getId());
		            	zOrgUser.setOrgId(array.getString(i));
		        		zOrgUserService.save(zOrgUser);
		            }
		        }
		        jr.setResultCode(0);
			}
		}
        return jr;
    }
	
	@RequestMapping(value = "/update")
    public JsonResult update(String models,HttpServletRequest request) throws IOException{
		Zuser model = JsonUtil.fromJson(models,Zuser.class);
//		boolean b = true;
//		if(models!=null && models.length()>1){
//	        JSONObject jsonObj = JSONObject.fromObject(models);
//	        if (jsonObj.has("isUpPass")) {
//	        	String isUpPass = jsonObj.getString("isUpPass");
//	            if(isUpPass!=null && "1".equals(isUpPass)){
//	            	String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
//	                String passwordHash = new Sha512Hash(model.getPassword(), salt, 1024).toBase64();
//	                model.setPassword(passwordHash);
//	                model.setSalt(salt);
//	                b = false;
//	            }
//	        }
//		}
//		if(b){
//        	Zuser nmodel = new Zuser();
//    		nmodel = zuserService.getObjectById(model.getId());
//    		model.setPassword(nmodel.getPassword());
//            model.setSalt(nmodel.getSalt());
//		}
        Set<Zrole> zroles=model.getRoles();
        model.setOrgs(null);
        model.setRoles(null);
		zuserService.update(model);
		if(zroles!=null && zroles.size()>0){
			for(Zrole zrole:zroles){
				ZRoleUser zRoleUser = new ZRoleUser();
				zRoleUser.setUserId(model.getId());
        		zRoleUser.setRoleId(zrole.getId());
        		zroleUserService.save(zRoleUser);
			}
		}
		if(models!=null && models.length()>1){
	        JSONObject jsonObj = JSONObject.fromObject(models);
	        if (jsonObj.has("orgIds")) {
	            JSONArray array = jsonObj.getJSONArray("orgIds");
	            for (int i = 0; i < array.size(); i++) {
	            	ZOrgUser zOrgUser = new ZOrgUser();
	            	String userId = model.getId();
	            	zOrgUser.setUserId(userId);
	            	zOrgUser.setOrgId(array.getString(i));
	        		zOrgUserService.save(zOrgUser);
	        		String curUserId = WebContext.getInstance().getHandle().getUserId();
	        		if (userId.equals(curUserId)) {
	        			request.getSession().setAttribute("orgId",array.getString(i));
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
		Zuser model = JsonUtil.fromJson(models,Zuser.class);
		model.setRoles(null);
		model.setOrgs(null);
		zuserService.delete(model);
    	//构造返回对象
        JsonResult jr =  new JsonResult(0,"",null);
        return jr;
    }
	
	@RequestMapping(value = "/read")
    public ModelAndView read() throws Exception{
		Zuser user = new Zuser();
		
//		String orgRootId=  WebContext.getInstance().getHandle().getOrgRootId();
//		user.setOrgRootId(orgRootId);
		List<Zuser> zuserList = zuserService.query(-1, -1, user);
		List<ZuserQuery> zuserQueries = new ArrayList<ZuserQuery>();
		
		for(Zuser zuser:zuserList){
			ZuserQuery zuserQuery = new ZuserQuery();
			zuserQuery.setId(zuser.getId());
			zuserQuery.setName(zuser.getName());
			zuserQuery.setAccount(zuser.getAccount());
			String showName = zuser.getName();
			showName += " (";
			for(Zorganize zorganize: zuser.getOrgs()){
				showName += zorganize.getName();
				showName += ",";
			}
			showName = showName.substring(0, showName.length()-1);
			if(zuser.getOrgs()!=null && zuser.getOrgs().size()>0){
				showName += " )";
			}
			zuserQuery.setShowName(showName);
			zuserQueries.add(zuserQuery);
		}
		return new ModelAndView().addObject(zuserQueries);
    }
	
	@RequestMapping(value = "/base/ul_ls")
	public String ls(Model model,ServletRequest req, ServletResponse resp){
		/*
		 * if(SecurityUtils.getSubject().isPermitted("p1")){ } Subject
		 * currentUser = SecurityUtils.getSubject(); Session session =
		 * currentUser.getSession();
		 */
        String userName = WebContext.getInstance().getHandle().getUserName();
        if(userName!=null && !"".equals(userName)){
        	model.addAttribute("userName",userName);
        }
        return "/mainframe/index";
    }
	
	@RequestMapping(value = "/getOrgData")
    public JsonResult getOrgData(String id) throws IOException{
		List<Map> orgData=commonService.mapListByNative("select * from z_org_user_t where userid='"+id+"'");
		//构造返回对象
        JsonResult jr =  new JsonResult(0,"",orgData);
        return jr;
    }
	
	@RequestMapping(value = "/getDirectData")
    public JsonResult getDirectData(String id) throws IOException{
		List<Map> data=commonService.mapListByNative("select * from z_user_t where id='"+id+"'");
		//构造返回对象
        JsonResult jr =  new JsonResult(0,"",data);
        return jr;
    }
	
	@RequestMapping(value = "/getOrgNameById")
    public JsonResult getOrgNameById(String orgId) throws IOException{
		List<Map> data=commonService.mapListByNative("select * from z_organize_t where id='"+orgId+"'");
		//构造返回对象
        JsonResult jr =  new JsonResult(0,"",data);
        return jr;
    }
	
	@RequestMapping(value = "/updateAccount")
    public JsonResult updateAccount(HttpServletRequest request) throws IOException{
		JsonResult jr =  new JsonResult(1,"原密码错误，请检查后重新输入！",null);
		
		String userId = request.getParameter("userId");
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		
		Zuser user = zuserService.get(userId);
		if(oldPwd.equals(user.getPassword())){
			zuserService.updataAccount(user.getId(),newPwd);
			jr.setResultCode(0);
		}
		//构造返回对象
        return jr;
    }
	
	@RequestMapping(value = "/modifyPwd")
	public JsonResult modifyPwd(String oldPwd, String newPwd,String yesNewPwd,HttpServletRequest request){
		int success = 1;
		String msg = "";
		String userId = WebContext.getInstance().getHandle().getUserId();
		Zuser zuser = new Zuser();
		zuser = zuser.get(userId);
		if("".equals(oldPwd)){
			msg = "原密码不能为空!";
		}else if("".equals(newPwd)){
			msg = "新密码不能为空!";
		}else if("".equals(yesNewPwd)){
			msg = "确认新密码不能为空!";
		}else if(!newPwd.equals(yesNewPwd)){
			msg = "两次输入的密码不一致!";
		}else if(zuser == null || zuser.getPassword() == null || !zuser.getPassword().equals(oldPwd)){
			msg = "原密码不正确!";
		}else if(newPwd.length() < 6){
			msg = "新密码至少为六位!";
		}else if(zuser.getOriginalpassword() != null ? zuser.getOriginalpassword().indexOf(newPwd) > -1 : false){
			msg = "新密码最近已使用过，请输入新的密码!";
		}else{
			try {
				
			success = 0;
			
//			String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
//	        String passwordHash = new Sha512Hash(newPwd, salt, 1024).toBase64();
	        zuser.setPassword(yesNewPwd);
	        //修改最后修改密码时间
	        zuser.setLastupdatedate(new Date());
	        if(zuser.getOriginalpassword() == null)
	        	zuser.setOriginalpassword("");
	        String [] pdws = zuser.getOriginalpassword().split(",");
	        if(pdws.length > 4){
	        	zuser.setOriginalpassword(zuser.getOriginalpassword().substring(zuser.getOriginalpassword().indexOf(",")+1)+oldPwd+",");
	        }else{
	        	zuser.setOriginalpassword(zuser.getOriginalpassword()+oldPwd+",");
	        }
//	        zuser.setSalt(salt);
			zuser.update();
			msg = "密码修改成功!点击确定后系统将自动退出!";
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		JsonResult jr = new JsonResult(success, msg, null);
		return jr;
	}
	
	@RequestMapping(value = "/findRccs")
    public JsonResult findRccs(){
		JsonResult jr = new JsonResult();
    	try {
			String limitingfrequency = PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, "limitingfrequency");
			jr.setResultData(limitingfrequency);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return jr;
    }
	
	@RequestMapping(value = "/saveRccs")
    public JsonResult saveRccs(String rccs){
		JsonResult jr = new JsonResult();
    	try {
    		PropertiesUtil.setPropertie(CmsCC.WEB_CONFIG_ZH, "limitingfrequency", rccs);
    		jr.setResultCode(0);
		} catch (Exception e) {
			jr.setResultCode(-1);
		}
    	return jr;
    }
	
	@RequestMapping(value = "/thaw")
    public JsonResult thaw(String id, String status){
		JsonResult jr = new JsonResult();
    	try {
    		zuserService.updateStatus(id, status);
    		jr.setResultCode(0);
		} catch (Exception e) {
			jr.setResultCode(-1);
		}
    	return jr;
    }
}
