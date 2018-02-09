package com.xjj.cms.base.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.Assert;

/**
 * 读取配置文件的工具类
 * <p>
 * @author yeyunfeng 2013-11-23 上午11:11:58 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2013-11-23
 * @modify by reason:{方法名}:{原因}
 */
public class PropertiesUtil {
	
	/**
	 * 读取指定配置文件下所有属性
	 * @author yeyunfeng 2013-11-23  上午11:12:28
	 * @param fileDir  配置文件相对路径
	 * @return
	 * @throws IOException
	 *
	 */
	public static Map<String, String> getProperties(String fileDir) throws IOException {
		Assert.hasText(fileDir, "fileDir connot be null !");
		Properties properties = loadProperties(fileDir);
		String[] fullKeys = getKeys(properties);
		return getProperties(properties, fullKeys);
	}
	
	/**
	 * 读取指定配置文件下的指定属性
	 * @author yeyunfeng 2013-11-23  上午11:12:54
	 * @param fileDir 配置文件相对路径
	 * @param keys 属性名集合
	 * @return
	 * @throws IOException
	 *
	 */
	public static Map<String, String> getProperties(String fileDir, String... keys) throws IOException {
		Assert.hasText(fileDir, "fileDir connot be null !");
		Assert.notEmpty(keys, "keys connot be empty !");
		Properties properties = loadProperties(fileDir);
		return getProperties(properties, keys);
	}
	
	/**
	 * 读取指定配置文件的单个属性
	 * @author yeyunfeng 2013-11-23  上午11:13:16
	 * @param fileDir 配置文件相对路径
	 * @param key 属性名
	 * @return
	 * @throws IOException
	 *
	 */
	public static String getProperty(String fileDir, String key) throws IOException {
		Assert.hasText(fileDir, "fileDir connot be null !");
		Assert.hasText(key, "key connot be null !");
		Properties properties = loadProperties(fileDir);
		return getProperty(properties, key);
	}
	
	/**
	 * 获得指定配置文件的所有属性名
	 * @author yeyunfeng 2013-11-23  上午11:13:37
	 * @param fileDir 配置文件相对路径
	 * @return
	 * @throws IOException
	 *
	 */
	public static String[] getKeys(String fileDir) throws IOException {
		Assert.hasText(fileDir, "fileDir connot be null !");
		Properties properties = loadProperties(fileDir);
		return getKeys(properties);
	}

	/**
	 * 加载配置文件到Properties
	 * @author yeyunfeng 2013-11-23  上午11:13:51
	 * @param fileDir 配置文件相对路径
	 * @return
	 * @throws IOException
	 *
	 */
	private static Properties loadProperties(String fileDir) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = PropertiesUtil.class.getResourceAsStream(fileDir);
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}
	
	private static String[] getKeys(Properties properties) {
		return properties.keySet().toArray(new String[] {});
	}
	
	private static String getProperty(Properties properties, String key) {
		return (String) properties.get(key);
	}
	
	private static Map<String, String> getProperties(Properties properties, String... keys) {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		for (String key : keys) {
			propertiesMap.put(key, getProperty(properties, key));
		}
		return propertiesMap;
	}
	
	
	public static String getProperty4Rlod(String fileDir,String key)throws Exception{
		String value = null;
		URL url= PropertiesUtil.class.getResource(fileDir);
		String path = url.getPath();
		path = path.replace("%20", " ");
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(path);
		//InputStream inputStream = PropertiesUtil.class.getResourceAsStream(fileDir);
		properties.load(inputStream);
		value = properties.getProperty(key);
		inputStream.close();
		return value;
	}
	
	/**
	 * 设置文件的key的value值
	 * @author yeyunfeng 2013-11-23  上午11:15:11
	 * @param fileDir
	 * @param key
	 * @param value
	 * @throws Exception
	 *
	 */
	public static void setPropertie(String fileDir,String key,String value)throws Exception{
		URL url= PropertiesUtil.class.getResource(fileDir);
		String path = url.getPath();
		path = path.replace("%20", " ");
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(path);
		//InputStream inputStream = PropertiesUtil.class.getResourceAsStream(fileDir);
		properties.load(inputStream);
		properties.setProperty(key, value);
		FileOutputStream fos = new FileOutputStream(path);
		properties.store(fos,null);
		fos.close();
		inputStream.close();
	}
	
}
