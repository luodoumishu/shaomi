<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ page import="com.xjj.framework.core.util.SpringContextUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xjj.cms.menu.service.impl.CmsMenuService" %>
<%@ page import="com.xjj.cms.menu.model.CmsMenu" %>
<%@ page import="com.xjj.framework.core.page.Page" %>
<%@ page import="com.xjj._extensions.roleUserPer.service.impl.ZorganizeService" %>
<%@ page import="com.xjj._extensions.roleUserPer.model.Zorganize" %>
<%@ page import="com.xjj.cms.menu.model.CmsMenuOrg" %>
<%@ page import="com.xjj.cms.menu.service.impl.CmsMenuOrgService" %>
<%

	CmsMenuService menuService =(CmsMenuService) SpringContextUtil.getInstance().getBean("cmsMenuService");
	CmsMenuOrgService menuOrgService =(CmsMenuOrgService) SpringContextUtil.getInstance().getBean("cmsMenuOrgService");
	ZorganizeService zorganizeService = (ZorganizeService)SpringContextUtil.getInstance().getBean("ZorganizeService");

   int count = 0;

	/***********镇、乡级别的栏目分级授权 start yeyunfeng****************/
	String z_menu_hql = " and parentMenuName ='镇'";
	String z_org_hql = " and name like '%镇' or name like '%乡'";

	Page<CmsMenu> z_menus = menuService.page(z_menu_hql,-1,-1,null);
	List<CmsMenu> z_menuList = z_menus.getItems();

	Page<Zorganize> z_orgs = zorganizeService.page(z_org_hql,-1,-1,null);
	List<Zorganize> z_orgList = z_orgs.getItems();

	if (null!= z_menuList && null != z_orgList){
		int m_size = z_menuList.size();
		int o_size = z_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = z_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = z_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
						count++;
						System.out.println("镇、乡栏目分级批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********镇、乡级别的栏目分级授权 eng yeyunfeng****************/



/***********农场级别的栏目分级授权 start yeyunfeng****************/
	String n_menu_hql = " and parentMenuName ='农场'";
	String n_org_hql = " and name like '%农场'";

	Page<CmsMenu> n_menus = menuService.page(n_menu_hql,-1,-1,null);
	List<CmsMenu> n_menuList = n_menus.getItems();

	Page<Zorganize> n_orgs = zorganizeService.page(n_org_hql,-1,-1,null);
	List<Zorganize> n_orgList = n_orgs.getItems();

	if (null!= n_menuList && null != n_orgList){
		int m_size = n_menuList.size();
		int o_size = n_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = n_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = n_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
							count++;
						System.out.println("农场栏目分级批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********农场级别的栏目分级授权 eng yeyunfeng****************/

/***********村级别的栏目分级授权 start yeyunfeng****************/
	String c_menu_hql = " and parentMenuName ='村'";
	String c_org_hql = " and name like '%村' or name = '新兴' or name = '龙坡' or name = '和舍'";

	Page<CmsMenu> c_menus = menuService.page(c_menu_hql,-1,-1,null);
	List<CmsMenu> c_menuList = c_menus.getItems();

	Page<Zorganize> c_orgs = zorganizeService.page(c_org_hql,-1,-1,null);
	List<Zorganize> c_orgList = c_orgs.getItems();

	if (null!= c_menuList && null != c_orgList){
		int m_size = c_menuList.size();
		int o_size = c_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = c_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = c_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
							count++;
						System.out.println("村栏目分级批量入库第"+count+"条");
					}
				}


			}
		}

	}
/***********村级别的栏目分级授权 eng yeyunfeng****************/

/***********社区级别的栏目分级授权 start yeyunfeng****************/
	String s_menu_hql = " and parentMenuName ='社区'";
	String s_org_hql = " and name like '%社区'";

	Page<CmsMenu> s_menus = menuService.page(s_menu_hql,-1,-1,null);
	List<CmsMenu> s_menuList = s_menus.getItems();

	Page<Zorganize> s_orgs = zorganizeService.page(s_org_hql,-1,-1,null);
	List<Zorganize> s_orgList = s_orgs.getItems();

	if (null!= s_menuList && null != s_orgList){
		int m_size = s_menuList.size();
		int o_size = s_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = s_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = s_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
						count++;
						System.out.println("社区栏目分级批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********社区级别的栏目分级授权 eng yeyunfeng****************/



/***********机、局关级别的栏目分级授权 start yeyunfeng****************/
	String j_menu_hql = " and parentMenuName ='机关'";
	String j_org_hql = " and name like '%局' or name = '县直机关' or name = '%党委'";

	Page<CmsMenu> j_menus = menuService.page(j_menu_hql,-1,-1,null);
	List<CmsMenu> j_menuList = j_menus.getItems();

	Page<Zorganize> j_orgs = zorganizeService.page(j_org_hql,-1,-1,null);
	List<Zorganize> j_orgList = j_orgs.getItems();

	if (null!= j_menuList && null != j_orgList){
		int m_size = j_menuList.size();
		int o_size = j_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = j_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = j_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
						count++;
						System.out.println("机关、局栏目分级批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********机、局关级别的栏目分级授权 eng yeyunfeng****************/

/***********居委会级别的栏目分级授权 start yeyunfeng****************/
	String jwh_menu_hql = " and parentMenuName ='居委会'";
	String jwh_org_hql = " and name like '%居委会'";

	Page<CmsMenu> jwh_menus = menuService.page(jwh_menu_hql,-1,-1,null);
	List<CmsMenu> jwh_menuList = jwh_menus.getItems();

	Page<Zorganize> jwh_orgs = zorganizeService.page(jwh_org_hql,-1,-1,null);
	List<Zorganize> jwh_orgList = jwh_orgs.getItems();

	if (null!= jwh_menuList && null != jwh_orgList){
		int m_size = jwh_menuList.size();
		int o_size = jwh_orgList.size();
		if (m_size>0 && o_size>0){
			for (int i =0;i<m_size;i++){
				CmsMenu menu = jwh_menuList.get(i);
				for (int j =0;j<o_size;j++){
					Zorganize org = jwh_orgList.get(j);
					if (null != menu && null != org){
						CmsMenuOrg menuOrg = new CmsMenuOrg();
						menuOrg.setMenu(menu);
						menuOrg.setOrg(org);
						menuOrgService.save(menuOrg);
						count++;
						System.out.println("居委会栏目分级批量入库第"+count+"条");
					}
				}
			}
		}

	}
/***********机、局关级别的栏目分级授权 eng yeyunfeng****************/


%>
