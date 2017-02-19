package com.fbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fbb.bean.ThreeRed;
import com.fbb.util.LogUtil;

public class ThreeRedDao {
	public static final String TABLE_NAME = "three_red";
	public static final String sql_add = "insert into "+TABLE_NAME+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
	public static final String sql_add_or_update = "replace into "+TABLE_NAME+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
	public static final String sql_get_all = "select * from "+TABLE_NAME;
	
	public ArrayList<ThreeRed> getAllThreeRedList() {
		ArrayList<ThreeRed> result = new ArrayList<ThreeRed>();
		Connection conn = DB.getConnection();
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, sql_get_all);
		try {
			while (resultSet.next()) {
				ThreeRed bean = fromResultset(resultSet);
				result.add(bean);
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
	
	public boolean addOrUpdateThreeRed(ThreeRed bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection();
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add_or_update);
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
	
	private boolean addOrUpdateThreeRed(Connection conn, ThreeRed bean) {
		boolean result = false;
		if (bean != null) {
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add_or_update);
			try {
				fillPreparedStatement(bean,pstmt);
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
	
	public int addOrUpdateThreeRedList(ArrayList<ThreeRed> beans) {
		int successCount = 0;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection();
			for(ThreeRed bean : beans){
				boolean success = addOrUpdateThreeRed(conn, bean);
				if(success) successCount++;
			}
			DB.close(conn);
		}
		return successCount;
	}
	
	private ThreeRed fromResultset(ResultSet set) {
		ThreeRed bean = null;
		try {
			bean = new ThreeRed();
			bean.setCode(set.getString("code"));
			bean.setName(set.getString("name"));
			bean.setCur_increase(set.getFloat("cur_increase"));
			bean.setCur_start_price(set.getFloat("cur_start_price"));
			bean.setCur_end_price(set.getFloat("cur_end_price"));
			bean.setCur_max_price(set.getFloat("cur_max_price"));
			bean.setCur_min_price(set.getFloat("cur_min_price"));
			bean.setCur_trade_num(set.getLong("cur_trade_num"));
			bean.setCur_trade_date(set.getDate("cur_trade_date"));
			
			bean.setPre_increase(set.getFloat("pre_increase"));
			bean.setPre_start_price(set.getFloat("pre_start_price"));
			bean.setPre_end_price(set.getFloat("pre_end_price"));
			bean.setPre_max_price(set.getFloat("pre_max_price"));
			bean.setPre_min_price(set.getFloat("pre_min_price"));
			bean.setPre_trade_num(set.getLong("pre_trade_num"));
			bean.setPre_trade_date(set.getDate("pre_trade_date"));
			
			bean.setPrepre_increase(set.getFloat("prepre_increase"));
			bean.setPrepre_start_price(set.getFloat("prepre_start_price"));
			bean.setPrepre_end_price(set.getFloat("prepre_end_price"));
			bean.setPrepre_max_price(set.getFloat("prepre_max_price"));
			bean.setPrepre_min_price(set.getFloat("prepre_min_price"));
			bean.setPrepre_trade_num(set.getLong("prepre_trade_num"));
			bean.setPrepre_trade_date(set.getDate("prepre_trade_date"));
			
			bean.setUpdate_time(set.getDate("update_time"));
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtil.e("fromResultset表"+TABLE_NAME+"异常");
		}
		return bean;
	}

	private void fillPreparedStatement(ThreeRed bean, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, bean.getCode());
		pstmt.setString(2, bean.getName());
		
		pstmt.setFloat(3, bean.getCur_increase());
		pstmt.setFloat(4, bean.getCur_start_price());
		pstmt.setFloat(5, bean.getCur_end_price());
		pstmt.setLong(6, bean.getCur_trade_num());
		pstmt.setFloat(7, bean.getCur_max_price());
		pstmt.setFloat(8, bean.getCur_min_price());
		
		pstmt.setFloat(9, bean.getPre_increase());
		pstmt.setFloat(10, bean.getPre_start_price());
		pstmt.setFloat(11, bean.getPre_end_price());
		pstmt.setLong(12, bean.getPre_trade_num());
		pstmt.setFloat(13, bean.getPre_max_price());
		pstmt.setFloat(14, bean.getPre_min_price());
		
		pstmt.setFloat(15, bean.getPrepre_increase());
		pstmt.setFloat(16, bean.getPrepre_start_price());
		pstmt.setFloat(17, bean.getPrepre_end_price());
		pstmt.setLong(18, bean.getPrepre_trade_num());
		pstmt.setFloat(19, bean.getPrepre_max_price());
		pstmt.setFloat(20, bean.getPrepre_min_price());
		
		pstmt.setDate(21, bean.getCur_trade_date());
		pstmt.setDate(22, bean.getPre_trade_date());
		pstmt.setDate(23, bean.getPrepre_trade_date());
	}
}
