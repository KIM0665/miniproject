package com.kky2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlAccess {
	static Connection conn;
	static PreparedStatement pstmt;
	
	public MySqlAccess() {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/sqldb";
		String userid = "root";
		String pwd = "1234";

		try {
			Class.forName(driver);
			System.out.println("드라이버 연결 성공");
			System.out.println("드라이버 연결 준비...");
			conn = DriverManager.getConnection(url, userid, pwd);
			System.out.println("드라이버 연결 성공!");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 메서드 확인");
		} catch (SQLException e) {
			System.out.println("sql메서드 확인");
		}

	}
}
