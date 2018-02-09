package com.xjj.cms.link.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.link.dao.ICmsChlLinkDao;
import com.xjj.cms.link.dao.ICmsLinkDao;
import com.xjj.cms.link.model.CmsChlLink;
import com.xjj.cms.link.model.CmsLink;
import com.xjj.cms.link.service.ICmsLinkService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;


@Service("cmsLinkService")
public class CmsLinkService extends BaseService<CmsLink, String> implements ICmsLinkService{
	@Autowired
	@Qualifier("cmsLinkDao")
	private ICmsLinkDao cmsLinkDao;
	
	@Autowired
	@Qualifier("cmsChlLinkDao")
	private ICmsChlLinkDao cmsChlLinkDao;
	
	@Autowired
    @Qualifier("cmsLinkDao")
	@Override
	public void setBaseDao(IBaseDao<CmsLink, String> baseDao) {
		this.baseDao = cmsLinkDao;
	}
	
	@Override
	public Page<CmsLink> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsLink> items = cmsLinkDao.query(skip,pageSize,queryMap, " order by id");
		//获取总记录数
		int total =  cmsLinkDao.total(queryMap);
		//构造返回对象page
		Page<CmsLink> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public CmsLink findLink(String linkId) {
		return cmsLinkDao.findLink(linkId);
	}

	@Override
	public void deleteCmsChlLink(String linkId) {
		List<CmsChlLink> cclList = cmsChlLinkDao.findAllByLinkId(linkId);
		if(!cclList.isEmpty()){
			for (int i = 0; i < cclList.size(); i++) {
				CmsChlLink ccl = cclList.get(i);
				ccl.delete();
			}
		}
	}

	@Override
	public void updateCmsChlLink(String linkId) {
		List<CmsChlLink> cclList = cmsChlLinkDao.findAllByLinkId(linkId);		
		CmsLink link = cmsLinkDao.findLink(linkId);
		if(!cclList.isEmpty()){
			for (int i = 0; i < cclList.size(); i++) {
				CmsChlLink ccl = cclList.get(i);
				ccl.setLinkName(link.getLinkName());
				ccl.setLinkAddr(link.getLinkAddr());
				ccl.setLinkType(link.getLinkType());
				ccl.update();
			}
		}
	}

}
