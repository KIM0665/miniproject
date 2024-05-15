package com.kky2;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class miniTestmain extends miniTestClass{
	static Connection conn;
	static Connection conn1;
	static PreparedStatement pstmt;
	
	
	// sql 연결 root // static으로 해서 열고 닫기 가능 메서드마다 적용하기
	public static void driver1() throws Exception  {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/sqldb";
		String userid = "root";
		String pwd = "1234";

		Class.forName(driver);
		conn = DriverManager.getConnection(url, userid, pwd);	

		
	}
	
	// sql 연결 서브
	public  static void driver2() throws Exception {
		String driver2 = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.01:3306/user";
		String userid = "yun";
		String pwd = "1234";

		Class.forName(driver2);
		conn1 = DriverManager.getConnection(url, userid, pwd);
		
	}
	
	
	
	// 로그인 시도
	public boolean login(Usertbl usertbl) {
		String sql = "select *from user.usertbl "
				+ "where userid = '"+usertbl.getId()+"' and"
				+" password= '"+usertbl.getPassword()+"'";
		boolean b= false;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			if(rs.next()) b = true; 
			else b= false ;
			
		} catch (SQLException e) {

			System.out.println("로그인 실패");
		}
		return b;

	}
	
	// 관리자 계정으로 로그인시도
	public boolean managerlogin(Usertbl usertbl) {
		
		String sql = "select *from user.manageruserid "
				+ "where userid = '"+usertbl.getId()+"' and"
				+" password= '"+usertbl.getPassword()+"'";
		boolean b= false;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			if(rs.next()) b = true; 
		
			
		} 
		catch (SQLException e) {
			System.out.println("로그인 실패");
		}
		return b;
		
	}
	
	
	
	// 아이디 중복체크
	public boolean insert2(String userid) {
		
		String sqluserid = "insert into user.usertbl "
				+ "(userid) values(?)";
	
		boolean bo= false;
		try {
			pstmt = conn.prepareStatement(sqluserid);
			pstmt.setString(1, userid);

			int r2 = pstmt.executeUpdate();
			if(r2==1) {System.out.println("사용가능합니다");
			bo=false;	
			return bo;} 
			}
		 catch (SQLException e) {
			
			System.out.println("사용불가능한 아이디입니다.");
			bo=true; 
		 	}
		return bo;
		}
		
	
