package com.xjj._extensions.adrsbook.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj._extensions.groupUser.service.IZGroupService;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZOrgUserService;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.roleUserPer.service.IZroleService;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.jdk17.utils.StringUtil;

@Controller
@RequestMapping(value = "/addressbook")
public class AddressBookController {

	@Autowired
	private IZGroupService zGroupService;
	
	@Autowired
	@Qualifier("ZorganizeService")
	private IZorganizeService zorganizeService;
	
	@Autowired
	@Qualifier("ZuserService")
	private IZuserService zuserService;
	

	@RequestMapping("/getFlatUsers")
	public List<AddressContactVo> getFlatUsers(String id, String type,boolean isShowGroup) {
		System.out.println("getFlatUsers id: "+id+",type: "+type+"isShowGroup: "+isShowGroup);
		List<AddressContactVo> addressContactVos = null;
		if (StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))
		{
			addressContactVos = getAllGroup();
		}
	
		else if ("GROUP".equals(type) && !(StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))) {
			addressContactVos = getUserByGroupId(id);
		}
		
		return addressContactVos;

	}
	
	
	
	@RequestMapping("/getHierarchichalUsers")
	public List<AddressContactVo> getHierarchichalUsers(String id, String type,boolean isShowGroup,boolean selectOrgOnly) {
		System.out.println("getHierarchichalUsers id: "+id+",type: "+type+" isShowGroup: "+isShowGroup);
		List<AddressContactVo> addressContactVos = null;
		if (StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))
		{
			
			addressContactVos = zorganizeService.getChildOrgUserCount("0");
			if(isShowGroup==true&&addressContactVos!=null)
				addressContactVos.addAll(0,getAllGroup());
		}
		else if ("ASSIGN_ORG".equals(type) && !(StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))) {
			addressContactVos = new ArrayList<AddressContactVo>();
			addressContactVos.add(zorganizeService.getCurrentOrgACO(id));
			if(isShowGroup==true&&addressContactVos!=null)
				addressContactVos.addAll(0,getAllGroup());
		}
		else if (isShowGroup==true && "GROUP_TYPE".equals(type)
				&& !(StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))) {
			addressContactVos =  getGroupByTypeId(id);
		}
		else if (isShowGroup==true && "GROUP".equals(type) &&
				!(StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))) {
			addressContactVos =  getUserByGroupId(id);
		}
		else if ("ORG".equals(type) && !(StringUtil.isEmpty(id) || "null".equals(id) || "undefined".equals(id))) {
			addressContactVos = zorganizeService.getChildOrgUserCount(id);
			if(addressContactVos == null){
				addressContactVos = new ArrayList<>();
			}
			if(!selectOrgOnly){
				List<AddressContactVo> userContactVos = getUserByOrgId(id);
				if (userContactVos != null) {
					addressContactVos.addAll(userContactVos);
				}
			}
		}
		return addressContactVos;

	}
	
	@RequestMapping("/getUserByFilter")
	public List<AddressContactVo> getUserByFilter(String filter) throws UnsupportedEncodingException {
		if(!StringUtil.isEmpty(filter))
			filter = java.net.URLDecoder.decode(filter,"UTF-8");  

		System.out.println("filter:"+filter);
		List<AddressContactVo> addressContactVos = new ArrayList<AddressContactVo>();
		if(!StringUtil.isEmpty(filter))
		{
			Map filterMap = filterMap = JSONObject.fromObject(filter);
			
			String assignOrgId = filterMap.get("assignOrgId")!=null?filterMap.get("assignOrgId").toString():null;
			String filterStr = filterMap.get("filterStr")!=null?filterMap.get("filterStr").toString():null;
			if(!StringUtil.isEmpty(filterStr))
				addressContactVos = zuserService.getUserOrgname(assignOrgId, filterStr);
		}
		return addressContactVos;
	}
	
	@RequestMapping("/getUserByUserIds")
	public JsonResult getUserByUserIds(String _userIdList) throws Exception {
		JsonResult result = new JsonResult(1, "NO_EXIST", null);
		if("null".equals(_userIdList)||"[]".equals(_userIdList)||StringUtil.isEmpty(_userIdList))
			return result;
		 JSONArray  userIdList = JSONArray.fromObject(_userIdList);
		 
		 String[] userIds = new String[userIdList.size()];
		 for(int i=0;i<userIdList.size();i++)
		 {
			 userIds[i]= userIdList.getString(i);
		 }
		 
		 Map<String, Object> filtersMap = new HashMap<String, Object>();
		 filtersMap.put("idList", userIds);
		 
		 List<AddressContactVo> contactVos = null;
		 
		 List<Zuser> users = zuserService.queryList(filtersMap);
		 contactVos = transformZuserToACOList(users);
		 result.setResultCode(0);
		 result.setResultData(contactVos);
		 result.setResultMsg("EXIST");
		 return result;
		 
	}
	
	@RequestMapping("/getOrgByOrgIds")
	public JsonResult getOrgByOrgIds(String _orgIdList) throws Exception {
		JsonResult result = new JsonResult(1, "NO_EXIST", null);
		if("null".equals(_orgIdList)||"[]".equals(_orgIdList)||StringUtil.isEmpty(_orgIdList))
			return result;
		 JSONArray  orgIdList = JSONArray.fromObject(_orgIdList);
		 
		 String[] orgIds = new String[orgIdList.size()];
		 for(int i=0;i<orgIdList.size();i++)
		 {
			 orgIds[i]= orgIdList.getString(i);
		 }
		 
		 Map<String, Object> filtersMap = new HashMap<String, Object>();
		 filtersMap.put("idList", orgIds);
		 
		 List<AddressContactVo> contactVos = null;
		 
		 List<Zorganize> orgs = zorganizeService.queryList(filtersMap);
		 contactVos = transformZorgToACOList(orgs);
		 result.setResultCode(0);
		 result.setResultData(contactVos);
		 result.setResultMsg("EXIST");
		 return result;
		 
	}
	
	private List<AddressContactVo>getUserByGroupId(String groupId)
	{
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		List<ZGroupUser>groupUsers = zGroupService.getUsersByGroupId( groupId);
		for(ZGroupUser guser:groupUsers)
		{
			AddressContactVo acvo = new AddressContactVo();
			
			Zuser zuser = zuserService.get(guser.getUserId());
			String orgName = "";
			int index=0;
			for(Zorganize zorganize: zuser.getOrgs()){
				orgName += zorganize.getName();
				if(index++!=zuser.getOrgs().size()-1)
					orgName += ",";
			}
			acvo.getUserInfo().put("orgName", orgName);
			acvo.getUserInfo().put("orgId", zuser.getOrgId());
			acvo.getUserInfo().put("userName", zuser.getName());
			acvo.getUserInfo().put("userAccount", zuser.getAccount());
			acvo.getUserInfo().put("oaduty", zuser.getOaduty());
			acvo.setId(guser.getUserId());
			acvo.setHasChildren(false);
			acvo.setText(guser.getUserName());
			acvo.setType("GROUP_USER");
			contactVos.add(acvo);
		}
		return contactVos;
		
	}
	
	
	private List<AddressContactVo> transformZuserToACOList(List<Zuser>orgUsers)
	{
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		for(Zuser user:orgUsers)
		{
			String orgName = "";
			 int index=0;
			for(Zorganize zorganize: user.getOrgs()){
				orgName += zorganize.getName();
				if(index++!=user.getOrgs().size()-1)
					orgName += ",";
			}
			
			AddressContactVo acvo = new AddressContactVo();
			acvo.getUserInfo().put("oaduty", user.getOaduty()!=null?user.getOaduty().trim():null);
			acvo.getUserInfo().put("orgId", user.getOrgId());
			acvo.getUserInfo().put("orgName", orgName);
			acvo.getUserInfo().put("userName", user.getName());
			acvo.getUserInfo().put("userAccount", user.getAccount());
			acvo.getUserInfo().put("oaduty", user.getOaduty());
			acvo.getUserInfo().put("orgId", user.getOrgId());
			acvo.setId(user.getId());
			acvo.setHasChildren(false);
			acvo.setText(user.getName());
			acvo.setType("ORG_USER");
			contactVos.add(acvo);
		}
		return contactVos;
	}
	
	private List<AddressContactVo> transformZorgToACOList(List<Zorganize>orgs)
	{
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		for(Zorganize org:orgs)
		{
			AddressContactVo acvo = new AddressContactVo();
			acvo.setId(org.getId());
			acvo.setHasChildren(false);
			acvo.setText(org.getName());
			acvo.setType("ORG");
			contactVos.add(acvo);
		}
		return contactVos;
	}
	
	
	private List<AddressContactVo>getUserByOrgId(String orgId)
	{
		List<AddressContactVo> contactVos = null;
		List<Zuser>orgUsers = zuserService.getListByOrgId(orgId, false);
		
		contactVos = transformZuserToACOList(orgUsers);
		return contactVos;
	}
	
	private List<AddressContactVo>getGroupTypes()
	{
		List<ZGroupType> groupsTypes = zGroupService.getGroupTypeList();
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		for(ZGroupType groupType:groupsTypes)
		{
			AddressContactVo acvo = new AddressContactVo();
			acvo.setId(groupType.getId());
			List<ZGroup>groups = zGroupService.getGroupsByTypeId(groupType.getId());
			acvo.setHasChildren(groups!=null&&groups.size()>0?true:false);
			acvo.setText(groupType.getTypeName());
			acvo.setType("GROUP_TYPE");
			contactVos.add(acvo);
		}
		return contactVos;
	}
	
	private List<AddressContactVo>getGroupByTypeId(String typeId)
	{
		List<ZGroup> groups = zGroupService.getGroupsByTypeId(typeId);
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		for(ZGroup group:groups)
		{
			List<ZGroupUser>groupUsers = zGroupService.getUsersByGroupId(group.getId());
			AddressContactVo acvo = new AddressContactVo();
			acvo.setUserCount(groupUsers!=null?groupUsers.size():0L);
			acvo.setId(group.getId());
			acvo.setHasChildren(groupUsers!=null&&groupUsers.size()>0?true:false);
			acvo.setText(group.getGroupName());
			acvo.setType("GROUP");
			contactVos.add(acvo);
		}
		return contactVos;
	}
	
	private List<AddressContactVo>getAllGroup()
	{
		List<ZGroup> groups = zGroupService.getGroupsByTypeId(null);
		List<AddressContactVo> contactVos = new ArrayList<AddressContactVo>();
		for(ZGroup group:groups)
		{
			List<ZGroupUser>groupUsers = zGroupService.getUsersByGroupId(group.getId());
			AddressContactVo acvo = new AddressContactVo();
			acvo.setUserCount(groupUsers!=null?groupUsers.size():0L);
			acvo.setId(group.getId());
			acvo.setHasChildren(groupUsers!=null&&groupUsers.size()>0?true:false);
			acvo.setText(group.getGroupName());
			acvo.setType("GROUP");
			contactVos.add(acvo);
		}
		return contactVos;
	}
	
	
	
	/*
	@RequestMapping(value = "/testJsonpList")
    public List<AppReg> testJsonpList(String hello)
    {
    	List<AppReg> appList = new ArrayList<AppReg>();
    	for(int i=0;i<10;i++)
    	{
    		AppReg appReg = new AppReg(i+"", "hello world", hello);
    		appList.add(appReg);
    	}
    	return appList;
    }
    */

}
