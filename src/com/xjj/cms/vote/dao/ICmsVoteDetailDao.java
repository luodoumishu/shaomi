package com.xjj.cms.vote.dao;

import java.util.List;
import java.util.Map;

import com.xjj.cms.vote.model.CmsVoteDetail;
import com.xjj.cms.vote.model.CmsVoteItem;
import com.xjj.framework.core.dao.IBaseDao;

public interface ICmsVoteDetailDao extends IBaseDao<CmsVoteDetail, String> {

	List<CmsVoteDetail> query(int skip, int pageSize,
			Map<String, Object> queryMap, String string);

	int total(Map<String, Object> queryMap);

	/**
	 * 保存多个明细项
	 * @param detailNames
	 * @param item
	 * @return
	 * @throws Exception
	 *  上午10:21:17
	 */
	void creatByDetailName(String[] detailNames, CmsVoteItem item) throws Exception;
	/**
	 * 返回该itemId下所有的投票项明细
	 * @param itemId
	 * @return
	 *  上午11:24:14
	 */
	List<CmsVoteDetail> findListByItemId(String itemId);

	void deleteAll(String itemId);

	List<CmsVoteDetail> getValidDetailListByItemId(String itemId);
	
	/**
	 * 根据投票项Id查询可用且不删除的投票项详细
	 * @author yeyunfeng 2014-12-9  上午11:56:45
	 * @param itemId
	 * @return
	 * @throws Exception
	 *
	 */
	List<CmsVoteDetail> getValidtByItemId(String itemId)throws Exception;

}
