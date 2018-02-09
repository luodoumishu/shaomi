package com.xjj._extensions.attachment.dao.hibernate4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj._extensions.attachment.dao.ICommonDocumentModelDao;
import com.xjj._extensions.attachment.model.CommonDocumentModel;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("CommonDocumentModelDao")
public class CommonDocumentModelDao extends BaseHibernateDao<CommonDocumentModel, String> implements ICommonDocumentModelDao{

    public List<CommonDocumentModel> query(int start, int pageSize, CommonDocumentModel documentModel, String subHql) {
        StringBuilder hql = new StringBuilder(HQL_LIST_ALL);//初始化HQL
        Map<String, Object> paramMap = new HashMap<String, Object>();//采用map装载 通配符和参数值
        builtCondition(documentModel, hql, paramMap);//用map拼装查询条件
        if (subHql != null)
            hql.append(subHql);
        return listPage(hql.toString(), start, pageSize, paramMap);
    }
	
    protected void builtCondition(CommonDocumentModel documentModel, StringBuilder hql, Map paramMap) {
        if (documentModel != null) {
            if (documentModel.getFilename() != null) {
            	hql.append(" and filename = :filename");
                paramMap.put("filename", documentModel.getFilename());
            }
            if (documentModel.getDataId() != null) {
                hql.append(" and dataId = :dataId");
                paramMap.put("dataId", documentModel.getDataId());
            }
            if (documentModel.getLocalname() != null) {
                hql.append(" and localname = :localname");
                paramMap.put("localname", documentModel.getLocalname());
            }
        }
    }
}
