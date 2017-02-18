package com.fbb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fbb.bean.DownUrlBean;
import com.fbb.bean.FilmBean;
import com.fbb.util.LogUtil;

public class FilmDao {
	public static final String DB_NAME = "film";
	public static final String TABLE_NAME = "film";
	public static final String sql = "select * from "+TABLE_NAME+" limit 0, 20";
	public static final String sql_add = "insert into "+TABLE_NAME+" values (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
	public static final String sql_search = "select * from "+TABLE_NAME+" where url like '%?%'";

	public static ArrayList<FilmBean> getAllFilms() {
		ArrayList<FilmBean> result = new ArrayList<FilmBean>();
		Connection conn = DB.getConnection(DB_NAME);
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, sql);
		try {
			while (resultSet.next()) {
				FilmBean FilmIpBean = new FilmBean();
				FilmIpBean.fromResultset(resultSet);
				result.add(FilmIpBean);
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
	
	public static ArrayList<FilmBean> search(String keywords) {
		ArrayList<FilmBean> result = new ArrayList<FilmBean>();
		Connection conn = DB.getConnection(DB_NAME);
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select * from "+TABLE_NAME+" where url like '%"+keywords+"%'");
		try {
			while (resultSet.next()) {
				FilmBean FilmIpBean = new FilmBean();
				FilmIpBean.fromResultset(resultSet);
				result.add(FilmIpBean);
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
	
	public static boolean addFilm(FilmBean bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection(DB_NAME);
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				fillPreparedStatement(bean, pstmt);
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
//				if(result){
//					DownUrlDao.addUrlBean(conn, bean.getDownUrls());
//				}
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.log("message:"+e.getMessage());
				LogUtil.log("string:"+e.toString());
				throw new RuntimeException(e.getMessage());
			} finally {
				DB.close(conn);
				DB.close(pstmt);
			}

		}
		return result;
	}

	public static boolean addFilm(Connection conn, FilmBean bean) {
		boolean result = false;
		if (bean != null) {
			if(isHave(conn, bean)){
				return true;
			}
			PreparedStatement pstmt = DB.prepareStmt(conn, sql_add);
			try {
				fillPreparedStatement(bean, pstmt);
				int executeUpdate = pstmt.executeUpdate();
				result = executeUpdate > 0;
				if(result){
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if(generatedKeys.next()){
						int id = generatedKeys.getInt(1);
						bean.setId(id);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DB.close(pstmt);
			}
		}
		return result;
	}

	private static void fillPreparedStatement(FilmBean bean, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getNameOther());
		pstmt.setString(3, bean.getBrief());
		pstmt.setString(4, bean.getDirector());
		pstmt.setString(5, bean.getActors());
		pstmt.setString(6, bean.getScreenWriter());
		pstmt.setString(7, bean.getPoster());
		pstmt.setString(8, bean.getPictures());
		pstmt.setDate(9, bean.getRelease_time());
		pstmt.setString(10, bean.getLanguage());
		pstmt.setInt(11, bean.getDuration());
		pstmt.setString(12, bean.getFilm_type());
		pstmt.setString(13, bean.getArea());
		pstmt.setString(14, bean.getSubtitle());
		pstmt.setString(15, bean.getUrl());
		pstmt.setFloat(16, bean.getScore());
		pstmt.setInt(17, bean.getFile_size());
	}
	
	public static boolean isHave(FilmBean bean) {
		boolean result = false;
		if (bean != null) {
			Connection conn = DB.getConnection(DB_NAME);
			Statement stmt = DB.createStmt(conn);
			ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from "+DB_NAME+" where url = '"+bean.getUrl()+"'");
			try {
				resultSet.next();
				int int1 = resultSet.getInt(1);
				result = int1 > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DB.close(conn);
				DB.close(stmt);
			}
		}
		return result;
	}
	
	public static boolean isHave(Connection conn, FilmBean bean) {
		boolean result = false;
		if (bean != null) {
			Statement stmt = DB.createStmt(conn);
			ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from "+DB_NAME+" where name = '"+bean.getName()+"'");
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

	public static boolean addFilm(ArrayList<FilmBean> beans) {
		boolean result = false;
		if (beans != null && beans.size() > 0) {
			Connection conn = DB.getConnection(DB_NAME);
			for (int i = 0; i < beans.size(); i++) {
				FilmBean bean = beans.get(i);
				boolean addFilm = addFilm(conn, bean);
				if (addFilm) {
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
		Connection conn = DB.getConnection(DB_NAME);
		Statement stmt = DB.createStmt(conn);
		ResultSet resultSet = DB.executeQuery(stmt, "select count(*) from "+DB_NAME);
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
