package com.xjj._extensions.groupUser.dao;

import java.util.List;
import java.util.Map;

import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZGroupDao extends IBaseDao<ZGroup, String>
{
    List<ZGroup> getGroupsByTypeId(String TypeId);

    List<ZGroup> query(int start, int pageSize, Map<String, Object> queryMap,
            String subHql);

    int total(Map<String, Object> queryMap);
}
