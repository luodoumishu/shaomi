package com.xjj.cms.video.service.impl;

import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.cms.video.dao.ICmsVideoDao;
import com.xjj.cms.video.model.CmsVideo;
import com.xjj.cms.video.service.ICmsVideoService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("cmsVideoService")
public class CmsVideoService extends BaseService<CmsVideo, String> implements ICmsVideoService {

	@Autowired
    @Qualifier("cmsVideoDao")
    private ICmsVideoDao cmsVideoDao;
	
	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;

    @Autowired
    @Qualifier("cmsVideoDao")
    @Override
	public void setBaseDao(IBaseDao<CmsVideo, String> baseDao) {
		this.baseDao = cmsVideoDao;
	}
	
    public List<CmsVideo> getByPage(List<Criterion> criterions, int from,
			int length) {
		return null;
	}

    public CmsVideo save(CmsVideo model) {
		return super.save(model);
	}

	@Override
	public Page<CmsVideo> query(Map<String, Object> filtersMap) throws Exception{
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsVideo> items = cmsVideoDao.query(skip,pageSize,queryMap, " order by endModifyTime desc,createTime desc");
		//获取总记录数
		int total =  cmsVideoDao.total(queryMap);
		//构造返回对象page
		Page<CmsVideo> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public Page<CmsVideo> getALLVideosByMenuId(String menuId,
			Map<String, Object> filtersMap) throws Exception {
		List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
		childMenuList = cmsMenuService.getAllChildMenus(menuId, childMenuList);
		StringBuffer menuIdsStr = new StringBuffer();
		menuIdsStr.append("'" + menuId + "'");
		int childSize = childMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + childMenuList.get(i).getId() + "'");
		}
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap .get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20 : (Integer) filtersMap.get("pageSize");
		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap .get("queryObj");
		queryMap.put("menuIds", menuIdsStr.toString());

		List<CmsVideo> items = cmsVideoDao.query(skip, pageSize, queryMap, " order by createTime desc");
		// 获取总记录数
		int total = cmsVideoDao.total(queryMap);
		// 构造返回对象page
		Page<CmsVideo> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}

	@Override
	public CmsVideo findCmsVideoById(String id) {
		return cmsVideoDao.findCmsVideoById(id);
	}

	public void saveStateByModelId(String id,String state)throws  Exception{
		CmsVideo video =  cmsVideoDao.get(id);
		if(null != video){
			video.setState(state);
			cmsVideoDao.update(video);
			System.out.println("service里保存");
		}

	}
	
}