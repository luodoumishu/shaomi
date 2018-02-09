package com.xjj._extensions.groupUser.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj._extensions.groupUser.service.IZGroupService;
import com.xjj._extensions.groupUser.util.TreeHelper;
import com.xjj._extensions.groupUser.util.TreeNode;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;

@Controller
@RequestMapping(value = "/Zgroup")
public class ZGroupController
{

    @Autowired
    private IZGroupService zGroupService;

    @Autowired
    @Qualifier("ZorganizeService")
    private IZorganizeService zOrganizeService;

    @RequestMapping(value = "/test")
    public JsonResult test(String test)
    {
        System.out.println("test!!!");
        return new JsonResult(0, "", "test");
    }

    @RequestMapping(value = "/getAllGroupType")
    public List<ZGroupType> getAllGroupType()
    {
        return zGroupService.getAllGroupType();
    }


    @RequestMapping(value="/getGroupTreeDataSource")
    public JsonResult getGroupTreeDataSource()
    {
    	 List<TreeNode> treeList = new ArrayList<TreeNode>();
    	try {
			
	
    	TreeNode rootNode  = new TreeNode(null, "0", "所有组", new ZGroupType());
    	List<ZGroupType> groupTypeList = zGroupService.getAllGroupType();
    	 for (ZGroupType gt : groupTypeList)
         {
    		 TreeNode groupTypeNode  = new TreeNode("0", gt.getId(), gt.getTypeName(), gt);
    		 List<ZGroup> zGroups = zGroupService.getGroupsByTypeId(gt
                     .getId());
    		 for (ZGroup zg : zGroups)
             {
    			 TreeNode groupNode  = new TreeNode(zg.getTypeId(), zg.getId(), zg.getGroupName(), zg);
    			 groupTypeNode.addChildNode(groupNode);
             }
    		 rootNode.addChildNode(groupTypeNode);
         }
    	
         treeList.add(rootNode);
    	} catch (Exception e) {
			e.printStackTrace();
		}
         return new JsonResult(0, "", treeList);
    }
    
    
    
    
    @RequestMapping(value = "/getGroupTypeTree")
    public JsonResult getGroupTypeTree()
    {
        StringBuffer treeDataSource = new StringBuffer(
                "[{\"text\":\"所有组\",\"id\":0,\"type\":0");
        List<ZGroupType> groupTypeList = zGroupService.getAllGroupType();
        int groupTypeIndex = 0;
        int groupIndex = 0;
        if (groupTypeList.size() != 0)
        {
            groupTypeIndex = 0;
            treeDataSource.append(",\"items\":[");
            for (ZGroupType gt : groupTypeList)
            {
                treeDataSource.append("{\"text\":\"" + gt.getTypeName()
                        + " \",\"id\":\"" + gt.getId() + "\",\"type\":1");
                List<ZGroup> zGroups = zGroupService.getGroupsByTypeId(gt
                        .getId());
                int groupNum = zGroups.size();
                if (groupNum != 0)
                {
                    groupIndex = 0;
                    treeDataSource.append(",\"items\":[");
                    for (ZGroup zg : zGroups)
                    {
                        treeDataSource.append("{\"text\":\""
                                + zg.getGroupName() + "\",\"id\":\""
                                + zg.getId() + "\",\"type\":2}");
                        if (++groupIndex != zGroups.size())
                            treeDataSource.append(",");
                    }
                    treeDataSource.append("]}");
                }
                else
                {
                    treeDataSource.append("}");
                }
                if (++groupTypeIndex != groupTypeList.size())
                    treeDataSource.append(",");
            }
            treeDataSource.append("]}]");
        }
        else
        {
            treeDataSource.append("}]");
        }

        return new JsonResult(0, "", treeDataSource.toString());

    }

    @RequestMapping("/getOrgTreeTest")
    public JsonResult getOrgTreeTest() throws Exception
    {
        List<Zorganize> orgs = zOrganizeService.listAll();
        Zorganize headOrg = new Zorganize();
        headOrg.setId("0");
        headOrg.setName("所有部门");
        headOrg.setParentId(null);

        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("id", "id");
        fieldMap.put("parentId", "parentId");
        fieldMap.put("text", "name");

        TreeHelper th = new TreeHelper();
        TreeNode tree = th.listToTreeByRootNode(orgs, fieldMap, headOrg);
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        treeList.add(tree);
        return new JsonResult(0, "", treeList);
    }

