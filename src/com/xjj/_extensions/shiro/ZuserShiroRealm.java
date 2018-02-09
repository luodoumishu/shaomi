package com.xjj._extensions.shiro;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj.framework.core.util.SpringContextUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Zhengyuyang
 * Date: 14-2-11
 */
public class ZuserShiroRealm extends AuthorizingRealm {
	
	private IZuserService zuserService = SpringContextUtil.getInstance().getBean("ZuserService");

//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
	
	/*public ZuserShiroRealm() {
		 
        super();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-512");

        matcher.setHashIterations(1024);
        
        matcher.setStoredCredentialsHexEncoded(false);

        setCredentialsMatcher(matcher);

	} */

	
    protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals) {
        String account = (String) principals.fromRealm(getName()).iterator().next();

        if( account != null ){
            /* AccountManager accountManager = new AccountManagerImpl();
           Collection<Role> myRoles = accountManager.getRoles( username );
            if( myRoles != null ){
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                for( Role each:myRoles ){
                    info.addRole(each.getName());
                    info.addStringPermissions( each.getPermissionsAsString() );
                }

                return info;
            }*/
        	Zuser zuser = new Zuser();
            zuser.setAccount(account);
            List<Zuser> zusers= zuserService.query(-1 , -1, zuser);
            if(zusers!=null && zusers.size()>0){
            	zuser = zusers.get(0);
            }else{
            	throw new UnknownAccountException("未知帐号错误！"); 
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
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("xjjadmin");
            info.addStringPermission("p1");
           // info.addStringPermission("demo:p1");
            return info;
        }

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	//Subject currentUser = SecurityUtils.getSubject();
    	//currentUser.logout();
    	
    	UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String account = token.getUsername();
        
        if(!(account != null && account.length() > 0)){
        	throw new UnknownAccountException("未知帐号错误！"); 
        }
        
        Zuser zuser = new Zuser();
        zuser.setAccount(account);
        List<Zuser> zusers= zuserService.query(-1 , -1, zuser);
        if(zusers!=null && zusers.size()>0){
        	zuser = zusers.get(0);
        	Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute("userName",zuser.getName());
        }else{
        	throw new UnknownAccountException("未知帐号错误！"); 
        }
        
        if( zuser != null ){
        	return new SimpleAuthenticationInfo(zuser.getAccount(),
        			zuser.getPassword(), getName());
        }
        return null;
    }
}
