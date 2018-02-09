package com.xjj.oa.JDBC;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * jdbc链接类
 * <p>
 * @author yeyunfeng 2015年5月27日 下午3:08:11 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年5月27日
 * @modify by reason:{方法名}:{原因}
 */
public class JDBConnection {
	
	public Connection connection = null;

	public JDBConnection() {
		ResourceBundle bundle = ResourceBundle.getBundle("OA_JDBC");
		String driver = bundle.getString("driver_class");
		String url = bundle.getString("url");
		String user = bundle.getString("userName");
		String password = bundle.getString("password");
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			System.out.println("数据库加载失败");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}