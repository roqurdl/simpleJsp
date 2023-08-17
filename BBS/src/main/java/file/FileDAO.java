package file;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FileDAO {
	//DB연결부
		private Connection conn;
		private Statement stmt;
		private ResultSet rs;
		
		public FileDAO() {
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
			
		public String getDate() {
			String SQL = "SELECT NOW()";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) {
					return rs.getString(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return ""; //DB ERROR
		}
		
		public int getNext() {
			String SQL = "SELECT fileID FROM FILE ORDER BY fileID DESC";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				if(rs.next()) { 
					return rs.getInt(1)+1;
				}
				return 1; // 
			}catch(Exception e){
				e.printStackTrace();
			}
			return -1; //DB ERROR
		}
		
		public int upload (String fileName, String fileRealName) {
			String SQL = "INSERT INTO FILE VALUES(" + getNext() + ", '" + fileName + "', '" + fileRealName + "', '" + getDate() + "')";		
			try {
				stmt = conn.createStatement();
				return stmt.executeUpdate(SQL);
			}catch(Exception e){
				e.printStackTrace();
			}
			return -1; //DB ERROR
		}
		
		public ArrayList<FileDTO> getList (int pageNumber){
			String SQL = "SELECT * FROM FILE WHERE fileID < " + (getNext() - (pageNumber - 1) * 10) + " ORDER BY fileID DESC LIMIT 10";
			ArrayList<FileDTO> list = new ArrayList<FileDTO>();
			try {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					FileDTO file = new FileDTO();
					file.setFileID(rs.getInt(1));
					file.setFileName(rs.getString(2));
					file.setSysName(rs.getString(3));
					file.setPostDate(rs.getString(4));
					list.add(file);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		//page 기능
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM FILE WHERE fileID <"+(getNext() - (pageNumber -1)*10);
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

		
	}

