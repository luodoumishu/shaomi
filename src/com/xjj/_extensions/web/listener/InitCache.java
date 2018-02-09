package com.xjj._extensions.web.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import com.xjj._extensions.roleUserPer.model.Menu;
import com.xjj._extensions.roleUserPer.util.MenuUtil;
import com.xjj._extensions.roleUserPer.util.PermissionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 第一步：生成CacheManager对象 第二步：生成Cache对象 第三步：向Cache对象里添加由key,value组成的键值对的Element元素
 * 
 */
public class InitCache implements ServletContextListener {
    private static Logger LOG = LoggerFactory.getLogger(InitCache.class);
	private static InitCache inst;
	public static InitCache getInst() {
		if (inst == null) {
			return new InitCache();
		} else {
			return inst;
		}
	}
	public  List<Map<String, String>> getPermissionSelectMap(){
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("initCache");
		//从cache中取得元素
		Element element = cache.get("permissionSelect");
		//取得元素的值
		//System.out.println(element.getValue());
		return (List<Map<String, String>>) element.getValue();
	}
	public  List<Map<String, String>> getPermissionSelectMap(String org){
		Map<String, String> map = PermissionUtil.getInst().getPermissionSelectMap(org);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("id", entry.getKey());
			map2.put("name", entry.getValue());
			list.add(map2);
		}
		return list;
	}
	public  List<HashMap<String, String>> getPermissionAppMap(String appCode){
		Map<String, String> map = PermissionUtil.getInst().getPermissionAppMap(appCode);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("id", entry.getKey());
			map2.put("name", entry.getValue());
			list.add(map2);
		}
		return list;
	}
	public List<Menu> getMenuList(){
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("initCache");
		//从cache中取得元素
		Element element = cache.get("menuList");
		//取得元素的值
		System.out.println(element.getValue());
		return (List<Menu>) element.getValue();
	}
	//EHCache
	public void initCache() {
		// 创建CacheManager
		CacheManager singletonManager = CacheManager.create();
		//设置一个名为initCache 的新cache,initCache属性为默认
		singletonManager.addCache("initCache");
		//生成一个Cache对象 
		Cache cache = singletonManager.getCache("initCache");   
		setPermissionSelect(cache);	//设置权限,下拉显示
		setMenuList(cache);	//设置菜单栏目列表
		// 向Cache对象里添加Element元素，Element元素有key，value键值对组成
		//cache.put(new Element("key", "value"));
		//从cache中取得元素
		//Element element = cache.get("permissionSelect");
		//取得元素的值
		//System.out.println(element.getValue());
		//Object obj = element.getObjectValue();
		//System.out.println((String) obj);
		//关闭singletonManager
		//singletonManager.shutdown();
		//从Cache中移除一个元素
		//cache.remove("key");
		// 使用manager移除指定名称的Cache对象
		//manager.removeCache("initCache");
	}
	//设置权限,下拉显示
	public void setPermissionSelect(Cache cache){
		Map<String, String> map = PermissionUtil.getInst().getPermissionSelectMap();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("id", entry.getKey());
			map2.put("name", entry.getValue());
			list.add(map2);
		}
		Element element = new Element("permissionSelect", list);
		cache.put(element);
	}
	//设置菜单栏目列表
	public void setMenuList(Cache cache){
		List<Menu> menuList = MenuUtil.getInst().getMenuList();
		for(int i=0;i<menuList.size();i++){
			  Menu m = menuList.get(i); 
			  //java.net.URLDecoder.decode(m.getMenuName(),"UTF-8")
			  System.out.println(m.getMenu()+","+m.getMenuName()+","+m.getCodeStr());
		  }
		Element element = new Element("menuList", menuList);
		cache.put(element);
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
        //initCache();
	}

}