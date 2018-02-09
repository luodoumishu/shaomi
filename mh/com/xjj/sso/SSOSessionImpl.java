package com.xjj.sso;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj.framework.core.util.SpringContextUtil;
import com.xjj.ws.distribute.service.Zorganize2Service;

public class SSOSessionImpl implements ISSOSession {

	/**
	 * 根据返回的用户信息，重新从本地数据取出对应的用户信息,保存session
	 */
	public void setSeasion(HttpServletRequest httpRequest,
			Map<String, String> userInfo) {
		/**
		 * {userDesc=, status=0, pwdErrLock=0, addr=, loginMode=0, id=860293,
		 * orgId=7775, phoneNo=, certificate=, email=, duty=, priority=0,
		 * name=省高院管理员, gender=1, faxNo=, account=sgyadmin, pwdErrNum=0,
		 * pwdLimitDate=2043-10-29 00:00:00.0, mobileNo=}
		 */
		String userId = (String) userInfo.get("id");
		String account = (String) userInfo.get("account");
		String name = (String) userInfo.get("name");
		String mobileNo = (String) userInfo.get("mobileNo");
		String faxNo = (String) userInfo.get("faxNo");
		String phoneNo = (String) userInfo.get("phoneNo");
		String email = (String) userInfo.get("email");
		String orgId = (String) userInfo.get("orgId");
		String gender = (String) userInfo.get("gender");
		String addr = (String) userInfo.get("addr");
		
		
		HttpSession session = httpRequest.getSession();
		if(!(session != null && session.getAttribute("userId") != null)) {
			String rootOrgId = "";
			IZuserService zuserService = (IZuserService) SpringContextUtil.getInstance().getBean("ZuserService");
			Zuser userModel = zuserService.getZuserByAccount(account != null ? account : "");
			if (userModel != null) {
				rootOrgId = userModel.getOrgRootId();
			}
			session.setAttribute("orgRootId", rootOrgId);
			Zorganize2Service  zorganize2Service = (Zorganize2Service)SpringContextUtil.getInstance().getBean("zorganize2Service");
			String orgName = zorganize2Service.get(orgId).getName();
			session.setAttribute("orgName", orgName);
			System.out.println("userinfo[userId:"+userId+", account:"+account+", name:"+name+", orgName:"+orgName+"]");
		}
		session.setAttribute("userId", userId);
		session.setAttribute("userName", name);
		session.setAttribute("account", account);
		session.setAttribute("orgId", orgId);
		session.setAttribute("faxNo", faxNo);
		session.setAttribute("mobileNo", mobileNo);
		session.setAttribute("phoneNo", phoneNo);
		session.setAttribute("email", email);
		session.setAttribute("gender", gender);
		session.setAttribute("addr", addr);
	}
}
