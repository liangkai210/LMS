//package com.kailiang.lms.utils;
//
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import com.mysql.jdbc.Statement;
//
//@Component
//public class BasicUtil {
//	
//	@Autowired
//	JdbcTemplate template;
//
//	public static <T> List<T> getAll(Connection conn, Class<T> clazz, String sql)
//			throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {
//		PreparedStatement pstmt = null;
//		List<T> results = null;
//		conn = ConnectionManager.getConnection();
//		pstmt = conn.prepareStatement(sql);
//		results = new ArrayList<T>();
//		conn.setAutoCommit(false);
//
//		// get resultSet and RSMD
//		ResultSet rst = pstmt.executeQuery();
//		ResultSetMetaData rsmd = rst.getMetaData();
//		// Deal with RST, moving pointer forward
//		while (rst.next()) {
//			// create new instance as reflection
//			T entity = clazz.newInstance();
//			// get key and value, using reflection to assign values. Also
//			// could
//			// get AS name.
//			for (int i = 0; i < rsmd.getColumnCount(); i++) {
//				String columnLabel = rsmd.getColumnLabel(i + 1);
//				Object columnValue = rst.getObject(i + 1);
//				ReflectionUtils.setFieldValue(entity, columnLabel, columnValue);
//				// BeanUtils.setProperty(entity, columnLabel, columnValue);
//			}
//			results.add(entity);
//		}
//		conn.commit();
//		return results;
//
//	}
//
//	public void common(Connection conn, String sql, Object... args) throws SQLException {
//		template.update(sql, args);
//		template.query(psc, rse)
//		
//	}
//
//	public <T> List<T> extractData(Class<T> cls, ResultSet rs) {
//		List<T> list = new ArrayList<T>();
//		try {
//			ResultSetMetaData rsmd = rs.getMetaData();
//			
//			while (rs.next()) {
//				// create new instance as reflection
//				T entity = cls.newInstance();
//				// get key and value, using reflection to assign values. Also
//				// could
//				// get AS name.
//				for (int i = 0; i < rsmd.getColumnCount(); i++) {
//					String columnLabel = rsmd.getColumnLabel(i + 1);
//					Object columnValue = rs.getObject(i + 1);
//					ReflectionUtils.setFieldValue(entity, columnLabel, columnValue);
//					// BeanUtils.setProperty(entity, columnLabel, columnValue);
//				}
//				list.add(entity);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}		
//		return list;
//	}
//	public <T> List<T> getList(Class<T> clazz, String sql, Object... args)
//			throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
//		template.que;
//		List<T> results = new ArrayList<T>();
//		// get the connection
//		PreparedStatement pstmt = null;
//
//		pstmt = conn.prepareStatement(sql);
//		conn.setAutoCommit(false);
//		// get the preparedStatement
//		for (int i = 0; i < args.length; i++) {
//			pstmt.setObject(i + 1, args[i]);
//		}
//		// get resultSet and RSMD
//		ResultSet rst = pstmt.executeQuery();
//		ResultSetMetaData rsmd = rst.getMetaData();
//		// Deal with RST, moving pointer forward
//		while (rst.next()) {
//			// create new instance as reflection
//			T entity = clazz.newInstance();
//			// get key and value, using reflection to assign values. Also
//			// could
//			// get AS name.
//			for (int i = 0; i < rsmd.getColumnCount(); i++) {
//				String columnLabel = rsmd.getColumnLabel(i + 1);
//				Object columnValue = rst.getObject(i + 1);
//				ReflectionUtils.setFieldValue(entity, columnLabel, columnValue);
//				// BeanUtils.setProperty(entity, columnLabel, columnValue);
//			}
//			results.add(entity);
//		}
//		conn.commit();
//		return results;
//	}
//
//	public static Matcher isMatch(String s) {
//		String pattern = "(\\d+)";
//		Pattern pat = Pattern.compile(pattern);
//		return pat.matcher(s);
//	}
//
//	public static <T> T getSingle(Connection conn, Class<T> clazz, String sql, Object... args) {
//		T entity = null;
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rst = null;
//		try {
//			// 1. get the resultSet object
//			connection = ConnectionManager.getConnection();
//			pstmt = connection.prepareStatement(sql);
//			for (int i = 0; i < args.length; i++) {
//				pstmt.setObject(i + 1, args[i]);
//			}
//			rst = pstmt.executeQuery();
//			// 2. get resultSetMetaData object
//			ResultSetMetaData rsmd = rst.getMetaData();
//			// 3. make a Map<String(SQL column AS name), Object(value)>
//			// 4. fill the values
//			while (rst.next()) {
//				// create new instance as reflection
//				entity = clazz.newInstance();
//				// get key and value, using reflection to assign values. Also
//				// could
//				// get AS name.
//				for (int i = 0; i < rsmd.getColumnCount(); i++) {
//					String columnLabel = rsmd.getColumnLabel(i + 1);
//					Object columnValue = rst.getObject(i + 1);
//					ReflectionUtils.setFieldValue(entity, columnLabel, columnValue);
//					// BeanUtils.setProperty(entity, columnLabel, columnValue);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return entity;
//	}
//
//}
