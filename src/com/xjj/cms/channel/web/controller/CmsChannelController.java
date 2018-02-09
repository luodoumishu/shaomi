package com.xjj.cms.channel.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj._extensions.web.util.JsonResult;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.cms.base.web.controller.CMSBaseController;
import com.xjj.cms.channel.model.CmsChannel;
import com.xjj.cms.channel.model.CmsChannelItem;
import com.xjj.cms.channel.service.ICmsChannelItemService;
import com.xjj.cms.channel.service.ICmsChannelService;
import com.xjj.cms.menu.model.CmsMenu;
import com.xjj.cms.menu.service.ICmsMenuService;
import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.util.JsonUtil;
import com.xjj.framework.core.web.filter.WebContext;

@Controller
@RequestMapping(value = "/CmsChannel")
public class CmsChannelController extends CMSBaseController<CmsChannel>{
		@Autowired
		@Qualifier("CmsChannelService")
		private ICmsChannelService iCmsChannelService;
		
		@Autowired
		@Qualifier("CmsChannelItemService")
		private ICmsChannelItemService cmsChannelItemService;
		
		@Autowired
		@Qualifier("cmsMenuService")
		private ICmsMenuService cmsMenuService;
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST })
		public JsonResult list(String filters) throws IOException {
			// //json数据转成查询条件(包含分页信息)的map,格式为{skip=0, pageSize=20, queryObj={name=,
			// account=}}
			Map<String, Object> filtersMap = new ObjectMapper().readValue(filters,
					Map.class);
			// 进行分页查询,skip为开始下标，pageSize为每页条数,queryObj为查询条件
			Page<CmsChannel> page = iCmsChannelService.query(filtersMap);
			
			// 构造返回对象
			JsonResult jr = new JsonResult(0, "", page);
			return jr;
		}
	
		@Override
		protected void extendQueryOrder(ConditionQuery conditionQuery,
				OrderBy orderBy) {
			
		}	
		/**
		 * 获得频道信息
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("listOrgs")
		public JsonResult listOrgs() throws IOException{
			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
			List<CmsChannel> list = iCmsChannelService.listAllByOrgRootId(orgRootId);
			JsonResult jr = new JsonResult(0, "", list);
	        return jr;
		}
		@RequestMapping(value = "/read")
	    public ModelAndView read() throws Exception{
			String orgRootId= WebContext.getInstance().getHandle().getOrgRootId();
			List<CmsChannel> list = iCmsChannelService.listAllByOrgRootId(orgRootId);
		   return new ModelAndView().addObject(list);
	    }
		
		@RequestMapping(value = "/save")
		public JsonResult save(String models) throws Exception {
			CmsChannel model = JsonUtil.fromJson(models,
					CmsChannel.class);
			this.setCmsBaseModel(model,BaseConstant.ADD);
			model.save();
			// 构造返回对象
			JsonResult jr = new JsonResult(0, "", null);
			return jr;
		}
		@RequestMapping(value = "/update")
		public JsonResult update(String models) throws Exception {
			CmsChannel model = JsonUtil.fromJson(models,
					CmsChannel.class);
			this.setCmsBaseModel(model, BaseConstant.UPDATE);
			model.update();
			//同步修改CmsChannelItem中的频道名称
			List<CmsChannelItem> list = iCmsChannelService.findCmsChannelItemByChannelId(model.getId());
			if(list!=null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					CmsChannelItem item = list.get(i);
					item.setChanneName(model.getChanneName());
					item.update();
				}
			}
			// 构造返回对象
			JsonResult jr = new JsonResult(0, "", null);
			return jr;
		}
		@RequestMapping(value = "/delete")
		public JsonResult delete(String models) throws IOException {
			JsonResult jr = new JsonResult(0, "", null);
			CmsChannel model = JsonUtil.fromJson(models,
					CmsChannel.class);
			if(iCmsChannelService.checkExistChildOrg(model.getId())){
				jr.setResultMsg("存在子单位,不能删除,请先删除子单位!");
			}else{
				//同步删除CmsChannelItem中的该频道下的频道项
				List<CmsChannelItem> list = iCmsChannelService.findCmsChannelItemByChannelId(model.getId());
				if(list!=null && list.size()>0){
					for(int i = 0;i<list.size();i++){
						CmsChannelItem item = list.get(i);
						item.delete();
					}
				}
				model.delete();
			}
			// 构造返回对象
			return jr;
		}
		
		/**
		 * 根据频道ID获取其频道下所属的子栏目(真实栏目)用于前台展示
		 * @param chlCode
		 * @return
		 */
		@RequestMapping(value = "/getMenusByChlCode")
		public List<CmsMenu> getMenusByChlCode(HttpServletRequest request){
			String chlCode = request.getParameter("chlCode");
			CmsChannel cc = (CmsChannel)iCmsChannelService.findByChlCode(chlCode);
			List<CmsMenu> menus = new ArrayList<CmsMenu>();
			List<CmsChannelItem> setChlItem = cmsChannelItemService.findAllItem(cc.getId());
			
			for(int i= 0;i<setChlItem.size();i++){
				CmsChannelItem ccItem = (CmsChannelItem)setChlItem.get(i);
   				String itemId = ccItem.getMenuId();
   				CmsMenu cm = (CmsMenu)cmsMenuService.get(itemId);
   				if(cm.getMenuType() == 0){
   					menus.add(cm);
   				}
			}
			
			return menus;
		}
}
