package com.xjj._extensions.groupUser.dao.hibernate4;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.groupUser.dao.IZGroupTypeDao;
import com.xjj._extensions.groupUser.model.ZGroupType;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZGroupTypeDao")
public class ZGroupTypeDao extends BaseHibernateDao<ZGroupType, String>
        implements IZGroupTypeDao
{

    @Override
    public ZGroupType getById(String id)
    {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        sql.append(" and id=:id");// 表字段名，非实体属性名
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        return super.uniqueByNative(sql.toString(), map);
    }

}
