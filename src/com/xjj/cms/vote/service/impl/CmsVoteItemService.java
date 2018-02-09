package com.xjj.cms.vote.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.vote.dao.ICmsVoteDetailDao;
import com.xjj.cms.vote.dao.ICmsVoteItemDao;
import com.xjj.cms.vote.dao.ICmsVoteThemeDao;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteItemService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsVoteItemService")
public class CmsVoteItemService extends BaseService<CmsVoteItem, String> implements ICmsVoteItemService{
	
	@Autowired
	@Qualifier("cmsVoteItemDao")
	private ICmsVoteItemDao cmsVoteItemDao;
	
	@Autowired
	@Qualifier("cmsVoteThemeDao")
	private ICmsVoteThemeDao cmsVoteThemeDao;
	
	@Autowired
	@Qualifier("cmsVoteDetailDao")
	private ICmsVoteDetailDao cmsVoteDetailDao ;
	
	@Autowired
    @Qualifier("cmsVoteItemDao")
	@Override
	public void setBaseDao(IBaseDao<CmsVoteItem, String> baseDao) {
		this.baseDao = cmsVoteItemDao;
	}

	@Override
	public Page<CmsVoteItem> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsVoteItem> items = cmsVoteItemDao.query(skip,pageSize,queryMap, " order by sort");
		//获取总记录数
		int total =  cmsVoteItemDao.total(queryMap);
		//构造返回对象page
		Page<CmsVoteItem> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public void addThemeAndDetail(CmsVoteItem item, String themeId,
			String[] detailNames) throws Exception {
		//设置投票项关联的主题
		item.setTheme_id(themeId);
		
		Integer sort = cmsVoteItemDao.findItemByThemeId(themeId);
		//设置投票项排序
		item.setSort(sort + 1);
		item.save();
		if(detailNames != null){
			cmsVoteDetailDao.creatByDetailName(detailNames, item);
		}
	}

	@Override
	public CmsVoteTheme findThemeById(String themeId) {
		return cmsVoteThemeDao.get(themeId);
	}

	@Override
	public void deleteByItemId(String itemId) {
		//找到对应的投票项删除
		CmsVoteItem item = cmsVoteItemDao.get(itemId);
		String themeId = item.getTheme_id();
		Integer sort = item.getSort();
		List<CmsVoteItem> items = cmsVoteItemDao.findListByThemeId(themeId);
		if(!items.isEmpty()){
			for (int i = 0; i < items.size(); i++) {
				CmsVoteItem voteItem = items.get(i);
				Integer ItemSort = voteItem.getSort();
				if(ItemSort > sort){
					voteItem.setSort(ItemSort - 1);
				}
			}
		}
		cmsVoteItemDao.delete(item);
	}

	@Override
	public void updateThemeAndDetail(CmsVoteItem item, String themeId,
			String[] detailNames) throws Exception {
		cmsVoteDetailDao.deleteAll(item.getId());
		cmsVoteDetailDao.creatByDetailName(detailNames, item);
	}

	@Override
	public List<CmsVoteItem> getValidListOnlySelectByMainId(String themeId) {
		return cmsVoteItemDao.getValidListOnlySelectByMainId(themeId);
	}

	@Override
	public List<CmsVoteItem> getValidListByMainId(String themeId) {
		return cmsVoteItemDao.getValidListByMainId(themeId);
	}

	@Override
	public void updateDetail(CmsVoteItem item) {
		List<CmsVoteDetail> details = cmsVoteDetailDao.getValidDetailListByItemId(item.getId());
		item.setCmsVoteDetails(details);
		item.update();
	}
}
