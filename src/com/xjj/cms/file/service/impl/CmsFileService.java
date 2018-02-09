package com.xjj.cms.file.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xjj.cms.article.model.CmsArticle;
import com.xjj.cms.base.dao.ICmsAffixDao;
import com.xjj.cms.base.model.CmsAffix;
import com.xjj.cms.file.dao.ICmsFileDao;
import com.xjj.cms.file.model.CmsFile;
import com.xjj.cms.file.service.ICmsFileService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.IBaseDao;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj.framework.core.service.impl.BaseService;

@Service("cmsFileService")
public class CmsFileService extends BaseService<CmsFile, String> implements ICmsFileService{
	@Autowired
	@Qualifier("cmsFileDao")
	private ICmsFileDao cmsFileDao;
	
	@Autowired
	@Qualifier("cmsAffixDao")
	private ICmsAffixDao cmsAffixDao;
	
	@Autowired
	@Qualifier("cmsMenuService")
	private ICmsMenuService cmsMenuService;
	
	@Autowired
    @Qualifier("cmsFileDao")
	@Override
	public void setBaseDao(IBaseDao<CmsFile, String> baseDao) {
		this.baseDao = cmsFileDao;
	}
	
	@Override
	public Page<CmsFile> query(Map<String, Object> filtersMap) {
		 //开始查询下标
		int skip = filtersMap.get("skip")==null?1:(Integer)filtersMap.get("skip");
		//每页显示条数
		int pageSize = filtersMap.get("pageSize")==null?20:(Integer)filtersMap.get("pageSize");
		//获取查询条件
		@SuppressWarnings("unchecked")
		Map<String,Object> queryMap = (Map<String,Object>) filtersMap.get("queryObj");
		//进行条件查询
		List<CmsFile> items = cmsFileDao.query(skip,pageSize,queryMap, " order by id");
		//获取总记录数
		int total =  cmsFileDao.total(queryMap);
		//构造返回对象page
		Page<CmsFile> page = PageUtil.getPage(skip, pageSize, items, total);
				
		return page;
	}

	@Override
	public void saveFileAndAffix(CmsFile cmsFile, List<CmsAffix> affixs) {
		String fileId = cmsFile.getId();
		if(fileId.isEmpty() || null  == fileId ){
			//新增文件信息
			cmsFile.setCmsAffixs(affixs);
			cmsFile.save();
			fileId = cmsFile.getId();
		}else{
			//修改文件信息
			cmsFile.update();
		}
		//新增附件信息
		int size = affixs.size();
		CmsAffix affix = affixs.get(size-1);
		affix.setModeId(fileId);
		affix.save();
	}

	@Override
	public List<CmsAffix> findAffixByModelId(String modelId) {
		return cmsAffixDao.findAffixByModelId(modelId);
	}

	@Override
	public void del(String modelId) {
		List<CmsAffix> affixs = cmsAffixDao.findAffixByModelId(modelId);
		if(affixs != null){
			for(int i = 0;i < affixs.size();i++){
				CmsAffix affix = affixs.get(i);
				affix.delete();
			}
		}
		List<CmsFile> cmsfiles = cmsFileDao.listAll();
		if(!cmsfiles.isEmpty()){
			for(int i = 0;i < cmsfiles.size();i++){
				CmsFile cmsfile = cmsfiles.get(i);
				if(cmsfile.getId().equals(modelId)){
					cmsfile.delete();
				}
			}
		}
	}

	@Override
	public CmsAffix findAffixById(String affixId) {
		return cmsAffixDao.get(affixId);
	}

	@Override
	public Page<CmsAffix> getALLFilesByMenuIds(String[] menuIds,
			Map<String, Object> filtersMap) throws Exception {
		List<CmsMenu> allChildMenuList = new ArrayList<CmsMenu>();
		StringBuffer menuIdsStr = new StringBuffer();
		int menuidLength = menuIds.length;
		for(int i=0;i<menuidLength;i++){
			List<CmsMenu> childMenuList = new ArrayList<CmsMenu>();
			childMenuList = cmsMenuService.getAllChildMenus(menuIds[i], childMenuList);
			allChildMenuList.addAll(childMenuList);
			if(i==0){
				menuIdsStr.append("'"+menuIds[i]+"'");
			}else{
				menuIdsStr.append(",'"+menuIds[i]+"'");
			}
			
		}
		int childSize = allChildMenuList.size();
		for (int i = 0; i < childSize; i++) {
			menuIdsStr.append(",'" + allChildMenuList.get(i).getId()+"'");
		}
		// 开始查询下标
		int skip = filtersMap.get("skip") == null ? 1 : (Integer) filtersMap
				.get("skip");
		// 每页显示条数
		int pageSize = filtersMap.get("pageSize") == null ? 20
				: (Integer) filtersMap.get("pageSize");
		// 获取查询条件
		@SuppressWarnings("unchecked")
		Map<String, Object> queryMap = (Map<String, Object>) filtersMap
				.get("queryObj");
		queryMap.put("menuIds", menuIdsStr.toString());
		/*List<CmsArticle> items = cmsArticleDao.query(skip, pageSize, queryMap,
				);,endModifyTime*/
		
		List<CmsAffix> items = cmsFileDao.affixQuery(skip, pageSize, " order by createTime desc", queryMap);
		// 获取总记录数
		int total = cmsFileDao.total(queryMap);
		// 构造返回对象page
		Page<CmsAffix> page = PageUtil.getPage(skip, pageSize, items, total);
		return page;
	}
}
