package com.fbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fbb.bean.ProxyBean;

public class ProxyDao {
	public static final String sql = "select * from ip limit 0, 20";
	public static final String sql_add = "insert into ip values (null, ?, ?, ?, ?, ?, ?, now(), ?) ";

	public static ArrayList<ProxyBean> getAllProxys() {
		ArrayList<ProxyBean> result = new ArrayList<ProxyBean>();
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, sql);
		try {
			while (resultSet.next()) {
				ProxyBean proxyIpBean = new ProxyBean();
				proxyIpBean.fromResultset(resultSet);
				result.add(proxyIpBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(resultSet);
		}
		return result;
	}

	public static boolean addProxy(ProxyBean bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection();
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				pstmt.setString(1, bean.getIp());
				pstmt.setString(2, bean.getPort());
				pstmt.setInt(3, bean.getNiming());
				pstmt.setString(4, bean.getType());
				pstmt.setString(5, bean.getArea());
				pstmt.setLong(6, bean.getSpeed());
				pstmt.setDate(7, bean.getVertifytime());
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DB.close(conn);
				DB.close(pstmt);
			}

		}
		return result;
	}

	public static boolean addProxy(Connection conn, ProxyBean bean) {
		boolean result = false;
		if (bean != null) {
			if(isHave(conn, bean)){
				return true;
			}
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				pstmt.setString(1, bean.getIp());
				pstmt.setString(2, bean.getPort());
				pstmt.setInt(3, bean.getNiming());
				pstmt.setString(4, bean.getType());
				pstmt.setString(5, bean.getArea());
				pstmt.setLong(6, bean.getSpeed());
				pstmt.setDate(7, bean.getVertifytime());
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DB.close(pstmt);
			}
		}
		return result;
	}
	
	public static boolean isHave(Connection conn, ProxyBean bean) {
		boolean result = false;
		if (bean != null) {
			Statement stmt = DB.createStmt(conn);
			ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from ip where ip = '"+bean.getIp()+"'");
			try {
				resultSet.next();
				int int1 = resultSet.getInt(1);
				result = int1 > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean addProxy(ArrayList<ProxyBean> beans) {
		boolean result = false;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection("");
			for (int i = 0; i < beans.size(); i++) {
				ProxyBean bean = beans.get(i);
				boolean addProxy = addProxy(conn, bean);
				if (addProxy) {
					continue;
				} else {
					return false;
				}
			}
			DB.close(conn);
			result = true;
		}
		return result;
	}
	
	public static int getCount() {
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from ip");
		try {
			resultSet.next();
			int result = resultSet.getInt(1);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(resultSet);
		}
		return -1;
	}
}