    @RequestMapping(value = "/listGroup")
    public JsonResult listGroup(String filters) throws Exception
    {
        // json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={type=,
        // id=}}
        Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
                Map.class);

        // 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
        Page<ZGroup> page = zGroupService.queryGroupByType(filtersMap);
        // 构造返回对象
        JsonResult jr = new JsonResult(0, "", page);
        return jr;
    }

    @RequestMapping(value = "/listUser")
    public JsonResult listUser(String filters) throws Exception
    {

        // json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={type=,
        // id=}}

        @SuppressWarnings("unchecked")
        Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
                Map.class);

        // 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
        Page<ZGroupUser> page = zGroupService.queryUserByGroup(filtersMap);
        // 构造返回对象
        JsonResult jr = new JsonResult(0, "", page);
        return jr;
    }

    @RequestMapping(value = "/addGroup")
    public JsonResult addGroup(String models) throws Exception
    {

        ZGroup model = JsonUtil.fromJson(models, ZGroup.class);

        zGroupService.save(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/addGroupType")
    public JsonResult addGroupType(String models) throws Exception
    {

        ZGroupType model = JsonUtil.fromJson(models, ZGroupType.class);

        zGroupService.saveGroupType(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/updateGroup")
    public JsonResult updateGroup(String models) throws Exception
    {
        ZGroup model = JsonUtil.fromJson(models, ZGroup.class);
        zGroupService.update(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/updateUser")
    public JsonResult updateUser(String models) throws Exception
    {
        ZGroup model = JsonUtil.fromJson(models, ZGroup.class);
        zGroupService.update(model);

        return null;
    }

    @RequestMapping(value = "/updateGroupType")
    public JsonResult updateGroupType(String models) throws Exception
    {
        ZGroupType model = JsonUtil.fromJson(models, ZGroupType.class);
        zGroupService.updateGroupType(model);
        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/delGroup")
    public JsonResult delGroup(String models) throws Exception
    {
        ZGroup model = JsonUtil.fromJson(models, ZGroup.class);
        zGroupService.delGroupUsersByGroupId(model.getId());
        zGroupService.delete(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/delGroupType")
    public JsonResult delGroupType(String typeId) throws Exception
    {

        zGroupService.delGroupType(typeId);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;
    }

    @RequestMapping(value = "/readUserByOrgId")
    public List<Zuser> readUserByOrgId(String orgId, String groupId)
            throws Exception
    {
        List<Zuser> userList = new ArrayList<Zuser>();

        List<Zuser> users = zGroupService.getUsersByOrgId(orgId);
        for (Zuser userInfo : users)
        {
            Zuser user = new Zuser();
            user.setId(userInfo.getId());
            user.setName(userInfo.getName());
            try
            {
                user.setPri(zGroupService.getPri(groupId, userInfo.getId()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            userList.add(user);
        }

        return userList;
    }

    @RequestMapping(value = "/getUsersByGroupId")
    public List<ZGroupUser> getUsersByGroupId(String groupId) throws Exception
    {
        List<ZGroupUser> userList = null;

        if (!StringUtils.isEmpty(groupId))
        {
            userList = zGroupService.getUsersByGroupId(groupId);
        }

        // List<ZGroup> groups = null;
        // userList = zGroupService.getUserFilterByUserOrg("1000", groupId);
        // groups = zGroupService.getGroupsByTypeId("2");
        return userList;

    }

    @RequestMapping(value = "/getGroupTypeRemark")
    public JsonResult getGroupTypeRemark(String id)
    {

        String groupTypeRemark = "";
        if (!StringUtils.isEmpty(id))
        {
            groupTypeRemark = zGroupService.getGroupType(id).getRemark();
        }

        // List<ZGroup> groups = null;
        // userList = zGroupService.getUserFilterByUserOrg("1000", groupId);
        // groups = zGroupService.getGroupsByTypeId("2");
        JsonResult jr = new JsonResult(0, "", groupTypeRemark);
        return jr;

    }

    @RequestMapping(value = "/saveUsers")
    public JsonResult saveUsers(String models) throws Exception
    {
        ZGroup model = JsonUtil.fromJson(models, ZGroup.class);
        zGroupService.saveGroupUsers(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;

    }

    @RequestMapping(value = "/saveSingleUser")
    public JsonResult saveSingleUser(String models) throws Exception
    {
        ZGroupUser model = JsonUtil.fromJson(models, ZGroupUser.class);

        zGroupService.saveSingleUser(model);

        JsonResult jr = new JsonResult(0, "", null);
        return jr;

    }

}
