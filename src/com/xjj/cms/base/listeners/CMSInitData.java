/**
 * slgaweb
 * CMSInitData.java
 * @Copyright: 2013 海南新境界软件有限公司. All Right Reserved.
 * @author yeyunfeng 2015年5月20日 上午10:35:48 
 * @Description: 本内容仅限于海南新境界软件有限公司内部使用，禁止转发.
 */
package com.xjj.cms.base.listeners;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.PropertiesUtil;

/**
 * 数据初始化
 * <p>
 * @author yeyunfeng 2015年5月20日 上午10:35:48 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月20日
 * @modify by reason:{方法名}:{原因}
 */
@Component("cmsInitData")
@Lazy(false)
public class CMSInitData implements InitializingBean {

	 private String  message;
	 
	 public String getMessage() {
		return message;
	}
	 
	 public void setMessage(String message) {
		this.message = message;
	}

	 
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	//@Lazy(false)
	public void afterPropertiesSet() throws Exception {
		
		Map<String, String> map =  PropertiesUtil.getProperties(CmsCC.WEB_CONFIG_ZH);
		
		String menu_fjsq_str = map.get("enabled_menu_fjsq");
		//设置是否启用栏目分级授权
		CmsCC.ENABLED_MENU_FJSQ = Boolean.parseBoolean(menu_fjsq_str) ;
		
		String menu_fjsq_org_showLevel = map.get("menu_fjsq_org_showLevel");
		//栏目分级授权-部门显示的层级
		CmsCC.MENU_FJSQ_ORG_SHOWLEVEL = Integer.parseInt(menu_fjsq_org_showLevel);
		
	}

}
