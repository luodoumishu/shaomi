package com.xjj.cms.template.dao.hibernate4;



import org.springframework.stereotype.Repository;

import com.xjj.cms.template.dao.ICmsTemplateTypeDao;
import com.xjj.cms.template.model.CmsTemplateType;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

/**
 * 模板类型dao实现
 * <p>
 * @author yeyunfeng 2015年5月28日 下午12:06:12 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月28日
 * @modify by reason:{方法名}:{原因}
 */
@Repository("cmsTemplateTypeDao")
public class CmsTemplateTypeDao extends BaseHibernateDao<CmsTemplateType, String> implements
		ICmsTemplateTypeDao {

}
