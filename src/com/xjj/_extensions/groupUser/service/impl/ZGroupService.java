package com.xjj._extensions.groupUser.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj._extensions.groupUser.dao.IZGroupDao;
import com.xjj._extensions.groupUser.dao.IZGroupTypeDao;
import com.xjj._extensions.groupUser.dao.IZGroupUserDao;
import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj._extensions.groupUser.service.IZGroupService;
import com.xjj._extensions.roleUserPer.dao.IZuserDao;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZorganizeService;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import com.xjj.jdk17.utils.StringUtil;

@Service(" ZGroupService")
public class ZGroupService extends BaseService<ZGroup, String> implements
        IZGroupService
{
    @Autowired
    @Qualifier("ZGroupDao")
    private IZGroupDao dao;

    @Autowired
    @Qualifier("ZGroupTypeDao")
    private IZGroupTypeDao zGrouptypeDao;

    @Autowired
    @Qualifier("ZGroupUserDao")
    private IZGroupUserDao zGroupUserDao;

    @Autowired
    private IZuserDao zuserDao;

    @Autowired
    @Qualifier("ZuserService")
    private IZuserService zuserService;

    @Autowired
    @Qualifier("ZorganizeService")
    private IZorganizeService zOrganizeService;

    @Autowired
    @Qualifier("ZGroupDao")
    @Override
    public void setBaseDao(IBaseDao<ZGroup, String> baseDao)
    {
        this.baseDao = dao;

    }

    @Override
    public List<ZGroupType> getAllGroupType()
    {
        return zGrouptypeDao.listAll();
    }

    @Override
    public Page<ZGroup> queryGroupByType(Map<String, Object> filtersMap)
    {
        // 开始查询下标
        int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
                .get("skip");
        // 每页显示条数
        int pageSize = filtersMap.get("pageSize") == null ? 20
                : (Integer) filtersMap.get("pageSize");

        // 获取查询条件
        @SuppressWarnings("unchecked")
        Map<String, Object> queryMap = (Map<String, Object>) filtersMap
                .get("queryObj");

        String id = queryMap.get("elementId").toString();

        if (!"0".equals(id))
        {
            queryMap.put("typeId", id);
        }
        List<ZGroup> items = dao.query(skip, pageSize, queryMap, null);
        appandTypeName(items);
        // 获取总记录数
        int total = dao.total(queryMap);
        // 构造返回对象page
        Page<ZGroup> page = PageUtil.getPage(skip, pageSize, items, total);
        return page;

    }

    @Override
    public List<ZGroup> getAllGroups()
    {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        List<ZGroup> groups = dao.query(-1, -1, queryMap, null);
        return groups;
    }

    @Override
    public Page<ZGroupUser> queryUserByGroup(Map<String, Object> filtersMap)
    {
        // 开始查询下标
        int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
                .get("skip");
        // 每页显示条数
        int pageSize = filtersMap.get("pageSize") == null ? 20
                : (Integer) filtersMap.get("pageSize");

        // 获取查询条件
        @SuppressWarnings("unchecked")
        Map<String, Object> queryMap = (Map<String, Object>) filtersMap
                .get("queryObj");

        String id = queryMap.get("elementId").toString();

        queryMap.put("groupId", id);
        List<ZGroupUser> items = zGroupUserDao.query(skip, pageSize, queryMap,
                " order by pri ASC");
        appandOtherInfo(items);
        // 获取总记录数
        int total = zGroupUserDao.total(queryMap);
        // 构造返回对象page
        Page<ZGroupUser> page = PageUtil.getPage(skip, pageSize, items, total);
        return page;

    }

    @Override
    public List<Zuser> getUsersByOrgId(String orgId)
    {
        List<Zuser> users = zuserService.getListByOrgId(orgId, true);
        return users;
    }

    @Override
    public Integer getPri(String groupId, String userId)
    {
        ZGroupUser gus = getUniqueGroupUser(groupId, userId);
        if (gus != null && gus.getPri() != null)
        {
            return gus.getPri();
        }
        else
        {
            List<ZGroupUser> guList = getUsersByGroupId(groupId);
            if (guList.size() != 0)
            {
                return guList.get(0).getPri() + 1;
            }

        }
        return 1;

    }

    private void appandOtherInfo(List<ZGroupUser> items)
    {
        for (ZGroupUser gu : items)
        {

            Zuser zu = new Zuser();
            ZGroup zg = new ZGroup();
            Zuser newZuser = zu.get(gu.getUserId());
            ZGroup newZg = zg.get(gu.getGroupId());
            if (newZg != null && newZg.getGroupName() != null)
            {
                gu.setGroupName(newZg.getGroupName());
            }
            if (newZuser != null && !StringUtil.isEmpty(newZuser.getAccount()))
            {
                gu.setUserAccount(newZuser.getAccount());
            }

            // gu.setPri(getPri(gu.getGroupId().toString(), gu.getUserId()));

        }
    }

    private void appandTypeName(List<ZGroup> items)
    {
        for (ZGroup g : items)
        {
            ZGroupType gt = zGrouptypeDao.getById(g.getTypeId());
            g.setTypeName(gt.getTypeName());

        }
    }

    @Override
    public List<ZGroupUser> getUsersByGroupId(String groupId)
    {
        List<ZGroupUser> groupUsers = null;
        if ("0".equals(groupId))
        {
            List<Zuser> users = zuserService.getListByOrgId("", true);
            groupUsers = new ArrayList<ZGroupUser>();
            for (Zuser user : users)
            {
                ZGroupUser gu = new ZGroupUser();
                gu.setId("0");
                gu.setGroupId("0");
                gu.setGroupName("allGroup");
                gu.setPri(user.getPri());
                gu.setUserAccount(user.getAccount());
                gu.setUserId(user.getId());
                gu.setUserName(user.getName());
                groupUsers.add(gu);
            }
        }
        else
        {
            Map<String, Object> qryMap = new HashMap<String, Object>();
            qryMap.put("groupId", groupId);
            String subHql = " order by pri ASC";
            groupUsers = zGroupUserDao.getGroupUserByCondition(qryMap, subHql);
            appandOtherInfo(groupUsers);
        }

        return groupUsers;
    }

    @Override
    public List<ZGroupUser> getGroupUserByOrgId(String orgId,
            boolean includeSubOrg, String groupId)
    {

        List<ZGroupUser> groupUsers = null;
        List<Zuser> users = null;
        if ("0".equals(groupId))
        {
            groupUsers = new ArrayList<ZGroupUser>();
            users = zuserService.getListByOrgId(orgId, includeSubOrg);

            for (Zuser user : users)
            {
                ZGroupUser gu = new ZGroupUser();
                gu.setId("0");
                gu.setGroupId("0");
                gu.setGroupName("allGroup");
                gu.setPri(user.getPri());
                gu.setUserAccount(user.getAccount());
                gu.setUserId(user.getId());
                gu.setUserName(user.getName());
                groupUsers.add(gu);
            }
        }
        else if (groupId != null)
        {
            groupUsers = getUserFilterByOrg(orgId, groupId);
        }

        return groupUsers;

    }

    @Override
    public ZGroupType getGroupType(String typeId)
    {
        return zGrouptypeDao.getById(typeId);
    }

    @Override
    public List<ZGroupUser> getUserFilterByOrg(String orgId, String groupId)
    {
        List<ZGroupUser> groupUsers = getUsersByGroupId(groupId);
        List<ZGroupUser> newGroupUsers = null;
        if ("0".equals(orgId))
        {
            newGroupUsers = groupUsers;
        }
        else if (orgId != null)
        {
            newGroupUsers = new ArrayList<ZGroupUser>();
            for (ZGroupUser gu : groupUsers)
            {
                if (orgId.equals(zuserDao.get(gu.getUserId()).getOrgId()))
                    newGroupUsers.add(gu);
            }
        }

        return newGroupUsers;
    }

    @Override
    public List<ZGroupUser> getUserFilterByUserOrg(String userId, String groupId)
    {
        String orgId = zuserDao.get(userId).getOrgId();
        return getUserFilterByOrg(orgId, groupId);
    }

    @Override
    public List<ZGroup> getGroupsByTypeId(String typeId)
    {
        List<ZGroup> groupList = dao.getGroupsByTypeId(typeId);
        return groupList;
    }

    @Override
    public void delGroupUsersByGroupId(String groupId)
    {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("groupId", groupId);
        zGroupUserDao.delGroupUserByCondition(queryMap);
    }

    @Override
    public void delGroupType(String typeId)
    {
        List<ZGroup> groups = getGroupsByTypeId(typeId);
        if (groups != null)
        {
            for (ZGroup g : groups)
            {
                delGroupUsersByGroupId(g.getId());
                dao.delete(g);
            }
        }
        zGrouptypeDao.deleteByPK(typeId);
    }

    private Map<String, ZGroupUser> convertListToMap(List<ZGroupUser> gus)
    {

        HashMap<String, ZGroupUser> guMap = new HashMap<String, ZGroupUser>();
        Iterator<ZGroupUser> it = gus.iterator();
        while (it.hasNext())
        {
            ZGroupUser gu = (ZGroupUser) it.next();
            String userId = gu.getUserId().toString();
            guMap.put(userId, gu);
        }
        return guMap;
    }

    @Override
    public void saveGroupUsers(ZGroup groupObj)
    {
        List<String> userIds = groupObj.getUserIds();

        if (userIds.size() != 0)
        {
            List<ZGroupUser> usersFromDB = getUsersByGroupId(groupObj.getId()
                    .toString());
            Map<String, ZGroupUser> gusMap = convertListToMap(usersFromDB);
            delGroupUsersByGroupId(groupObj.getId());
            int pri = 1;

            for (String userId : userIds)
            {
                ZGroupUser gu = new ZGroupUser();
                ZGroupUser guFromDB = gusMap.get(userId);

                if (!StringUtil.isEmpty(groupObj.getRemark()))
                    gu.setRemark(groupObj.getRemark());
                else if (guFromDB != null)
                {
                    gu.setRemark(guFromDB.getRemark());
                }
                Zuser zuser = new Zuser();
                Zuser newZuser = zuser.get(userId);
                gu.setGroupId(groupObj.getId());
                gu.setUserId(userId);
                gu.setUserName(newZuser.getName());
                gu.setPri(pri++);

                zGroupUserDao.save(gu);
            }
        }
        else
        {
            delGroupUsersByGroupId(groupObj.getId());
        }
    }

    @Override
    public void saveGroupType(ZGroupType groupTypeObj)
    {
        zGrouptypeDao.save(groupTypeObj);
    }

    @Override
    public void saveSingleUser(ZGroupUser guFromView)
    {
        /*
         * ZGroupUser oldGu = zGroupUserDao.getUniqueGroupUser(gu.getGroupId(),
         * gu.getUserId()); oldGu.setPri(gu.getPri());
         * oldGu.setRemark(gu.getRemark()); zGroupUserDao.update(oldGu);
         */
        ZGroupUser guFromDB = getUniqueGroupUser(guFromView.getGroupId(),
                guFromView.getUserId());
        if (!guFromDB.getPri().equals(guFromView.getPri()))
        {
            guFromDB.delete();
            List<ZGroupUser> usersInGroup = getUsersByGroupId(guFromView
                    .getGroupId().toString());
            if (guFromView.getPri() > usersInGroup.size() + 1)
                guFromView.setPri(usersInGroup.size() + 1);
            usersInGroup.add(guFromView.getPri() - 1, guFromView);
            delGroupUsersByGroupId(guFromView.getGroupId());
            int priIndex = 1;
            for (ZGroupUser gu : usersInGroup)
            {
                gu.setPri(priIndex++);
                zGroupUserDao.save(gu);
            }
        }
        else
        {
            guFromDB.delete();
            zGroupUserDao.save(guFromView);
        }
    }

    @Override
    public ZGroupUser getUniqueGroupUser(String groupId, String userId)
    {
        return zGroupUserDao.getUniqueGroupUser(groupId, userId);
    }

    @Override
    public void updateGroupType(ZGroupType groupType)
    {
        ZGroupType oldGroupType = zGrouptypeDao.getById(groupType.getId());
        if (oldGroupType != null)
        {
            oldGroupType.setTypeName(groupType.getTypeName());
            oldGroupType.setRemark(groupType.getRemark());
        }
        zGrouptypeDao.update(oldGroupType);

    }

    @Override
    public ZGroupUser getGroupUserByAccount(String account, String groupId)
    {
        Zuser user = zuserService.getZuserByAccount(account);
        ZGroupUser gu = new ZGroupUser("0", "0", user.getId(), user.getName(),
                null);
        gu.setUserAccount(user.getAccount());
        gu.setPri(user.getPri());
        return gu;

    }

    @Override
    public List<Zorganize> getOrgsByType(String orgId, Integer type)
    {
        List<Zorganize> orgList = zOrganizeService
                .getOrgsByOrgType(orgId, type);
        return orgList;
    }

    @Override
    public List<ZGroupUser> getGroupUserByOrgType(String orgId, String groupId,
            Integer type)
    {
        List<ZGroupUser> guList = null;
        List<ZGroupUser> newGuList = new ArrayList<ZGroupUser>();
        guList = getUsersByGroupId(groupId);
        Zuser userObj = new Zuser();
        List<Zorganize> orgList = zOrganizeService
                .getOrgsByOrgType(orgId, type);
        for (ZGroupUser gu : guList)
        {

            Zuser realUser = userObj.get(gu.getUserId());
            if (realUser != null && isUserBelongOrg(realUser, orgList))
            {
                gu.setUserAccount(realUser.getAccount());
                newGuList.add(gu);
            }

        }
        return newGuList;
    }

    private boolean isUserBelongOrg(Zuser user, List<Zorganize> orgList)
    {
        boolean ret = false;
        for (Zorganize org : orgList)
        {
            if (user.getOrgId().equals(org.getId()))
                ret = true;
        }
        return ret;
    }
    
    @Override
	public List<ZGroupType> getGroupTypeList() {
		List<ZGroupType>  groupTypes = zGrouptypeDao.listAll();
		return groupTypes;
	}
}
