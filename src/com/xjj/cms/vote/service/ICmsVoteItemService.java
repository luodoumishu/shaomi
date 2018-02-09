package com.xjj.cms.vote.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsVoteItemService extends IBaseService<CmsVoteItem, String>{

	Page<CmsVoteItem> query(Map<String, Object> filtersMap);

	/**
	 * 保存主题项和明细项
	 * @param item
	 * @param themeId
	 * @param detailNames
	 * @throws Exception
	 *  上午10:23:42
	 */
	void addThemeAndDetail(CmsVoteItem item, String themeId,
			String[] detailNames) throws Exception;

	CmsVoteTheme findThemeById(String themeId);
	/**
	 * 根据itemId删除，并重新排序
	 * @param itemId
	 *  上午8:59:33
	 */
	void deleteByItemId(String itemId);
	/**
	 * 更新主题项和明细项
	 * @param item
	 * @param themeId
	 * @param detailNames
	 * @throws Exception
	 *  上午10:23:42
	 */
	void updateThemeAndDetail(CmsVoteItem item, String themeId,
			String[] detailNames)throws Exception;
	/**
	 * 获取仅仅为选择形式的有效的投票项
	 * @param themeId
	 * @return
	 */
	List<CmsVoteItem> getValidListOnlySelectByMainId(String themeId);
	/**
	 * 获取全部有效的投票项
	 * @param themeId
	 * @return
	 */
	List<CmsVoteItem> getValidListByMainId(String themeId);
	/**
	 * 更新投票项
	 * @param item
	 *  下午2:46:55
	 */
	void updateDetail(CmsVoteItem item);

}
