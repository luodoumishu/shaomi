package com.xjj.cms.vote.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsVoteItemDao extends IBaseDao<CmsVoteItem, String>{

	List<CmsVoteItem> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

	Integer findItemByThemeId(String themeId);
	/**
	 * 查找改主题ID下所有的投票项
	 * @param themeId
	 * @return
	 *  上午11:18:08
	 */
	List<CmsVoteItem> findListByThemeId(String themeId);

	List<CmsVoteTheme> getVoteByPagesize(Integer pageSize, Integer pageNo);

	List<CmsVoteTheme> getAllValidMain();

	List<CmsVoteItem> getValidListOnlySelectByMainId(String themeId);

	List<CmsVoteItem> getValidListByMainId(String themeId);
	
	/**
	 * 根据主题ID查询出未删除且可用的投票项
	 * @author yeyunfeng 2014-12-9  上午11:43:01
	 * @param themeId
	 * @return
	 * @throws Exception
	 *
	 */
	List<CmsVoteItem> getValidListBythemeId(String themeId) throws Exception;

}
