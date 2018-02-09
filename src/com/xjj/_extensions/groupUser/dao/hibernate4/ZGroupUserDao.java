package com.xjj._extensions.groupUser.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.groupUser.dao.IZGroupUserDao;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("ZGroupUserDao")
public class ZGroupUserDao extends BaseHibernateDao<ZGroupUser, String>
        implements IZGroupUserDao
{
    @Override
    public List<ZGroupUser> query(int start, int pageSize,
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
        List<ZGroupUser> list = listPage(hql.toString(), start, pageSize,
                queryMap);
        return list;
    }

    @Override
    public List<ZGroupUser> getGroupUserByCondition(
            Map<String, Object> queryMap, String subHql)
    {
        StringBuilder hql = new StringBuilder(
                "select * from z_group_user where 1=1");
        builtCondition(queryMap, hql);
        builtCondition(queryMap, hql);
        if (subHql != null)
        {
            hql.append(subHql);
        }

        return listByNative(hql.toString(), ZGroupUser.class, queryMap);
    }

    @Override
    public ZGroupUser getUniqueGroupUser(String groupId, String userId)
    {
        Map<String, Object> queryMap = new HashMap<String, Object>();

        queryMap.put("groupId", groupId);
        queryMap.put("userId", userId);
        List<ZGroupUser> gUsers = getGroupUserByCondition(queryMap, null);
        if (gUsers.size() == 1)
            return gUsers.get(0);
        else
        {
            return null;
        }

    }

    @Override
    public void delGroupUserByCondition(Map<String, Object> queryMap)
    {
        StringBuilder hql = new StringBuilder(
                "select * from z_group_user where 1=1");
        builtCondition(queryMap, hql);

        List<ZGroupUser> groupUsers = listByNative(hql.toString(),
                ZGroupUser.class, queryMap);
        if (groupUsers.size() != 0)
        {
            for (ZGroupUser gu : groupUsers)
            {
                delete(gu);
            }
        }
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
            if (queryMap.get("groupId") != null
                    && queryMap.get("groupId") != "")
            {
                hql.append(" and group_id = :groupId");
                paramMap.put("groupId", queryMap.get("groupId"));
            }
            if (queryMap.get("userName") != null
                    && queryMap.get("userName") != "")
            {
                hql.append(" and user_name = :userName");
                paramMap.put("userName", queryMap.get("userName"));
            }
            if (queryMap.get("userId") != null && queryMap.get("userId") != "")
            {
                hql.append(" and user_Id = :userId");
                paramMap.put("userId", queryMap.get("userId"));
            }

            // 解析后重新赋值
            queryMap.clear();
            queryMap.putAll(paramMap);
        }
    }

}
