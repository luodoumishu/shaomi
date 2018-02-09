package com.xjj._extensions.roleUserPer.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xjj._extensions.cons.AppConstant;
import com.xjj._extensions.web.listener.InitCache;
import com.xjj.framework.multitenant.ds.TenantAppMapUtil;
import org.dtools.ini.BasicIniFile;
import org.dtools.ini.FormatException;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniSection;

public class PermissionUtil {

    private static PermissionUtil inst;

    public static PermissionUtil getInst() {
        if (inst == null) {
            return new PermissionUtil();
        } else {
            return inst;
        }
    }

    public Map<String, String> getPermissionSelectMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        //String path = "E:\\Workspaces\\MyEclipse 10\\ggyypt\\src\\permission.ini";
        String a = this.getClass().getResource("/").getPath() + "permission.ini";
        String path = "";
        try {
            path = java.net.URLDecoder.decode(a, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        System.out.println("path=" + path);
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            IniFile ini = new BasicIniFile();
            IniFileReader reader = new IniFileReader(ini, file);
            try {
                reader.read();
            } catch (FormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < ini.getNumberOfSections(); i++) {
                IniSection section = ini.getSection(i);
                for (int j = 0; j < section.getNumberOfItems(); j++) {
                    String pCode = section.getItem(j).getName();
                    String pName = section.getName() + "|" + section.getItem(j).getValue().split("@")[0];
                    map.put(pCode, pName);
                }
            }

        }
//        System.out.println("通过Map.entrySet遍历key和value");
//		  for (Map.Entry<String,String> entry : map.entrySet()) {
//		   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//		  }
        return map;
    }

    public Map<String, String> getPermissionSelectMap(String org) {
//		Set<String> apps = TenantAppMapUtil.INSTANCE.getMap().get(org);
        Map<String, String> map = new LinkedHashMap<String, String>();
        String a = this.getClass().getResource("/").getPath() + "permission.ini";
        String path = "";
        try {
            path = java.net.URLDecoder.decode(a, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            IniFile ini = new BasicIniFile();
            IniFileReader reader = new IniFileReader(ini, file);
            try {
                reader.read();
            } catch (FormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < ini.getNumberOfSections(); i++) {
                IniSection section = ini.getSection(i);
                for (int j = 0; j < section.getNumberOfItems(); j++) {
                    String module = section.getName();
                    String[] mArr = module.split("@");
                    if (mArr.length > 1) {
//            			if(apps!=null&&apps.size()>0){
                        boolean exist = TenantAppMapUtil.INSTANCE.hasAuthority(org, mArr[1]);
                			/*for(int z=0;z<apps.size();z++){
                                if(mArr[1].length()>0&&apps.get(z).equals(mArr[1])){
                					exist = true;
                				}
                			}*/
                        if (exist) {
                            String pCode = section.getItem(j).getName();
                            String pName = mArr[0] + "|" + section.getItem(j).getValue().split("@")[0];
                            map.put(pCode, pName);
                        }
//            			}
                    } else {
                        String pCode = section.getItem(j).getName();
                        String pName = mArr[0] + "|" + section.getItem(j).getValue().split("@")[0];
                        map.put(pCode, pName);
                    }

                }
            }

        }
        return map;
    }
	public  Map<String,String> getPermissionAppMap(String appCode) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		String a = this.getClass().getResource("/").getPath()+ "permission.ini";
		String path="";
		try {
			path = java.net.URLDecoder.decode(a,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        File file = new File(path);
        if(file.isFile()&&file.exists()){
        	IniFile ini = new BasicIniFile();
            IniFileReader reader = new IniFileReader( ini, file );
            try {
                reader.read();
            }
            catch( FormatException e ) {
                e.printStackTrace();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
            for (int i = 0; i < ini.getNumberOfSections(); i++) {
            	IniSection section = ini.getSection(i);
            	for(int j =0;j<section.getNumberOfItems();j++){
            		String module = section.getName();
            		String[] mArr = module.split("@");
            		if(mArr.length>1){
        				if(mArr[1].length()>0&&appCode.equals(mArr[1])){
        					String pCode = section.getItem(j).getName();
                            String pName = mArr[0]+"|"+section.getItem(j).getValue().split("@")[0];
                            map.put(pCode, pName);
        				}
            		}else{
            			String pCode = section.getItem(j).getName();
                        String pName = mArr[0]+"|"+section.getItem(j).getValue().split("@")[0];
                        map.put(pCode, pName);
            		}
            		
            	}
    		}
            
        }
		return map;
	}
	public List<Map<String, String>> getModuleList(){
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		String a = this.getClass().getResource("/").getPath()+ "permission.ini";
		String path="";
		try {
			path = java.net.URLDecoder.decode(a,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        File file = new File(path);
        if(file.isFile()&&file.exists()){
        	IniFile ini = new BasicIniFile();
            IniFileReader reader = new IniFileReader( ini, file );
            try {
                reader.read();
            }
            catch( FormatException e ) {
                e.printStackTrace();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
            for (int i = 0; i < ini.getNumberOfSections(); i++) {
            	IniSection section = ini.getSection(i);
            	for(int j =0;j<section.getNumberOfItems();j++){
            		String module = section.getName();
            		String[] mArr = module.split("@");
            		if(mArr.length>1){
        				map.put(mArr[1], mArr[0]);
        	        }
            	}
    		}
            
        }
        Set<String> keSet=map.keySet();  
        for (Iterator<String> iterator = keSet.iterator(); iterator.hasNext();) {  
            String key = iterator.next();   
            Map<String, String> mapNew = new HashMap<String, String>();
            mapNew.put("id", key);
            mapNew.put("name", map.get(key));
            maps.add(mapNew);
        }
		return maps;
	}
}
