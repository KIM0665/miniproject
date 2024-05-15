package com.kky2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class miniTestClass {
	static Connection conn1;
	static PreparedStatement pstmt1;
	
	
	public  static void driver2() throws Exception {
		String driver2 = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.01:3306/user";
		String userid = "yun";
		String pwd = "1234";
		Class.forName(driver2);
		conn1 = DriverManager.getConnection(url, userid, pwd);
	
	}
	
	

	
	// 전체 게시글 조회 or  전체데이터 조회
	protected void selectAll(String a)  {

		
		String sql = "";  // 테이블용
		String sql2 = ""; // 갯수확인용
	
		// 게시글 전체조회
		if(a.equals("2")) {sql = "select * from user.post";
				  sql2 = "SELECT COUNT(*) FROM information_schema.columns "
								+"WHERE table_schema= 'user'"
								+"AND table_name = 'post'";}

		// 데이터 전체 조회
		else if(a.equals("1")) {sql = "select * from user.company";
					sql2 = "SELECT COUNT(*) FROM information_schema.columns " // 행 갯수 확인용
					+ "WHERE table_schema= 'user' "
					+ "AND table_name = 'company'";}

		
		// 행갯수 확인용
		try {
			pstmt1 = conn1.prepareStatement(sql2);
			ResultSet r2 = pstmt1.executeQuery(sql2);
			// 자료 조회용
			pstmt1 = conn1.prepareStatement(sql);
			ResultSet rs = pstmt1.executeQuery(sql);
			// 행갯수 확인용  r2.next가 비어있을떄까지 출력을 한다
			r2.next();
			
			if(!rs.next()){System.out.println("데이터가 없습니다");}
				// 자료 출력용 rs.next 자료가 남아있다면 true
			else {
			do {
			    // 자료가 있을 때까지 뽑아낸다. 행의 갯수만큼 뽑아낸다
			    for (int i = 1; i <= r2.getInt(1); i++) {
			        System.out.print("\t" + rs.getObject(i));
			    }
			    System.out.println();
			} while (rs.next());}}
			catch (Exception e) {
			e.printStackTrace();
			System.out.println("sql문법 확인");
		}
	}
		
	
	// 원하는 게시글 조회, 원하는 데이터 검색 
	protected void oneselect(String a, String company) {

			String sql = "";  // 테이블용
			String sql2 = "";

			try {

				// 게시글 특정 조회
				if(a.equals("1")) {sql = "select * from user.post" 
										+ " where post LIKE '%"+company+"%'";
						   		sql2 = "SELECT COUNT(*) FROM information_schema.columns "
						   				+"WHERE table_schema= 'user'"
						   				+"AND table_name ='post'";}

				
				
				// 데이터 특정 조회
				else if(a.equals("2")) {sql = "select * from user.company" 
									  + " where company LIKE '%"+company+"%'";
				
							   sql2= "SELECT COUNT(*) FROM information_schema.columns "
									  +"WHERE table_schema= 'user'"
									  +"AND table_name = 'company'";}
				else {System.out.println("번호를 다시 입력해주세요");}
				
				pstmt1 = conn1.prepareStatement(sql2);
				ResultSet r2 = pstmt1.executeQuery(sql2);
				
				pstmt1 = conn1.prepareStatement(sql); 
				ResultSet rs = pstmt1.executeQuery(sql);

				if (r2.next())
					// 자료 출력용 rs.next 자료가 남아있다면 true
					while (rs.next()) {
						// 자료가 있을떄까지 뽑아낸다. 행의 갯수만큼 뽑아낸다
						for (int i = 1; i <= r2.getInt(1); i++) {
							System.out.print("\t" + rs.getObject(i));
						}
						System.out.println();
					}

				pstmt1.close();

				}
			 catch (SQLException e) {
				 e.printStackTrace();
				System.out.println("테이블명을 확인해주세요");

			}}
			
		
		
		
		// 게시글 작성  
	protected void insertPosttbl(Posttbl posttbl)  {
		String sql = "";
		sql = "insert into user.post "
							+ "(userid,post,no) "
							+ "values (?,?,now())";
		
		
		 
		  try {
			  pstmt1 = conn1.prepareStatement(sql);
			  pstmt1.setObject(1, posttbl.getId());
			  pstmt1.setObject(2, posttbl.getTale());

				int result = pstmt1.executeUpdate();
				if (result == 1) {
					System.out.println("게시글 추가");
				} else {
					System.out.println("실패");
				}pstmt1.close();
				
			} catch (SQLException e) {
				System.out.println("문법확인");
			}
		
		
		
		
		}



//	update user.post set post = ? where userid = ? and num = ?;
		
	
	// 데이터 삭제
	protected void deleteNum(String table,String num)  {
		String sql ="";
		if(table.equals("1")) {
			sql = "delete from user.company"+ " where num = ?";
			
		}
		else if(table.equals("2")) {
			sql = "delete from user.post" + " where num = ?";
		}
		
		

		try {
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setObject(1, num);
			int result = pstmt1.executeUpdate();
			if (result == 1) {
				System.out.println(num + "번 데이터 삭제 성공");
			} else {
				System.out.println(num + "번 데이터는 본인이 작성한 글이 아닙니다.");
			}
			pstmt1.close();
		} catch (SQLException e) {
			System.out.println("선택을 다시 해주시기 바랍니다");
		}}
	
	
	
	
	
	
	
	
	
//	게시글 수정하기
	
		protected void postupdate(String post,String userid,String num)  {
			String sql ="update user.post set post = ? where userid = ? and num = ?";

			

			try {
				pstmt1 = conn1.prepareStatement(sql);
				pstmt1.setObject(1, post);
				pstmt1.setObject(2, userid);
				pstmt1.setObject(3, num);
				int result = pstmt1.executeUpdate();
				if (result == 1) {
					System.out.println(num + "번 데이터 수정 성공");
				} else {
					System.out.println(num + "번 데이터는 본인이 작성한 글이 아닙니다.");
				}
				pstmt1.close();
			} catch (SQLException e) {
				System.out.println("수정권한이 없습니다.");
			}


		}
		}
	
	
	
	

		
		
		
		
		
		
	
	





	
			




