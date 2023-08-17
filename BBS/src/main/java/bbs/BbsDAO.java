package bbs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BbsDAO {
	//DB연결부
		private Connection conn;
		private Statement stmt;
		private ResultSet rs;
		private int[] check = new int[4];
		
		public BbsDAO() {
			
		for (int i = 0; i < check.length; i++) {
            check[i] = 0; // 모든 항목의 초기 값은 0으로 설정
        }
			try {
				String dbURL= "jdbc:mysql://localhost:3306/BBS";
				String dbID="root";
				String dbPassword="root";
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
			}catch(Exception e) {
			e.printStackTrace();
		}
			}
		public int[] getCheck() {
	        return check;
	    }
		//게시글 날짜
		public String getDate() {
			String SQL = "SELECT NOW()";
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) {
					return rs.getString(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return ""; //DB ERROR
		}
		//게시글 번호부여
		public int getNext() {
			String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) { 
					return Integer.parseInt(rs.getString(1))+1;
				}
				return 1; // 1st 게시물 || rs.next()==false 마지막 행을 의미, DESC로 정렬=>마지막 행은 첫 번쨰 게시물을 의미
			}catch(Exception e){
				e.printStackTrace();
			}
			return -1; //DB ERROR
		}
		
		public int write (String bbsTitle, String userID, String bbsContent) {
			//String의 경우 ''를 씌워줘야함.
			String SQL = "INSERT INTO BBS VALUES(" + getNext() + ", '" + bbsTitle + "', '" + userID + "', '" + getDate() +"', '"+bbsContent+"', 1)";
			try {
				Statement stmt = conn.createStatement();
				return stmt.executeUpdate(SQL);
			}catch(Exception e){
				e.printStackTrace();
			}
			return -1; //DB ERROR
		}
		
		public ArrayList<Bbs> getList (int pageNumber){
			String SQL = "SELECT * FROM BBS WHERE bbsID < "+(getNext() - (pageNumber-1)*10)+" AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			ArrayList<Bbs> list = new ArrayList<Bbs>();
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getString(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					//반드시 해당구문을 넣어 list에 내용을 추가해주어야한다.
					list.add(bbs);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		
		//page 기능
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM BBS WHERE bbsID < "+(getNext() - (pageNumber-1)*10)+" AND bbsAvailable = 1";
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) {
					return true;
				}
				}catch(Exception e){
				e.printStackTrace();
			}
			return false;
		}
		
		public Bbs getBbs(String bbsID) {
			String SQL = "SELECT * FROM BBS WHERE bbsID = "+bbsID;
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) { 
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getString(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		public int update(String bbsID, String bbsTitle, String bbsContent) {
			String SQL = "UPDATE BBS SET bbsTitle = '" + bbsTitle + "', bbsContent = '" + bbsContent + "' WHERE bbsID = " + bbsID;
			try {
				Statement stmt = conn.createStatement();
				return stmt.executeUpdate(SQL);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // DB ERRORS
		}
		
		public int delete(String bbsID) {
			String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = "+bbsID; //삭제를 진행해도 DB에 자료는 남음.
			try {
				Statement stmt = conn.createStatement();
				return stmt.executeUpdate(SQL);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // DB ERRORS
		}
		
		public ArrayList<Bbs> getSearch (String searchField, String searchText){
			ArrayList<Bbs> list = new ArrayList<Bbs>();
			String SQL ="SELECT * FROM BBS WHERE "+searchField;
			try {
				if(searchText != "" || searchText!= null) {
					SQL = SQL+" Like '%"+searchText+"%' ORDER BY bbsID DESC LIMIT 10";
				}
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getString(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					//반드시 해당구문을 넣어 list에 내용을 추가해주어야한다.
					list.add(bbs);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		public ArrayList<Bbs> getOrder (String columnName, int sortOrder){
			ArrayList<Bbs> list = new ArrayList<Bbs>();
				String SQL ="SELECT * FROM BBS ORDER BY "+columnName;
				if(sortOrder == 0) {
				SQL = SQL+" DESC LIMIT 10";
			}else {
				SQL = SQL+" LIMIT 10";
			}
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getString(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					//반드시 해당구문을 넣어 list에 내용을 추가해주어야한다.
					list.add(bbs);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
}

