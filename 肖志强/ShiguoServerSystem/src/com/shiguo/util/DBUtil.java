package com.shiguo.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class DBUtil {

	private static Properties dbProps = new Properties();

	/**
	 * 读取配置文件，加载数据库驱动
	 */
	static {
		try {
			InputStream is = DBUtil.class.getResourceAsStream("/dbinfo.properties");
			dbProps.load(is);
			Class.forName(dbProps.getProperty("db.driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getCon() {
		try {
			return DriverManager.getConnection(dbProps.getProperty("db.connectUrl"), dbProps.getProperty("db.user"),
					dbProps.getProperty("db.pwd"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 关闭数据库连接等对象
	 * 
	 * @param rs
	 * @param pstm
	 * @param con
	 */
	public static void close(ResultSet rs, PreparedStatement pstm, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行增删改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object[] params) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++)
					pstm.setObject(i + 1, params[i]);
			}
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			close(null, pstm, con);
		}
	}

	/**
	 * 根据主键查询单个对象
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	public static Object findById(Class cls,String sql, Object id) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			pstm.setObject(1, id);
			rs = pstm.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			Object obj = cls.newInstance();
			if (rs.next()) {
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					Field field = cls.getDeclaredField(metaData.getColumnLabel(i + 1));
					field.setAccessible(true);
					field.set(obj, rs.getObject(i + 1));
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs, pstm, con);
		}
	}

	/**
	 * 查询某表全部数据
	 * 
	 * @param sql
	 * @return
	 */
	public static List findAll(Class cls,String sql) {
		return find(cls,sql,null);
	}

	/**
	 * 根据条件查询，返回List集合，集合中存储表对应的对象
	 * @param cls
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List find(Class cls, String sql, Object[] params) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++)
					pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			List list = new ArrayList();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				
				Object obj = cls.newInstance();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					Field field = cls.getDeclaredField(metaData.getColumnLabel(i+1));
					field.setAccessible(true);
					field.set(obj, rs.getObject(i + 1));
				}
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs, pstm, con);
		}
	}
	
	/**
	 *  根据条件查询，返回某表单列数据
	 * @param sql
	 * @return
	 */
	public static List findOne(String sql, Object[] params) {
		
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++)
					pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			List list = new ArrayList();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				list.add(rs.getObject(1));
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs, pstm, con);
		}
		
	}
	
	/**
	 *  根据条件查询，返回某表单列数据
	 * @param sql
	 * @return
	 */
	public static String[] find(String sql, Object[] params) {
		
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++)
					pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();	
			ResultSetMetaData metaData = rs.getMetaData();
			
			StringBuffer stringBuffer = new StringBuffer();
			
			while (rs.next()) {
				stringBuffer.append(rs.getObject(1) + ";");
			}
			
			String[] arr = stringBuffer.toString().split(";");
			
			return arr;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs, pstm, con);
		}
		
	}
	
	/**
	 * 得到数据长度
	 * @param sql
	 * @return
	 */
	public static int getCount(String sql) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count=0;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			close(rs, pstm, con);
		}
	}

}
