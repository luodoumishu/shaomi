package com.xjj.oa.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xjj.oa.JDBC.JDBConnection;

public class OA_Dao {
	
	private Connection connection = null; // 定义连接的对象
	private PreparedStatement ps = null; // 定义预准备的对象
	private JDBConnection jdbc = null; // 定义数据库连接对象

	public OA_Dao() {
		jdbc = new JDBConnection();
		connection = jdbc.connection; // 利用构造方法取得数据库连接
	}

	public ResultSet executeQuery(String sql) {
		ResultSet resultSet = null;
		try {
			ps = connection.prepareStatement(sql);
			resultSet = ps.executeQuery();
			ps.close();
			return resultSet;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (resultSet != null)
					resultSet.close();
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}

	public int execute(String sql) {
		int size = 0;
		try {
			ps = connection.prepareStatement(sql);
			size = ps.executeUpdate();
			ps.close();
			return size;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * 查询OA登录表里，是否在登录
	 * 
	 * @author yeyunfeng 2015年5月27日 下午3:21:42
	 * @return
	 * 
	 */
	public boolean existOAuser(String userId,String token) {
		String sql = "select uli.* from UM_LOGIN_INFO uli where uli.USER_ID ='"+userId+"' and uli.TOKEN = '"+token+"'";
		int size = execute(sql);
		if (size > 0) {
			return true;
		}
		return false;
    }
}