//	회원가입
	public void join1(Usertbl u) {
		
		String sql = "";
		sql = "UPDATE user.usertbl "
		        + "SET password = ?, name = ?, email = ?, phone = ? "
		        + "WHERE userid = ?"; 
		 
		  try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, u.getPassword());
				pstmt.setString(2, u.getName());
				pstmt.setString(3, u.getEmail());
				pstmt.setString(4, u.getPhone());
				pstmt.setString(5, u.getId());

				pstmt.executeUpdate();
				int result = pstmt.executeUpdate();

				if (result == 1) {
					System.out.println("가입이 완료하였습니다");
				} else {
					System.out.println("추가 실패!");
				}
			} catch (SQLException e) {
				System.out.println("문법확인");
			}
		
		
	}
	
	
	
	// 주식 백데이터
	public void backdata() throws Exception {
		
		String resultbf = "";
		
		try {
			URL url = new URL("https://api.odcloud.kr/"
								+ "api/3070507/v1/uddi:d88b7e3b-b26c-4cee-887c-8a256cce8a56?page=1&perPage="
								+ "10000&`returnType=JSON&serviceKey=gseW08OJ8DSEjiT7oUvSm6mJUV0NeKUE19Q2ZFi5"
								+ "4yyUIo1ZqmkB%2BIX04ban3veCMtBF%2BJEYA2qbSJOyxNc4KQ%3D%3D");
			
			
			BufferedReader bf;
			bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			resultbf = bf.readLine();
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject) parser.parse(resultbf);
			System.out.println("dd");
			JSONArray company = (JSONArray) jsonObj.get("data");

			String sql = "insert into user.company" 
					+ "(company,rate,share,Evaluation) "
					+ "value(?,?,?,?) ";

			
			for(int i = 0 ; i<company.size();i++) {
				
				JSONObject num = (JSONObject) company.get(i);
				String name = (String) num.get("종목명");
				String rate = (String) num.get("자산군 내 비중(퍼센트)");
				String share = (String) num.get("지분율(퍼센트)");
				Long Evaluation = (Long) num.get("평가액(억 원)");
				

				pstmt = conn.prepareStatement(sql);
				pstmt.setObject(1, name);
				pstmt.setObject(2, rate);
				pstmt.setObject(3, share);
				pstmt.setObject(4, Evaluation);
				pstmt.executeUpdate();

				bf.close();
				

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/// 테이블 생성
//	id, commanyName, valuation, Korean_rate,company_equity_rate
	protected void ListCreate(String table) throws Exception {
		
		

		String sql = "CREATE TABLE " + table + " (" + "num INT PRIMARY key AUTO_INCREMENT NOT NULL,"
				+ "company VARCHAR(100)," 
				+ "share VARCHAR(100)," 
				+ "Evaluation VARCHAR(100), "
				+ "rate VARCHAR(100))";
		pstmt = conn.prepareStatement(sql);
		int result = pstmt.executeUpdate();
//		System.out.println(result);
		if (result == 0)
			System.out.println(table+"으로 테이블이 생성되었습니다. ");
		else
			System.out.println(table+"으로 테이블이 생성되지 않았습니다");}
	
	
	
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
			pstmt = conn.prepareStatement(sql2);
			ResultSet r2 = pstmt.executeQuery(sql2);
			// 자료 조회용
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
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
				if(a.equals("2")) {sql = "select * from user.post" 
										+ " where post LIKE '%"+company+"%'";
		   						
								  sql2 = "SELECT COUNT(*) FROM information_schema.columns "
		   								  +"WHERE table_schema= 'user'"
		   								  +"AND table_name ='post'";}
				// 데이터 특정 조회
				else if(a.equals("1")) {sql = "select * from user.company" 
									  + " where company LIKE '%"+company+"%'";
				
							   sql2= "SELECT COUNT(*) FROM information_schema.columns "
									  +"WHERE table_schema= 'user'"
									  +"AND table_name = 'company'";}
				else {System.out.println("번호를 다시 입력해주세요");}
				
				pstmt = conn.prepareStatement(sql); 

				ResultSet rs = pstmt.executeQuery(sql);
				pstmt = conn.prepareStatement(sql2);
				ResultSet r2 = pstmt.executeQuery(sql2);
				if (r2.next())
					// 자료 출력용 rs.next 자료가 남아있다면 true
					while (rs.next()) {
						// 자료가 있을떄까지 뽑아낸다. 행의 갯수만큼 뽑아낸다
						for (int i = 1; i <= r2.getInt(1); i++) {
							System.out.print("\t" + rs.getObject(i));
						}
						System.out.println();
					}




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
				pstmt = conn.prepareStatement(sql);
				pstmt.setObject(1, posttbl.getId());
				pstmt.setObject(2, posttbl.getTale());

				int result = pstmt.executeUpdate();
				if (result == 1) {
					System.out.println("게시글 추가");
				} else {
					System.out.println("추가 실패!");
				}
			} catch (SQLException e) {
				System.out.println("문법확인");
			}
		
		
		
		
		}



			
	//  주식 데이터 추가
	protected void insertCompany(stock stcok) {
			
		 String sql = "insert into user.company "
							+ "(company,rate,share,Evaluation) "
							+ "value(?,?,?,?)";

		 
		  try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setObject(1, stcok.getCompany());
				pstmt.setObject(2, stcok.getRate());
				pstmt.setObject(3, stcok.getShare());
				pstmt.setObject(4, stcok.getEvaluation());

				int result = pstmt.executeUpdate();
				if (result == 1) {
					System.out.println("주식데이터 추가");
				} else {
					System.out.println("추가 실패!");
				}
			} catch (SQLException e) {}
	}	
		
	
	// 데이터 삭제
	protected void deleteNum(String table,String num)  {
		String sql ="";
		if(table.equals("1")) {
			sql = "delete from user.company"+ " where num = ?";
			
		}
		else if(table.equals("2")) {
			sql = "delete from user.post" + " where num = ?";
		}
		
		
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, num);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println(num + "번 데이터 삭제 성공");
			} else {
				System.out.println(num + "번 데이터 삭제 실패");
			}
		} catch (SQLException e) {
			System.out.println("선택을 다시 해주시기 바랍니다");
		}


	}
	
	
	
	public static void main(String[] args) throws Exception  {
		
		String num;
		String tale;
		String a;
		int c;
		String table;
		String id ="";
		String password;
		String username;
		String phone;
		String email;
		double  rate;// 자산내 비중
		String  company;
		String  share; // 전체 지분	율
		String 	Evaluation;
		
		
		
		miniTestmain t = new miniTestmain();
		Scanner in = new Scanner(System.in);
		driver1();
		
		miniTestClass m = new miniTestClass();
		
		cc as = new cc();
		

		while(true) {
			System.out.println("1.로그인 \n2.회원가입");
			a= in.next();
			if(a.equals("1")) {
				System.out.println("아이디를 입력해주세요");
				id = in.next();
				System.out.println("비밀번호를 입력해주세요");
				password = in.next();
				Usertbl u = new Usertbl(id,password);
					if(t.managerlogin(u)) {
						
						System.out.println("관리자모드로 로그인하였습니다");
						c=1; break;
					} else if(t.login(u)){
						System.out.println("일반모드로 로그인 하였습니다.");
						c=2; break;
					}
					else {System.out.println("아이디 또는 비밀번호가 틀립니다.");}
				}
			else if(a.equals("2")){
				System.out.println("생성할 아이디를 입력해주세요");
				id = in.next();
				while(t.insert2(id)) {
					System.out.println("생성할 아이디를 입력해주세요");
					id = in.next();

					
				}


				System.out.println("이름을 입력해주세요");
				username = in.next();
				System.out.println("사용하실 비밀번호 입력해주세요");
				password = in.next();
				System.out.println("전화번호를 입력해주세요");
				phone = in.next();
				System.out.println("이메일을 입력해주세요");
				email = in.next();
				Usertbl u = new Usertbl(id,username,password,phone,email);
				t.join1(u);
				
				}
			}
		
		conn.close();
		as.kky();
		

		switch (c) {
		case(1):
		System.out.println("관리자모드로 로그인하였습니다");
		while (true) {
		System.out.print("기능을 입력해주세요 " 
						+ "\n1.테이블 생성 " 
						+ "\n2.백업데이터 저장하기 " 
						+ "\n3.데이터 추가하기 "
						+ "\n4.데이터 전체조회하기 "
						+ "\n5.선택적 조회 " 
						+ "\n6.데이터삭제" 
						+ "\n9.서비스종료");
			a = in.next();
			// 테이블 생성
			if (a.equals("1")) {
				driver1();
				System.out.println("생성할 테이블명을 입력해주세요");
				String b = in.next();
				t.ListCreate(b);
				conn.close();
				pstmt.close();

			}

			// 백업데이터 추가하기
			else if (a.equals("2")) {
				driver1();
				System.out.println("use.company에 백업하겠습니다");
				t.backdata();
				System.out.println("백업데이터 추가가 완료하였습니다");
				conn.close();
				pstmt.close();
			}				
//			 데이터 추가
			else if (a.equals("3")) {
				driver1();
				System.out.println("1.주식데이터 추가 \n2.게시글 작성");
				table = in.next();
			// 주식데이터 추가
				if(table.equals("1")) {
					System.out.println("기업 이름을 입력해주세요");
					company= in.next();
					System.out.println("자산내 비중을 입력해주세요");
					rate = in.nextDouble();
					System.out.println("전체 지분울을 입력해주세요");
					share = in.next();
					System.out.println("평가금액을 입력해주세요");
					Evaluation = in.next();
					stock s= new stock(company,rate,share,Evaluation);
					t.insertCompany(s);
					pstmt.close();
					conn.close();
					
			}
			// 게시글 작성
				else if(table.equals("2")) {
	

					System.out.println("게시글을 작성해주세요");
					tale = in.nextLine();
					Posttbl p = new Posttbl(id,tale);
					t.insertPosttbl(p);
					pstmt.close();
					conn.close();
					}
			}
			
//			// 전체조회
			else if (a.equals("4")) {
				driver1();
				System.out.println("1.주식데이터 전체조회 \n2.게시글 전체조회");
				table = in.next();
				t.selectAll(table);
				conn.close();
				pstmt.close();
				}
//
			// 선택적 조회
			else if (a.equals("5")) {
				driver1();
				System.out.println("1.주식데이터 전체조회 \n2.게시글 전체조회");
				table = in.next();
				System.out.println("조회할 이름을 입력해주세요");
				company = in.next();
				t.oneselect(table, company);
				conn.close();
				pstmt.close();
			}
//


//
			else if (a.equals("6")) {	
				driver1();
				System.out.println("1.주식데이터 삭제 \n2.게시글 삭제");
				table = in.next();
				t.selectAll(table);
				if(table.equals("1")) {
						System.out.println("검색어를 입력해주세요");
						company =in.next();
						t.oneselect(table,company);
						System.out.println("삭제할 번호를 입력해주세요");
						num= in.next();
						t.deleteNum(table,num);
						System.out.println("삭제가 완료되었습니다");
						conn.close();
						pstmt.close();
					}
				
					else if(table.equals("2")) {
						System.out.println("검색어를 입력해주세요");
						company =in.next();
						t.oneselect(table,company);
						System.out.println("삭제할 번호를 입력해주세요");
						num= in.next();
						t.deleteNum(table,num );	
						System.out.println("삭제가 완료되었습니다");
					}
					

			}
//
			else if (a.equals("9")) {
				System.out.println("서비스를 종료합니다");
				break;
			} else
				System.out.println("다시입력해주세요");}
				break;

				
				
				
			
		case(2):
			m.driver2();
		System.out.println("일반인 모드로 로그인하였습니다");
			while (true) {
				System.out.print("기능을 입력해주세요 " 
								+ "\n1.게시글 추가하기 "
								+ "\n2.데이터 전체조회하기 "
								+ "\n3.선택적 조회 "
								+ "\n4.게시글 수정하기"
								+ "\n9.서비스 종료"
								);
					a = in.next();
					
								
					 // 게시글 작성
					 if (a.equals("1")) {
					

						in.nextLine();
						System.out.println("게시글을 작성해주세요");
						String q = in.nextLine();
						System.out.println(q+"내용추가되었습니다");
						Posttbl p = new Posttbl(id,q);
						m.insertPosttbl(p);	
						
					}
					
					
//					// 전체조회
					else if (a.equals("2")) {
				
						System.out.println("1.주식데이터 전체조회 \n2.게시글 전체조회");
						table = in.next();
						m.selectAll(table);

						}
					// 선택적 조회
					else if (a.equals("3")) {
				
						System.out.println("1.게시글 검색 \n2.데이터 검색");
						table = in.next();
						System.out.println("조회할 이름을 입력해주세요");
						company = in.next();
						m.oneselect(table, company);

						}
					 
					else if (a.equals("4")) {
						m.selectAll("2");
						System.out.println("수정하고 싶은 게시물을 선택해주세요");
						company = in.next();
						in.nextLine();
						System.out.println("수정하고 싶은 내용을 입력해주세요");
						String post=in.nextLine();
						m.postupdate(post,id, company);

						}
					 
					else if (a.equals("9")) {
						System.out.println("서비스를 종료합니다");
						break;
					} else
						System.out.println("다시입력해주세요");}
			break;
		}
		
				
	
		
		
		
		
		
		
	}
	}





	
			




