package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
	//DB연결부
	private Connection conn;
//	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	public UserDAO() {
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
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = '"+userID+"'";
		try {
			stmt = conn.createStatement(); //SQL Injection 취약.			
			rs = stmt.executeQuery(SQL);
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // Login Success
				}else
					return 0; // Wrong Password
			}
			return -1; // ID is not exist.
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //DB ERROR
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES ('"+user.getUserID()+"','"+user.getUserPassword()+"','"+user.getUserName()+"','"+user.getUserGender()+"','"+user.getUserEmail()+"')";
		try {
			stmt = conn.createStatement();
			return stmt.executeUpdate(SQL);
		}catch(Exception e) {
		e.printStackTrace();
	}
		return -1; //DB ERROR
	}
	}
