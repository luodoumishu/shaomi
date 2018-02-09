<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ page import="com.xjj.framework.core.util.SpringContextUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xjj.cms.menu.service.impl.CmsMenuService" %>
<%@ page import="com.xjj.cms.menu.model.CmsMenu" %>
<%@ page import="com.xjj.framework.core.page.Page" %>
<%@ page import="com.xjj.cms.menu.service.impl.CmsMenuUserService" %>
<%@ page import="com.xjj._extensions.roleUserPer.service.impl.ZuserService" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.xjj.cms.menu.model.CmsMenuUser" %>
<%

	CmsMenuService menuService =(CmsMenuService) SpringContextUtil.getInstance().getBean("cmsMenuService");
	CmsMenuUserService menuUserService =(CmsMenuUserService) SpringContextUtil.getInstance().getBean("cmsMenuUserService");
	ZuserService zuserService = (ZuserService)SpringContextUtil.getInstance().getBean("ZuserService");

    int count = 0;

	/***********镇、乡级别的栏目分级授权 start yeyunfeng****************/
	String z_menu_hql = " and parentMenuName ='镇'";
	String z_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND (ZO.\"NAME\" LIKE '%镇' or ZO.\"NAME\" LIKE '%乡')";

	Page<CmsMenu> z_menus = menuService.page(z_menu_hql,-1,-1,null);
	List<CmsMenu> z_menuList = z_menus.getItems();
	List<Map> z_user_list = new ArrayList();
	try {
		z_user_list = zuserService.findBysql(z_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= z_menuList && null != z_user_list){
		int m_size = z_menuList.size();
		int u_size = z_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = z_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = z_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("镇权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********镇、乡级别的栏目分级授权 eng yeyunfeng****************/

/***********农场级别的栏目分级授权 start yeyunfeng****************/
	String n_menu_hql = " and parentMenuName ='农场'";
	String n_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND ZO.\"NAME\" LIKE '%农场'";

	Page<CmsMenu> n_menus = menuService.page(n_menu_hql,-1,-1,null);
	List<CmsMenu> n_menuList = n_menus.getItems();
	List<Map> n_user_list = new ArrayList();
	try {
		n_user_list = zuserService.findBysql(n_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= n_menuList && null != n_user_list){
		int m_size = n_menuList.size();
		int u_size = n_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = n_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = n_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("农场权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********农场级别的栏目分级授权 eng yeyunfeng****************/


/***********村级别的栏目分级授权 start yeyunfeng****************/
	String c_menu_hql = " and parentMenuName ='村'";
	String c_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND (ZO.\"NAME\" LIKE '%村' or ZO.\"NAME\" = '新兴' or ZO.\"NAME\" = '龙坡' or ZO.\"NAME\" = '和舍')";

	Page<CmsMenu> c_menus = menuService.page(c_menu_hql,-1,-1,null);
	List<CmsMenu> c_menuList = c_menus.getItems();
	List<Map> c_user_list = new ArrayList();
	try {
		c_user_list = zuserService.findBysql(c_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= c_menuList && null != c_user_list){
		int m_size = c_menuList.size();
		int u_size = c_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = c_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = c_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("村权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********村级别的栏目分级授权 eng yeyunfeng****************/

/***********社区级别的栏目分级授权 start yeyunfeng****************/
	String s_menu_hql = " and parentMenuName ='社区'";
	String s_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND ZO.\"NAME\" LIKE '%社区'";

	Page<CmsMenu> s_menus = menuService.page(s_menu_hql,-1,-1,null);
	List<CmsMenu> s_menuList = s_menus.getItems();
	List<Map> s_user_list = new ArrayList();
	try {
		s_user_list = zuserService.findBysql(s_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= s_menuList && null != s_user_list){
		int m_size = s_menuList.size();
		int u_size = s_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = s_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = s_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("社区权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********社区级别的栏目分级授权 eng yeyunfeng****************/

/***********机关、局级别的栏目分级授权 start yeyunfeng****************/
	String j_menu_hql = " and parentMenuName ='机关'";
	String j_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND (ZO.\"NAME\" LIKE '%局' or ZO.\"NAME\" LIKE '%党委')";

	Page<CmsMenu> j_menus = menuService.page(j_menu_hql,-1,-1,null);
	List<CmsMenu> j_menuList = j_menus.getItems();
	List<Map> j_user_list = new ArrayList();
	try {
		j_user_list = zuserService.findBysql(j_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= j_menuList && null != j_user_list){
		int m_size = j_menuList.size();
		int u_size = j_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = j_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = j_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("机关权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********机、局关级别的栏目分级授权 eng yeyunfeng****************/


/***********居委会级别的栏目分级授权 start yeyunfeng****************/
	String jwh_menu_hql = " and parentMenuName ='居委会'";
	String jwh_user_sql = "SELECT ZU.\"ID\" u_id,ZU.\"NAME\" u_name from Z_USER_T ZU,  Z_ORGANIZE_T ZO,Z_ORG_USER_T ZOU where ZOU.ORGID = ZO.\"ID\" and ZOU.USERID = ZU.\"ID\" AND ZO.\"NAME\" LIKE '%居委会'";

	Page<CmsMenu> jwh_menus = menuService.page(jwh_menu_hql,-1,-1,null);
	List<CmsMenu> jwh_menuList = jwh_menus.getItems();
	List<Map> jwh_user_list = new ArrayList();
	try {
		jwh_user_list = zuserService.findBysql(jwh_user_sql);
	}catch (Exception e){
		e.printStackTrace();
	}

	if (null!= jwh_menuList && null != jwh_user_list){
		int m_size = jwh_menuList.size();
		int u_size = jwh_user_list.size();
		if (m_size>0 && u_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = jwh_menuList.get(i);
				for (int j =0;j<u_size;j++){
					Map userMap = jwh_user_list.get(j);
					if (null != menu && null != userMap){
						String userId = userMap.get("u_id").toString();
						String userName = userMap.get("u_name").toString();
						CmsMenuUser menuUser = new CmsMenuUser();
						menuUser.setUserName(userName);
						menuUser.setUserId(userId);
						menuUser.setMenuId(menu.getId());
						menuUser.setMenuName(menu.getMenuName());
						menuUserService.save(menuUser);
						count++;
						System.out.println("居委会权限配置批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********居委会级别的栏目分级授权 eng yeyunfeng****************/


%>
