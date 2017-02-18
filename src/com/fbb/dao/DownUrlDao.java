package com.fbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fbb.bean.DownUrlBean;
import com.fbb.bean.FilmBean;

public class DownUrlDao {
	public static final String DB_NAME = "film";
	public static final String TABLE_NAME = "down_url";
	public static final String sql_add = "insert into "+TABLE_NAME+" values (null,?,?,now())";

	public static ArrayList<DownUrlBean> getUrlsByFilmId(int filmId) {
		ArrayList<DownUrlBean> result = new ArrayList<DownUrlBean>();
		Connection conn = DB.getConnection(DB_NAME);
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select * from "+TABLE_NAME+" where film_id = "+filmId);
		try {
			while (resultSet.next()) {
				DownUrlBean mDownUrlBean = new DownUrlBean();
				mDownUrlBean.fromResultset(resultSet);
				result.add(mDownUrlBean);
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
	
	public static boolean addUrlBean(ArrayList<DownUrlBean> beans) {
		boolean result = false;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection(DB_NAME);
			for(DownUrlBean bean : beans){
				addUrlBean(conn, bean);
			}
			DB.close(conn);
		}
		return result;
	}
	
	public static boolean addUrlBean(Connection conn, DownUrlBean bean) {
		boolean result = false;
		if (bean != null) {
			if(isHave(conn, bean)){
				return true;
			}
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				pstmt.setInt(1, bean.getFilmId());
				pstmt.setString(2, bean.getUrl());
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
	
	public static void addUrlBean(Connection conn, ArrayList<DownUrlBean> beans) {
		if(beans != null && beans.size() > 0){
			for(DownUrlBean bean : beans){
				addUrlBean(conn, bean);
			}
		}
	}
	
	public static boolean isHave(Connection conn, DownUrlBean bean) {
		boolean result = false;
		if (bean != null) {
			Statement stmt = DB.createStmt(conn);
			ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from "+DB_NAME+" where url = '"+bean.getUrl()+"'");
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
}
