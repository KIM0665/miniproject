package com.kky2;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CreateTable extends MySqlAccess {

//	 String table;
public CreateTable() throws Exception {
		super();
	}




public  static void ListCreate(String table) throws Exception {

//	id, commanyName, valuation, Korean_rate,company_equity_rate
//// 게시판 글 작성시 
//	String sql_post = "CREATE TABLE user." + table + " (" 
//			+ "company VARCHAR(100) PRIMARY key NOT NULL,"
//			+ " post TEXT )";
	
//	pstmt = conn.prepareStatement(sql2);
//	int result = pstmt.executeUpdate();
//	if (result == 0)
//		System.out.println(table+"으로 테이블이 생성되었습니다. ");
//	else
//		System.out.println(table+"으로 테이블이 생성되지 않았습니다");
//
//}
	
	for(int i= 0 ;i <100;i++) {
		String sql_posts = "CREATE TABLE user." + table + " (" 
		+ "company VARCHAR(100) PRIMARY key NOT NULL,"
		+ " post TEXT )";
		
		
	}
	
	
	
	
	String sql = "CREATE TABLE user." + table + " (" 
			+ "userid VARCHAR(100) PRIMARY key NOT NULL,"
			+ "name VARCHAR(100)," 
			+ "password VARCHAR(100)," 
			+ "email VARCHAR(100) ,"
			+ "phone VARCHAR(100) )";
	pstmt = conn.prepareStatement(sql);
	int result = pstmt.executeUpdate();
//	System.out.println(result);
	if (result == 0)
		System.out.println(table+"으로 테이블이 생성되었습니다. ");
	else
		System.out.println(table+"으로 테이블이 생성되지 않았습니다");

}

// 모든자료 조회
public void selectAll(String table)  {

	String sql = "select * from user." + table;  // 자료 조회용
	String sql2 = "SELECT COUNT(*) FROM information_schema.columns " // 행 갯수 확인용
			+ "WHERE table_name= '" + table + "'";
	
	// 행갯수 확인용
	try {
		pstmt = conn.prepareStatement(sql2);
		ResultSet r2 = pstmt.executeQuery(sql2);
		// 자료 조회용
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		// 행갯수 확인용  r2.next가 비어있을떄까지 출력을 한다
		if (r2.next())
			// 자료 출력용 rs.next 자료가 남아있다면 true
			while (rs.next()) {
				// 자료가 있을떄까지 뽑아낸다. 행의 갯수만큼 뽑아낸다
				for (int i = 1; i <= r2.getInt(1); i++) {
					System.out.print("\t" + rs.getObject(i));
				}
				System.out.println();
			}
	} catch (SQLException e) {
		System.out.println("테이블명을 확인해주세요");
	}


	}


// 선택적 조회
public void oneselect(String table, String userid) {

	String sql = "select * from user." + table + " where addr like '" + userid + "%'";// 
	String sql2 = "SELECT COUNT(*) FROM information_schema.columns WHERE table_name= '" + table + "'";
	
	try {
		pstmt = conn.prepareStatement(sql2);
		ResultSet r2 = pstmt.executeQuery(sql2);

		pstmt = conn.prepareStatement(sql); 
		ResultSet rs = pstmt.executeQuery(sql);

		
		
//		ResultSet rs = pstmt.executeQuery(sql); 이걸 이용해서 만들어보자. 로그인 시도
		if (r2.next()) {   // rs.next 있다면  true 반환 로그인 성공, 없다면 false 반환 로그인 실패
//			System.out.println(r2.getInt(1));
			while (rs.next()) {
				for (int i = 1; i <= r2.getInt(1); i++) {
					System.out.print("\t" + rs.getObject(i));
				}
				System.out.println();
			}

		}
	} catch (SQLException e) {
		System.out.println("테이블명을 확인해주세요");

	}
	
}


// 회원가입
//insert into user.usertbl set(userid)
//values('yun0665'),
//delete user.usertbl where userid is 'yun0665' and  password is Null;
// 데이터 입력

// 아이디 비교
public void insert2(String userid) {
	
	String sqluserid = "insert into user.usertbl "
			+ "(userid) values('"+userid+"')";
	while(true) {
	try {
		pstmt = conn.prepareStatement(sqluserid);
		int r2 = pstmt.executeUpdate();
		if(r2==1) {System.out.println("사용가능한 아이디입니다"); break;}
		
	} catch (SQLException e) {
		System.out.println("중복된 아이디입니다.");

		continue;
		
		}
	}
	
}



// 로그인 시도
public void login(String userid,String password) {
	String sql = "select *from user.usertbl "
			+ "where userid = '"+userid+"' and"
			+" password= '"+password+"'";
	
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		if(rs.next()) {
			System.out.println("로그인 성공"); 
		}

		
	} catch (SQLException e) {

		System.out.println("로그인 실패");
	}

}



