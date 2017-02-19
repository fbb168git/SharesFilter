package com.fbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fbb.bean.TradeDetail;
import com.fbb.util.LogUtil;

public class TradeDetailDao {
	public static final String TABLE_NAME = "trade_detail";
	public static final String sql_add = "replace into "+TABLE_NAME+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";

	private void fillPreparedStatement(TradeDetail bean, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, bean.getCode());
		pstmt.setDate(2, bean.getTraDate());
		pstmt.setString(3, bean.getName());
		pstmt.setFloat(4, bean.getIncrePer());
		pstmt.setFloat(5, bean.getIncrease());
		pstmt.setFloat(6, bean.getTodayStartPri());
		pstmt.setFloat(7, bean.getYestodEndPri());
		pstmt.setFloat(8, bean.getNowPri());
		pstmt.setFloat(9, bean.getTodayMax());
		pstmt.setFloat(10, bean.getTodayMin());
		pstmt.setLong(11, bean.getTraNumber());
		pstmt.setDouble(12, bean.getTraAmount());
		pstmt.setString(13, bean.getMinurl());
		pstmt.setString(14, bean.getDayurl());
		pstmt.setString(15, bean.getWeekurl());
		pstmt.setString(16, bean.getMonthurl());
		pstmt.setInt(17, bean.getStatus());
	}
	
	public boolean addOrUpdateTradeDetail(TradeDetail bean) {
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
				LogUtil.e("replace表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
				DB.close(conn);
			}
		}
		return result;
	}
	
	private boolean addOrUpdateTradeDetail(Connection conn, TradeDetail bean) {
		boolean result = false;
		if (bean != null) {
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				fillPreparedStatement(bean,pstmt);
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.e("replacet表"+TABLE_NAME+"异常");
			} finally {
				DB.close(pstmt);
			}
		}
		return result;
	}
	
	public int addOrUpdateTradeDetailList(ArrayList<TradeDetail> beans) {
		int successCount = 0;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection();
			for(TradeDetail bean : beans){
				boolean success = addOrUpdateTradeDetail(conn, bean);
				if(success) successCount++;
			}
			DB.close(conn);
		}
		return successCount;
	}
}
