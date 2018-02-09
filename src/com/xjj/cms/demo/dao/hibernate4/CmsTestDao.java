package com.xjj.cms.demo.dao.hibernate4;


import org.springframework.stereotype.Repository;

import com.xjj.cms.demo.dao.ICmsTestDao;
import com.xjj.cms.demo.model.CmsTest;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;



/**

 * @author yeyunfeng 2014-9-5 下午8:13:43 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-9-5
 * @modify by reason:{方法名}:{原因}
 */
@Repository("cmsTestDao")
public class CmsTestDao extends BaseHibernateDao<CmsTest, String> implements
		ICmsTestDao {

}
