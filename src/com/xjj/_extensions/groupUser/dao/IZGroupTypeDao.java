package com.xjj._extensions.groupUser.dao;

import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj.framework.core.dao.IBaseDao;

public interface IZGroupTypeDao extends IBaseDao<ZGroupType, String>
{
    public ZGroupType getById(String string);

}
