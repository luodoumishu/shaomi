package com.xjj._extensions.groupUser.dao;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZGroupUserDao extends IBaseDao<ZGroupUser, String>
{

    List<ZGroupUser> query(int start, int pageSize,
            Map<String, Object> queryMap, String subHql);

    int total(Map<String, Object> queryMap);

    void delGroupUserByCondition(Map<String, Object> queryMap);

    ZGroupUser getUniqueGroupUser(String groupId, String userId);

    List<ZGroupUser> getGroupUserByCondition(Map<String, Object> queryMap,
            String subHql);

}
