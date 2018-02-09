package com.xjj._extensions.roleUserPer.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj._extensions.roleUserPer.util.Base64;
import com.xjj._extensions.roleUserPer.util.decode64;
import com.xjj._extensions.web.listener.InitCache;
import com.xjj.cms.base.VO.CmsCC;
import com.xjj.cms.base.util.PropertiesUtil;
import com.xjj.framework.core.service.ICommonService;
import com.xjj.framework.core.util.SpringContextUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Zhengyuyang
 * Date: 2014/10/29
 */
@Controller
public class LoginController {
	@Autowired
	@Qualifier("CommonService")
	private ICommonService commonService;
	
    private static Logger LOG = LoggerFactory.getLogger(InitCache.class);
    IZuserService zuserService = SpringContextUtil.getInstance().getBean("ZuserService");
    private String path_index =  "/pages/mainframe/index.jsp";
    private String path_login =  "/login.jsp";

    @RequestMapping(value = "/login", method ={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView login(String username, String _upwd, boolean rememberMe, HttpServletRequest request){

        String host = getRemoteHost(request);
        Subject subject = SecurityUtils.getSubject();
        //TODO kickOutUser 如果用户已登录，先踢出 by zhengyuyang
        //TODO rememberMe功能 by zhengyuyang
        String _verifyCode = request.getParameter("_verifyCode");
        String PERANDOMS = (String)request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        String msg = "";
        if(_verifyCode!=null){
        	/*if (!_verifyCode.equals(PERANDOMS)) {
            	//身份验证异常
            	msg = "您填写的验证码输入不正确，请重新填写！";
                ModelAndView mv = new ModelAndView("redirect:"+path_login);
                mv.addObject("msg", msg);
                return mv;
            }*/
        }
        username =new String(decode64.decode(username));
        _upwd =new String(decode64.decode(_upwd));
        
        UsernamePasswordToken token = new UsernamePasswordToken(username, _upwd,rememberMe,host);
        
        int rccs = 10;
		try {
			rccs = Integer.parseInt(PropertiesUtil.getProperty(CmsCC.WEB_CONFIG_ZH, "limitingfrequency").toString());
		} catch (Exception e1) {
		}
        //通过shiro校验用户身份
        try{
            subject.login(token);
        }catch (IncorrectCredentialsException | UnknownAccountException e) {
            //身份验证异常
        	msg = "您填写的用户不存在或密码不正确，请重新填写！";
        	Map map = zuserService.getUserStatus(username);
        	String status = "0";
        	if(map != null){
				if(Integer.parseInt(map.get("failuretimes").toString()) >= rccs){
					msg = "该账户连续输入密码错误超过"+rccs+"次,已被冻结,请联系管理员！";
					status = "-1";
				}
        	}
            ModelAndView mv = new ModelAndView("redirect:"+path_login);
            mv.addObject("msg", msg);
            zuserService.updateFailuretimes(username, status);
            return mv;
        }catch (AuthenticationException e) {
            //身份验证异常
        	msg = "您填写的用户不存在或密码不正确，请重新填写！";
        	Map map = zuserService.getUserStatus(username);
        	String status = "0";
        	if(map != null){
				if(Integer.parseInt(map.get("failuretimes").toString()) >= rccs){
					msg = "该账户连续输入密码错误超过"+rccs+"次,已被冻结,请联系管理员！";
					status = "-1";
				}
        	}
            ModelAndView mv = new ModelAndView("redirect:"+path_login);
            mv.addObject("msg", msg);
            zuserService.updateFailuretimes(username, status);
            return mv;
        }

        //将用户信息写入会话
        Zuser zuser = new Zuser();
        zuser.setAccount(username);
        List<Zuser> zusers= zuserService.query(-1 , -1, zuser);
        if(zusers!=null && zusers.size()>0){
            zuser = zusers.get(0);
        }else{
            throw new UnknownAccountException("未知帐号错误！");
        }
        if(!"0".equals(zuser.getStatus())){
        	msg = "账号已冻结 , 请联系管理员解冻！";
        	ModelAndView mv = new ModelAndView("redirect:"+path_login);
        	mv.addObject("msg", msg);
        	return mv;
        }
        
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String orgId = zuser.getOrgs()!=null && zuser.getOrgs().size()>0?zuser.getOrgs().get(0).getId():"";
        String orgRootId = zuser.getOrgRootId();

        session.setAttribute("userId",zuser.getId());
        session.setAttribute("userName",zuser.getName());
        session.setAttribute("userAccount",zuser.getAccount());
        session.setAttribute("orgId", orgId);
        session.setAttribute("orgRootId", orgRootId);
        session.setAttribute("orgName",zuser.getOrgs()!=null && zuser.getOrgs().size()>0?zuser.getOrgs().get(0).getName():"");
        
        GregorianCalendar gc = new GregorianCalendar(); 
        gc.setTime(zuser.getLastupdatedate());
        gc.add(2, 3);
        Date nowDate = new Date();
        if(nowDate.after(gc.getTime())){
        	//3月内未修改密码
        	session.setAttribute("isEditPass", 0);
        }else{
        	//3月内已修改密码
        	session.setAttribute("isEditPass", 1);
        }
        
        zuser.setFailuretimes(0);
        zuser.update();
        
        //登录成功
        ModelAndView mv = new ModelAndView("redirect:"+path_index);
        return mv;
    }

    @RequestMapping(value = "/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        //获取当前用户
        // String account = (String) subject.getPreviousPrincipals().iterator().next();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
           // LOG.info("用户" + account + "退出登录");
        }
    }

    public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
