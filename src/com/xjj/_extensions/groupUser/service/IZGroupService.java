package com.xjj._extensions.groupUser.service;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj._extensions.roleUserPer.model.Zorganize;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface IZGroupService extends IBaseService<ZGroup, String>
{
    List<ZGroupType> getAllGroupType();

    Page<ZGroupUser> queryUserByGroup(Map<String, Object> filtersMap);

    Page<ZGroup> queryGroupByType(Map<String, Object> filtersMap);

    List<Zuser> getUsersByOrgId(String orgId);

    void saveGroupUsers(ZGroup groupObj);

    void delGroupUsersByGroupId(String groupId);

    /**
     * 通过组ID获取组里用户信息
     * 
     * @param groupId
     * @return
     */
    List<ZGroupUser> getUsersByGroupId(String groupId);

    /**
     * 通过组织ID与组ID获取用户信息
     * 
     * @param orgId
     * @param groupId
     * @return
     */
    List<ZGroupUser> getUserFilterByOrg(String orgId, String groupId);

    /**
     * 获取用户所在组织下同组用户信息
     * 
     * @param userId
     * @param groupId
     * @return
     */
    List<ZGroupUser> getUserFilterByUserOrg(String userId, String groupId);

    /**
     * 获取该组类型下所有组信息
     * 
     * @param typeId
     * @return
     */
    List<ZGroup> getGroupsByTypeId(String typeId);

    ZGroupUser getUniqueGroupUser(String groupId, String userId);

    void saveSingleUser(ZGroupUser gu);

    void saveGroupType(ZGroupType groupTypeObj);

    void delGroupType(String typeId);

    ZGroupType getGroupType(String typeId);

    Integer getPri(String groupId, String userId);

    void updateGroupType(ZGroupType groupType);

    ZGroupUser getGroupUserByAccount(String account, String groupId);

    List<ZGroupUser> getGroupUserByOrgType(String orgId, String groupId,
            Integer type);

    List<ZGroup> getAllGroups();

    List<Zorganize> getOrgsByType(String orgId, Integer type);

    List<ZGroupUser> getGroupUserByOrgId(String orgId, boolean includeSubOrg,
            String groupId);

	List<ZGroupType> getGroupTypeList();

}
