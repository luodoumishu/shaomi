package com.xjj.ws.sso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil {
	
	/**
	 * 通过文件名获取 Properties
	 * @param file
	 * @return
	 */
	public static Properties getProperties(String file) {
		Properties prop = new Properties();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(getFilepath(file));
			prop.load(fin);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Load Properties file <" + file + "> Error!");
		} finally {
			if(fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String getFilepath(String file) {
		return PropertiesUtil.class.getClassLoader().getResource(file).getPath();
	}
	
	
	/**
	 * 
	 * @param file
	 * @param key
	 * @return
	 */
	public static String getProperty(String file, String key) {
		return getProperties(file).getProperty(key);
	}
	
	/**
	 * 
	 * @param file
	 * @param key
	 * @param value
	 */
	public static void setProperty(String file, String key, String value, String comments) {
		Properties prop = getProperties(file);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(getFilepath(file));
			prop.setProperty(key, value);
			prop.store(fos, comments);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param file
	 */
	public static void getAllString(String file) {
		Properties prop = getProperties(file);
		Enumeration<?> en = prop.propertyNames();
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			String value = prop.getProperty(key);
			System.out.println(key + "=" + value);
		}
	}

}
