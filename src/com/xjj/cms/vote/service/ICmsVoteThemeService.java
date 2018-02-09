package com.xjj.cms.vote.service;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteTheme;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.service.IBaseService;

public interface ICmsVoteThemeService extends IBaseService<CmsVoteTheme, String>{

	Page<CmsVoteTheme> query(Map<String, Object> filtersMap);
	/**
	 * 设置改主题ID下的投票项
	 * @param theme
	 * @return
	 *  上午11:03:36
	 */
	void setItem(CmsVoteTheme theme);
	/**
	 * 设置改主题ID下的投票项明细
	 * @param theme
	 * @return
	 *  上午11:03:36
	 */
	void setDetail(CmsVoteTheme theme);
	/**
	 * 根据条数来显示投票 
	 * @return
	 */
	List<CmsVoteTheme> getVoteByPagesize(Integer pageSize, Integer pageNo);
	/**
	 * 显示所有有效的投票主题   
	 * @return
	 */
	List<CmsVoteTheme> getAllValidMain();
	/**
	 * 设置投票项明细的投票数和该主题的投票总数
	 * @param detailIdList
	 * @param themeId
	 *  下午7:00:28
	 */
	void addVote(String[] detailIdList, String themeId)throws Exception;
	/**
	 * 保存问答类型的投票项和正文
	 * @param itemIdList
	 * @param contentList
	 *  下午7:11:29
	 */
	void saveVoteContent(String[] itemIdList, String[] contentList);
	/**
	 * 更新投票主题
	 * @param theme
	 *  下午2:39:21
	 */
	void updateTheme(CmsVoteTheme theme);
	/**
	 * 根据主题查询，该主题下的投票项和投票主题
	 * @author yeyunfeng 2014-12-9  上午11:07:25
	 * @param themeId
	 * @return
	 * @throws Exception
	 *
	 */
	CmsVoteTheme getById(String themeId)throws Exception;
}
