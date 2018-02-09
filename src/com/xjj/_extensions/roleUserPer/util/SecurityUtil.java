package com.xjj._extensions.roleUserPer.util;

import com.xjj._extensions.roleUserPer.model.Zrole;
import com.xjj._extensions.roleUserPer.model.Zuser;
import com.xjj.framework.core.web.filter.WebContext;


public class SecurityUtil {
	
	private static SecurityUtil inst;
	public static SecurityUtil getInst() {
		if (inst == null) {
			return new SecurityUtil();
		} else {
			return inst;
		}
	}
	
	public boolean isPermitted(String userId,String permission){
		if(userId==null || userId.equals("") || permission==null || permission.equals("")){
			return false;
		}
		Zuser zuser = new Zuser();
		zuser = zuser.get(userId);
		if(zuser!=null && zuser.getRoles()!=null && zuser.getRoles().size()>0){
			for(Zrole zrole: zuser.getRoles()){
				if(zrole.getIsValid()==1 && zrole.getPermissions()!=null){
					String[] permissions = zrole.getPermissions().split(",");
					if(permissions!=null && permissions.length>0){
						for(String str:permissions){
							if(permission.equals(str)){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isPermitted(String permission){
		String userId = WebContext.getInstance().getHandle().getUserId();
		if(userId==null || userId.equals("") || permission==null || permission.equals("")){
			return false;
		}
		Zuser zuser = new Zuser();
		zuser = zuser.get(userId);
		if(zuser!=null && zuser.getRoles()!=null && zuser.getRoles().size()>0){
			for(Zrole zrole: zuser.getRoles()){
				if(zrole.getIsValid()==1 && zrole.getPermissions()!=null){
					String[] permissions = zrole.getPermissions().split(",");
					if(permissions!=null && permissions.length>0){
						for(String str:permissions){
							if(permission.equals(str)){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
}
