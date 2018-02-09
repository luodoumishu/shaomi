package com.xjj._extensions.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

//import com.xjj.shiro.Utils.EncryptUtils;
//import com.xjj.shiro.model.User;


public class MonitorRealm extends AuthorizingRealm {
	/*
	 * @Autowired UserService userService;
	 * 
	 * @Autowired RoleService roleService;
	 * 
	 * @Autowired LoginLogService loginLogService;
	 */

	public MonitorRealm() {
		super();

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		/* 这里编写授权代码 */
		Set<String> roleNames = new HashSet<String>();
	    Set<String> permissions = new HashSet<String>();
	    roleNames.add("admin");
	    permissions.add("user.do?myjsp");
	    permissions.add("login.do?main");
	    permissions.add("login.do?logout");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
	    info.setStringPermissions(permissions);
		return info;

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		/* 这里编写认证代码 UsernamePasswordToken用来将从Java应用程序通过某种方式获取到的用户名和密码绑定到一起。*/
		//UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//		User user = securityApplication.findby(upToken.getUsername());
		//User user = new User();
		//user.setUsercode(token.getUsername());
		//user.setUserName("admin");
		//user.setPassword("");
        //String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
        //String passwordHash = new Sha512Hash("admin", salt).toBase64();
//		if (user != null) {
		//return new SimpleAuthenticationInfo(user.getUserName(),
				//user.getPassword(), getName());

		return null;
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

}
