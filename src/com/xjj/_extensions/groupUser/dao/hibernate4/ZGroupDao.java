package com.xjj._extensions.groupUser.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.groupUser.dao.IZGroupDao;
import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZGroupDao")
public class ZGroupDao extends BaseHibernateDao<ZGroup, String> implements
        IZGroupDao
{

    @Override
    public List<ZGroup> getGroupsByTypeId(String typeId)
    {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        sql.append(" and type_id=:typeId");

        Map<String, String> map = new HashMap<String, String>();
        map.put("typeId", typeId);

        return super.listByNative(sql.toString(), ZGroup.class, map);
    }

    @Override
    public List<ZGroup> query(int start, int pageSize,
            Map<String, Object> queryMap, String subHql)
    {
        // 初始化HQL
        StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
        // 解析查询条件，拼接查询sql
        builtCondition(queryMap, hql);
        if (subHql != null)
        {
            hql.append(subHql);
        }
        // 调用框架提供的方法进行分页查询
        List<ZGroup> list = listPage(hql.toString(), start, pageSize, queryMap);
        return list;
    }

    @Override
    public int total(Map<String, Object> queryMap)
    {
        StringBuilder hql = new StringBuilder(HQL_COUNT_ALL);
        builtCondition(queryMap, hql);
        Long total = unique(hql.toString(), queryMap);
        return total.intValue();
    }

    /**
     * @Description: 解析查询条件，拼接查询sql
     * @param queryMap
     *            查询
     * @author longdp 2013-12-11下午12:14:20
     */
    protected void builtCondition(Map<String, Object> queryMap,
            StringBuilder hql)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (queryMap != null)
        {
            if (queryMap.get("id") != null && queryMap.get("id") != "")
            {
                hql.append(" and id = :id");
                paramMap.put("id", queryMap.get("id"));
            }
            if (queryMap.get("groupName") != null
                    && queryMap.get("groupName") != "")
            {
                hql.append(" and group_name = :groupName");
                paramMap.put("groupName", queryMap.get("groupName"));
            }
            if (queryMap.get("typeId") != null && queryMap.get("typeId") != "")
            {
                hql.append(" and type_id = :typeId");
                paramMap.put("typeId", queryMap.get("typeId"));
            }

            // 解析后重新赋值
            queryMap.clear();
            queryMap.putAll(paramMap);
        }
    }

}
