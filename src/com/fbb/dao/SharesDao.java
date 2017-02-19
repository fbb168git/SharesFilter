package com.fbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fbb.bean.Shares;
import com.fbb.util.LogUtil;

public class SharesDao {
	public static final String TABLE_NAME = "shares";
	public static final String sql_add = "replace into "+TABLE_NAME+" values (?,?,now())";

	public Shares getSharesByCode(String code) {
		Shares result = null;
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select * from "+TABLE_NAME+" where code = "+code);
		try {
			if (resultSet.next()) {
				result = fromResultset(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("select表"+TABLE_NAME+"异常");
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(resultSet);
		}
		return result;
	}
	
	public ArrayList<Shares> getAllSharesList(){
		ArrayList<Shares> result = new ArrayList<Shares>();;
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select * from "+TABLE_NAME);
		try {
			while (resultSet.next()) {
				Shares shares = fromResultset(resultSet);
				result.add(shares);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("获取shares列表异常");
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(resultSet);
		}
		return result;
	}
	
	public Shares fromResultset(ResultSet set) {
		Shares shares = null;
		try {
			shares = new Shares();
			shares.setCode(set.getString("code"));
			shares.setName(set.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return shares;
	}
	
	public boolean addOrUpdateShares(Shares bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection();
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				pstmt.setString(1, bean.getCode());
				pstmt.setString(2, bean.getName());
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("replace表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
				DB.close(conn);
			}
		}
		return result;
	}
	
	private boolean addOrUpdateShares(Connection conn, Shares bean) {
		boolean result = false;
		if (bean != null) {
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				pstmt.setString(1, bean.getCode());
				pstmt.setString(2, bean.getName());
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("replace表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
			}
		}
		return result;
	}
	
	public int addOrUpdateSharesList(ArrayList<Shares> beans) {
		int successCount = 0;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection();
			for(Shares bean : beans){
				boolean success = addOrUpdateShares(conn, bean);
				if (success) successCount++;
			}
			DB.close(conn);
		}
		return successCount;
	}
}
