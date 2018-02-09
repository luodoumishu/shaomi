/**
 * sgyweb
 * CMSBaseController.java
 * @Copyright: 2014 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2014-8-19 下午4:27:53 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.base.web.controller;

import java.sql.Timestamp;

import com.xjj._extensions.web.controller.BaseController;
import com.xjj.cms.base.model.CMSBaseModel;
import com.xjj.cms.base.util.BaseConstant;
import com.xjj.framework.core.web.filter.WebContext;

/**
 * <p>CMS基础Controller
 * @author yeyunfeng 2014-8-19 下午4:27:53 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-19
 * @modify by reason:{方法名}:{原因}
 */
public abstract class CMSBaseController<T extends CMSBaseModel> extends BaseController<T> {
	
	
	/**
	 * CMS保存的基础信息
	 * @author yeyunfeng 2014-8-19  下午4:30:09
	 * @param obj
	 * @param saveType
	 * @return
	 * @throws Exception
	 *
	 */
	public Object setCmsBaseModel(Object obj, String saveType)throws Exception {

		String superClassName = obj.getClass().getSuperclass().getSimpleName();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		String userId = WebContext.getInstance().getHandle().getUserId();
		String userName = WebContext.getInstance().getHandle().getUserName();
		if (superClassName.equals(BaseConstant.CMSBSEMODEL)) {
			CMSBaseModel baseModel = (CMSBaseModel) obj;
			if (saveType.equals(BaseConstant.ADD)) { // 添加
				baseModel.setCreateTime(currentTime);
				baseModel.setCreatUserId(userId);
				baseModel.setCreatUserName(userName);
				baseModel.setEndModifyTime(currentTime);
				baseModel.setEndModifyUserId(userId);
				baseModel.setEndModifyUserName(userName);
				
			} else if (saveType.equals(BaseConstant.UPDATE)) {
				baseModel.setEndModifyTime(currentTime);
				baseModel.setEndModifyUserId(userId);
				baseModel.setEndModifyUserName(userName);
			}
			baseModel.setIsDelete(BaseConstant.NO_DELETE);
		}
		
		return obj;
	}
}
