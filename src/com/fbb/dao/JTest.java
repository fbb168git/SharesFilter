package com.fbb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JTest {

	public static void main(String[] args) {
		Connection connection = DB.getConnection();
		Statement createStmt = DB.createStmt(connection);
		ResultSet set = DB.executeQuery(createStmt, "select * from ip");
		try {
			while(set.next()){
				String string = set.getString("type");
				System.out.print(string);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		
//		ArrayList<ProxyIpBean> allProxyIps = ProxyIpDao.getAllProxyIps();
//		System.out.print(allProxyIps.size());

	}

}