// 관리자 계정으로 로그인
public void managerlogin(String userid,String password) {
	
	String sql = "select *from user.managertbl "
			+ "where userid = '"+userid+"' and"
			+" password= '"+password+"'";
	
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		if(rs.next()) {
			boolean b = true; 
		}
		else {boolean q= false;}
		
	} catch (SQLException e) {

		System.out.println("로그인 실패");
	}
	
	
}






// 가입
public void insert(join join)  {
	
	

	// 아이디가 일치하는게 없다면 데이터 입력
	String sqlupdate ="UPDATE user.usertbl "  
			+" SET name = ?,password = ?,email = ?,phone = ?"
			+" where userid = ?";
			
			
			

	try {
		pstmt = conn.prepareStatement(sqlupdate);
		pstmt.setString(1, join.getName());
		pstmt.setString(2,join.getPassword());
		pstmt.setString(3,join.getEmail());
		pstmt.setString(4,join.getPhone());
		pstmt.setString(5,join.getUserid());

		int result = pstmt.executeUpdate();
		if (result == 1)System.out.println(join.getName() + "님 회원가입에 성공");
		else System.out.println("회원가입 실패!");
		
	} catch (SQLException e) {
		System.out.println("다시한번 확인해주세요");
	}


}





// 데이터 삭제
public void deleteNum(String table, int num)  {
	String sql = "delete from " + table + " where id = ?";
	PreparedStatement pstmt;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		int result = pstmt.executeUpdate();
		if (result == 1) {
			System.out.println(num + "번 고객 삭제 성공");
		} else {
			System.out.println(num + "번 고객 삭제 실패");
		}
	} catch (SQLException e) {
		System.out.println("테이블명을 확인해주세요");
	}


}

//백데이터
public void backdata(String table)  {
	
	String resultbf = "";

	try 
	{
		URL url = new URL(
				"https://apis.data.go.kr/B551011/GoCamping/basedList?numOfRows=100&pageNo=2&MobileOS=ETC&MobileApp=appApp&serviceKey=gseW08OJ8DSEjiT7oUvSm6mJUV0NeKUE19Q2ZFi54yyUIo1ZqmkB%2BIX04ban3veCMtBF%2BJEYA2qbSJOyxNc4KQ%3D%3D&_type=json");

		BufferedReader bf;
		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		resultbf = bf.readLine();
		System.out.println();

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(resultbf);
		JSONObject response = (JSONObject) jsonObj.get("response");
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray item = (JSONArray) items.get("item");

	

		

		for(int i=0;i<item.size();i++) {
			String sql = "insert into " + table 
					+ " (facltNm,lineIntro,addr) "
					+ "value(?,?,?) ";
			pstmt = conn.prepareStatement(sql);
			JSONObject arr = (JSONObject) item.get(i);
			String facltNm = (String) arr.get("facltNm");
			String lineIntro = (String) arr.get("lineIntro");
			String addr = (String) arr.get("addr1");

			
			
			pstmt.setString(1, facltNm);
			pstmt.setString(2, lineIntro);
			pstmt.setString(3, addr);
			pstmt.executeUpdate();
			 
		}
		
		bf.close();

		int result = pstmt.executeUpdate();
		if (result != 0) System.out.println(result+" 데이터 추가 성공!");
		} 
		catch (Exception e) {}
	
	
	System.out.println("데이터추가에 실패하였습니다.");

}

// 테이블 수정
public void update(String table, String name1, String name2)  {

	String sql = "UPDATE " + table + " SET name = '" + name2 + "' WHERE = '" + name1 + "'";
	try {
		pstmt = conn.prepareStatement(sql);
		int result = pstmt.executeUpdate(sql);
		if (result == 1) {
			System.out.println("수정 성공!");
		} else {
			System.out.println("수정하시고 싶은 데이터가 존재하지 않습니다");
		}
	} catch (SQLException e) {
		System.out.println("테이블 명을 확인해 주세요");
	}


}



	}


	
	

