package com.fbb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.fbb.bean.FilterResult;
import com.fbb.util.LogUtil;

public class FilterResultDao {
	public static final String TABLE_NAME = "filter_result";
	public static final String sql_add = "insert into "+TABLE_NAME+" values (null,?,?,?,?,,now())";
	public static final String sql_get_by_trade_date = "select * from "+TABLE_NAME +" where date_format(trade_date,'%Y-%m-%d')=";
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public ArrayList<FilterResult> getFilterResultListByTradeDate(Date date) {
		ArrayList<FilterResult> result = new ArrayList<FilterResult>();
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, sql_get_by_trade_date+dateFormat.format(date));
		try {
			while (resultSet.next()) {
				FilterResult bean = fromResultset(resultSet);
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("select 表"+TABLE_NAME+"异常");
		} finally {
			DB.close(conn);
			DB.close(stmt);
			DB.close(resultSet);
		}
		return result;
	}
	
	public boolean addFilterResult(FilterResult bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection();
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				fillPreparedStatement(bean,pstmt);
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("insert表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
				DB.close(conn);
			}
		}
		return result;
	}
	
	private boolean addFilterResult(Connection conn, FilterResult bean) {
		boolean result = false;
		if (bean != null) {
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				fillPreparedStatement(bean,pstmt);
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("insert表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
			}
		}
		return result;
	}
	
	public int addFilterResultList(ArrayList<FilterResult> beans) {
		int successCount = 0;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection();
			for(FilterResult bean : beans){
				boolean success = addFilterResult(conn, bean);
				if(success) successCount++;
			}
			DB.close(conn);
		}
		return successCount;
	}
	
	private FilterResult fromResultset(ResultSet set) {
		FilterResult bean = null;
		try {
			bean = new FilterResult();
			bean.setId(set.getInt("id"));
			bean.setCode(set.getString("code"));
			bean.setFilter_name(set.getString("filter_name"));
			bean.setLevel(set.getInt("level"));
			bean.setTrade_Date(set.getDate("trade_date"));
			bean.setUpdate_time(set.getDate("update_time"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	private void fillPreparedStatement(FilterResult bean, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, bean.getCode());
		pstmt.setString(2, bean.getFilter_name());
		pstmt.setInt(3, bean.getLevel());
		pstmt.setDate(4, bean.getTrade_Date());
	}
}
