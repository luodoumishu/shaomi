package com.xjj.cms.vote.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.vote.dao.ICmsVoteAnserDao;
import com.xjj.cms.vote.dao.ICmsVoteDetailDao;
import com.xjj.cms.vote.dao.ICmsVoteItemDao;
import com.xjj.cms.vote.dao.ICmsVoteThemeDao;
import com.xjj.cms.vote.model.CmsVoteAnser;
import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.cms.vote.service.ICmsVoteThemeService;
import com.xjj.cms.vote.web.controller.CmsVoteAnserController;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsVoteThemeService")
public class CmsVoteThemeService extends BaseService<CmsVoteTheme, String> implements ICmsVoteThemeService{
	
	@Autowired
	@Qualifier("cmsVoteThemeDao")
	private ICmsVoteThemeDao cmsVoteThemeDao;
	
	@Autowired
	@Qualifier("cmsVoteItemDao")
	private ICmsVoteItemDao cmsVoteItemDao;
	
	@Autowired
	@Qualifier("cmsVoteDetailDao")
	private ICmsVoteDetailDao cmsVoteDetailDao;
	
	@Autowired
	@Qualifier("cmsVoteAnserDao")
	private ICmsVoteAnserDao cmsVoteAnserDao;
	
	@Autowired
    @Qualifier("cmsVoteThemeDao")
	@Override
	public void setBaseDao(IBaseDao<CmsVoteTheme, String> baseDao) {
		this.baseDao = cmsVoteThemeDao;
	}

	@Override
	public Page<CmsVoteTheme> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsVoteTheme> items = cmsVoteThemeDao.query(skip,pageSize,queryMap, " order by sort");
		//获取总记录数
		int total =  cmsVoteThemeDao.total(queryMap);
		//构造返回对象page
		Page<CmsVoteTheme> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public void setItem(CmsVoteTheme theme) {
		List<CmsVoteItem> items = cmsVoteItemDao.findListByThemeId(theme.getId());
		theme.setCmsVoteItems(items);
	}

	@Override
	public void setDetail(CmsVoteTheme theme) {
		System.out.println(theme);
	}

	@Override
	public List<CmsVoteTheme> getVoteByPagesize(Integer pageSize, Integer pageNo) {
		return cmsVoteItemDao.getVoteByPagesize(pageSize,pageNo);
	}

	@Override
	public List<CmsVoteTheme> getAllValidMain() {
		return cmsVoteItemDao.getAllValidMain();
	}

	@Override
	public void addVote(String[] detailIdList, String themeId) throws Exception {
		try {
			CmsVoteTheme cmsVoteTheme = cmsVoteThemeDao.get(themeId);
			for (int i = 0; i < detailIdList.length; i++) {
				String detailId = detailIdList[i];
				CmsVoteDetail detail = cmsVoteDetailDao.get(detailId);
				if(detail.getVote_num() == null){
					detail.setVote_num(1);
				}else{
					detail.setVote_num(detail.getVote_num()+1);
				}
				detail.update();
				if(cmsVoteTheme.getVoteTotal() == null){
					cmsVoteTheme.setVoteTotal(1);
				}else{
					cmsVoteTheme.setVoteTotal(cmsVoteTheme.getVoteTotal()+1);
				}
				cmsVoteTheme.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveVoteContent(String[] itemIdList, String[] contentList) {
		for (int i = 0; i < itemIdList.length; i++) {
			String itemId = itemIdList[i];
			String content = contentList[i];
			CmsVoteAnser cmsVoteAnser = new CmsVoteAnser();
			CMSBaseController<CmsVoteAnser> controller = new CmsVoteAnserController();
			try {
				controller.setCmsBaseModel(cmsVoteAnser, BaseConstant.ADD);
				cmsVoteAnser.setItem_id(itemId);
				cmsVoteAnser.setContent(content);
				cmsVoteAnser.setSort(cmsVoteAnserDao.countAll()+1);
				cmsVoteAnserDao.save(cmsVoteAnser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateTheme(CmsVoteTheme theme) {
		List<CmsVoteItem> items = cmsVoteItemDao.getValidListByMainId(theme.getId());
		theme.setCmsVoteItems(items);
		theme.update();
	}
	
	@Override
	public CmsVoteTheme getById(String themeId) throws Exception {
		CmsVoteTheme theme = null;
		List<CmsVoteItem> itemList = new ArrayList<CmsVoteItem>();
		theme = cmsVoteThemeDao.getById(themeId);
		if (null != theme) {
			itemList = cmsVoteItemDao.getValidListBythemeId(themeId);
			if (null != itemList && !itemList.isEmpty()) {
				int itemSize = itemList.size();
				for (int i = 0; i < itemSize; i++) {
					List<CmsVoteDetail> detailList = new ArrayList<CmsVoteDetail>();
					CmsVoteItem item =  itemList.get(i);
					String itemId = item.getId();
					detailList = cmsVoteDetailDao.getValidtByItemId(itemId);
					item.setCmsVoteDetails(detailList);
				}
			}
			theme.setCmsVoteItems(itemList);
		}
		return theme;
	}
}
