package com.fbb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fbb.util.LogUtil;

public class DB {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "a123456";
	static final String DBNAME = "shares_filter";

	static Connection conn = null;
	Statement stmt = null;

	public static Connection getConnection(String dbName) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false", "root" , PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LogUtil.e("getConnection异常 ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("getConnection异常 SQLException "+e.getMessage());
		}

		return connection;
	}
	
	public static Connection getConnection() {
		return getConnection(DBNAME);
	}
	

	public static Statement createStmt(Connection conn){
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("createStmt异常 "+e.getMessage());
		}
		return statement;
	}
	
	public static PreparedStatement prepareStmt(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("prepareStmt异常 "+e.getMessage()+" sql:"+sql);
		}
		return pstmt;
	}
	
	public static ResultSet executeQuery(Statement stmt, String sql){
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("executeQuery异常  "+e.getMessage()+" sql:"+sql);
		}
		return resultSet;
	}
	
	public static int executeUpdate(Connection conn, String sql) {
		int ret = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ret = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("executeUpdate异常  "+e.getMessage()+" sql:"+sql);
		} finally {
			close(stmt);
		}
		return ret;
	}
	
	public static boolean executeSql(Connection conn, String sql) {
		boolean ret = false;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ret = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("executeSql异常  "+e.getMessage()+" sql:"+sql);
		} finally {
			close(stmt);
		}
		return ret;
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("close Connection 异常"+e.getMessage());
			}
			conn = null;
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("close Statement 异常"+e.getMessage());
			}
			stmt = null;
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("close ResultSet 异常"+e.getMessage());
			}
			rs = null;
		}
	}
}
