package com.xjj.cms.menu.dao.hibernate4;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xjj.cms.menu.dao.ICmsMenuTransmitDao;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.model.CmsMenuTransmit;
import com.xjj.cms.menu.model.CmsMenuTransmitInfo;
import com.xjj.framework.core.dao.hibernate4.BaseHibernateDao;

@Repository("cmsMenuTransmitDao")
public class CmsMenuTransmitDao extends BaseHibernateDao<CmsMenuTransmit, String> implements ICmsMenuTransmitDao {
}
